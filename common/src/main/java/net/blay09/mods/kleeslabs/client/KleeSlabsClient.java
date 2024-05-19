package net.blay09.mods.kleeslabs.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.balm.api.event.client.BlockHighlightDrawEvent;
import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.blay09.mods.kleeslabs.mixin.LevelRendererAccessor;
import net.blay09.mods.kleeslabs.registry.SlabRegistry;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class KleeSlabsClient {

    public static void initialize() {
        Balm.getEvents().onEvent(BlockHighlightDrawEvent.class, KleeSlabsClient::onDrawBlockHighlight);
    }

    private static void onDrawBlockHighlight(BlockHighlightDrawEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !KleeSlabs.isPlayerKleeSlabbing(player)) {
            return;
        }

        BlockHitResult hitResult = event.getHitResult();
        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }

        BlockPos pos = hitResult.getBlockPos();
        BlockState state = player.level().getBlockState(pos);
        SlabConverter slabConverter = SlabRegistry.getSlabConverter(state.getBlock());
        if (slabConverter != null && slabConverter.isDoubleSlab(state)) {
            AABB halfAABB = new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 0.5, pos.getZ() + 1);
            if (hitResult.getLocation().y - (double) pos.getY() > 0.5) {
                halfAABB = halfAABB.move(0, 0.5, 0);
            }

            PoseStack poseStack = event.getPoseStack();
            MultiBufferSource buffers = event.getMultiBufferSource();
            VertexConsumer vertexBuilder = buffers.getBuffer(RenderType.LINES);
            VoxelShape shape = Shapes.create(halfAABB.inflate(0.002));

            Camera camera = event.getCamera();
            double camX = camera.getPosition().x;
            double camY = camera.getPosition().y;
            double camZ = camera.getPosition().z;
            LevelRendererAccessor.callRenderShape(poseStack, vertexBuilder, shape, -camX, -camY, -camZ, 0f, 0f, 0f, 0.4f);

            event.setCanceled(true);
        }
    }
}
