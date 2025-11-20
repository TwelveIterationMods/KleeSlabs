package net.blay09.mods.kleeslabs.converter;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Optional;

public class EnchantedVerticalSlabConverter implements VerticalSlabConverter {

    @Override
    public BlockState getSingleSlab(BlockState state, LevelAccessor level, BlockPos pos, Player player, Direction direction) {
        if (direction.getAxis() == Direction.Axis.Y) {
            return state;
        }

        return locateProperty(state, "single_slab")
                .flatMap(property -> withLocateValue(state.setValue(BlockStateProperties.HORIZONTAL_FACING, direction.getOpposite()), property, "true"))
                .orElse(state);
    }

    private <T extends Comparable<T>> Optional<BlockState> withLocateValue(BlockState state, Property<T> property, String valueName) {
        return property.getValue(valueName).map(value -> state.setValue(property, value));
    }

    private Optional<Property<?>> locateProperty(BlockState state, String name) {
        for (final var property : state.getProperties()) {
            if (property.getName().equals(name)) {
                return Optional.of(property);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean isDoubleSlab(BlockState state) {
        return locateProperty(state, "single_slab")
                .map(property -> (BooleanProperty) property)
                .map(property -> !state.getValue(property))
                .orElse(false);
    }
}
