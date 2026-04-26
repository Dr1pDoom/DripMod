package net.Dripdoom.dripmod.events;

import net.Dripdoom.dripmod.DripMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.ObjectUtils;


@Mod.EventBusSubscriber(modid = DripMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MycustomEvent {

    @SubscribeEvent
    public static void Myevent(LivingDeathEvent event){
        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if(level.isClientSide) return;
        if(entity instanceof Sheep sheep && event.getSource().getEntity() != null && event.getSource().getEntity() instanceof Player player){
            Vec3 SheepPos = sheep.position();
            AABB Boundingbox = AABB.ofSize(SheepPos, 20, 20, 20);
            player.sendSystemMessage(Component.literal("YOU SHALL DIE!!!!!"));

//            level.explode(sheep, SheepPos.x, SheepPos.y, SheepPos.z, 6.0F, Level.ExplosionInteraction.BLOCK);

            for(double x = Boundingbox.minX; x <= Boundingbox.maxX; x++){
                for(double y = Boundingbox.minY; y <= Boundingbox.maxY; y++){
                    for(double z = Boundingbox.minZ; z <= Boundingbox.maxZ; z++){
                        BlockPos pos1 = BlockPos.containing(x, y, z);
                        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos().set(pos1);
                        if(level.getBlockState(pos).is(BlockTags.LEAVES)){
                            level.destroyBlock(pos, true);
                        }
                    }
                }
            }

        }
    }

}
