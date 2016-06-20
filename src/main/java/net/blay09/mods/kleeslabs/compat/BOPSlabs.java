package net.blay09.mods.kleeslabs.compat;

import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.converter.SmartSlabConverter;
import net.minecraft.block.Block;

public class BOPSlabs extends CompatSlabs {

	public BOPSlabs() {
		for(int i = 0; i <= 1; i++) {
			registerSlab("wood_slab_" + i, "double_wood_slab_" + i);
		}
	}

	private void registerSlab(String singleSlabName, String doubleSlabName) {
		Block singleSlab = getModBlock(BIOMES_O_PLENTY, singleSlabName);
		Block doubleSlab = getModBlock(BIOMES_O_PLENTY, doubleSlabName);
		if(singleSlab != null && doubleSlab != null) {
			KleeSlabs.registerSlabConverter(doubleSlab, new SmartSlabConverter(singleSlab));
		}
	}

}
