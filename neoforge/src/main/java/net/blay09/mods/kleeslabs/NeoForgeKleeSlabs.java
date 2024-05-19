package net.blay09.mods.kleeslabs;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(KleeSlabs.MOD_ID)
public class NeoForgeKleeSlabs {

    public NeoForgeKleeSlabs(IEventBus eventBus) {
        final var context = new NeoForgeLoadContext(eventBus);
        Balm.initialize(KleeSlabs.MOD_ID, context, KleeSlabs::initialize);
    }

}
