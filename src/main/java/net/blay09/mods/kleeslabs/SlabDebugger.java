package net.blay09.mods.kleeslabs;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//@Mod.EventBusSubscriber
class SlabDebugger {
    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        ResourceLocation registryName = event.getItemStack().getItem().getRegistryName();
        if (registryName != null) {
            event.getToolTip().add(new TextComponentString("Mod: " + registryName.getNamespace()));
            event.getToolTip().add(new TextComponentString("Name: " + registryName.getPath()));
        }
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getSide() == Dist.CLIENT) {
            IBlockState state = event.getWorld().getBlockState(event.getPos());
            ResourceLocation registryName = state.getBlock().getRegistryName();
            if (registryName != null) {
                TextComponentString message = new TextComponentString("Mod: " + registryName.getNamespace() + " Name: " + registryName.getPath());
                event.getEntityPlayer().sendStatusMessage(message, false);
            }
        }
    }
}
