package net.Dripdoom.dripmod.GUI.menu;

import net.Dripdoom.dripmod.GUI.registries.ModMenuRegistry;
import net.Dripdoom.dripmod.ModThings.CustomBlocks.CustomBlockEntities.ItemDisplayerBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class DisplayerMenu extends AbstractContainerMenu {
    private ItemDisplayerBlockEntity be;

    public DisplayerMenu(int pContainerId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(pContainerId, playerInventory);
    }

    public DisplayerMenu(int id, Inventory playerInv) {
        super(ModMenuRegistry.DISPLAYER_MENU.get(), id);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}
