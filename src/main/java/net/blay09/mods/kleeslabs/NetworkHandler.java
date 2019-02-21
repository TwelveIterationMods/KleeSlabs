package net.blay09.mods.kleeslabs;

import io.netty.buffer.UnpooledByteBufAllocator;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.event.EventNetworkChannel;

public class NetworkHandler {

    public static EventNetworkChannel eventChannel = NetworkRegistry.newEventChannel(new ResourceLocation(KleeSlabs.MOD_ID, "kleeslabs_compat"), () -> "1.0", it -> true, it -> true);

    public static void init() {
        eventChannel.addListener(NetworkHandler::gather);
        eventChannel.addListener(NetworkHandler::receiveItBoi);
    }

    private static void gather(NetworkEvent.GatherLoginPayloadsEvent event) {
        PacketBuffer packetBuffer = new PacketBuffer(UnpooledByteBufAllocator.DEFAULT.buffer());

        System.out.println("oh jeezie weezie");
        event.add(packetBuffer, new ResourceLocation(KleeSlabs.MOD_ID, "kleeslabs_compat"), "wat");
    }

    private static void receiveItBoi(NetworkEvent.LoginPayloadEvent event) {
        System.out.println("oh famma lamma");
    }

}
