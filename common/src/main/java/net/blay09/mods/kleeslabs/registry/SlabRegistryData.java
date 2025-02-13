package net.blay09.mods.kleeslabs.registry;

import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.minecraft.world.level.block.Block;

public class SlabRegistryData {
    private final Class<? extends SlabConverter> converterClass;
    private final Block singleSlab;
    private final Block doubleSlab;

    public SlabRegistryData(Class<? extends SlabConverter> converterClass, Block singleSlab, Block doubleSlab) {
        this.converterClass = converterClass;
        this.singleSlab = singleSlab;
        this.doubleSlab = doubleSlab;
    }

    public Class<? extends SlabConverter> getConverterClass() {
        return converterClass;
    }

    public Block getSingleSlab() {
        return singleSlab;
    }

    public Block getDoubleSlab() {
        return doubleSlab;
    }
}
