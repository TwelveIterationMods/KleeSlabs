package net.blay09.mods.kleeslabs.tag;

import net.blay09.mods.kleeslabs.KleeSlabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
    public static final TagKey<Block> SLABS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(KleeSlabs.MOD_ID, "slabs"));
    public static final TagKey<Block> VERTICAL_SLABS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(KleeSlabs.MOD_ID, "vertical_slabs"));
    public static final TagKey<Block> QUARK_VERTICAL_SLABS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(KleeSlabs.MOD_ID, "vertical_slabs/quark"));
}
