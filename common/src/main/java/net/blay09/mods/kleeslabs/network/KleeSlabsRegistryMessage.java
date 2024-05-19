package net.blay09.mods.kleeslabs.network;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.kleeslabs.KleeSlabs;
import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.blay09.mods.kleeslabs.registry.SlabRegistry;
import net.blay09.mods.kleeslabs.registry.SlabRegistryData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class KleeSlabsRegistryMessage implements CustomPacketPayload {

    public static CustomPacketPayload.Type<KleeSlabsRegistryMessage> TYPE = new CustomPacketPayload.Type<>(new ResourceLocation(KleeSlabs.MOD_ID, "registry"));

    private final boolean isFirst;
    private final List<SlabRegistryData> data;

    public KleeSlabsRegistryMessage(boolean isFirst, List<SlabRegistryData> data) {
        this.isFirst = isFirst;
        this.data = data;
    }

    public static void encode(FriendlyByteBuf buf, KleeSlabsRegistryMessage message) {
        buf.writeBoolean(message.isFirst);
        buf.writeShort(message.data.size());
        for (SlabRegistryData data : message.data) {
            buf.writeUtf(data.getConverterClass().getName());
            buf.writeResourceLocation(Balm.getRegistries().getKey(data.getSingleSlab()));
            buf.writeResourceLocation(Balm.getRegistries().getKey(data.getDoubleSlab()));
        }
    }

    @SuppressWarnings("unchecked")
    public static KleeSlabsRegistryMessage decode(FriendlyByteBuf buf) {
        boolean isFirst = buf.readBoolean();
        int size = buf.readShort();

        List<SlabRegistryData> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            try {
                String converterClassName = buf.readUtf(Short.MAX_VALUE);
                ResourceLocation singleBlockId = buf.readResourceLocation();
                ResourceLocation doubleBlockId = buf.readResourceLocation();

                Class<? extends SlabConverter> converterClass = (Class<? extends SlabConverter>) Class.forName(converterClassName);
                Block singleSlab = Balm.getRegistries().getBlock(singleBlockId);
                Block doubleSlab = Balm.getRegistries().getBlock(doubleBlockId);
                if (singleSlab != null && doubleSlab != null) {
                    data.add(new SlabRegistryData(converterClass, singleSlab, doubleSlab));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return new KleeSlabsRegistryMessage(isFirst, data);
    }

    public static void handle(Player player, KleeSlabsRegistryMessage message) {
        if (message.isFirst) {
            SlabRegistry.clearRegistry();
        }

        for (SlabRegistryData data : message.data) {
            SlabRegistry.registerSlab(data);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
