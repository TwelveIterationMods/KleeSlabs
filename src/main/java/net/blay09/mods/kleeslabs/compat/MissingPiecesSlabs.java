package net.blay09.mods.kleeslabs.compat;

import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.converter.SmartSlabConverter;
import net.minecraft.block.Block;

public class MissingPiecesSlabs extends CompatSlabs {

	public MissingPiecesSlabs() {
		isSilent = true;
		for(int i = 1; i <= 12; i++) {
			registerSlab("stone_patt" + i + "_slab", "stone_patt" + i + "_slab_double");
		}
	}

	private void registerSlab(String singleSlabName, String doubleSlabName) {
		Block singleSlab = getModBlock(MISSING_PIECES, singleSlabName);
		Block doubleSlab = getModBlock(MISSING_PIECES, doubleSlabName);
		if(singleSlab != null && doubleSlab != null) {
			KleeSlabs.registerSlabConverter(doubleSlab, new SmartSlabConverter(singleSlab));
		}
	}

}
