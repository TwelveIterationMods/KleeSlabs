package net.blay09.mods.kleeslabs.registry;

import com.google.gson.Gson;
import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mod.EventBusSubscriber
public class JsonCompatLoader implements ISelectiveResourceReloadListener {

    private static final Gson gson = new Gson();

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        for (ResourceLocation resourceLocation : resourceManager.getAllResourceLocations("kleeslabs_compat", it -> it.endsWith(".json"))) {
            try (IResource resource = resourceManager.getResource(resourceLocation)) {
                InputStreamReader reader = new InputStreamReader(resource.getInputStream());
                parse(reader);
            } catch (IOException e) {
                KleeSlabs.logger.error("Parsing error loading KleeSlabs Data File at {}", resourceLocation, e);
            }
        }
    }

    private static void parse(Reader reader) {
        load(gson.fromJson(reader, JsonCompatDataSlab.class));
    }

    private static boolean isCompatEnabled(String modId) {
        return true;
//        return KleeSlabs.config.getBoolean(modId, "compat", true, ""); TODO 1.14 Add disabledCompat array field
    }

    private static void load(JsonCompatDataSlab data) {
        String modId = data.getModId();
        if ((!modId.equals("minecraft") && !ModList.get().isLoaded(modId)) || !isCompatEnabled(modId)) {
            return;
        }

        boolean isSilent = data.isSilent();

        String converterName = data.getConverter();
        Class<?> converterClass;
        try {
            converterClass = Class.forName("net.blay09.mods.kleeslabs.converter." + converterName);
        } catch (ClassNotFoundException ignored) {
            try {
                converterClass = Class.forName(converterName);
            } catch (ClassNotFoundException e) {
                KleeSlabs.logger.error("Slab converter class was not found: {}", converterName);
                return;
            }
        }

        if (!SlabConverter.class.isAssignableFrom(converterClass)) {
            KleeSlabs.logger.error("Slab converter class was not found: {}", converterName);
            return;
        }

        Set<String> slabs = data.getSlabs();
        if (slabs != null) {
            for (String slabName : slabs) {
                Block slab = parseBlock(modId, slabName);
                if (slab != Blocks.AIR) {
                    registerSlab(converterClass, slab, slab);
                } else if (!isSilent) {
                    KleeSlabs.logger.error("Slab {} could not be found.", slabName);
                }
            }
        }

        Map<String, String> mappedSlabs = data.getMappedSlabs();
        if (mappedSlabs != null) {
            for (Map.Entry<String, String> entry : mappedSlabs.entrySet()) {
                String singleSlabName = entry.getKey();
                Block singleSlab = parseBlock(modId, singleSlabName);
                if (singleSlab == Blocks.AIR) {
                    KleeSlabs.logger.error("Slab {} could not be found.", singleSlabName);
                    continue;
                }

                String doubleSlabName = entry.getValue();
                Block doubleSlab = parseBlock(modId, doubleSlabName);
                if (doubleSlab == Blocks.AIR) {
                    KleeSlabs.logger.error("Slab {} could not be found.", doubleSlabName);
                    continue;
                }

                registerSlab(converterClass, singleSlab, doubleSlab);
            }
        }

        String pattern = data.getPatternSearch() != null ? data.getPatternSearch() : ".+";
        Pattern patternSearch = Pattern.compile(pattern);
        Matcher matcherSearch = patternSearch.matcher("");
        String patternReplace = data.getPatternReplace() != null ? data.getPatternReplace() : "$0_double";
        Set<String> patternSlabs = data.getPatternSlabs();
        if (patternSlabs != null) {
            for (String singleSlabName : patternSlabs) {
                matcherSearch.reset(singleSlabName);
                String doubleSlabName = matcherSearch.replaceFirst(patternReplace);
                Block singleSlab = parseBlock(modId, singleSlabName);
                if (singleSlab == Blocks.AIR) {
                    KleeSlabs.logger.error("Slab {} could not be found.", singleSlabName);
                    continue;
                }

                Block doubleSlab = parseBlock(modId, doubleSlabName);
                if (doubleSlab == Blocks.AIR) {
                    KleeSlabs.logger.error("Slab {} could not be found.", doubleSlabName);
                    continue;
                }

                registerSlab(converterClass, singleSlab, doubleSlab);
            }
        }
    }

    private static void registerSlab(Class<?> converterClass, Block singleSlab, Block doubleSlab) {
        try {
            Constructor constructor = converterClass.getConstructor(Block.class);
            SlabConverter converter = (SlabConverter) constructor.newInstance(singleSlab);
            SlabRegistry.registerSlabConverter(doubleSlab, converter);
        } catch (NoSuchMethodException e) {
            KleeSlabs.logger.error("Slab converter class does not have a constructor that takes a Block argument: {}", converterClass);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            KleeSlabs.logger.error("Slab converter class constructor invocation failed: {}", converterClass, e);
        }
    }

    private static Block parseBlock(String modId, String name) {
        int colon = name.indexOf(':');
        if (colon != -1) {
            modId = name.substring(0, colon);
            name = name.substring(colon + 1);
        }

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(modId, name));
        return block != null ? block : Blocks.AIR;
    }

}
