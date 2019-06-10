package net.blay09.mods.kleeslabs.network;

import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.blay09.mods.kleeslabs.registry.SlabRegistry;
import net.blay09.mods.kleeslabs.registry.SlabRegistryData;
import net.minecraft.block.Block;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class MessageKleeSlabsRegistry {

    private final boolean isFirst;
    private final List<SlabRegistryData> data;

    public MessageKleeSlabsRegistry(boolean isFirst, List<SlabRegistryData> data) {
        this.isFirst = isFirst;
        this.data = data;
    }

    public static void encode(MessageKleeSlabsRegistry message, PacketBuffer buf) {
        buf.writeBoolean(message.isFirst);
        buf.writeShort(message.data.size());
        for (SlabRegistryData data : message.data) {
            buf.writeString(data.getConverterClass().getName());
            buf.writeResourceLocation(Objects.requireNonNull(data.getSingleSlab().getRegistryName()));
            buf.writeResourceLocation(Objects.requireNonNull(data.getDoubleSlab().getRegistryName()));
        }
    }

    @SuppressWarnings("unchecked")
    public static MessageKleeSlabsRegistry decode(PacketBuffer buf) {
        boolean isFirst = buf.readBoolean();
        int size = buf.readShort();

        List<SlabRegistryData> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            try {
                String converterClassName = buf.readString(Short.MAX_VALUE);
                ResourceLocation singleBlockId = buf.readResourceLocation();
                ResourceLocation doubleBlockId = buf.readResourceLocation();

                Class<? extends SlabConverter> converterClass = (Class<? extends SlabConverter>) Class.forName(converterClassName);
                Block singleSlab = ForgeRegistries.BLOCKS.getValue(singleBlockId);
                Block doubleSlab = ForgeRegistries.BLOCKS.getValue(doubleBlockId);
                if (singleSlab != null && doubleSlab != null) {
                    data.add(new SlabRegistryData(converterClass, singleSlab, doubleSlab));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return new MessageKleeSlabsRegistry(isFirst, data);
    }

    public static void handle(MessageKleeSlabsRegistry message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        NetworkHandler.ensureClientSide(context);

        context.enqueueWork(() -> {
            if (message.isFirst) {
                SlabRegistry.clearRegistry();
            }

            for (SlabRegistryData data : message.data) {
                SlabRegistry.registerSlab(data);
            }
        });
    }

}
