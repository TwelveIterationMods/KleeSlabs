package net.blay09.mods.kleeslabs;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.common.config.ConfigLocalization;

public class KleeSlabsConfig {

    public static KleeSlabsConfigData getActive() {
        return Balm.getConfig().getActive(KleeSlabsConfigData.class);
    }

    public static void initialize() {
        ConfigLocalization.enableModernTranslationKeys(KleeSlabs.MOD_ID);
        Balm.getConfig().registerConfig(KleeSlabsConfigData.class, null);
    }

}
