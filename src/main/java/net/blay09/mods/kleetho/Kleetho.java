package net.blay09.mods.kleetho;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

import java.util.Random;

@Mod(modid = "kleetho", name = "Kleetho")
public class Kleetho {

    private static final Random rand = new Random();

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void kleetho(BlockEvent.BreakEvent event) {
        if(event.getPlayer().isSneaking() && isDoubleSlab(event.block)) {
            if(!event.world.isRemote) {
                float scale = 0.7f;
                double xOffset = rand.nextFloat() * scale + 1f - scale * 0.5;
                double yOffset = rand.nextFloat() * scale + 1f - scale * 0.5;
                double zOffset = rand.nextFloat() * scale + 1f - scale * 0.5;
                EntityItem entityItem = new EntityItem(event.world, event.x + xOffset, event.y + yOffset, event.z + zOffset, new ItemStack(Item.getItemFromBlock(getSingleSlab(event.block)), 1, event.block.damageDropped(event.blockMetadata)));
                entityItem.delayBeforeCanPickup = 10;
                event.world.spawnEntityInWorld(entityItem);
            }
            event.world.setBlock(event.x, event.y, event.z, getSingleSlab(event.block), event.blockMetadata, 1 | 2);
            event.setCanceled(true);
        }
    }

    public boolean isDoubleSlab(Block block) {
        return block == Blocks.double_stone_slab || block == Blocks.double_wooden_slab;
    }

    public Block getSingleSlab(Block doubleSlab) {
        if(doubleSlab == Blocks.double_stone_slab) {
            return Blocks.stone_slab;
        }
        if(doubleSlab == Blocks.double_wooden_slab) {
            return Blocks.wooden_slab;
        }
        return null;
    }

}
