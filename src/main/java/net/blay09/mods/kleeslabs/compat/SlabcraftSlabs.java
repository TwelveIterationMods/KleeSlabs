package net.blay09.mods.kleeslabs.compat;

import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.converter.SmartSlabConverter;
import net.minecraft.block.Block;

public class SlabcraftSlabs extends CompatSlabs {

	public SlabcraftSlabs() {
		registerSlab("bookshelfslabsingle", "bookshelfslabdouble");
		registerSlab("dirtslabsingle", "dirtslabdouble");
		registerSlab("endstoneslabsingle", "endstoneslabdouble");
		registerSlab("glassslabsingle", "glassslabdouble");
		registerSlab("glowstoneslabsingle", "glowstoneslabdouble");
		registerSlab("grassslabsingle", "grassslabdouble");
		registerSlab("gravelslabsingle", "gravelslabdouble");
		registerSlab("hardclayslabsingle", "hardclayslabdouble");
		registerSlab("hayslabsingle", "hayslabdouble");
		registerSlab("iceslabsingle", "iceslabdouble");
		registerSlab("lavaslabsingle", "lavaslabdouble");
		registerSlab("leavesslabsingle", "leavesslabdouble");
		registerSlab("logslabsingle", "logslabdouble");
		registerSlab("mushroomslabsingle", "mushroomslabdouble");
		registerSlab("myceliumslabsingle", "myceliumslabdouble");
		registerSlab("netherrackslabsingle", "netherrackslabdouble");
		registerSlab("orecoalslabsingle", "orecoalslabdouble");
		registerSlab("orediamondslabsingle", "orediamondslabdouble");
		registerSlab("oreemeraldslabsingle", "oreemeraldslabdouble");
		registerSlab("oregoldslabsingle", "oregoldslabdouble");
		registerSlab("oreironslabsingle", "oreironslabdouble");
		registerSlab("orelapisslabsingle", "orelapisslabdouble");
		registerSlab("oreobsidianslabsingle", "oreobsidianslabdouble");
		registerSlab("oreredstoneslabsingle", "oreredstoneslabdouble");
		registerSlab("packediceslabsingle", "packediceslabdouble");
		registerSlab("sandslabsingle", "sandslabdouble");
		registerSlab("sealaternslabsingle", "sealaternslabdouble");
		registerSlab("slimeslabsingle", "slimeslabdouble");
		registerSlab("snowslabsingle", "snowslabdouble");
		registerSlab("soulsandslabsingle", "soulsandslabdouble");
		registerSlab("stainedclay1slabsingle", "stainedclay1slabdouble");
		registerSlab("stainedclayslabsingle", "stainedclayslabdouble");
		registerSlab("stainedglass1slabsingle", "stainedglass1slabdouble");
		registerSlab("stainedglassslabsingle", "stainedglassslabdouble");
		registerSlab("stone1slabsingle", "stone1slabdouble");
		registerSlab("stone2slabsingle", "stone2slabdouble");
		registerSlab("stone3slabsingle", "stone3slabdouble");
		registerSlab("stoneslabsingle", "stoneslabdouble");
		registerSlab("waterslabsingle", "waterslabdouble");
		registerSlab("wool1slabsingle", "wool1slabdouble");
		registerSlab("woolslabsingle", "woolslabdouble");
		registerSlab("boneslabsingle", "boneslabdouble");
		registerSlab("rednetherrackslabsingle", "rednetherrackslabdouble");
		registerSlab("netherwartslabsingle", "netherwartslabdouble");
		registerSlab("magmaslabsingle", "magmaslabdouble");
		registerSlab("grasspathslabsingle", "grasspathslabdouble");
	}

	private void registerSlab(String singleSlabName, String doubleSlabName) {
		Block singleSlab = getModBlock(SLABCRAFT, singleSlabName);
		Block doubleSlab = getModBlock(SLABCRAFT, doubleSlabName);
		if (singleSlab != null && doubleSlab != null) {
			KleeSlabs.registerSlabConverter(doubleSlab, new SmartSlabConverter(singleSlab));
		}
	}

}
