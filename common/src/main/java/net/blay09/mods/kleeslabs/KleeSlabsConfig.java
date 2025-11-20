package net.blay09.mods.kleeslabs;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.platform.config.reflection.Comment;
import net.blay09.mods.balm.platform.config.reflection.Config;

@Config(KleeSlabs.MOD_ID)
public class KleeSlabsConfig {
    @Comment("Control whether KleeSlabs should trigger ALWAYS, ONLY_WHEN_SNEAKING or ONLY_WHEN_NOT_SNEAKING")
    public KleeSlabsMode mode = KleeSlabsMode.ALWAYS;

    @Comment("Set to true to have KleeSlabs dump a list of items containing the word 'slab' in their name upon world load")
    public boolean dumpSlabs = false;

    public static KleeSlabsConfig getActive() {
        return Balm.config().getActiveConfig(KleeSlabsConfig.class);
    }

    public static void initialize() {
        Balm.config().registerConfig(KleeSlabsConfig.class);
    }
}
