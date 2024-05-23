package net.blay09.mods.kleeslabs;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.EmptyLoadContext;
import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.kleeslabs.client.KleeSlabsClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(KleeSlabs.MOD_ID)
public class ForgeKleeSlabs {

    public ForgeKleeSlabs() {
        Balm.initialize(KleeSlabs.MOD_ID, EmptyLoadContext.INSTANCE, KleeSlabs::initialize);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> BalmClient.initialize(KleeSlabs.MOD_ID, EmptyLoadContext.INSTANCE, KleeSlabsClient::initialize));
    }

}
