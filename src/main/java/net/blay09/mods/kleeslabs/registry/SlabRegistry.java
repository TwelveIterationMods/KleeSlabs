package net.blay09.mods.kleeslabs.registry;

import com.google.common.collect.Maps;
import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.minecraft.block.Block;

import javax.annotation.Nullable;
import java.util.Map;

public class SlabRegistry {
    private static final Map<Block, SlabConverter> slabMap = Maps.newHashMap();

    public static void registerSlabConverter(Block doubleSlab, SlabConverter converter) {
        slabMap.put(doubleSlab, converter);
    }

    @Nullable
    public static SlabConverter getSlabConverter(Block block) {
        return slabMap.get(block);
    }
}
