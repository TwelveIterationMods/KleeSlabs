package net.blay09.mods.kleeslabs;

import net.blay09.mods.balm.api.Balm;
import net.fabricmc.api.ModInitializer;

public class FabricKleeSlabs implements ModInitializer {
    @Override
    public void onInitialize() {
        Balm.initialize(KleeSlabs.MOD_ID, KleeSlabs::initialize);
    }
}
