package net.Dripdoom.dripmod.ModThings.CustomBlocks;

import com.mojang.serialization.MapCodec;
import net.Dripdoom.dripmod.ModThings.CustomBlocks.CustomBlockEntities.LightningSummonerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        return new LightningSummonerBlockEntity(pPos, pState);
    }

    @Override
    protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
        return false;
    }

    @Override
    protected void onRemove(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
//        if(pState.getBlock() != pNewState.getBlock()){
//            if(pLevel.getBlockEntity(pPos) instanceof LightningSummonerBlockEntity lightningSummonerBlockEntity){
//                for(ItemStack items : lightningSummonerBlockEntity.drops()){
//                    popResource(pLevel, pPos, items);
//                    pLevel.updateNeighbourForOutputSignal(pPos, pState.getBlock());
//                }
//            }
//        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if(!pLevel.isClientSide){
            if(pLevel.getBlockEntity(pPos) instanceof LightningSummonerBlockEntity lightningSummonerBlockEntity) {
                ((ServerPlayer) pPlayer)
                        .openMenu(new SimpleMenuProvider(lightningSummonerBlockEntity, Component.literal("")), pPos);

                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }
}
