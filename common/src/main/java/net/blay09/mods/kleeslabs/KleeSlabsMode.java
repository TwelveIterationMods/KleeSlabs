package net.blay09.mods.kleeslabs;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum KleeSlabsMode implements StringRepresentable {
    ALWAYS,
    ONLY_WHEN_SNEAKING,
    ONLY_WHEN_NOT_SNEAKING;

    @Override
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
