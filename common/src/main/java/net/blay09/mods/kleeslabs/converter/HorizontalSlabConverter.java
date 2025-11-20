package net.blay09.mods.kleeslabs.converter;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;

public interface HorizontalSlabConverter extends SlabConverter {
    BlockState getSingleSlab(BlockState state, LevelAccessor level, BlockPos pos, Player player, SlabType slabType);
}
