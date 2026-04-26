package net.Dripdoom.dripmod.Networking.PacketTypes;

import net.Dripdoom.dripmod.Networking.Packets.LightningC2SPacket;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.resources.ResourceLocation;

public class ModPacketTypes {


    private static <T extends Packet<ClientGamePacketListener>> PacketType<T> createClientbound(String pId) {
        return new PacketType<>(PacketFlow.CLIENTBOUND, ResourceLocation.withDefaultNamespace(pId));
    }

    private static <T extends Packet<ServerGamePacketListener>> PacketType<T> createServerbound(String pId) {
        return new PacketType<>(PacketFlow.SERVERBOUND, ResourceLocation.withDefaultNamespace(pId));
    }

}
