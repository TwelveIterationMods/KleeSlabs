package net.blay09.mods.kleeslabs.fabric;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.EmptyLoadContext;
import net.blay09.mods.kleeslabs.KleeSlabs;
import net.fabricmc.api.ModInitializer;

public class FabricKleeSlabs implements ModInitializer {
    @Override
    public void onInitialize() {
        Balm.initialize(KleeSlabs.MOD_ID, EmptyLoadContext.INSTANCE, KleeSlabs::initialize);
    }
}
