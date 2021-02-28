package net.blay09.mods.kleeslabs;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KleeSlabsConfig {
    public static class Common {
        public final ForgeConfigSpec.BooleanValue requireSneak;
        public final ForgeConfigSpec.BooleanValue invertSneak;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> disabledCompat;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("KleeSlabs settings").push("common");

            requireSneak = builder
                    .comment("Set this to true to only break half a slab when the player is sneaking.")
                    .translation("kleeslabs.config.requireSneak")
                    .define("requireSneak", false);

            invertSneak = builder
                    .comment("If Require Sneaking is enabled. Set this to true to invert the sneaking check for breaking only half a slab.")
                    .translation("kleeslabs.config.invertSneak")
                    .define("invertSneak", false);

            disabledCompat = builder
                    .comment("IDs of mods whose compatibility should be disabled.")
                    .translation("kleeslabs.config.disabledCompat")
                    .defineList("disabledCompat", new ArrayList<>(), o -> o instanceof String);
        }
    }

    static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

}
