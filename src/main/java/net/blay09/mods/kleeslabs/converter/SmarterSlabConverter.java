package net.blay09.mods.kleeslabs.converter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IProperty;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IStringSerializable;

import java.util.Optional;

public class SmarterSlabConverter implements SlabConverter {

    private Block slabBlock;

    public SmarterSlabConverter(Block slabBlock) {
        this.slabBlock = slabBlock;
    }

    @Override
    public BlockState getSingleSlab(BlockState state, SlabType slabType) {
        BlockState newState = slabBlock.getDefaultState();
        for (IProperty<?> property : state.getProperties()) {
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
        for (IProperty<?> property : state.getProperties()) {
            if (property.getName().equals("half")) {
                IStringSerializable value = (IStringSerializable) state.get(property);
                if (value.getName().equals("full")) {
                    return true;
                }
            }
        }

        return false;
    }

    private <T extends Comparable<T>> BlockState copyProperty(BlockState sourceState, BlockState targetState, IProperty<T> property) {
        return targetState.with(property, sourceState.get(property));
    }

    private <T extends Comparable<T>> BlockState getHalfBlockState(BlockState state, IProperty<T> property, SlabType slabType) {
        Optional<T> parsedValue = Optional.empty();
        if (slabType == SlabType.BOTTOM) {
            parsedValue = property.parseValue("bottom");
        } else if (slabType == SlabType.TOP) {
            parsedValue = property.parseValue("top");
        }

        return parsedValue.map(t -> state.with(property, t)).orElse(state);

    }

}
