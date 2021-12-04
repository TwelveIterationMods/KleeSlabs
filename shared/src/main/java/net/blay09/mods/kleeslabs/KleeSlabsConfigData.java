package net.blay09.mods.kleeslabs;

import me.shedaniel.autoconfig.annotation.Config;
import net.blay09.mods.balm.api.config.BalmConfigData;
import net.blay09.mods.balm.api.config.Comment;

import java.util.ArrayList;
import java.util.List;

@Config(name = KleeSlabs.MOD_ID)
public class KleeSlabsConfigData implements BalmConfigData {
    @Comment("Control whether KleeSlabs should trigger ALWAYS, ONLY_WHEN_SNEAKING or ONLY_WHEN_NOT_SNEAKING")
    public KleeSlabsMode mode = KleeSlabsMode.ALWAYS;

    @Comment("IDs of mods whose compatibility should be disabled.")
    public List<String> disabledCompat = new ArrayList<>();
}
