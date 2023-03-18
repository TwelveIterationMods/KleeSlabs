package net.blay09.mods.kleeslabs;

import net.blay09.mods.balm.api.event.server.ServerStartedEvent;
import net.minecraft.core.Registry;
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
        Map<String, List<ResourceLocation>> slabsByMod = Registry.BLOCK.keySet()
                .stream()
                .filter(itemName -> itemName.getPath().endsWith("_slab"))
                .collect(Collectors.groupingBy(ResourceLocation::getNamespace));

        for (Map.Entry<String, List<ResourceLocation>> slabs : slabsByMod.entrySet()) {
            String slabsOutput = slabs.getValue().stream()
                    .map(ResourceLocation::getPath)
                    .map(it -> "\"" + it + "\"")
                    .sorted()
                    .collect(Collectors.joining(",\n"));
            log.info("Slabs from mod {}:\n{}", slabs.getKey(), slabsOutput);
        }
    }
}
