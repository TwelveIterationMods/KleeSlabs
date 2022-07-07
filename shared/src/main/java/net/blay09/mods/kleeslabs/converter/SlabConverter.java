package net.blay09.mods.kleeslabs.converter;

import net.blay09.mods.balm.api.event.BreakBlockEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;

public interface SlabConverter {

    BlockState getSingleSlab(BlockState state, SlabType slabType);

    default BlockState getSingleSlab(BreakBlockEvent event, SlabType slabType) {
        return getSingleSlab(event.getState(), slabType);
    }

    default boolean isDoubleSlab(BlockState state) {
        return true;
    }

}