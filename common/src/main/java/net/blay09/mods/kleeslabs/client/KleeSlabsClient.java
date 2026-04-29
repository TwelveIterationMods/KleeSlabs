package net.blay09.mods.kleeslabs.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blay09.mods.balm.client.BalmClientRegistrars;
import net.blay09.mods.balm.client.platform.event.callback.RenderCallback;
import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.converter.HorizontalSlabConverter;
import net.blay09.mods.kleeslabs.registry.SlabRegistry;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.BlockOutlineRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class KleeSlabsClient {

    public static void initialize(BalmClientRegistrars registrars) {
        RenderCallback.BlockHighlight.EVENT.register(KleeSlabsClient::onDrawBlockHighlight);
    }

    private static BlockOutlineRenderState onDrawBlockHighlight(BlockHitResult hitResult, Camera camera, BlockOutlineRenderState blockOutlineRenderState) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !KleeSlabs.isPlayerKleeSlabbing(player)) {
            return blockOutlineRenderState;
        }

        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return blockOutlineRenderState;
        }

        BlockPos pos = hitResult.getBlockPos();
        BlockState state = player.level().getBlockState(pos);
        final var slabConverter = SlabRegistry.getSlabConverter(state).orElse(null);
        if (slabConverter instanceof HorizontalSlabConverter && slabConverter.isDoubleSlab(state)) {
            AABB halfAABB = new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 0.5, pos.getZ() + 1);
            if (hitResult.getLocation().y - (double) pos.getY() > 0.5) {
                halfAABB = halfAABB.move(0, 0.5, 0);
            }

            VoxelShape shape = Shapes.create(halfAABB.inflate(0.002));
            final var translucent = blockOutlineRenderState.isTranslucent();
            final var highContrast = blockOutlineRenderState.highContrast();
            return new BlockOutlineRenderState(pos, translucent, highContrast, shape);
        }

        return blockOutlineRenderState;
    }
}
