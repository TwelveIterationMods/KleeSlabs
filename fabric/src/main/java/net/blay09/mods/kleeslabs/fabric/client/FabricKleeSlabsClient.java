package net.blay09.mods.kleeslabs.fabric.client;

import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.client.KleeSlabsClient;
import net.fabricmc.api.ClientModInitializer;

public class FabricKleeSlabsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BalmClient.initialize(KleeSlabs.MOD_ID, KleeSlabsClient::initialize);
    }
}
