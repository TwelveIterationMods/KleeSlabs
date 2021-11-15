package net.blay09.mods.kleeslabs.registry;

import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlabRegistry {
    private static final List<SlabRegistryData> slabRegistry = new ArrayList<>();
    private static final Map<Block, SlabConverter> slabMap = new HashMap<>();

    public static void registerSlabConverter(Block doubleSlab, SlabConverter converter) {
        slabMap.put(doubleSlab, converter);
    }

    public static void registerSlab(SlabRegistryData data) {
        try {
            Constructor<?> constructor = data.getConverterClass().getConstructor(Block.class);
            SlabConverter converter = (SlabConverter) constructor.newInstance(data.getSingleSlab());
            registerSlabConverter(data.getDoubleSlab(), converter);
            slabRegistry.add(data);
        } catch (NoSuchMethodException e) {
            KleeSlabs.logger.error("Slab converter class does not have a constructor that takes a Block argument: {}", data.getConverterClass());
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

    public static void clearRegistry() {
        slabRegistry.clear();
        slabMap.clear();
    }
}
