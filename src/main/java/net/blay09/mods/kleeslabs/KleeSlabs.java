package net.blay09.mods.kleeslabs;

import net.blay09.mods.kleeslabs.network.NetworkHandler;
import net.blay09.mods.kleeslabs.registry.json.JsonCompatLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(KleeSlabs.MOD_ID)
public class KleeSlabs {

    public static final String MOD_ID = "kleeslabs";

    public static final Logger logger = LogManager.getLogger();

    public KleeSlabs() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        MinecraftForge.EVENT_BUS.addListener(this::setupServer);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, KleeSlabsConfig.clientSpec);
    }

    public void setupCommon(FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(NetworkHandler::init);
    }

    public void setupServer(FMLServerAboutToStartEvent event) {
        IReloadableResourceManager resourceManager = event.getServer().getResourceManager();
        resourceManager.addReloadListener(new JsonCompatLoader());
    }

    public static boolean isPlayerKleeSlabbing(PlayerEntity player) {
        return !KleeSlabsConfig.CLIENT.requireSneak.get() || player.isSneaking() != KleeSlabsConfig.CLIENT.invertSneak.get();
    }
}
