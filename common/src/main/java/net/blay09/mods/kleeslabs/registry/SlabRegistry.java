package net.blay09.mods.kleeslabs.registry;

import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SlabRegistry {
    private static final Map<TagKey<Block>, SlabConverter> converters = new HashMap<>();

    public static void registerSlabConverter(TagKey<Block> tag, SlabConverter converter) {
        converters.put(tag, converter);
    }

    public static Optional<SlabConverter> getSlabConverter(BlockState block) {
        for (final var entry : converters.entrySet()) {
            if (block.is(entry.getKey())) {
                return Optional.of(entry.getValue());
            }
        }
        return Optional.empty();
    }
}
