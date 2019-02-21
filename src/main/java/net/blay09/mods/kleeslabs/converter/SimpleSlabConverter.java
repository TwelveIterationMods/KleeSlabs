package net.blay09.mods.kleeslabs.converter;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;

public class SimpleSlabConverter implements SlabConverter {

    private final Block singleSlab;

    public SimpleSlabConverter(Block singleSlab) {
        this.singleSlab = singleSlab;
    }

    @Override
    public IBlockState getSingleSlab(IBlockState state, SlabType slabType) {
        return singleSlab.getDefaultState().with(BlockStateProperties.SLAB_TYPE, slabType);
    }

}
