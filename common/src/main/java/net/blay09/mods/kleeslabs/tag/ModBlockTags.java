package net.blay09.mods.kleeslabs.tag;

import net.blay09.mods.kleeslabs.KleeSlabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
    public static final TagKey<Block> SLABS = create("slabs");
    public static final TagKey<Block> VERTICAL_SLABS = create("vertical_slabs");
    public static final TagKey<Block> QUARK_VERTICAL_SLABS = create("vertical_slabs/quark");
    public static final TagKey<Block> ENCHANTED_VERTICAL_SLABS = create("vertical_slabs/enchanted_vertical_slabs");
    public static final TagKey<Block> NEMOS_VERTICAL_SLABS = create("vertical_slabs/nemos_vertical_slabs");

    private static TagKey<Block> create(String path) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(KleeSlabs.MOD_ID, path));
    }
}
