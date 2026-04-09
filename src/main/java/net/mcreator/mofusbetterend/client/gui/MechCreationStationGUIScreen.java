package net.mcreator.mofusbetterend.client.gui;

import net.mcreator.mofusbetterend.MofusBetterEndMod;
import net.mcreator.mofusbetterend.network.MechCreationStationGUIButtonMessage;
import net.mcreator.mofusbetterend.procedures.NautilusBlueprintDisplayGUIProcedure;
import net.mcreator.mofusbetterend.world.inventory.MechCreationStationGUIMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import net.minecraft.core.BlockPos;

public class MechCreationStationGUIScreen extends AbstractContainerScreen<MechCreationStationGUIMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MofusBetterEndMod.MODID, "textures/screens/mech_creation_station_gui.png");
    private static final ResourceLocation NAUTILUS_BLUEPRINT = ResourceLocation.fromNamespaceAndPath(MofusBetterEndMod.MODID, "textures/screens/nautilusblueprint.png");

    public MechCreationStationGUIScreen(MechCreationStationGUIMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 250;
        this.imageHeight = 211;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(Button.builder(
                Component.translatable("gui.mofus_better_end_.mech_creation_station_gui.button_build"),
                button -> {
                    BlockPos pos = menu.getPos();
                    System.out.println("[GUI屏幕] 发送按钮点击，坐标: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
                    PacketDistributor.sendToServer(new MechCreationStationGUIButtonMessage(0, pos));
                }
        ).bounds(this.leftPos + 5, this.topPos + 79, 51, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
          // 使用父类的方法，不是你定义的空方法
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        if (NautilusBlueprintDisplayGUIProcedure.execute(this.minecraft.player)) {
            guiGraphics.blit(NAUTILUS_BLUEPRINT, this.leftPos + 143, this.topPos + 12, 0, 0, 96, 96, 96, 96);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.translatable("gui.mofus_better_end_.mech_creation_station_gui.label_empty"), -7, 9, 0xFFCCCCCC, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.mofus_better_end_.mech_creation_station_gui.label_blueprint"), 7, 13, 0xFFCCCCCC, false);
    }
}