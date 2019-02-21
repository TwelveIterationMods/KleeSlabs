package net.blay09.mods.kleeslabs;

import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.blay09.mods.kleeslabs.registry.SlabRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class BlockBreakHandler {

    @SubscribeEvent
    public static void onBreakBlock(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof FakePlayer) {
            return;
        }

        if (!KleeSlabs.isPlayerKleeSlabbing(event.getPlayer())) {
            return;
        }

        RayTraceResult mop = rayTrace(event.getPlayer(), 6);
        Vec3d hitVec = mop != null ? mop.hitVec : null;

        // Relativize the hit vector around the player position
        if (hitVec != null) {
            hitVec = hitVec.add(-event.getPos().getX(), -event.getPos().getY(), -event.getPos().getZ());
        }

        IBlockState state = event.getState();
        SlabConverter slabConverter = SlabRegistry.getSlabConverter(state.getBlock());
        if (slabConverter == null || !slabConverter.isDoubleSlab(state)) {
            return;
        }

        IBlockState dropState = slabConverter.getSingleSlab(state, SlabType.BOTTOM);
        IWorld world = event.getWorld();
        if (!world.isRemote() && event.getPlayer().canHarvestBlock(event.getState()) && !event.getPlayer().abilities.isCreativeMode) {
            Item slabItem = Item.getItemFromBlock(dropState.getBlock());
            if (slabItem != Items.AIR) {
                ItemStack itemStack = new ItemStack(slabItem);
                float scale = 0.7f;
                double xOffset = world.getRandom().nextFloat() * scale + 1f - scale * 0.5;
                double yOffset = world.getRandom().nextFloat() * scale + 1f - scale * 0.5;
                double zOffset = world.getRandom().nextFloat() * scale + 1f - scale * 0.5;
                EntityItem entityItem = new EntityItem((World) world, event.getPos().getX() + xOffset, event.getPos().getY() + yOffset, event.getPos().getZ() + zOffset, itemStack);
                entityItem.setPickupDelay(10);
                world.spawnEntity(entityItem);
            }
        }

        IBlockState newState;
        if (hitVec != null && hitVec.y < 0.5f) {
            newState = slabConverter.getSingleSlab(state, SlabType.TOP);
        } else {
            newState = slabConverter.getSingleSlab(state, SlabType.BOTTOM);
        }

        event.getWorld().setBlockState(event.getPos(), newState, 1 | 2);
        event.setCanceled(true);
    }

    @Nullable
    public static RayTraceResult rayTrace(EntityLivingBase entity, double length) {
        Vec3d startPos = new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
        Vec3d endPos = startPos.add(entity.getLookVec().x * length, entity.getLookVec().y * length, entity.getLookVec().z * length);
        return entity.world.rayTraceBlocks(startPos, endPos);
    }

}
