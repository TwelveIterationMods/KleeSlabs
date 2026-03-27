package net.blay09.mods.kleeslabs.forge;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.neoforge.platform.runtime.NeoForgeLoadContext;
import net.blay09.mods.kleeslabs.KleeSlabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(KleeSlabs.MOD_ID)
public class NeoForgeKleeSlabs {

    public NeoForgeKleeSlabs(ModContainer modContainer, IEventBus eventBus) {
        final var context = new NeoForgeLoadContext(modContainer, eventBus);
        Balm.initializeMod(KleeSlabs.MOD_ID, context, KleeSlabs::initialize);
    }

}
