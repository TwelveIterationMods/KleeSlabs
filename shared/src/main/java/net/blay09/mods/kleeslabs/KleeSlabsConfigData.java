package net.blay09.mods.kleeslabs;

import me.shedaniel.autoconfig.annotation.Config;
import net.blay09.mods.balm.api.config.BalmConfigData;
import net.blay09.mods.balm.api.config.Comment;
import net.blay09.mods.balm.api.config.ExpectedType;

import java.util.ArrayList;
import java.util.List;

@Config(name = KleeSlabs.MOD_ID)
public class KleeSlabsConfigData implements BalmConfigData {
    @Comment("Control whether KleeSlabs should trigger ALWAYS, ONLY_WHEN_SNEAKING or ONLY_WHEN_NOT_SNEAKING")
    public KleeSlabsMode mode = KleeSlabsMode.ALWAYS;

    @Comment("IDs of mods whose compatibility should be disabled.")
    @ExpectedType(String.class)
    public List<String> disabledCompat = new ArrayList<>();

    @Comment("Set to true to have KleeSlabs dump a list of items containing the word 'slab' in their name upon world load")
    public boolean dumpSlabs = false;
}
