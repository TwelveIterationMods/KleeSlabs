package net.blay09.mods.kleeslabs.registry.json;

import com.google.gson.Gson;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.KleeSlabsConfig;
import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.blay09.mods.kleeslabs.registry.SlabRegistry;
import net.blay09.mods.kleeslabs.registry.SlabRegistryData;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.io.BufferedReader;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonCompatLoader implements ResourceManagerReloadListener {

    private static final Gson gson = new Gson();
    private static final FileToIdConverter COMPAT_JSONS = FileToIdConverter.json("kleeslabs_compat");

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        for (Map.Entry<ResourceLocation, Resource> entry : COMPAT_JSONS.listMatchingResources(resourceManager).entrySet()) {
            try (BufferedReader reader = entry.getValue().openAsReader()) {
                load(gson.fromJson(reader, JsonCompatData.class));
            } catch (Exception e) {
                KleeSlabs.logger.error("Parsing error loading KleeSlabs Data File at {}", entry.getKey(), e);
            }
        }
    }

    private static boolean isCompatEnabled(String modId) {
        return !KleeSlabsConfig.getActive().disabledCompat.contains(modId);
    }

    @SuppressWarnings("unchecked")
    private static void load(JsonCompatData data) {
        String modId = data.getModId();
        if ((!modId.equals("minecraft") && !Balm.isModLoaded(modId)) || !isCompatEnabled(modId)) {
            return;
        }

        boolean isSilent = data.isSilent();

        String converterName = data.getConverter();
        Class<? extends SlabConverter> converterClass;
        try {
            converterClass = (Class<? extends SlabConverter>) Class.forName("net.blay09.mods.kleeslabs.converter." + converterName);
        } catch (ClassNotFoundException ignored) {
            try {
                converterClass = (Class<? extends SlabConverter>) Class.forName(converterName);
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
                    SlabRegistry.registerSlab(new SlabRegistryData(converterClass, slab, slab));
                } else if (!isSilent) {
                    KleeSlabs.logger.error("Slab {}:{} could not be found.", modId, slabName);
                }
            }
        }

        Map<String, String> mappedSlabs = data.getMappedSlabs();
        if (mappedSlabs != null) {
            for (Map.Entry<String, String> entry : mappedSlabs.entrySet()) {
                String singleSlabName = entry.getKey();
                Block singleSlab = parseBlock(modId, singleSlabName);
                if (singleSlab == Blocks.AIR) {
                    KleeSlabs.logger.error("Slab {}:{} could not be found.", modId, singleSlabName);
                    continue;
                }

                String doubleSlabName = entry.getValue();
                Block doubleSlab = parseBlock(modId, doubleSlabName);
                if (doubleSlab == Blocks.AIR) {
                    KleeSlabs.logger.error("Slab {}:{} could not be found.", modId, doubleSlabName);
                    continue;
                }

                SlabRegistry.registerSlab(new SlabRegistryData(converterClass, singleSlab, doubleSlab));
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
                    KleeSlabs.logger.error("Slab {}:{} could not be found.", modId, singleSlabName);
                    continue;
                }

                Block doubleSlab = parseBlock(modId, doubleSlabName);
                if (doubleSlab == Blocks.AIR) {
                    KleeSlabs.logger.error("Slab {}:{} could not be found.", modId, doubleSlabName);
                    continue;
                }

                SlabRegistry.registerSlab(new SlabRegistryData(converterClass, singleSlab, doubleSlab));
            }
        }
    }

    private static Block parseBlock(String modId, String name) {
        int colon = name.indexOf(':');
        if (colon != -1) {
            modId = name.substring(0, colon);
            name = name.substring(colon + 1);
        }

        Block block = Balm.getRegistries().getBlock(ResourceLocation.fromNamespaceAndPath(modId, name));
        return block != null ? block : Blocks.AIR;
    }

}
