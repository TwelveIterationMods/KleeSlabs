package net.blay09.mods.kleeslabs;

import net.blay09.mods.kleeslabs.client.KleeSlabsClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(KleeSlabs.MOD_ID)
public class ForgeKleeSlabs {

    public ForgeKleeSlabs() {
        KleeSlabs.initialize();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> KleeSlabsClient::initialize);
    }

}
