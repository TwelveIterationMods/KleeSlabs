package net.blay09.mods.kleeslabs.converter;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Optional;

public class NemosVerticalSlabConverter implements VerticalSlabConverter {

    @Override
    public BlockState getSingleSlab(BlockState state, Level level, BlockPos pos, Player player, Direction direction) {
        if (direction.getAxis() == Direction.Axis.Y) {
            return state;
        }

        return locateProperty(state, "type")
                .flatMap(property -> withLocateValue(state, property, getVerticalSlabType(direction)))
                .orElse(state);
    }

    private static String getVerticalSlabType(Direction direction) {
        return switch (direction) {
            case SOUTH -> "front";
            case NORTH -> "back";
            case WEST -> "right";
            case EAST -> "left";
            default -> throw new IllegalArgumentException();
        };
    }

    private <T extends Comparable<T>> Optional<BlockState> withLocateValue(BlockState state, Property<T> property, String valueName) {
        return property.getValue(valueName).map(value -> state.setValue(property, value));
    }

    private Optional<Property<?>> locateProperty(BlockState state, String name) {
        for (final var property : state.getProperties()) {
            if (property.getName().equals(name) && StringRepresentable.class.isAssignableFrom(property.getValueClass())) {
                return Optional.of(property);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean isDoubleSlab(BlockState state) {
        return locateProperty(state, "type").map(state::getValue)
                .map(value -> ((StringRepresentable) value).getSerializedName())
                .map(name -> name.equals("double"))
                .orElse(false);
    }
}
