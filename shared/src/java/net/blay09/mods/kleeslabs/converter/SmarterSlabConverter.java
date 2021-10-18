package net.blay09.mods.kleeslabs.converter;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;

import java.util.Optional;

public class SmarterSlabConverter implements SlabConverter {

    private Block slabBlock;

    public SmarterSlabConverter(Block slabBlock) {
        this.slabBlock = slabBlock;
    }

    @Override
    public BlockState getSingleSlab(BlockState state, SlabType slabType) {
        BlockState newState = slabBlock.defaultBlockState();
        for (Property<?> property : state.getProperties()) {
            if (property.getName().equals("half")) {
                newState = getHalfBlockState(newState, property, slabType);
            } else {
                newState = copyProperty(state, newState, property);
            }
        }

        return newState;
    }

    @Override
    public boolean isDoubleSlab(BlockState state) {
        for (Property<?> property : state.getProperties()) {
            if (property.getName().equals("half")) {
                StringRepresentable value = (StringRepresentable) state.getValue(property);
                if (value.getSerializedName().equals("full")) {
                    return true;
                }
            }
        }

        return false;
    }

    private <T extends Comparable<T>> BlockState copyProperty(BlockState sourceState, BlockState targetState, Property<T> property) {
        return targetState.setValue(property, sourceState.getValue(property));
    }

    private <T extends Comparable<T>> BlockState getHalfBlockState(BlockState state, Property<T> property, SlabType slabType) {
        Optional<T> parsedValue = Optional.empty();
        if (slabType == SlabType.BOTTOM) {
            parsedValue = property.getValue("bottom");
        } else if (slabType == SlabType.TOP) {
            parsedValue = property.getValue("top");
        }

        return parsedValue.map(t -> state.setValue(property, t)).orElse(state);

    }

}
