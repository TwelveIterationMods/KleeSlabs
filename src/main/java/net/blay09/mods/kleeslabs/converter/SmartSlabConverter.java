package net.blay09.mods.kleeslabs.converter;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.state.IProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;

public class SmartSlabConverter implements SlabConverter {

    private final Block singleSlab;

    public SmartSlabConverter(Block singleSlab) {
        this.singleSlab = singleSlab;
    }

    @Override
    public IBlockState getSingleSlab(IBlockState state, SlabType slabType) {
        IBlockState newState = singleSlab.getDefaultState();
        for (IProperty<?> property : state.getProperties()) {
            if (newState.getProperties().contains(property)) {
                newState = copyProperty(state, newState, property);
            }
        }
        return newState.with(BlockStateProperties.SLAB_TYPE, slabType);
    }

    private <T extends Comparable<T>> IBlockState copyProperty(IBlockState sourceState, IBlockState targetState, IProperty<T> property) {
        return targetState.with(property, sourceState.get(property));
    }

}
