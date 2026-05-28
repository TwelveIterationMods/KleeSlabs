package net.blay09.mods.kleeslabs.registry;

import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SlabRegistry {
    private static final List<SlabRegistryData> slabRegistry = new ArrayList<>();
    private static final Map<Block, SlabConverter> slabMap = new HashMap<>();
    private static final Map<TagKey<Block>, SlabConverter> tagMap = new LinkedHashMap<>();

    public static void registerSlabConverter(Block doubleSlab, SlabConverter converter) {
        slabMap.put(doubleSlab, converter);
    }

    public static void registerSlabConverter(TagKey<Block> tag, SlabConverter converter) {
        tagMap.put(tag, converter);
    }

    public static void registerSlab(SlabRegistryData data) {
        try {
            Constructor<?> constructor = data.getConverterClass().getConstructor();
            final var converter = (SlabConverter) constructor.newInstance();
            registerSlabConverter(data.getDoubleSlab(), converter);
            slabRegistry.add(data);
        } catch (NoSuchMethodException e) {
            KleeSlabs.logger.error("Slab converter class does not have a no-arg constructor: {}", data.getConverterClass());
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            KleeSlabs.logger.error("Slab converter class constructor invocation failed: {}", data.getConverterClass(), e);
        }
    }

    public static List<SlabRegistryData> getSlabEntries() {
        return slabRegistry;
    }

    @Nullable
    public static SlabConverter getSlabConverter(Block block) {
        return slabMap.get(block);
    }

    @Nullable
    public static SlabConverter getSlabConverter(BlockState state) {
        final var blockConverter = slabMap.get(state.getBlock());
        if (blockConverter != null) {
            return blockConverter;
        }

        for (final var entry : tagMap.entrySet()) {
            if (state.is(entry.getKey())) {
                return entry.getValue();
            }
        }

        return null;
    }

    public static void clearRegistry() {
        slabRegistry.clear();
        slabMap.clear();
    }
}
