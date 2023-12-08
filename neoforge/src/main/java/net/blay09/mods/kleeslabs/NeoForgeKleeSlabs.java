package net.blay09.mods.kleeslabs;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.kleeslabs.client.KleeSlabsClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.DistExecutor;
import net.neoforged.fml.common.Mod;

@Mod(KleeSlabs.MOD_ID)
public class NeoForgeKleeSlabs {

    public NeoForgeKleeSlabs() {
        Balm.initialize(KleeSlabs.MOD_ID, KleeSlabs::initialize);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> BalmClient.initialize(KleeSlabs.MOD_ID, KleeSlabsClient::initialize));
    }

}
