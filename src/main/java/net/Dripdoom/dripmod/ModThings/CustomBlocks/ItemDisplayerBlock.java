package net.Dripdoom.dripmod.ModThings.CustomBlocks;

import com.mojang.serialization.MapCodec;
import net.Dripdoom.dripmod.ModThings.CustomBlocks.CustomBlockEntities.ItemDisplayerBlockEntity;
import net.Dripdoom.dripmod.Networking.PacketChannel;
import net.Dripdoom.dripmod.Networking.Packets.DataLoadPacket;
import net.Dripdoom.dripmod.Networking.Packets.LightningC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerConnectionListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemDisplayerBlock extends BaseEntityBlock {

    private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 13, 14);
    private static MapCodec<ItemDisplayerBlock> CODEC = simpleCodec(ItemDisplayerBlock::new);

    public ItemDisplayerBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public static VoxelShape getShape() {
        return SHAPE;
    }

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ItemDisplayerBlockEntity(pPos, pState);
    }

    @Override
    protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
        return false;
    }

    @Override
    protected void onRemove(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if(pState.getBlock() != pNewState.getBlock()){
            if(pLevel.getBlockEntity(pPos) instanceof ItemDisplayerBlockEntity itemDisplayerBlockEntity){
                for(ItemStack items : itemDisplayerBlockEntity.drops()){
                    popResource(pLevel, pPos, items);
                    pLevel.updateNeighbourForOutputSignal(pPos, pState.getBlock());
                }
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

//    @Override
//    @NotNull
//    protected ItemInteractionResult useItemOn(@NotNull ItemStack pStack, @NotNull BlockState pState,
//                                                       Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer,
//                                                       @NotNull InteractionHand pHand, @NotNull BlockHitResult pHitResult) {
//        if(pLevel.getBlockEntity(pPos) instanceof ItemDisplayerBlockEntity itemDisplayerBlockEntity) {
//            for (int slot = 0; slot < itemDisplayerBlockEntity.inventory.getSlots(); slot++) {
//                if (itemDisplayerBlockEntity.inventory.getStackInSlot(slot).isEmpty() && !pStack.isEmpty() && !pPlayer.isCrouching()) {
//                    itemDisplayerBlockEntity.inventory.insertItem(slot, pStack, false);
//                    pStack.shrink(1);
//                    pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
//                    break;
//                }
//                else if (!itemDisplayerBlockEntity.inventory.getStackInSlot(slot).isEmpty() && pPlayer.getItemInHand(pHand).isEmpty() && !pPlayer.isCrouching()) {
//                    pPlayer.setItemInHand(pHand, itemDisplayerBlockEntity.inventory.extractItem(slot, 1, false));
//                    pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
//                    break;
//                }
//            }
//        }
//        return ItemInteractionResult.SUCCESS;
//    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if(!pLevel.isClientSide){
            if(pLevel.getBlockEntity(pPos) instanceof ItemDisplayerBlockEntity itemDisplayerBlockEntity) {
                ((ServerPlayer) pPlayer)
                        .openMenu(new SimpleMenuProvider(itemDisplayerBlockEntity, Component.literal("")), pPos);


//                Connection connection = ((ServerPlayer)pPlayer).connection.getConnection();
//
//                PacketChannel.channel.send(new LightningC2SPacket(
//                        itemDisplayerBlockEntity.getSavePosX(),
//                        itemDisplayerBlockEntity.getSavePosY(),
//                        itemDisplayerBlockEntity.getSavePosZ()
//                ), connection);


                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }
}
