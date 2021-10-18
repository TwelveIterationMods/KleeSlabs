package net.blay09.mods.kleeslabs;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.BreakBlockEvent;
import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.blay09.mods.kleeslabs.registry.SlabRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class BlockBreakHandler {

    public static void onBreakBlock(BreakBlockEvent event) {
        if (Balm.getHooks().isFakePlayer(event.getPlayer())) {
            return;
        }

        if (!KleeSlabs.isPlayerKleeSlabbing(event.getPlayer())) {
            return;
        }

        BlockHitResult rayTraceResult = rayTrace(event.getPlayer(), 6);
        Vec3 hitVec = rayTraceResult.getType() == BlockHitResult.Type.BLOCK ? rayTraceResult.getLocation() : null;

        // Relativize the hit vector around the player position
        if (hitVec != null) {
            hitVec = hitVec.add(-event.getPos().getX(), -event.getPos().getY(), -event.getPos().getZ());
        }

        BlockState state = event.getState();
        SlabConverter slabConverter = SlabRegistry.getSlabConverter(state.getBlock());
        if (slabConverter == null || !slabConverter.isDoubleSlab(state)) {
            return;
        }

        BlockState dropState = slabConverter.getSingleSlab(state, SlabType.BOTTOM);
        Level level = event.getLevel();
        if (!level.isClientSide() && event.getPlayer().hasCorrectToolForDrops(event.getState()) && !event.getPlayer().getAbilities().instabuild) {
            Item slabItem = Item.byBlock(dropState.getBlock());
            if (slabItem != Items.AIR) {
                ItemStack itemStack = new ItemStack(slabItem);
                float scale = 0.7f;
                double xOffset = level.getRandom().nextFloat() * scale + 1f - scale * 0.5;
                double yOffset = level.getRandom().nextFloat() * scale + 1f - scale * 0.5;
                double zOffset = level.getRandom().nextFloat() * scale + 1f - scale * 0.5;
                ItemEntity entityItem = new ItemEntity(level, event.getPos().getX() + xOffset, event.getPos().getY() + yOffset, event.getPos().getZ() + zOffset, itemStack);
                entityItem.setPickUpDelay(10);
                level.addFreshEntity(entityItem);
            }
        }

        BlockState newState;
        if (hitVec != null && hitVec.y < 0.5f) {
            newState = slabConverter.getSingleSlab(state, SlabType.TOP);
        } else {
            newState = slabConverter.getSingleSlab(state, SlabType.BOTTOM);
        }

        event.getLevel().setBlock(event.getPos(), newState, 1 | 2);
        event.setCanceled(true);
    }

    public static BlockHitResult rayTrace(LivingEntity entity, double length) {
        Vec3 startPos = new Vec3(entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ());
        Vec3 endPos = startPos.add(entity.getLookAngle().x * length, entity.getLookAngle().y * length, entity.getLookAngle().z * length);
        ClipContext rayTraceContext = new ClipContext(startPos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
        return entity.level.clip(rayTraceContext);
    }

}
