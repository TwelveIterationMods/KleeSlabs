package net.blay09.mods.kleeslabs.compat;

import net.blay09.mods.kleeslabs.KleeSlabs;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

public abstract class CompatSlabs {

	public static final String BOTANIA = "botania";
	public static final String FORESTRY = "forestry";
	public static final String BIOMES_O_PLENTY = "biomesoplenty";
	public static final String QUARK = "quark";
	public static final String MISSING_PIECES = "missing_pieces";
	public static final String SLABCRAFT = "slabcraftmod";
	public static final String UNDERGROUNDBIOMES = "undergroundbiomes";

	protected boolean isSilent;

	public Block getModBlock(String modId, String blockName) {
		Block block = Block.REGISTRY.getObject(new ResourceLocation(modId, blockName));
		if(block == Blocks.AIR) {
			if(!isSilent) {
				KleeSlabs.logger.error("Slab {}:{} could not be found.", modId, blockName);
			}
			return null;
		}
		return block;
	}

}
