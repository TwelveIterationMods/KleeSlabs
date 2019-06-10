package net.blay09.mods.kleeslabs.converter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;

public class SimpleSlabConverter implements SlabConverter {

    private final Block singleSlab;

    public SimpleSlabConverter(Block singleSlab) {
        this.singleSlab = singleSlab;
    }

    @Override
    public BlockState getSingleSlab(BlockState state, SlabType slabType) {
        return singleSlab.getDefaultState().with(BlockStateProperties.SLAB_TYPE, slabType);
    }

}
