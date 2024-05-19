package net.blay09.mods.kleeslabs.client;

import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.balm.neoforge.NeoForgeLoadContext;
import net.blay09.mods.kleeslabs.KleeSlabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = KleeSlabs.MOD_ID, dist = Dist.CLIENT)
public class NeoForgeKleeSlabsClient {

    public NeoForgeKleeSlabsClient(IEventBus eventBus) {
        final var context = new NeoForgeLoadContext(eventBus);
        BalmClient.initialize(KleeSlabs.MOD_ID, context, KleeSlabsClient::initialize);
    }

}
