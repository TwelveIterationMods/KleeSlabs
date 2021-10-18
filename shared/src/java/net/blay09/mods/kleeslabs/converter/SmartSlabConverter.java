package net.blay09.mods.kleeslabs.converter;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;

public class SmartSlabConverter implements SlabConverter {

    private final Block singleSlab;

    public SmartSlabConverter(Block singleSlab) {
        this.singleSlab = singleSlab;
    }

    @Override
    public BlockState getSingleSlab(BlockState state, SlabType slabType) {
        BlockState newState = singleSlab.defaultBlockState();
        for (Property<?> property : state.getProperties()) {
            if (newState.getProperties().contains(property)) {
                newState = copyProperty(state, newState, property);
            }
        }
        return newState.setValue(BlockStateProperties.SLAB_TYPE, slabType);
    }

    @Override
    public boolean isDoubleSlab(BlockState state) {
        return state.getValue(BlockStateProperties.SLAB_TYPE) == SlabType.DOUBLE;
    }

    private <T extends Comparable<T>> BlockState copyProperty(BlockState sourceState, BlockState targetState, Property<T> property) {
        return targetState.setValue(property, sourceState.getValue(property));
    }

}
