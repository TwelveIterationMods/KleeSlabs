package net.blay09.mods.kleeslabs;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.kleeslabs.converter.HorizontalSlabConverter;
import net.blay09.mods.kleeslabs.converter.VerticalSlabConverter;
import net.blay09.mods.kleeslabs.registry.SlabRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class BlockBreakHandler {

    public static boolean onBreakBlock(LevelAccessor level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, @Nullable Player player) {
        if (player == null || Balm.hooks().isFakePlayer(player)) {
            return true;
        }

        if (!KleeSlabs.isPlayerKleeSlabbing(player)) {
            return true;
        }

        final var blockReachDistance = player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE);
        BlockHitResult rayTraceResult = rayTrace(player, blockReachDistance);
        final var hitSide = rayTraceResult.getDirection();
        var hitVec = rayTraceResult.getType() == BlockHitResult.Type.BLOCK ? rayTraceResult.getLocation() : null;

        // Relativize the hit vector around the player position
        if (hitVec != null) {
            hitVec = hitVec.add(-pos.getX(), -pos.getY(), -pos.getZ());
        }

        final var slabConverter = SlabRegistry.getSlabConverter(state).orElse(null);
        if (slabConverter == null || !slabConverter.isDoubleSlab(state)) {
            return true;
        }

        SlabType hit;
        SlabType stay;
        BlockState dropState;
        BlockState newState;

        if (slabConverter instanceof HorizontalSlabConverter horizontalSlabConverter) {
            if (hitVec != null && hitVec.y > 0.5f) {
                hit = SlabType.TOP;
                stay = SlabType.BOTTOM;
            } else {
                stay = SlabType.TOP;
                hit = SlabType.BOTTOM;
            }

            dropState = horizontalSlabConverter.getSingleSlab(state, level, pos, player, hit);
            newState = horizontalSlabConverter.getSingleSlab(state, level, pos, player, stay);
        } else if (slabConverter instanceof VerticalSlabConverter verticalSlabConverter) {
            if (hitSide.getAxis() != Direction.Axis.Y) {
                dropState = verticalSlabConverter.getSingleSlab(state, level, pos, player, hitSide.getOpposite());
                newState = verticalSlabConverter.getSingleSlab(state, level, pos, player, hitSide);
            } else {
                return true;
            }
        } else {
            return true;
        }

        if (level instanceof ServerLevel serverLevel && player.hasCorrectToolForDrops(dropState) && !player.getAbilities().instabuild) {
            Item slabItem = Item.byBlock(dropState.getBlock());
            if (slabItem != Items.AIR) {
                ItemStack itemStack = new ItemStack(slabItem);
                float scale = 0.7f;
                double xOffset = level.getRandom().nextFloat() * scale + 1f - scale * 0.5;
                double yOffset = level.getRandom().nextFloat() * scale + 1f - scale * 0.5;
                double zOffset = level.getRandom().nextFloat() * scale + 1f - scale * 0.5;
                ItemEntity entityItem = new ItemEntity(serverLevel,
                        pos.getX() + xOffset,
                        pos.getY() + yOffset,
                        pos.getZ() + zOffset,
                        itemStack);
                entityItem.setPickUpDelay(10);
                level.addFreshEntity(entityItem);
            }
        }

        level.setBlock(pos, newState, 1 | 2);
        return false;
    }

    public static BlockHitResult rayTrace(LivingEntity entity, double length) {
        Vec3 startPos = new Vec3(entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ());
        Vec3 endPos = startPos.add(entity.getLookAngle().x * length, entity.getLookAngle().y * length, entity.getLookAngle().z * length);
        ClipContext rayTraceContext = new ClipContext(startPos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
        return entity.level().clip(rayTraceContext);
    }

}
