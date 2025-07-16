package net.blay09.mods.kleeslabs;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.BreakBlockEvent;
import net.blay09.mods.balm.api.event.server.ServerStartedEvent;
import net.blay09.mods.kleeslabs.converter.DefaultSlabConverter;
import net.blay09.mods.kleeslabs.converter.EnchantedVerticalSlabConverter;
import net.blay09.mods.kleeslabs.converter.QuarkVerticalSlabConverter;
import net.blay09.mods.kleeslabs.network.ModNetworking;
import net.blay09.mods.kleeslabs.registry.SlabRegistry;
import net.blay09.mods.kleeslabs.tag.ModBlockTags;
import net.minecraft.world.entity.player.Player;

public class KleeSlabs {

    public static final String MOD_ID = "kleeslabs";

    public static void initialize() {
        KleeSlabsConfig.initialize();
        SlabRegistry.registerSlabConverter(ModBlockTags.SLABS, new DefaultSlabConverter());
        SlabRegistry.registerSlabConverter(ModBlockTags.QUARK_VERTICAL_SLABS, new QuarkVerticalSlabConverter());
        SlabRegistry.registerSlabConverter(ModBlockTags.ENCHANTED_VERTICAL_SLABS, new EnchantedVerticalSlabConverter());

        ModNetworking.initialize(Balm.getNetworking());

        Balm.getEvents().onEvent(ServerStartedEvent.class, SlabDumpHandler::onServerStarted);
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
