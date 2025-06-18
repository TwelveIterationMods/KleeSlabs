package net.blay09.mods.kleeslabs;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.balm.forge.ForgeLoadContext;
import net.blay09.mods.kleeslabs.client.KleeSlabsClient;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(KleeSlabs.MOD_ID)
public class ForgeKleeSlabs {

    public ForgeKleeSlabs(FMLJavaModLoadingContext context) {
        final var loadContext = new ForgeLoadContext(context.getModBusGroup());
        Balm.initializeMod(KleeSlabs.MOD_ID, loadContext, KleeSlabs::initialize);
        if (FMLEnvironment.dist.isClient()) {
            BalmClient.initializeMod(KleeSlabs.MOD_ID, loadContext, KleeSlabsClient::initialize);
        }
    }

}
