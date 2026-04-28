package net.Dripdoom.dripmod.ModThings.CustomBlocks.CustomBlockEntities;

import net.Dripdoom.dripmod.GUI.menu.DisplayerMenu;
import net.Dripdoom.dripmod.ModThings.CustomBlocks.BlockRegistries.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LightningSummonerBlockEntity extends BlockEntity implements MenuProvider {

    public LightningSummonerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ItemDisplayerEntity.get(), pPos, pBlockState);
    }

    public final ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    };

    public void clearContents() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public List<ItemStack> drops() {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack Items = inventory.getStackInSlot(i);
            items.add(Items.copy());
        }
        return items;
    }


    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.put("inventory", inventory.serializeNBT(pRegistries));
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        inventory.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider holders) {
        this.loadAdditional(tag, holders);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookup) {
        this.handleUpdateTag(pkt.getTag(), lookup);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Lightning_Block");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new DisplayerMenu(pContainerId, pPlayerInventory, this);
    }
}