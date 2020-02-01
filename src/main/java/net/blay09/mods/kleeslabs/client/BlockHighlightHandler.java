package net.blay09.mods.kleeslabs.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.blay09.mods.kleeslabs.registry.SlabRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class BlockHighlightHandler {

    @SubscribeEvent
    public static void onDrawBlockHighlight(DrawHighlightEvent.HighlightBlock event) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null || !KleeSlabs.isPlayerKleeSlabbing(player)) {
            return;
        }

        if (event.getTarget().getType() != RayTraceResult.Type.BLOCK) {
            return;
        }

        BlockPos pos = event.getTarget().getPos();
        BlockState target = player.world.getBlockState(pos);
        SlabConverter slabConverter = SlabRegistry.getSlabConverter(target.getBlock());
        if (slabConverter != null) {
            AxisAlignedBB halfAABB = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 0.5, pos.getZ() + 1);
            if (event.getTarget().getHitVec().y - (double) pos.getY() > 0.5) {
                halfAABB = halfAABB.offset(0, 0.5, 0);
            }

            MatrixStack matrixStack = event.getMatrix();
            IRenderTypeBuffer buffers = event.getBuffers();
            IVertexBuilder vertexBuilder = buffers.getBuffer(RenderType.lines());
            VoxelShape shape = VoxelShapes.create(halfAABB.grow(0.002));

            double camX = event.getInfo().getProjectedView().x;
            double camY = event.getInfo().getProjectedView().y;
            double camZ = event.getInfo().getProjectedView().z;
            WorldRenderer.drawShape(matrixStack, vertexBuilder, shape, -camX, -camY, -camZ, 0f, 0f, 0f, 0.4f);

            event.setCanceled(true);
        }
    }
}
