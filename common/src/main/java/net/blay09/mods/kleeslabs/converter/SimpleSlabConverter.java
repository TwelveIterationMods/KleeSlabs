package net.blay09.mods.kleeslabs.converter;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;

public class SimpleSlabConverter implements SlabConverter {

    private final Block singleSlab;

    public SimpleSlabConverter(Block singleSlab) {
        this.singleSlab = singleSlab;
    }

    @Override
    public BlockState getSingleSlab(BlockState state, Level level, BlockPos pos, Player player, SlabType slabType) {
        return singleSlab.defaultBlockState().setValue(BlockStateProperties.SLAB_TYPE, slabType);
    }

}
