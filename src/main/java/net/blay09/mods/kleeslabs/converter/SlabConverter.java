package net.blay09.mods.kleeslabs.converter;

import net.minecraft.block.BlockState;
import net.minecraft.state.properties.SlabType;

public interface SlabConverter {

    BlockState getSingleSlab(BlockState state, SlabType slabType);

    default boolean isDoubleSlab(BlockState state) {
        return true;
    }

}
