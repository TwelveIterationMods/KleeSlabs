package net.blay09.mods.kleeslabs.fabric.datagen;

import net.blay09.mods.kleeslabs.tag.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider<Block> {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BLOCK, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(ModBlockTags.SLABS).addOptionalTag(BlockTags.SLABS);
        getOrCreateTagBuilder(ModBlockTags.VERTICAL_SLABS).addOptionalTag(ModBlockTags.QUARK_VERTICAL_SLABS);
        getOrCreateTagBuilder(ModBlockTags.QUARK_VERTICAL_SLABS)
                .addOptional(quark("acacia_vertical_slab"))
                .addOptional(quark("ancient_planks_vertical_slab"))
                .addOptional(quark("andesite_bricks_vertical_slab"))
                .addOptional(quark("andesite_vertical_slab"))
                .addOptional(quark("azalea_planks_vertical_slab"))
                .addOptional(quark("bamboo_mosaic_vertical_slab"))
                .addOptional(quark("bamboo_vertical_slab"))
                .addOptional(quark("birch_vertical_slab"))
                .addOptional(quark("black_shingles_vertical_slab"))
                .addOptional(quark("blackstone_bricks_vertical_slab"))
                .addOptional(quark("blackstone_vertical_slab"))
                .addOptional(quark("blossom_planks_vertical_slab"))
                .addOptional(quark("blue_nether_bricks_vertical_slab"))
                .addOptional(quark("blue_shingles_vertical_slab"))
                .addOptional(quark("brick_vertical_slab"))
                .addOptional(quark("brown_shingles_vertical_slab"))
                .addOptional(quark("calcite_bricks_vertical_slab"))
                .addOptional(quark("calcite_vertical_slab"))
                .addOptional(quark("cherry_vertical_slab"))
                .addOptional(quark("cobbled_deepslate_vertical_slab"))
                .addOptional(quark("cobblestone_bricks_vertical_slab"))
                .addOptional(quark("cobblestone_vertical_slab"))
                .addOptional(quark("crimson_vertical_slab"))
                .addOptional(quark("cut_copper_vertical_slab"))
                .addOptional(quark("cut_red_sandstone_vertical_slab"))
                .addOptional(quark("cut_sandstone_vertical_slab"))
                .addOptional(quark("cut_soul_sandstone_vertical_slab"))
                .addOptional(quark("cyan_shingles_vertical_slab"))
                .addOptional(quark("dark_oak_vertical_slab"))
                .addOptional(quark("dark_prismarine_vertical_slab"))
                .addOptional(quark("deepslate_brick_vertical_slab"))
                .addOptional(quark("deepslate_tile_vertical_slab"))
                .addOptional(quark("diorite_bricks_vertical_slab"))
                .addOptional(quark("diorite_vertical_slab"))
                .addOptional(quark("dirt_bricks_vertical_slab"))
                .addOptional(quark("dripstone_block_vertical_slab"))
                .addOptional(quark("dripstone_bricks_vertical_slab"))
                .addOptional(quark("duskbound_block_vertical_slab"))
                .addOptional(quark("end_stone_brick_vertical_slab"))
                .addOptional(quark("exposed_cut_copper_vertical_slab"))
                .addOptional(quark("granite_bricks_vertical_slab"))
                .addOptional(quark("granite_vertical_slab"))
                .addOptional(quark("gray_shingles_vertical_slab"))
                .addOptional(quark("green_shingles_vertical_slab"))
                .addOptional(quark("iron_plate_vertical_slab"))
                .addOptional(quark("jasper_bricks_vertical_slab"))
                .addOptional(quark("jasper_vertical_slab"))
                .addOptional(quark("jungle_vertical_slab"))
                .addOptional(quark("light_blue_shingles_vertical_slab"))
                .addOptional(quark("light_gray_shingles_vertical_slab"))
                .addOptional(quark("lime_shingles_vertical_slab"))
                .addOptional(quark("limestone_bricks_vertical_slab"))
                .addOptional(quark("limestone_vertical_slab"))
                .addOptional(quark("magenta_shingles_vertical_slab"))
                .addOptional(quark("mangrove_vertical_slab"))
                .addOptional(quark("midori_block_vertical_slab"))
                .addOptional(quark("mossy_cobblestone_bricks_vertical_slab"))
                .addOptional(quark("mossy_cobblestone_vertical_slab"))
                .addOptional(quark("mossy_stone_brick_vertical_slab"))
                .addOptional(quark("mud_brick_vertical_slab"))
                .addOptional(quark("myalite_bricks_vertical_slab"))
                .addOptional(quark("myalite_vertical_slab"))
                .addOptional(quark("nether_brick_vertical_slab"))
                .addOptional(quark("netherrack_bricks_vertical_slab"))
                .addOptional(quark("oak_vertical_slab"))
                .addOptional(quark("orange_shingles_vertical_slab"))
                .addOptional(quark("oxidized_cut_copper_vertical_slab"))
                .addOptional(quark("permafrost_bricks_vertical_slab"))
                .addOptional(quark("permafrost_vertical_slab"))
                .addOptional(quark("pink_shingles_vertical_slab"))
                .addOptional(quark("polished_andesite_vertical_slab"))
                .addOptional(quark("polished_blackstone_brick_vertical_slab"))
                .addOptional(quark("polished_blackstone_vertical_slab"))
                .addOptional(quark("polished_calcite_vertical_slab"))
                .addOptional(quark("polished_deepslate_vertical_slab"))
                .addOptional(quark("polished_diorite_vertical_slab"))
                .addOptional(quark("polished_dripstone_vertical_slab"))
                .addOptional(quark("polished_granite_vertical_slab"))
                .addOptional(quark("polished_jasper_vertical_slab"))
                .addOptional(quark("polished_limestone_vertical_slab"))
                .addOptional(quark("polished_myalite_vertical_slab"))
                .addOptional(quark("polished_shale_vertical_slab"))
                .addOptional(quark("polished_tuff_vertical_slab"))
                .addOptional(quark("prismarine_brick_vertical_slab"))
                .addOptional(quark("prismarine_vertical_slab"))
                .addOptional(quark("purple_shingles_vertical_slab"))
                .addOptional(quark("purpur_vertical_slab"))
                .addOptional(quark("quartz_vertical_slab"))
                .addOptional(quark("raw_copper_bricks_vertical_slab"))
                .addOptional(quark("raw_gold_bricks_vertical_slab"))
                .addOptional(quark("raw_iron_bricks_vertical_slab"))
                .addOptional(quark("red_nether_brick_vertical_slab"))
                .addOptional(quark("red_sandstone_bricks_vertical_slab"))
                .addOptional(quark("red_sandstone_vertical_slab"))
                .addOptional(quark("red_shingles_vertical_slab"))
                .addOptional(quark("rusty_iron_plate_vertical_slab"))
                .addOptional(quark("sandstone_bricks_vertical_slab"))
                .addOptional(quark("sandstone_vertical_slab"))
                .addOptional(quark("shale_bricks_vertical_slab"))
                .addOptional(quark("shale_vertical_slab"))
                .addOptional(quark("shingles_vertical_slab"))
                .addOptional(quark("smooth_quartz_vertical_slab"))
                .addOptional(quark("smooth_red_sandstone_vertical_slab"))
                .addOptional(quark("smooth_sandstone_vertical_slab"))
                .addOptional(quark("smooth_soul_sandstone_vertical_slab"))
                .addOptional(quark("smooth_stone_vertical_slab"))
                .addOptional(quark("soul_sandstone_bricks_vertical_slab"))
                .addOptional(quark("soul_sandstone_vertical_slab"))
                .addOptional(quark("spruce_vertical_slab"))
                .addOptional(quark("stone_brick_vertical_slab"))
                .addOptional(quark("stone_vertical_slab"))
                .addOptional(quark("thatch_vertical_slab"))
                .addOptional(quark("tuff_bricks_vertical_slab"))
                .addOptional(quark("tuff_vertical_slab"))
                .addOptional(quark("warped_vertical_slab"))
                .addOptional(quark("waxed_cut_copper_vertical_slab"))
                .addOptional(quark("waxed_exposed_cut_copper_vertical_slab"))
                .addOptional(quark("waxed_oxidized_cut_copper_vertical_slab"))
                .addOptional(quark("waxed_weathered_cut_copper_vertical_slab"))
                .addOptional(quark("weathered_cut_copper_vertical_slab"))
                .addOptional(quark("white_shingles_vertical_slab"))
                .addOptional(quark("yellow_shingles_vertical_slab"));
    }

    private static ResourceLocation quark(String path) {
        return ResourceLocation.fromNamespaceAndPath("quark", path);
    }

}
