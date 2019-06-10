package net.blay09.mods.kleeslabs;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.List;

public class KleeSlabsConfig {
    public static class Client {
        public final ForgeConfigSpec.BooleanValue requireSneak;
        public final ForgeConfigSpec.BooleanValue invertSneak;
        public final ForgeConfigSpec.ConfigValue<List<String>> disabledCompat;

        Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Client only settings").push("client");

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
                    .define("disabledCompat", Collections.emptyList());
        }
    }

    static final ForgeConfigSpec clientSpec;
    public static final KleeSlabsConfig.Client CLIENT;

    static {
        final Pair<KleeSlabsConfig.Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(KleeSlabsConfig.Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

}
