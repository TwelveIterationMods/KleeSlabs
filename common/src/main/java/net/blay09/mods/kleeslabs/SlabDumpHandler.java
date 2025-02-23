package net.blay09.mods.kleeslabs;

import net.blay09.mods.balm.api.event.server.ServerStartedEvent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SlabDumpHandler {

    private static final Logger log = LoggerFactory.getLogger(SlabDumpHandler.class);

    public static void onServerStarted(ServerStartedEvent event) {
        if (KleeSlabsConfig.getActive().dumpSlabs) {
            dumpSlabs();
        }
    }

    public static void dumpSlabs() {
        final var slabsByMod = BuiltInRegistries.BLOCK.keySet()
                .stream()
                .filter(itemName -> itemName.getPath().endsWith("_slab") && !itemName.getPath().contains("vertical"))
                .collect(Collectors.groupingBy(ResourceLocation::getNamespace));
        final var verticalSlabsByMod = BuiltInRegistries.BLOCK.keySet()
                .stream()
                .filter(itemName -> itemName.getPath().endsWith("_slab") && itemName.getPath().contains("vertical"))
                .collect(Collectors.groupingBy(ResourceLocation::getNamespace));

        for (final var slabs : slabsByMod.entrySet()) {
            final var slabsOutput = slabs.getValue().stream()
                    .map(ResourceLocation::getPath)
                    .map(it -> "\"" + it + "\"")
                    .sorted()
                    .collect(Collectors.joining(",\n"));
            log.info("Slabs from mod {}:\n{}", slabs.getKey(), slabsOutput);
        }

        for (final var verticalSlabs : verticalSlabsByMod.entrySet()) {
            final var slabsOutput = verticalSlabs.getValue().stream()
                    .map(ResourceLocation::getPath)
                    .map(it -> "\"" + it + "\"")
                    .sorted()
                    .collect(Collectors.joining(",\n"));
            log.info("Vertical slabs from mod {}:\n{}", verticalSlabs.getKey(), slabsOutput);
        }
    }
}
