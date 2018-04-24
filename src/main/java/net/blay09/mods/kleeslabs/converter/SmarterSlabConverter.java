package net.blay09.mods.kleeslabs.converter;

import com.google.common.base.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;

public class SmarterSlabConverter implements SlabConverter {

    private Block slabBlock;

    public SmarterSlabConverter(Block slabBlock) {
        this.slabBlock = slabBlock;
    }

    @Override
    public IBlockState getSingleSlab(IBlockState state, BlockSlab.EnumBlockHalf blockHalf) {
        IBlockState newState = slabBlock.getDefaultState();
        for (IProperty property : state.getPropertyKeys()) {
            if (property.getName().equals("half")) {
                newState = getHalfBlockState(newState, property, blockHalf);
            } else {
                newState = newState.withProperty(property, state.getValue(property));
            }
        }
        return newState;
    }

    @Override
    public boolean isDoubleSlab(IBlockState state) {
        for (IProperty property : state.getPropertyKeys()) {
            if (property.getName().equals("half")) {
                IStringSerializable value = (IStringSerializable) state.getValue(property);
                if (value.getName().equals("full")) {
                    return true;
                }
            }
        }

        return false;
    }

    private <T extends Comparable<T>> IBlockState getHalfBlockState(IBlockState state, IProperty<T> property, BlockSlab.EnumBlockHalf blockHalf) {
        Optional<T> opt = Optional.absent();
        if (blockHalf == BlockSlab.EnumBlockHalf.BOTTOM) {
            opt = property.parseValue("bottom");
        } else if (blockHalf == BlockSlab.EnumBlockHalf.TOP) {
            opt = property.parseValue("top");
        }

        if (opt.isPresent()) {
            return state.withProperty(property, opt.get());
        }

        return state;
    }

}
