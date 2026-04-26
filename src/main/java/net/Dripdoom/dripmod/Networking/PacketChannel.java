package net.Dripdoom.dripmod.Networking;

import net.Dripdoom.dripmod.DripMod;
import net.Dripdoom.dripmod.Networking.Packets.DataLoadPacket;
import net.Dripdoom.dripmod.Networking.Packets.DataSavePacket;
import net.Dripdoom.dripmod.Networking.Packets.LightningC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.*;

public class PacketChannel {

    private static final String PROTOCOL_VERSION = "3";
    private static final ResourceLocation location = ResourceLocation.fromNamespaceAndPath(DripMod.MOD_ID, "main");
    private static int id = 1;

    public static final SimpleChannel channel = ChannelBuilder.named(ResourceLocation.fromNamespaceAndPath(DripMod.MOD_ID, "dripmod_channel"))
            .networkProtocolVersion(67)
            .acceptedVersions(((status, version) -> status == Channel.VersionTest.Status.PRESENT && version == 67))
            .simpleChannel();



    public static void registerLightningPacket(){
        channel.messageBuilder(LightningC2SPacket.class, id++)
                .encoder(LightningC2SPacket::encode)
                .decoder(LightningC2SPacket::decode)
                .consumerMainThread(LightningC2SPacket.handlePacket())
                .add();
    }

    public static void registerDataSavePacket(){
        channel.messageBuilder(DataSavePacket.class, id++)
                .codec(DataSavePacket.packetCodec())
                .consumerMainThread(DataSavePacket::handlePacket)
                .add();
    }

    public static void registerDataLoadPacket(){
        channel.messageBuilder(DataLoadPacket.class, id++)
                .codec(DataLoadPacket.packetCodec())
                .consumerMainThread(DataLoadPacket::handlePacket)
                .add();
    }

}
