package net.Dripdoom.dripmod.Networking.Packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraftforge.event.network.CustomPayloadEvent;

public record DataLoadPacket(double PosX, double PosY, double PosZ) {

    private static StreamCodec<FriendlyByteBuf, DataLoadPacket> STREAM_CODEC =
            StreamCodec.of((buf, packet) -> {

                buf.writeDouble(packet.PosX);
                buf.writeDouble(packet.PosY);
                buf.writeDouble(packet.PosZ);

            }, (buf) -> new DataLoadPacket(buf.readDouble(), buf.readDouble(), buf.readDouble()));

    public static StreamCodec<FriendlyByteBuf, DataLoadPacket> packetCodec() {
        return STREAM_CODEC;
    }

    public static void handlePacket(DataLoadPacket packet, CustomPayloadEvent.Context ctx) {

    }

}
