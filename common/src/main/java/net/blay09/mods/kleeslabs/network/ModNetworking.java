package net.blay09.mods.kleeslabs.network;

import net.blay09.mods.balm.api.network.BalmNetworking;
import net.blay09.mods.kleeslabs.KleeSlabs;

public class ModNetworking {

    public static void initialize(BalmNetworking networking) {
        networking.allowServerOnly(KleeSlabs.MOD_ID);
    }

}
