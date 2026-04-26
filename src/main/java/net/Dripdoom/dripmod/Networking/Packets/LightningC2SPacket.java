package net.Dripdoom.dripmod.Networking.Packets;


import net.Dripdoom.dripmod.DripMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public record LightningC2SPacket(double PosX, double PosY, double PosZ) {


    public void encode(FriendlyByteBuf buf){
        buf.writeDouble(PosX);
        buf.writeDouble(PosY);
        buf.writeDouble(PosZ);
    }

    public static LightningC2SPacket decode(FriendlyByteBuf buf){
        return new LightningC2SPacket(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static BiConsumer<LightningC2SPacket, CustomPayloadEvent.Context> handlePacket(){
        return (packet, ctx) -> {


            //logic
            if(ctx.getSender() != null){
                Level level = ctx.getSender().level();
                if(!level.isClientSide()){
                    LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
                    if(lightning != null){
                        lightning.moveTo(packet.PosX, packet.PosY, packet.PosZ);
                        level.addFreshEntity(lightning);
                    }
                }
            }



        };
    }

}
