package net.blay09.mods.kleeslabs;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.PlayerLoginEvent;
import net.blay09.mods.kleeslabs.network.KleeSlabsRegistryMessage;
import net.blay09.mods.kleeslabs.network.ModNetworking;
import net.blay09.mods.kleeslabs.registry.SlabRegistry;
import net.blay09.mods.kleeslabs.registry.SlabRegistryData;
import net.blay09.mods.kleeslabs.registry.json.JsonCompatLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class KleeSlabs {

    public static final String MOD_ID = "kleeslabs";

    public static final Logger logger = LogManager.getLogger();

    public static void initialize() {
        KleeSlabsConfig.initialize();

        ModNetworking.initialize(Balm.getNetworking());

        Balm.addServerReloadListener(new ResourceLocation(MOD_ID, "json_registry"), new JsonCompatLoader());

        Balm.getEvents().onEvent(PlayerLoginEvent.class, event -> {
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

            if (subList.size() > 0) {
                Balm.getNetworking().sendTo(event.getPlayer(), new KleeSlabsRegistryMessage(isFirst, subList));
            }
        });

        Balm.initialize(MOD_ID);
    }

    public static boolean isPlayerKleeSlabbing(Player player) {
        return switch (KleeSlabsConfig.getActive().mode) {
            case ALWAYS -> true;
            case ONLY_WHEN_SNEAKING -> player.isShiftKeyDown();
            case ONLY_WHEN_NOT_SNEAKING -> !player.isShiftKeyDown();
        };
    }
}
