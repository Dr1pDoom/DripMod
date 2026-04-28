package net.Dripdoom.dripmod.Networking.Packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraftforge.event.network.CustomPayloadEvent;

public record DataSavePacket(double PosX, double PosY, double PosZ) {

    private static StreamCodec<FriendlyByteBuf, DataSavePacket> STREAM_CODEC =
            StreamCodec.of((buf, packet) -> {

                buf.writeDouble(packet.PosX);
                buf.writeDouble(packet.PosY);
                buf.writeDouble(packet.PosZ);

            }, (buf) -> new DataSavePacket(buf.readDouble(), buf.readDouble(), buf.readDouble()));

    public static StreamCodec<FriendlyByteBuf, DataSavePacket> packetCodec() {
        return STREAM_CODEC;
    }

    public static void handlePacket(DataSavePacket packet, CustomPayloadEvent.Context ctx) {

    }
}
