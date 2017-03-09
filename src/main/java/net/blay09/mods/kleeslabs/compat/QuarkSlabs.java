package net.blay09.mods.kleeslabs.compat;

import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.converter.SimpleSlabConverter;
import net.minecraft.block.Block;

public class QuarkSlabs extends CompatSlabs {

	public QuarkSlabs() {
		registerSlab("biotite_slab");
		registerSlab("snow_bricks_slab");
		registerSlab("sandy_bricks_slab");
		registerSlab("charred_nether_brick_slab");
		registerSlab("sandstone_bricks_slab");
		registerSlab("sandstone_smooth_slab");
		registerSlab("red_sandstone_bricks_slab");
		registerSlab("red_sandstone_smooth_slab");
		registerSlab("end_bricks_slab");
		registerSlab("prismarine_slab");
		registerSlab("prismarine_dark_slab");
		registerSlab("prismarine_bricks_slab");
		registerSlab("stone_diorite_slab");
		registerSlab("stone_diorite_bricks_slab");
		registerSlab("stone_granite_slab");
		registerSlab("stone_granite_bricks_slab");
		registerSlab("stone_andesite_slab");
		registerSlab("stone_andesite_bricks_slab");
		registerSlab("stone_basalt_slab");
		registerSlab("stone_basalt_bricks_slab");
		registerSlab("stained_clay_tiles_gray_slab");
		registerSlab("stained_clay_tiles_magenta_slab");
		registerSlab("stained_clay_tiles_cyan_slab");
		registerSlab("stained_clay_tiles_blue_slab");
		registerSlab("stained_clay_tiles_orange_slab");
		registerSlab("stained_clay_tiles_red_slab");
		registerSlab("stained_clay_tiles_yellow_slab");
		registerSlab("stained_clay_tiles_light_blue_slab");
		registerSlab("stained_clay_tiles_purple_slab");
		registerSlab("stained_clay_tiles_silver_slab");
		registerSlab("stained_clay_tiles_lime_slab");
		registerSlab("stained_clay_tiles_green_slab");
		registerSlab("stained_clay_tiles_black_slab");
		registerSlab("stained_clay_tiles_brown_slab");
		registerSlab("stained_clay_tiles_white_slab");
		registerSlab("stained_clay_tiles_pink_slab");
		registerSlab("reed_block_slab");
		registerSlab("thatch_slab");
	}

	private void registerSlab(String singleSlabName) {
		registerSlab(singleSlabName, singleSlabName + "_double");
	}

	private void registerSlab(String singleSlabName, String doubleSlabName) {
		Block singleSlab = getModBlock(QUARK, singleSlabName);
		Block doubleSlab = getModBlock(QUARK, doubleSlabName);
		if(singleSlab != null && doubleSlab != null) {
			KleeSlabs.registerSlabConverter(doubleSlab, new SimpleSlabConverter(singleSlab));
		}
	}

}
