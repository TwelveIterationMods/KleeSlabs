package net.blay09.mods.kleeslabs;

import com.google.gson.*;
import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mod.EventBusSubscriber
public class JsonCompatLoader {

    private static final Gson gson = new Gson();
    private static final NonNullList<ItemStack> nonFoodRecipes = NonNullList.create();

    public static boolean loadCompat() {
        nonFoodRecipes.clear();
        ModContainer mod = Loader.instance().getIndexedModList().get(KleeSlabs.MOD_ID);
        return findConfigFiles() && CraftingHelper.findFiles(mod, "assets/" + KleeSlabs.MOD_ID + "/compat", (root) -> true, (root, file) -> {
            String relative = root.relativize(file).toString();
            if (!"json".equals(FilenameUtils.getExtension(file.toString())) || relative.startsWith("_")) {
                return true;
            }

            String fileName = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
            try (BufferedReader reader = Files.newBufferedReader(file)) {
                parse(reader);
            } catch (JsonParseException e) {
                KleeSlabs.logger.error("Parsing error loading compat {}", fileName, e);
                return false;
            } catch (IOException e) {
                KleeSlabs.logger.error("Couldn't read compat {}", fileName, e);
                return false;
            }
            return true;
        }, true, true);
    }

    private static boolean findConfigFiles() {
        File compatDir = new File(KleeSlabs.configDir, "KleeSlabsCompat");
        if (!compatDir.exists()) {
            KleeSlabs.logger.info("If you wish to setup additional KleeSlabs compatibility, create a folder called 'KleeSlabsCompat' in your config directory and place JSON files inside.");
            return true;
        }

        Path path = compatDir.toPath();
        try {
            Files.walk(path).filter(it -> it.toString().endsWith(".json")).forEach(it -> {
                try (Reader reader = Files.newBufferedReader(it)) {
                    parse(reader);
                } catch (IOException e) {
                    KleeSlabs.logger.error("Couldn't read compat {}", it, e);
                }
            });
        } catch (IOException e) {
            KleeSlabs.logger.error("Couldn't walk compat dir", e);
            return false;
        }

        return true;
    }

    private static final JsonObject EMPTY_OBJECT = new JsonObject();
    private static final JsonArray EMPTY_ARRAY = new JsonArray();

    public static void parse(String json) {
        parse(gson.fromJson(json, JsonObject.class));
    }

    public static void parse(Reader reader) {
        JsonObject json = JsonUtils.fromJson(gson, reader, JsonObject.class);
        if (json != null) {
            parse(json);
        }
    }

    private static void parse(JsonObject root) {
        String modId = JsonUtils.getString(root, "modid");
        if (!modId.equals("minecraft") && !Loader.isModLoaded(modId) && KleeSlabs.config.getBoolean(modId, "compat", true, "")) {
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
            KleeSlabs.registerSlabConverter(doubleSlab, converter);
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
        return Block.REGISTRY.getObject(new ResourceLocation(modId, name));
    }

}
