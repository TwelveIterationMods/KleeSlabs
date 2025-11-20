package net.blay09.mods.kleeslabs.fabric;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.blay09.mods.kleeslabs.KleeSlabs;
import net.fabricmc.api.ModInitializer;

public class FabricKleeSlabs implements ModInitializer {
    @Override
    public void onInitialize() {
        Balm.initializeMod(KleeSlabs.MOD_ID, FabricLoadContext.INSTANCE, KleeSlabs::initialize);
    }
}
