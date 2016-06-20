package net.blay09.mods.kleeslabs.converter;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;

public interface SlabConverter {

	IBlockState getSingleSlab(IBlockState state, BlockSlab.EnumBlockHalf blockHalf);

}
