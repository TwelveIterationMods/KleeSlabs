package net.blay09.mods.kleeslabs.converter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;

public class SmartSlabConverter implements SlabConverter {

    private final Block singleSlab;

    public SmartSlabConverter(Block singleSlab) {
        this.singleSlab = singleSlab;
    }

    @Override
    public BlockState getSingleSlab(BlockState state, SlabType slabType) {
        BlockState newState = singleSlab.getDefaultState();
        for (IProperty<?> property : state.getProperties()) {
            if (newState.getProperties().contains(property)) {
                newState = copyProperty(state, newState, property);
            }
        }
        return newState.with(BlockStateProperties.SLAB_TYPE, slabType);
    }

    @Override
    public boolean isDoubleSlab(BlockState state) {
        return state.get(BlockStateProperties.SLAB_TYPE) == SlabType.DOUBLE;
    }

    private <T extends Comparable<T>> BlockState copyProperty(BlockState sourceState, BlockState targetState, IProperty<T> property) {
        return targetState.with(property, sourceState.get(property));
    }

}
