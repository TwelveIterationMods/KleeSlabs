package net.blay09.mods.kleetho;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.Map;
import java.util.Random;

@Mod(modid = "kleeslabs", name = "KleeSlabs")
public class Kleetho {

    private static final Random rand = new Random();

    private boolean invertSneak;
    private Configuration config;

    private final BiMap<Block, Block> slabMap = HashBiMap.create();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);

        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        invertSneak = config.getBoolean("Invert Sneaking Check", "general", false, "Set this to true to invert the sneaking check for breaking only half a slab.");

        config.getCategory("mods").setComment("***IMPORTANT*** This is not guaranteed to work for all slabs. Custom slab implementations that do not follow Vanilla's standard will likely break, so make sure to test if you add any here! ***IMPORTANT***");

        String modId = "BiomesOPlenty";
        config.get("mods." + modId, "id", modId);
        config.get("mods." + modId, "woodenSingleSlab1", "woodenDoubleSlab1");
        config.get("mods." + modId, "woodenSingleSlab2", "woodenDoubleSlab2");
        config.get("mods." + modId, "stoneSingleSlab", "stoneDoubleSlab");

        modId = "Thaumcraft";
        config.get("mods." + modId, "id", modId);
        config.get("mods." + modId, "blockCosmeticSlabStone", "blockCosmeticDoubleSlabStone");
        config.get("mods." + modId, "blockCosmeticSlabWood", "blockCosmeticDoubleSlabWood");

        modId = "Botania";
        config.get("mods." + modId, "id", modId);
        config.get("mods." + modId, "quartzSlabDarkHalf", "quartzSlabDarkFull");
        config.get("mods." + modId, "quartzSlabManaHalf", "quartzSlabManaFull");
        config.get("mods." + modId, "quartzSlabBlazeHalf", "quartzSlabBlazeFull");
        config.get("mods." + modId, "quartzSlabLavenderHalf", "quartzSlabLavenderFull");
        config.get("mods." + modId, "quartzSlabRedHalf", "quartzSlabRedFull");
        config.get("mods." + modId, "quartzSlabElfHalf", "quartzSlabElfFull");
        config.get("mods." + modId, "quartzSlabSunnyHalf", "quartzSlabSunnyFull");
        config.get("mods." + modId, "livingwood0Slab", "livingwood0SlabFull");
        config.get("mods." + modId, "livingwood1Slab", "livingwood1SlabFull");
        config.get("mods." + modId, "livingrock0Slab", "livingrock0SlabFull");
        config.get("mods." + modId, "livingrock1Slab", "livingrock1SlabFull");
        config.get("mods." + modId, "dreamwood0Slab", "dreamwood0SlabFull");
        config.get("mods." + modId, "dreamwood1Slab", "dreamwood1SlabFull");
        config.get("mods." + modId, "reedBlock0Slab", "reedBlock0SlabFull");
        config.get("mods." + modId, "thatch0Slab", "thatch0SlabFull");
        config.get("mods." + modId, "prismarine0Slab", "prismarine0SlabFull");
        config.get("mods." + modId, "prismarine1Slab", "prismarine1SlabFull");
        config.get("mods." + modId, "prismarine2Slab", "prismarine2SlabFull");
        config.get("mods." + modId, "customBrick0Slab", "customBrick0SlabFull");
        config.get("mods." + modId, "customBrick1Slab", "customBrick1SlabFull");
        config.get("mods." + modId, "customBrick2Slab", "customBrick2SlabFull");
        config.get("mods." + modId, "customBrick3Slab", "customBrick3SlabFull");
        config.get("mods." + modId, "dirtPath0Slab", "dirtPath0SlabFull");
        config.get("mods." + modId, "endStoneBrick0Slab", "endStoneBrick0SlabFull");
        config.get("mods." + modId, "endStoneBrick2Slab", "endStoneBrick2SlabFull");
        config.get("mods." + modId, "shimmerrock0Slab", "shimmerrock0SlabFull");
        config.get("mods." + modId, "shimmerrock0Slab", "shimmerrock0SlabFull");
        config.get("mods." + modId, "shimmerwoodPlanks0Slab", "shimmerwoodPlanks0SlabFull");
        config.get("mods." + modId, "biomeStoneA0Slab", "biomeStoneA0SlabFull");
        config.get("mods." + modId, "biomeStoneA1Slab", "biomeStoneA1SlabFull");
        config.get("mods." + modId, "biomeStoneA2Slab", "biomeStoneA2SlabFull");
        config.get("mods." + modId, "biomeStoneA3Slab", "biomeStoneA3SlabFull");
        config.get("mods." + modId, "biomeStoneA4Slab", "biomeStoneA4SlabFull");
        config.get("mods." + modId, "biomeStoneA5Slab", "biomeStoneA5SlabFull");
        config.get("mods." + modId, "biomeStoneA6Slab", "biomeStoneA6SlabFull");
        config.get("mods." + modId, "biomeStoneA7Slab", "biomeStoneA7SlabFull");
        config.get("mods." + modId, "biomeStoneA8Slab", "biomeStoneA8SlabFull");
        config.get("mods." + modId, "biomeStoneA9Slab", "biomeStoneA9SlabFull");
        config.get("mods." + modId, "biomeStoneA10Slab", "biomeStoneA10SlabFull");
        config.get("mods." + modId, "biomeStoneA11Slab", "biomeStoneA11SlabFull");
        config.get("mods." + modId, "biomeStoneA12Slab", "biomeStoneA12SlabFull");
        config.get("mods." + modId, "biomeStoneA13Slab", "biomeStoneA13SlabFull");
        config.get("mods." + modId, "biomeStoneA14Slab", "biomeStoneA14SlabFull");
        config.get("mods." + modId, "biomeStoneA15Slab", "biomeStoneA15SlabFull");
        config.get("mods." + modId, "biomeStoneB0Slab", "biomeStoneB0SlabFull");
        config.get("mods." + modId, "biomeStoneB1Slab", "biomeStoneB1SlabFull");
        config.get("mods." + modId, "biomeStoneB2Slab", "biomeStoneB2SlabFull");
        config.get("mods." + modId, "biomeStoneB3Slab", "biomeStoneB3SlabFull");
        config.get("mods." + modId, "biomeStoneB4Slab", "biomeStoneB4SlabFull");
        config.get("mods." + modId, "biomeStoneB5Slab", "biomeStoneB5SlabFull");
        config.get("mods." + modId, "biomeStoneB6Slab", "biomeStoneB6SlabFull");
        config.get("mods." + modId, "biomeStoneB6Slab", "biomeStoneB6SlabFull");
        config.get("mods." + modId, "biomeStoneB7Slab", "biomeStoneB7SlabFull");
        config.get("mods." + modId, "stone0Slab", "stone0SlabFull");
        config.get("mods." + modId, "stone1Slab", "stone1SlabFull");
        config.get("mods." + modId, "stone2Slab", "stone2SlabFull");
        config.get("mods." + modId, "stone3Slab", "stone3SlabFull");
        config.get("mods." + modId, "stone8Slab", "stone8SlabFull");
        config.get("mods." + modId, "stone9Slab", "stone9SlabFull");
        config.get("mods." + modId, "stone10Slab", "stone10SlabFull");
        config.get("mods." + modId, "stone11Slab", "stone11SlabFull");
        config.get("mods." + modId, "pavement0Slab", "pavement0SlabFull");
        config.get("mods." + modId, "pavement1Slab", "pavement1SlabFull");
        config.get("mods." + modId, "pavement2Slab", "pavement2SlabFull");
        config.get("mods." + modId, "pavement3Slab", "pavement3SlabFull");
        config.get("mods." + modId, "pavement4Slab", "pavement4SlabFull");
        config.get("mods." + modId, "pavement5Slab", "pavement5SlabFull");

        modId = "Forestry";
        config.get("mods." + modId, "id", modId);
        config.get("mods." + modId, "slabs", "slabsDouble");
        config.get("mods." + modId, "slabsFireproof", "slabsDoubleFireproof");

        modId = "ThaumicTinkerer";
        config.get("mods." + modId, "id", modId);
        config.get("mods." + modId, "darkQuartzSlab", "darkQuartzSlabFull");

        modId = "witchery";
        config.get("mods." + modId, "id", modId);
        config.get("mods." + modId, "witchwoodslab", "witchwooddoubleslab");
        config.get("mods." + modId, "iceslab", "icedoubleslab");
        config.get("mods." + modId, "snowslab", "snowdoubleslab");

        modId = "appliedenergistics2";
        config.get("mods." + modId, "id", modId);
        config.get("mods." + modId, "tile.SkyStoneSlabBlock", "tile.SkyStoneSlabBlock.double");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        slabMap.put(Blocks.double_stone_slab, Blocks.stone_slab);
        slabMap.put(Blocks.double_wooden_slab, Blocks.wooden_slab);

        ConfigCategory category = config.getCategory("mods");
        for(ConfigCategory mod : category.getChildren()) {
            String modId;
            if(mod.get("id") != null) {
                modId = mod.get("id").getString();
            } else {
                modId = mod.getName();
            }
            if(Loader.isModLoaded(modId)) {
                for (Map.Entry<String, Property> entry : mod.entrySet()) {
                    String singleSlabName = entry.getKey();
                    String doubleSlabName = entry.getValue().getString();
                    registerSlabs(modId, singleSlabName, doubleSlabName);
                }
            }
        }

        config.save();
    }

    private void registerSlabs(String modId, String singleName, String doubleName) {
        Block singleBlock = GameRegistry.findBlock(modId, singleName);
        Block doubleBlock = GameRegistry.findBlock(modId, doubleName);
        if(singleBlock != null && doubleBlock != null) {
            slabMap.put(doubleBlock, singleBlock);
        } else {
            if(singleBlock == null) {
                System.out.println("Could not register slabs for " + modId + ": " + singleName + " not found");
            } else {
                System.out.println("Could not register slabs for " + modId + ": " + doubleName + " not found");
            }
        }
    }

    /*@SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        GameRegistry.UniqueIdentifier uid = GameRegistry.findUniqueIdentifierFor(event.itemStack.getItem());
        event.toolTip.add("Mod: " + uid.modId);
        event.toolTip.add("Name: " + uid.name);
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent event) {
        if(event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            GameRegistry.UniqueIdentifier uid = GameRegistry.findUniqueIdentifierFor(event.world.getBlock(event.x, event.y, event.z));
            event.entityPlayer.addChatComponentMessage(new ChatComponentText("Mod: " + uid.modId + " Name: " + uid.name));
        }
    }*/

    @SubscribeEvent
    public void onBreakBlock(BlockEvent.BreakEvent event) {
        if(event.getPlayer().isSneaking() != invertSneak && isDoubleSlab(event.block)) {
            if(!event.world.isRemote && event.getPlayer().canHarvestBlock(event.block) && !event.getPlayer().capabilities.isCreativeMode) {
                spawnItem(new ItemStack(Item.getItemFromBlock(getSingleSlab(event.block)), 1, event.block.damageDropped(event.blockMetadata)), event.world, event.x, event.y, event.z);
            }
            int metadata = event.blockMetadata;
            if(event.getPlayer().posY + event.getPlayer().getEyeHeight() < event.y) {
                metadata |= 8;
            }
            event.world.setBlock(event.x, event.y, event.z, getSingleSlab(event.block), metadata, 1 | 2);
            event.setCanceled(true);
        }
    }

    private void spawnItem(ItemStack itemStack, World world, float x, float y, float z) {
        float scale = 0.7f;
        double xOffset = rand.nextFloat() * scale + 1f - scale * 0.5;
        double yOffset = rand.nextFloat() * scale + 1f - scale * 0.5;
        double zOffset = rand.nextFloat() * scale + 1f - scale * 0.5;
        EntityItem entityItem = new EntityItem(world, x + xOffset, y + yOffset, z + zOffset, itemStack);
        entityItem.delayBeforeCanPickup = 10;
        world.spawnEntityInWorld(entityItem);
    }

    public boolean isDoubleSlab(Block block) {
        return slabMap.containsKey(block);
    }

    public Block getSingleSlab(Block doubleSlab) {
        return slabMap.get(doubleSlab);
    }

}
