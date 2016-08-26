package net.blay09.mods.kleeslabs.compat;

import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.converter.SimpleSlabConverter;
import net.minecraft.block.Block;

public class BotaniaSlabs extends CompatSlabs {

	public BotaniaSlabs() {
		registerSlab("quartzSlabDarkHalf", "quartzSlabDarkFull");
		registerSlab("quartzSlabManaHalf", "quartzSlabManaFull");
		registerSlab("quartzSlabBlazeHalf", "quartzSlabBlazeFull");
		registerSlab("quartzSlabLavenderHalf", "quartzSlabLavenderFull");
		registerSlab("quartzSlabRedHalf", "quartzSlabRedFull");
		registerSlab("quartzSlabElfHalf", "quartzSlabElfFull");
		registerSlab("quartzSlabSunnyHalf", "quartzSlabSunnyFull");
		registerSlab("livingwood0Slab", "livingwood0SlabFull");
		registerSlab("livingwood1Slab", "livingwood1SlabFull");
		registerSlab("livingrock0Slab", "livingrock0SlabFull");
		registerSlab("livingrock1Slab", "livingrock1SlabFull");
		registerSlab("dreamwood0Slab", "dreamwood0SlabFull");
		registerSlab("dreamwood1Slab", "dreamwood1SlabFull");
		registerSlab("customBrick0Slab", "customBrick0SlabFull");
		registerSlab("customBrick1Slab", "customBrick1SlabFull");
		registerSlab("customBrick2Slab", "customBrick2SlabFull");
		registerSlab("customBrick3Slab", "customBrick3SlabFull");
		registerSlab("dirtPath0Slab", "dirtPath0SlabFull");
		registerSlab("shimmerrock0Slab", "shimmerrock0SlabFull");
		registerSlab("shimmerrock0Slab", "shimmerrock0SlabFull");
		registerSlab("shimmerwoodPlanks0Slab", "shimmerwoodPlanks0SlabFull");
		registerSlab("biomeStoneA0Slab", "biomeStoneA0SlabFull");
		registerSlab("biomeStoneA1Slab", "biomeStoneA1SlabFull");
		registerSlab("biomeStoneA2Slab", "biomeStoneA2SlabFull");
		registerSlab("biomeStoneA3Slab", "biomeStoneA3SlabFull");
		registerSlab("biomeStoneA4Slab", "biomeStoneA4SlabFull");
		registerSlab("biomeStoneA5Slab", "biomeStoneA5SlabFull");
		registerSlab("biomeStoneA6Slab", "biomeStoneA6SlabFull");
		registerSlab("biomeStoneA7Slab", "biomeStoneA7SlabFull");
		registerSlab("biomeStoneA8Slab", "biomeStoneA8SlabFull");
		registerSlab("biomeStoneA9Slab", "biomeStoneA9SlabFull");
		registerSlab("biomeStoneA10Slab", "biomeStoneA10SlabFull");
		registerSlab("biomeStoneA11Slab", "biomeStoneA11SlabFull");
		registerSlab("biomeStoneA12Slab", "biomeStoneA12SlabFull");
		registerSlab("biomeStoneA13Slab", "biomeStoneA13SlabFull");
		registerSlab("biomeStoneA14Slab", "biomeStoneA14SlabFull");
		registerSlab("biomeStoneA15Slab", "biomeStoneA15SlabFull");
		registerSlab("biomeStoneB0Slab", "biomeStoneB0SlabFull");
		registerSlab("biomeStoneB1Slab", "biomeStoneB1SlabFull");
		registerSlab("biomeStoneB2Slab", "biomeStoneB2SlabFull");
		registerSlab("biomeStoneB3Slab", "biomeStoneB3SlabFull");
		registerSlab("biomeStoneB4Slab", "biomeStoneB4SlabFull");
		registerSlab("biomeStoneB5Slab", "biomeStoneB5SlabFull");
		registerSlab("biomeStoneB6Slab", "biomeStoneB6SlabFull");
		registerSlab("biomeStoneB6Slab", "biomeStoneB6SlabFull");
		registerSlab("biomeStoneB7Slab", "biomeStoneB7SlabFull");
		registerSlab("pavement0Slab", "pavement0SlabFull");
		registerSlab("pavement1Slab", "pavement1SlabFull");
		registerSlab("pavement2Slab", "pavement2SlabFull");
		registerSlab("pavement3Slab", "pavement3SlabFull");
		registerSlab("pavement4Slab", "pavement4SlabFull");
		registerSlab("pavement5Slab", "pavement5SlabFull");
	}

	private void registerSlab(String singleSlabName, String doubleSlabName) {
		Block singleSlab = getModBlock(BOTANIA, singleSlabName);
		Block doubleSlab = getModBlock(BOTANIA, doubleSlabName);
		if(singleSlab != null && doubleSlab != null) {
			KleeSlabs.registerSlabConverter(doubleSlab, new SimpleSlabConverter(singleSlab));
		}
	}

}
