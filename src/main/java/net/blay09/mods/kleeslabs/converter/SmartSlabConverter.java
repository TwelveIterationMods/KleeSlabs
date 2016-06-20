package net.blay09.mods.kleeslabs.converter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

public class SmartSlabConverter implements SlabConverter {

	private final Block singleSlab;

	public SmartSlabConverter(Block singleSlab) {
		this.singleSlab = singleSlab;
	}

	@Override
	public IBlockState getSingleSlab(IBlockState state, BlockSlab.EnumBlockHalf blockHalf) {
		IBlockState newState = singleSlab.getDefaultState();
		for(IProperty property : state.getPropertyNames()) {
			if(newState.getPropertyNames().contains(property)) {
				newState = newState.withProperty(property, state.getValue(property));
			}
		}
		return newState.withProperty(BlockSlab.HALF, blockHalf);
	}

}
