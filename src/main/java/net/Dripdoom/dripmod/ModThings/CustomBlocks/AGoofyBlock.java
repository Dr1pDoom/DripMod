package net.Dripdoom.dripmod.ModThings.CustomBlocks;

import net.Dripdoom.dripmod.ModThings.CustomItems.ItemRegistries.ModItem;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AGoofyBlock extends Block {

    public AGoofyBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        ResourceKey<Level> dimension = pLevel.dimension();
        double PosX = pPos.getX();
        double PosZ = pPos.getZ();
        double PosY = pPos.getZ();
        double value = (PosX * PosX) + (PosY * PosY) + (PosZ * PosZ);
        double anothervalue = Math.sqrt(value) * 0.008987;
        int distance = ((int) anothervalue); //Setting the redstone output using distance formula
        if(dimension == Level.NETHER) return Math.min(distance, 15);
        return 0;
    }

    @Override
    protected void spawnAfterBreak(BlockState pState, @NotNull ServerLevel pLevel, BlockPos pPos, ItemStack pStack, boolean pDropExperience) {
        List<Item> ItemDropsList = List.of(
                Items.DIAMOND,
                ModItem.Alexandrite.get(),
                ModItem.Raw_Alexandrite.get()
        );

        RandomSource random = pLevel.getRandom();
        Item randomItem = ItemDropsList.get(random.nextInt(ItemDropsList.size()));
        int dropCount = random.nextInt(4);
        popResource(pLevel, pPos, new ItemStack(randomItem, dropCount));

        super.spawnAfterBreak(pState, pLevel, pPos, pStack, pDropExperience);
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        List<Item> ItemsList = List.of(
                Items.DIAMOND,
                ModItem.Alexandrite.get(),
                ModItem.Raw_Alexandrite.get(),
                Items.BEEHIVE
        );
        LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(pLevel);
        if(!(pLevel.isClientSide) && pEntity instanceof ItemEntity item){
            if(ItemsList.contains(item.getItem().getItem()) && item.getOwner() != null && lightningBolt != null){
                item.remove(Entity.RemovalReason.DISCARDED); // keeping this over coal transformation
            }
        }
        super.stepOn(pLevel, pPos, pState, pEntity);
    }
}