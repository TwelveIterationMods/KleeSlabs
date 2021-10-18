package net.blay09.mods.kleeslabs.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.blay09.mods.kleeslabs.registry.SlabRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
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

        if (event.getTarget().getType() != HitResult.Type.BLOCK) {
            return;
        }

        BlockPos pos = event.getTarget().getPos();
        BlockState target = player.level.getBlockState(pos);
        SlabConverter slabConverter = SlabRegistry.getSlabConverter(target.getBlock());
        if (slabConverter != null) {
            AABB halfAABB = new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 0.5, pos.getZ() + 1);
            if (event.getTarget().getHitVec().y - (double) pos.getY() > 0.5) {
                halfAABB = halfAABB.move(0, 0.5, 0);
            }

            PoseStack matrixStack = event.getMatrix();
            MultiBufferSource buffers = event.getBuffers();
            VertexConsumer vertexBuilder = buffers.getBuffer(RenderType.LINES);
            VoxelShape shape = Shapes.create(halfAABB.inflate(0.002));

            double camX = event.getInfo().getProjectedView().x;
            double camY = event.getInfo().getProjectedView().y;
            double camZ = event.getInfo().getProjectedView().z;
            LevelRenderer.renderShape(matrixStack, vertexBuilder, shape, -camX, -camY, -camZ, 0f, 0f, 0f, 0.4f);

            event.setCanceled(true);
        }
    }
}
