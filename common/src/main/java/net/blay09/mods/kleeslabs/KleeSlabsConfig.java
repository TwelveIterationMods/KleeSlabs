package net.blay09.mods.kleeslabs;

import net.blay09.mods.balm.api.Balm;

public class KleeSlabsConfig {

    public static KleeSlabsConfigData getActive() {
        return Balm.getConfig().getActive(KleeSlabsConfigData.class);
    }

    public static void initialize() {
        Balm.getConfig().registerConfig(KleeSlabsConfigData.class, null);
    }

}
