package net.blay09.mods.kleeslabs.network;

import net.blay09.mods.kleeslabs.KleeSlabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {

    public static SimpleChannel instance;

    public static void init() {
        instance = NetworkRegistry.newSimpleChannel(new ResourceLocation(KleeSlabs.MOD_ID, "network"), () -> "1.0", it -> true, it -> true);

        instance.registerMessage(0, MessageKleeSlabsRegistry.class, MessageKleeSlabsRegistry::encode, MessageKleeSlabsRegistry::decode, MessageKleeSlabsRegistry::handle);
    }

    public static void ensureClientSide(NetworkEvent.Context context) {
        if (context.getDirection() != NetworkDirection.PLAY_TO_CLIENT) {
            throw new UnsupportedOperationException("Expected packet side does not match; expected client");
        }
    }

}
