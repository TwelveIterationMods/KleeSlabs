package net.blay09.mods.kleeslabs;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.PlayerLoginEvent;
import net.blay09.mods.kleeslabs.network.KleeSlabsRegistryMessage;
import net.blay09.mods.kleeslabs.registry.SlabRegistry;
import net.blay09.mods.kleeslabs.registry.SlabRegistryData;

import java.util.ArrayList;
import java.util.List;

public class LoginSyncHandler {
    public static void onPlayerLogin(PlayerLoginEvent event) {
        boolean isFirst = true;
        final int pageSize = 20;
        List<SlabRegistryData> subList = new ArrayList<>();
        List<SlabRegistryData> entries = SlabRegistry.getSlabEntries();
        for (SlabRegistryData entry : entries) {
            subList.add(entry);

            if (subList.size() >= pageSize) {
                Balm.getNetworking().sendTo(event.getPlayer(), new KleeSlabsRegistryMessage(isFirst, subList));
                isFirst = false;
                subList = new ArrayList<>();
            }
        }

        if (!subList.isEmpty()) {
            Balm.getNetworking().sendTo(event.getPlayer(), new KleeSlabsRegistryMessage(isFirst, subList));
        }
    }
}
