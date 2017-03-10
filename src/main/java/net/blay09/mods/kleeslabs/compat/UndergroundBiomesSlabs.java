package net.blay09.mods.kleeslabs.compat;

import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.converter.SmartSlabConverter;
import net.minecraft.block.Block;

public class UndergroundBiomesSlabs extends CompatSlabs {

	public UndergroundBiomesSlabs() {
		registerSlab("igneous_stone_halfslab", "igneous_stone_fullslab");
		registerSlab("igneous_cobble_halfslab", "igneous_cobble_fullslab");
		registerSlab("igneous_brick_halfslab", "igneous_brick_fullslab");
		registerSlab("metamorphic_stone_halfslab", "metamorphic_stone_fullslab");
		registerSlab("metamorphic_cobble_halfslab", "metamorphic_cobble_fullslab");
		registerSlab("metamorphic_brick_halfslab", "metamorphic_brick_fullslab");
		registerSlab("sedimentary_stone_halfslab", "sedimentary_stone_fullslab");
	}

	private void registerSlab(String singleSlabName, String doubleSlabName) {
		Block singleSlab = getModBlock(UNDERGROUNDBIOMES, singleSlabName);
		Block doubleSlab = getModBlock(UNDERGROUNDBIOMES, doubleSlabName);
		if (singleSlab != null && doubleSlab != null) {
			KleeSlabs.registerSlabConverter(doubleSlab, new SmartSlabConverter(singleSlab));
		}
	}

}
