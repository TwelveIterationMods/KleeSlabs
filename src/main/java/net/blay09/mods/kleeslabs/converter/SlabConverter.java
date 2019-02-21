package net.blay09.mods.kleeslabs.converter;

import net.minecraft.block.state.IBlockState;
import net.minecraft.state.properties.SlabType;

public interface SlabConverter {

	IBlockState getSingleSlab(IBlockState state, SlabType slabType);

	default boolean isDoubleSlab(IBlockState state) {
		return true;
	}

}
