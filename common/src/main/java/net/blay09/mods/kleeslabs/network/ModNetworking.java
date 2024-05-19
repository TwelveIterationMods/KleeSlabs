package net.blay09.mods.kleeslabs.network;

import net.blay09.mods.balm.api.network.BalmNetworking;

public class ModNetworking {

    public static void initialize(BalmNetworking networking) {
        networking.registerClientboundPacket(KleeSlabsRegistryMessage.TYPE, KleeSlabsRegistryMessage.class, KleeSlabsRegistryMessage::encode, KleeSlabsRegistryMessage::decode, KleeSlabsRegistryMessage::handle);
    }

}
