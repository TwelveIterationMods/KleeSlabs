package net.blay09.mods.kleeslabs.network;

import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.registry.SlabRegistry;
import net.blay09.mods.kleeslabs.registry.SlabRegistryData;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = KleeSlabs.MOD_ID)
public class SyncHandler {

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        PacketDistributor.PacketTarget target = PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer());
        boolean isFirst = true;
        final int pageSize = 20;
        List<SlabRegistryData> subList = new ArrayList<>();
        List<SlabRegistryData> entries = SlabRegistry.getSlabEntries();
        for (SlabRegistryData entry : entries) {
            subList.add(entry);

            if (subList.size() >= pageSize) {
                NetworkHandler.instance.send(target, new MessageKleeSlabsRegistry(isFirst, subList));
                isFirst = false;
                subList = new ArrayList<>();
            }
        }

        if (subList.size() > 0) {
            NetworkHandler.instance.send(target, new MessageKleeSlabsRegistry(isFirst, subList));
        }
    }

}
