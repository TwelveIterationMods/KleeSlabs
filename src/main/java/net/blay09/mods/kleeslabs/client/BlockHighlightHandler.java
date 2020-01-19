package net.blay09.mods.kleeslabs.client;

import com.mojang.blaze3d.platform.GlStateManager;
import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.blay09.mods.kleeslabs.registry.SlabRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
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
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.lineWidth(Math.max(2.5f, (float) Minecraft.getInstance().func_228018_at_().getFramebufferWidth() / 1920f * 2.5f));
            GlStateManager.disableTexture();
            GlStateManager.depthMask(false);
            GlStateManager.matrixMode(5889);
            GlStateManager.pushMatrix();
            GlStateManager.scalef(1f, 1f, 0.999f);

            double offsetX = event.getInfo().getProjectedView().x;
            double offsetY = event.getInfo().getProjectedView().y;
            double offsetZ = event.getInfo().getProjectedView().z;
            AxisAlignedBB halfAABB = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 0.5, pos.getZ() + 1);
            if (event.getTarget().getHitVec().y - (double) pos.getY() > 0.5) {
                halfAABB = halfAABB.offset(0, 0.5, 0);
            }

            WorldRenderer.drawSelectionBoundingBox(halfAABB.grow(0.002).offset(-offsetX, -offsetY, -offsetZ), 0f, 0f, 0f, 0.4f);

            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture();
            GlStateManager.disableBlend();

            event.setCanceled(true);
        }
    }
}
