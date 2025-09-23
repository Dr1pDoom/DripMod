package net.Dripdoom.dripmod.events;
import net.Dripdoom.dripmod.DripMod;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import java.util.ArrayList;
import java.util.List;

 // Replace with your modid
public class ModClientEvents {

    public static List<BlockPos> blockpos(Level level, AABB box){
        List<BlockPos> Positions = new ArrayList<>();
        BlockPos min = BlockPos.containing(box.minX, box.minY, box.minZ);
        BlockPos max = BlockPos.containing(box.maxX, box.maxY, box.maxZ);

        for(BlockPos pos : BlockPos.betweenClosed(min, max)){
            Positions.add(pos);
        }
        return Positions;
    }

    public static void Myevent(LivingDeathEvent event){
        final LivingEntity entity = event.getEntity();

        if(entity instanceof Sheep sheep){
            Vec3 SheepPos = sheep.getPosition(2);
            AABB Boundingbox = AABB.ofSize(SheepPos, 20, 20, 20);
            Level level = sheep.level();
            List<BlockPos> pos = blockpos(level, Boundingbox);

            if(level.isClientSide)return;
            for(BlockPos positions : pos){
                BlockState state = level.getBlockState(positions);
                if(state.is(BlockTags.LEAVES)){
                    level.destroyBlock(positions, true);
                }
            }
        }
    }
}
