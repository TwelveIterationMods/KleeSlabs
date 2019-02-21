package net.blay09.mods.kleeslabs.registry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JsonUtils;
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

    private static final JsonObject EMPTY_OBJECT = new JsonObject();
    private static final JsonArray EMPTY_ARRAY = new JsonArray();

    private static void parse(Reader reader) {
        JsonObject json = JsonUtils.fromJson(gson, reader, JsonObject.class);
        if (json != null) {
            parse(json);
        }
    }

    private static boolean isCompatEnabled(String modId) {
        return true;
//        return KleeSlabs.config.getBoolean(modId, "compat", true, ""); TODO
    }

    private static void parse(JsonObject root) {
        String modId = JsonUtils.getString(root, "modid");
        if ((!modId.equals("minecraft") && !ModList.get().isLoaded(modId)) || !isCompatEnabled(modId)) {
            return;
        }

        boolean isSilent = JsonUtils.getBoolean(root, "silent", false);

        String converterName = JsonUtils.getString(root, "converter");
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

        JsonArray slabs = JsonUtils.getJsonArray(root, "slabs", EMPTY_ARRAY);
        for (JsonElement element : slabs) {
            String slabName = element.getAsString();
            Block slab = parseBlock(modId, slabName);
            if (slab != Blocks.AIR) {
                registerSlab(converterClass, slab, slab);
            } else if (!isSilent) {
                KleeSlabs.logger.error("Slab {} could not be found.", slabName);
            }
        }

        JsonObject mappedSlabs = JsonUtils.getJsonObject(root, "mapped_slabs", EMPTY_OBJECT);
        for (Map.Entry<String, JsonElement> entry : mappedSlabs.entrySet()) {
            String singleSlabName = entry.getKey();
            Block singleSlab = parseBlock(modId, singleSlabName);
            if (singleSlab == Blocks.AIR) {
                KleeSlabs.logger.error("Slab {} could not be found.", singleSlabName);
                continue;
            }

            String doubleSlabName = entry.getValue().getAsString();
            Block doubleSlab = parseBlock(modId, doubleSlabName);
            if (doubleSlab == Blocks.AIR) {
                KleeSlabs.logger.error("Slab {} could not be found.", doubleSlabName);
                continue;
            }

            registerSlab(converterClass, singleSlab, doubleSlab);
        }

        Pattern patternSearch = Pattern.compile(JsonUtils.getString(root, "pattern_search", ".+"));
        Matcher matcherSearch = patternSearch.matcher("");
        String patternReplace = JsonUtils.getString(root, "pattern_replace", "$0_double");
        JsonArray patternSlabs = JsonUtils.getJsonArray(root, "pattern_slabs", EMPTY_ARRAY);
        for (JsonElement element : patternSlabs) {
            String singleSlabName = element.getAsString();
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
