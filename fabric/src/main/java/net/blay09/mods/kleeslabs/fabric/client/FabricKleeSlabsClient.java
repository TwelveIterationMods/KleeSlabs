package net.blay09.mods.kleeslabs.fabric.client;

import net.blay09.mods.balm.client.BalmClient;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.client.KleeSlabsClient;
import net.fabricmc.api.ClientModInitializer;

public class FabricKleeSlabsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BalmClient.initializeMod(KleeSlabs.MOD_ID, FabricLoadContext.INSTANCE, KleeSlabsClient::initialize);
    }
}
