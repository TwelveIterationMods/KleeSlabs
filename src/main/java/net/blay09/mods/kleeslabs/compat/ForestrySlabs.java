package net.blay09.mods.kleeslabs.compat;

import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.converter.SmartSlabConverter;
import net.minecraft.block.Block;

public class ForestrySlabs extends CompatSlabs {

	public ForestrySlabs() {
		for(int i = 0; i <= 3; i++) {
			registerSlab("slabs." + i, "slabs.double." + i);
			registerSlab("slabs.fireproof." + i, "slabs.double.fireproof." + i);
		}
		for(int i = 0; i <= 0; i++) {
			registerSlab("slabs.vanilla.fireproof." + i, "slabs.vanilla.double.fireproof." + i);
		}
	}

	private void registerSlab(String singleSlabName, String doubleSlabName) {
		Block singleSlab = getModBlock(FORESTRY, singleSlabName);
		Block doubleSlab = getModBlock(FORESTRY, doubleSlabName);
		if(singleSlab != null && doubleSlab != null) {
			KleeSlabs.registerSlabConverter(doubleSlab, new SmartSlabConverter(singleSlab));
		}
	}

}
