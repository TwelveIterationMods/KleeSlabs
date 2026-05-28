package net.blay09.mods.kleeslabs;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.BreakBlockEvent;
import net.blay09.mods.balm.api.event.PlayerLoginEvent;
import net.blay09.mods.balm.api.event.server.ServerStartedEvent;
import net.blay09.mods.kleeslabs.converter.EnchantedVerticalSlabConverter;
import net.blay09.mods.kleeslabs.converter.NemosVerticalSlabConverter;
import net.blay09.mods.kleeslabs.converter.QuarkVerticalSlabConverter;
import net.blay09.mods.kleeslabs.converter.SmartSlabConverter;
import net.blay09.mods.kleeslabs.network.ModNetworking;
import net.blay09.mods.kleeslabs.registry.SlabRegistry;
import net.blay09.mods.kleeslabs.registry.json.JsonCompatLoader;
import net.blay09.mods.kleeslabs.tag.ModBlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KleeSlabs {

    public static final String MOD_ID = "kleeslabs";

    public static final Logger logger = LogManager.getLogger();

    public static void initialize() {
        KleeSlabsConfig.initialize();

        SlabRegistry.registerSlabConverter(ModBlockTags.QUARK_VERTICAL_SLABS, new QuarkVerticalSlabConverter());
        SlabRegistry.registerSlabConverter(ModBlockTags.ENCHANTED_VERTICAL_SLABS, new EnchantedVerticalSlabConverter());
        SlabRegistry.registerSlabConverter(ModBlockTags.NEMOS_VERTICAL_SLABS, new NemosVerticalSlabConverter());
        SlabRegistry.registerSlabConverter(ModBlockTags.SLABS, new SmartSlabConverter());

        ModNetworking.initialize(Balm.getNetworking());

        Balm.addServerReloadListener(new ResourceLocation(MOD_ID, "json_registry"), new JsonCompatLoader());

        Balm.getEvents().onEvent(ServerStartedEvent.class, SlabDumpHandler::onServerStarted);
        Balm.getEvents().onEvent(PlayerLoginEvent.class, LoginSyncHandler::onPlayerLogin);
        Balm.getEvents().onEvent(BreakBlockEvent.class, BlockBreakHandler::onBreakBlock);
    }

    public static boolean isPlayerKleeSlabbing(Player player) {
        return switch (KleeSlabsConfig.getActive().mode) {
            case ALWAYS -> true;
            case ONLY_WHEN_SNEAKING -> player.isShiftKeyDown();
            case ONLY_WHEN_NOT_SNEAKING -> !player.isShiftKeyDown();
        };
    }
}
