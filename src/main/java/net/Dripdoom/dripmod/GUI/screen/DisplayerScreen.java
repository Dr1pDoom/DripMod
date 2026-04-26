package net.Dripdoom.dripmod.GUI.screen;

import net.Dripdoom.dripmod.GUI.menu.DisplayerMenu;
import net.Dripdoom.dripmod.Networking.PacketChannel;
import net.Dripdoom.dripmod.Networking.Packets.DataSavePacket;
import net.Dripdoom.dripmod.Networking.Packets.LightningC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.Channel;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;


public class DisplayerScreen extends AbstractContainerScreen<DisplayerMenu> {

    public EditBox editBox;
    public EditBox editBox1;
    public EditBox editBox2;

    static int PosX = 0;
    static int PosY = 0;
    static int PosZ = 0;

    Button button;
    Player player;
    Inventory playerInventory;
    Predicate<String> predicate = s -> {return s.matches("^\\d*$");};
    Connection clientboundconnection = null;

    int topPos1 = this.topPos;
    int leftPos1 = this.leftPos;


    double centerX = this.width / 2 + 440;
    double centerY = this.height / 2 + 200;
    int w = 200;
    int h = 20;
    int gap = 40;
    int totalWidth = (200 * 3) + (40 * 2);
    double startX = centerX - (totalWidth / 2);

    private boolean ClickedOrNot;


    public DisplayerScreen(DisplayerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.player = pPlayerInventory.player;
        this.playerInventory = pPlayerInventory;
    }

    @Override
    protected void init() {
        super.init();

//      Editbox builder
        this.editBox = new EditBox(font, (int)startX,(int) centerY, w, h, Component.literal(""));
        this.editBox.setMaxLength(32);
        this.editBox.setValue(PosX  == 0 ? "" : String.valueOf(PosX));
        //this.editBox.insertText(PosX  == 0 ? "" : String.valueOf(PosX));
        editBox.setFilter(predicate);

//      EditBox1 builder
        this.editBox1 = new EditBox(font, (int)startX + w + gap, (int)centerY, w, h, Component.literal(""));
        this.editBox1.setMaxLength(32);
        this.editBox1.setValue(PosY == 0 ? "" : String.valueOf(PosY));
       //this.editBox1.insertText(PosX  == 0 ? "" : String.valueOf(PosX));
        editBox1.setFilter(predicate);

//      EditBox2 builder
        this.editBox2 = new EditBox(font, (int)startX + (w + gap) * 2, (int)centerY, w, h, Component.literal(""));
        this.editBox2.setMaxLength(32);
        this.editBox2.setValue(PosZ == 0 ? "" : String.valueOf(PosZ));
        //this.editBox2.insertText(PosX  == 0 ? "" : String.valueOf(PosX));
        editBox2.setFilter(predicate);

//      Button Builder
        this.button = Button.builder(Component.translatable("Generate_Lightning"), (b) -> {

            this.menu.clickMenuButton(player, 0);
            assert minecraft != null;
            
            if(!editBox.getValue().isEmpty() && !editBox1.getValue().isEmpty() && !editBox2.getValue().isEmpty()){

                int posX = Integer.parseInt(editBox.getValue());
                int posY = Integer.parseInt(editBox1.getValue());
                int posZ = Integer.parseInt(editBox2.getValue());


                Object obj = minecraft.gameMode;
                assert obj != null;
                if(Minecraft.getInstance().getConnection() != null){
                    this.clientboundconnection = Minecraft.getInstance().getConnection().getConnection();
                }

                if(this.clientboundconnection != null){
                    PacketChannel.channel.send(new LightningC2SPacket(posX, posY, posZ), clientboundconnection);
                }

                this.player.displayClientMessage(Component.literal(String.valueOf(posX)), true);
                this.player.displayClientMessage(Component.literal(String.valueOf(posY)), true);
                this.player.displayClientMessage(Component.literal(String.valueOf(posZ)), true);

            }

        }).bounds((int)centerX - w / 2, (int)centerY + 30, w, h).build();

        this.addRenderableWidget(editBox);
        this.addRenderableWidget(editBox1);
        this.addRenderableWidget(editBox2);
        this.addRenderableWidget(button);

        System.out.println("leftPos: " + this.leftPos);
        System.out.println("topPos: " + this.topPos);
        System.out.println("imageWidth: " + this.imageWidth);
        System.out.println("imageHeight: " + this.imageHeight);
        System.out.println("height: " + this.height);
        System.out.println("width: " + this.width);

    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {

        pGuiGraphics.fill(
                leftPos1 - 200,
                topPos1 - 100,
                leftPos1 + imageWidth + 200,
                topPos1 + imageHeight + 100,
                0xFFFFFFFF
        );

        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().scale(1.5f, 1.5f, 1.5f);

        for(int i = 0; i <= 3; i++){
            pGuiGraphics.renderFakeItem(!playerInventory.getArmor(3 - i).isEmpty()
                    ? playerInventory.getArmor(3 - i)
                    : ItemStack.EMPTY, this.leftPos + 20, this.topPos + 50 + (10 * i));
        }

        pGuiGraphics.pose().popPose();
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
//        if((pMouseX == this.leftPos + 80 && pMouseY == this.topPos + 20) || (pMouseX == this.leftPos + 90 && pMouseY == this.topPos + 30)) {
//            pGuiGraphics.renderTooltip(this.font, Component.literal("X"), pMouseX, pMouseY);
//
//        }
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (this.editBox.charTyped(codePoint, modifiers) || this.editBox1.charTyped(codePoint, modifiers) || this.editBox2.charTyped(codePoint, modifiers)) {
            return true;
        }
        return super.charTyped(codePoint, modifiers);


    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (this.editBox.keyPressed(pKeyCode, pScanCode, pModifiers) || this.editBox1.keyPressed(pKeyCode, pScanCode, pModifiers) || this.editBox2.keyPressed(pKeyCode, pScanCode, pModifiers)) {
            return true;
        }

        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {

        this.ClickedOrNot = true;

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {

        if(this.ClickedOrNot) this.ClickedOrNot = false;

        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {

        if(this.ClickedOrNot){
            this.leftPos1 += pDragX;
            this.topPos1 += pDragY;
        }

        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }


    //Let it stay here for now, might be usefull later

    private static Integer bytearray_to_double_convertor(byte[] bytearray){
        int value = 0;
        for(int i = 0; i < bytearray.length; i++){
            value += (int) ((bytearray[i] & 0xFF) + (Math.pow(10, i)));
        }

        return value;
    }

    @Override
    public void onClose() {

//        if(Minecraft.getInstance().getConnection() != null && this.clientboundconnection == null){
//            this.clientboundconnection = Minecraft.getInstance().getConnection().getConnection();
//        }
//
//        if(this.clientboundconnection != null){
//            PacketChannel.channel.send(new DataSavePacket(
//                    editBox.getValue().isEmpty() ? 0 : Integer.parseInt(editBox.getValue()),
//                    editBox1.getValue().isEmpty() ? 0 : Integer.parseInt(editBox1.getValue()),
//                    editBox2.getValue().isEmpty() ? 0 : Integer.parseInt(editBox2.getValue())), this.clientboundconnection);
//        }


        PosX = editBox.getValue().isEmpty() ? 0 : Integer.parseInt(editBox.getValue());
        PosY = editBox1.getValue().isEmpty() ? 0 : Integer.parseInt(editBox1.getValue());
        PosZ = editBox2.getValue().isEmpty() ? 0 : Integer.parseInt(editBox2.getValue());


        super.onClose();
    }


}
