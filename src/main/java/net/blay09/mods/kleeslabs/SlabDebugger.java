package net.blay09.mods.kleeslabs;

import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

//@Mod.EventBusSubscriber
class SlabDebugger {
    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        ResourceLocation registryName = event.getItemStack().getItem().getRegistryName();
        if (registryName != null) {
            event.getToolTip().add(new StringTextComponent("Mod: " + registryName.getNamespace()));
            event.getToolTip().add(new StringTextComponent("Name: " + registryName.getPath()));
        }
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getSide() == LogicalSide.CLIENT) {
            BlockState state = event.getWorld().getBlockState(event.getPos());
            ResourceLocation registryName = state.getBlock().getRegistryName();
            if (registryName != null) {
                StringTextComponent message = new StringTextComponent("Mod: " + registryName.getNamespace() + " Name: " + registryName.getPath());
                event.getEntityPlayer().sendStatusMessage(message, false);
            }
        }
    }
}
