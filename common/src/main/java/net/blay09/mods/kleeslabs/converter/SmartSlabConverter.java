package net.blay09.mods.kleeslabs.converter;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;

@Deprecated
public class SmartSlabConverter implements HorizontalSlabConverter {
    @Override
    public BlockState getSingleSlab(BlockState state, Level level, BlockPos pos, Player player, SlabType slabType) {
        BlockState newState = state.getBlock().defaultBlockState();
        for (Property<?> property : state.getProperties()) {
            if (newState.getProperties().contains(property)) {
                newState = copyProperty(state, newState, property);
            }
        }
        if (newState.hasProperty(BlockStateProperties.SLAB_TYPE)) {
            return newState.setValue(BlockStateProperties.SLAB_TYPE, slabType);
        }
        return newState;
    }

    @Override
    public boolean isDoubleSlab(BlockState state) {
        return state.hasProperty(BlockStateProperties.SLAB_TYPE) && state.getValue(BlockStateProperties.SLAB_TYPE) == SlabType.DOUBLE;
    }

    private <T extends Comparable<T>> BlockState copyProperty(BlockState sourceState, BlockState targetState, Property<T> property) {
        return targetState.setValue(property, sourceState.getValue(property));
    }

}
