package net.blay09.mods.kleeslabs;

import net.blay09.mods.kleeslabs.registry.JsonCompatLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, KleeSlabsConfig.clientSpec);
    }

    public void setupCommon(FMLCommonSetupEvent event) {
    }

    public void setupClient(FMLClientSetupEvent event) {
        IResourceManager resourceManager = event.getMinecraftSupplier().get().getResourceManager();
        if (resourceManager instanceof IReloadableResourceManager) {
            ((IReloadableResourceManager) resourceManager).addReloadListener(new JsonCompatLoader());
        }
    }

    public static boolean isPlayerKleeSlabbing(EntityPlayer player) {
        return !KleeSlabsConfig.CLIENT.requireSneak.get() || player.isSneaking() != KleeSlabsConfig.CLIENT.invertSneak.get();
    }
}
