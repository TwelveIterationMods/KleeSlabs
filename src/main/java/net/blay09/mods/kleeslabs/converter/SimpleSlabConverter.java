package net.blay09.mods.kleeslabs.converter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;

public class SimpleSlabConverter implements SlabConverter {

	private final Block singleSlab;

	public SimpleSlabConverter(Block singleSlab) {
		this.singleSlab = singleSlab;
	}

	@Override
	public IBlockState getSingleSlab(IBlockState state, BlockSlab.EnumBlockHalf blockHalf) {
		return singleSlab.getDefaultState().withProperty(BlockSlab.HALF, blockHalf);
	}

}
