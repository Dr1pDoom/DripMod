package net.Dripdoom.dripmod.ModThings.CustomItems;

import net.Dripdoom.dripmod.components.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraftforge.fml.common.Mod;

import java.util.List;


public class AlexandriteHammer extends Item {
    public AlexandriteHammer(Properties pProperties) {
        super(pProperties);
    }
<<<<<<< HEAD
    boolean aBoolean;
=======

//Mines through a 3×3 area around a block that is mined.
//Does not work in creative, will have to create a separate event for it
//I'm also planning to add a custom GUI when it is right clicked.
//Stay tuned for that. Once I get my laptop again, I will start working on it.
//Comments have ended. Stop reading! You ain't a nerd!!

>>>>>>> 4620073e90a8e415855b40425823e8a320e596c4
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

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if (pLevel.isClientSide()) return;
        if (!(pLivingEntity instanceof Player pPlayer)) return;

        Vec3 eyePos = pPlayer.getEyePosition();
        Vec3 look = pPlayer.getLookAngle();
        Vec3 target = eyePos.add(look.scale(10));

        AABB box = new AABB(eyePos, target).inflate(1.0D);

        List<Entity> entities = pLevel.getEntities(pPlayer, box,
                e -> !e.isSpectator() && e.isAlive());

        for (Entity entity : entities) {
            if (entity.getBoundingBox().intersects(box) && entity instanceof LivingEntity livingEntity) {
                livingEntity.setPos(target);
                livingEntity.setNoGravity(true);
                livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING));
            }
        }

        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

}
