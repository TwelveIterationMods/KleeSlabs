package net.blay09.mods.kleeslabs.network;

import net.blay09.mods.balm.api.network.BalmNetworking;
import net.blay09.mods.kleeslabs.KleeSlabs;
import net.minecraft.resources.ResourceLocation;

public class ModNetworking {

    public static void initialize(BalmNetworking networking) {
        networking.allowServerOnly(KleeSlabs.MOD_ID);
        networking.registerClientboundPacket(id("kleeslabs_registry"), KleeSlabsRegistryMessage.class, KleeSlabsRegistryMessage::encode, KleeSlabsRegistryMessage::decode, KleeSlabsRegistryMessage::handle);
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation(KleeSlabs.MOD_ID, path);
    }

}
