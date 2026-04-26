package net.Dripdoom.dripmod.GUI.menu;

import net.Dripdoom.dripmod.GUI.registries.ModMenuRegistry;
import net.Dripdoom.dripmod.GUI.screen.DisplayerScreen;
import net.Dripdoom.dripmod.ModThings.CustomBlocks.CustomBlockEntities.ItemDisplayerBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DisplayerMenu extends AbstractContainerMenu {
    private final ItemDisplayerBlockEntity be;
    private final Level level;


    public DisplayerMenu(int id, Inventory playerInv, ItemDisplayerBlockEntity be) {
        super(ModMenuRegistry.DISPLAYER_MENU.get(), id);
        this.level = playerInv.player.level();
        this.be = be;
    }

    public DisplayerMenu(int id, Inventory playerInv, FriendlyByteBuf byteBuf) {
        this(id, playerInv, (ItemDisplayerBlockEntity) playerInv.player.level().getBlockEntity(byteBuf.readBlockPos()));
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return pPlayer.isCreative()
                ? pPlayer.distanceToSqr(be.getBlockPos().getCenter()) <= 32
                : pPlayer.distanceToSqr(be.getBlockPos().getCenter()) < 9;
    }

    @Override
    public boolean clickMenuButton(Player pPlayer, int pId) {
        return pId == 0;
    }

    public ItemDisplayerBlockEntity getBlockEntity(){
        return this.be;
    }

}
