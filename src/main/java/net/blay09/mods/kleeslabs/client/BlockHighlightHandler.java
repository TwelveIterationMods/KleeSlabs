package net.blay09.mods.kleeslabs.client;

import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.blay09.mods.kleeslabs.registry.SlabRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class BlockHighlightHandler {

    @SubscribeEvent
    public static void onDrawBlockHighlight(DrawBlockHighlightEvent event) {
        if (!KleeSlabs.isPlayerKleeSlabbing(event.getPlayer())) {
            return;
        }

        if (event.getTarget().type != RayTraceResult.Type.BLOCK) {
            return;
        }

        BlockPos pos = event.getTarget().getBlockPos();
        //noinspection ConstantConditions missing @Nullable
        if (pos == null) {
            return;
        }

        IBlockState target = event.getPlayer().world.getBlockState(pos);
        SlabConverter slabConverter = SlabRegistry.getSlabConverter(target.getBlock());
        if (slabConverter != null) {
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.lineWidth(2f);
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

            WorldRenderer.drawSelectionBoundingBox(halfAABB.grow(0.002).offset(-offsetX, -offsetY, -offsetZ), 0f, 0f, 0f, 0.4f);
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            event.setCanceled(true);
        }
    }
}
