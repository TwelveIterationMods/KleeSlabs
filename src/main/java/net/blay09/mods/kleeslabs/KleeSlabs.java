package net.blay09.mods.kleeslabs;

import net.blay09.mods.kleeslabs.network.NetworkHandler;
import net.blay09.mods.kleeslabs.registry.json.JsonCompatLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(KleeSlabs.MOD_ID)
public class KleeSlabs {

    public static final String MOD_ID = "kleeslabs";

    public static final Logger logger = LogManager.getLogger();

    public KleeSlabs() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        MinecraftForge.EVENT_BUS.addListener(this::addReloadListeners);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, KleeSlabsConfig.commonSpec);
    }

    public void setupCommon(FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(NetworkHandler::init);
    }

    public void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new JsonCompatLoader());
    }

    public static boolean isPlayerKleeSlabbing(PlayerEntity player) {
        return !KleeSlabsConfig.COMMON.requireSneak.get() || player.isSneaking() != KleeSlabsConfig.COMMON.invertSneak.get();
    }
}
