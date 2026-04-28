package net.Dripdoom.dripmod.Networking.Packets;


import net.Dripdoom.dripmod.DripMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.ticks.TickContainerAccess;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.network.CustomPayloadEvent;
import org.jetbrains.annotations.Nullable;

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

                //This is chaotic has hell, unloaded chunks will get loaded so nobody is safe from this machine
                if(level.hasChunkAt(SectionPos.blockToSectionCoord(packet.PosX), SectionPos.blockToSectionCoord(packet.PosZ))){
                    ForgeChunkManager.forceChunk((ServerLevel) level,
                            DripMod.MOD_ID,
                            ctx.getSender().getBlockPosBelowThatAffectsMyMovement(),
                            SectionPos.blockToSectionCoord(packet.PosX),
                            SectionPos.blockToSectionCoord(packet.PosY),
                            true,
                            true);


                    summonLightning(level, packet.PosX(), packet.PosY(), packet.PosZ());

                    //The chunk will be loaded for 2 seconds to not keep it loaded forever
                    //I could have used ticking delay but this is fine as it is not repeated
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    ForgeChunkManager.forceChunk((ServerLevel) level,
                            DripMod.MOD_ID,
                            ctx.getSender().getBlockPosBelowThatAffectsMyMovement(),
                            SectionPos.blockToSectionCoord(packet.PosX),
                            SectionPos.blockToSectionCoord(packet.PosY),
                            false,
                            true);

                }
                else{
                    summonLightning(level, packet.PosX(), packet.PosY(), packet.PosZ());
                }
            }


        };
    }

    public static void summonLightning(Level level, double X, double Y, double Z){
        if(!level.isClientSide()){
            LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
            if(lightning != null){
                lightning.moveTo(X, Y, Z);
                level.addFreshEntity(lightning);
            }
        }
    }

}
