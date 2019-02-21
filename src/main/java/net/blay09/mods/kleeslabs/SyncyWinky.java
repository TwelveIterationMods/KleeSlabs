package net.blay09.mods.kleeslabs;

import io.netty.buffer.UnpooledByteBufAllocator;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SyncyWinky {

    @SubscribeEvent
    public void gather(NetworkEvent.GatherLoginPayloadsEvent event) {
        PacketBuffer packetBuffer = new PacketBuffer(UnpooledByteBufAllocator.DEFAULT.buffer());

        System.out.println("oh jeezie weezie");
        event.add(packetBuffer, new ResourceLocation(KleeSlabs.MOD_ID, "kleeslabs_compat"), "wat");
    }

    @SubscribeEvent
    public void receiveItBoi(NetworkEvent.LoginPayloadEvent event) {
        System.out.println("oh famma lamma");
    }

}
