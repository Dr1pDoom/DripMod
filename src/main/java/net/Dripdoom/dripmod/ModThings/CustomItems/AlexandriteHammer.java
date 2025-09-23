package net.Dripdoom.dripmod.ModThings.CustomItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;


public class AlexandriteHammer extends Item {
    public AlexandriteHammer(Properties pProperties) {
        super(pProperties);
    }

//Mines through a 3×3 area around a block that is mined.
//Does not work in creative, will have to create a separate event for it
//I'm also planning to add a custom GUI when it is right clicked.
//Stay tuned for that. Once I get my laptop again, I will start working on it.
//Comments have ended. Stop reading! You ain't a nerd!!

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pMiningEntity) {
        if (pLevel.isClientSide) return false;
        if(!(pMiningEntity instanceof Player player)) return false;

        Vec3 eyepos = player.getEyePosition();
        Vec3 lookangle = player.getLookAngle();
        Vec3 Raylength = eyepos.add(lookangle.scale(5));
        ClipContext clip = new ClipContext(eyepos,
                Raylength,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                pMiningEntity);
        BlockHitResult hit = pLevel.clip(clip);
        ServerPlayer player1 = (ServerPlayer) player;
        GameType gameMode = player1.gameMode.getGameModeForPlayer();

        if(hit.getType() == HitResult.Type.BLOCK && (gameMode.isSurvival() || gameMode.isCreative())){
            Direction dir = hit.getDirection();
            if(dir == Direction.UP || dir == Direction.DOWN){
                for(int x = -1; x <= 1; x++){
                    for(int z = -1; z <= 1; z++){
                    BlockPos newpos = pPos.offset(x, 0, z);
                    pLevel.destroyBlock(newpos, true);
                    }
                }
            }

            if(dir == Direction.NORTH || dir == Direction.SOUTH){
                for(int x = -1; x <= 1; x++){
                    for(int y = -1; y <= 1; y++){
                        BlockPos newpos = pPos.offset(x, y, 0);
                        pLevel.destroyBlock(newpos, true);
                    }
                }
            }

            if(dir == Direction.EAST || dir == Direction.WEST){
                for(int z = -1; z <= 1; z++){
                    for(int y = -1; y <= 1; y++){
                        BlockPos newpos = pPos.offset(0, y, z);
                        pLevel.destroyBlock(newpos, true);
                    }
                }
            }
        }

        return super.mineBlock(pStack, pLevel, pState, pPos, pMiningEntity);
    }
}
