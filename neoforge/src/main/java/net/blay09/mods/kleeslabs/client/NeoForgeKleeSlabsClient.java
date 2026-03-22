package net.blay09.mods.kleeslabs.client;

import net.blay09.mods.balm.client.BalmClient;
import net.blay09.mods.balm.neoforge.platform.runtime.NeoForgeLoadContext;
import net.blay09.mods.kleeslabs.KleeSlabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(value = KleeSlabs.MOD_ID, dist = Dist.CLIENT)
public class NeoForgeKleeSlabsClient {

    public NeoForgeKleeSlabsClient(ModContainer modContainer, IEventBus eventBus) {
        final var context = new NeoForgeLoadContext(modContainer, eventBus);
        BalmClient.initializeMod(KleeSlabs.MOD_ID, context, KleeSlabsClient::initialize);
    }

}
