package net.blay09.mods.kleeslabs.converter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;

public abstract class VanillaSlabConverter implements SlabConverter {

	private final Block singleSlab;

	public VanillaSlabConverter(Block singleSlab) {
		this.singleSlab = singleSlab;
	}

	@Override
	public IBlockState getSingleSlab(IBlockState state, BlockSlab.EnumBlockHalf blockHalf) {
		return withVariant(state, singleSlab.getDefaultState().withProperty(BlockSlab.HALF, blockHalf));
	}

	public abstract IBlockState withVariant(IBlockState state, IBlockState newState);

}
