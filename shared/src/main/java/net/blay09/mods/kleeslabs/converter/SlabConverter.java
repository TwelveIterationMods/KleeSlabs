

package net.blay09.mods.kleeslabs.converter;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;

public interface SlabConverter {
    BlockState getSingleSlab(BlockState state, Level level, BlockPos pos, Player player, SlabType slabType);

    default boolean isDoubleSlab(BlockState state) {
        return true;
    }

}