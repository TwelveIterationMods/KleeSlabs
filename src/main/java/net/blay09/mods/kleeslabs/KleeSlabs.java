package net.blay09.mods.kleeslabs;

import com.google.common.collect.Maps;
import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Map;
import java.util.Random;

@Mod(modid = KleeSlabs.MOD_ID, name = "KleeSlabs", acceptedMinecraftVersions = "[1.12]")
public class KleeSlabs {

    public static final String MOD_ID = "kleeslabs";

    public static final Logger logger = LogManager.getLogger();
    private static final Random rand = new Random();

    public static Configuration config;
    public static File configDir;

    private boolean requireSneak;
    private boolean invertSneak;

    private static final Map<Block, SlabConverter> slabMap = Maps.newHashMap();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configDir = event.getModConfigurationDirectory();

        MinecraftForge.EVENT_BUS.register(this);

        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        requireSneak = config.getBoolean("Require Sneaking", "general", false, "Set this to true to only break half a slab when the player is sneaking.");
        invertSneak = config.getBoolean("Invert Sneaking Check", "general", false, "If Require Sneaking is enabled. Set this to true to invert the sneaking check for breaking only half a slab.");

        // MinecraftForge.EVENT_BUS.register(new SlabDebugger());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        JsonCompatLoader.loadCompat();

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static void registerSlabConverter(Block doubleSlab, SlabConverter converter) {
        slabMap.put(doubleSlab, converter);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onDrawBlockHighlight(DrawBlockHighlightEvent event) {
        if (!requireSneak || event.getPlayer().isSneaking() != invertSneak) {
            if (event.getTarget().typeOfHit != RayTraceResult.Type.BLOCK) {
                return;
            }
            BlockPos pos = event.getTarget().getBlockPos();
            //noinspection ConstantConditions
            if (pos == null) {
                return;
            }
            IBlockState target = event.getPlayer().world.getBlockState(pos);
            SlabConverter slabConverter = slabMap.get(target.getBlock());
            if (slabConverter != null) {
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.glLineWidth(2f);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask(false);
                EntityPlayer player = event.getPlayer();
                double offsetX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) event.getPartialTicks();
                double offsetY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) event.getPartialTicks();
                double offsetZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) event.getPartialTicks();
                AxisAlignedBB halfAABB = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 0.5, pos.getZ() + 1);
                if (event.getTarget().hitVec.y - player.posY > 0.5) {
                    halfAABB = halfAABB.offset(0, 0.5, 0);
                }
                RenderGlobal.drawSelectionBoundingBox(halfAABB.grow(0.002).offset(-offsetX, -offsetY, -offsetZ), 0f, 0f, 0f, 0.4f);
                GlStateManager.depthMask(true);
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onBreakBlock(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof FakePlayer) {
            return;
        }

        RayTraceResult mop = rayTrace(event.getPlayer(), 6);
        Vec3d hitVec = mop != null ? mop.hitVec : null;
        if (hitVec != null) {
            hitVec = hitVec.addVector(-event.getPos().getX(), -event.getPos().getY(), -event.getPos().getZ());
        }
        if (!requireSneak || event.getPlayer().isSneaking() != invertSneak) {
            IBlockState state = event.getState();
            SlabConverter slabConverter = slabMap.get(state.getBlock());
            if (slabConverter == null || !slabConverter.isDoubleSlab(state)) {
                return;
            }

            IBlockState dropState = slabConverter.getSingleSlab(state, BlockSlab.EnumBlockHalf.BOTTOM);
            if (!event.getWorld().isRemote && event.getPlayer().canHarvestBlock(event.getState()) && !event.getPlayer().capabilities.isCreativeMode) {
                Item slabItem = Item.getItemFromBlock(dropState.getBlock());
                if (slabItem != Items.AIR) {
                    spawnItem(new ItemStack(slabItem, 1, dropState.getBlock().damageDropped(dropState)), event.getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
                }
            }
            IBlockState newState;
            if (hitVec != null && hitVec.y < 0.5f) {
                newState = slabConverter.getSingleSlab(state, BlockSlab.EnumBlockHalf.TOP);
            } else {
                newState = slabConverter.getSingleSlab(state, BlockSlab.EnumBlockHalf.BOTTOM);
            }
            event.getWorld().setBlockState(event.getPos(), newState, 1 | 2);
            event.setCanceled(true);
        }
    }

    private void spawnItem(ItemStack itemStack, World world, float x, float y, float z) {
        float scale = 0.7f;
        double xOffset = rand.nextFloat() * scale + 1f - scale * 0.5;
        double yOffset = rand.nextFloat() * scale + 1f - scale * 0.5;
        double zOffset = rand.nextFloat() * scale + 1f - scale * 0.5;
        EntityItem entityItem = new EntityItem(world, x + xOffset, y + yOffset, z + zOffset, itemStack);
        entityItem.setPickupDelay(10);
        world.spawnEntity(entityItem);
    }

    public static RayTraceResult rayTrace(EntityLivingBase entity, double length) {
        Vec3d startPos = new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
        Vec3d endPos = startPos.addVector(entity.getLookVec().x * length, entity.getLookVec().y * length, entity.getLookVec().z * length);
        return entity.world.rayTraceBlocks(startPos, endPos);
    }

    private static class SlabDebugger {
        @SubscribeEvent
        public void onTooltip(ItemTooltipEvent event) {
            event.getToolTip().add("Mod: " + event.getItemStack().getItem().getRegistryName().getResourceDomain());
            event.getToolTip().add("Name: " + event.getItemStack().getItem().getRegistryName().getResourcePath());
        }

        @SubscribeEvent
        public void onRightClick(PlayerInteractEvent.LeftClickBlock event) {
            if (event.getSide() == Side.CLIENT) {
                IBlockState state = event.getWorld().getBlockState(event.getPos());
                event.getEntityPlayer().sendStatusMessage(new TextComponentString("Mod: " + state.getBlock().getRegistryName().getResourceDomain() + " Name: " + state.getBlock().getRegistryName().getResourcePath()), false);
            }
        }
    }

}
