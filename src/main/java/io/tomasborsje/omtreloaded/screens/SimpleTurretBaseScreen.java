package io.tomasborsje.omtreloaded.screens;

import io.tomasborsje.omtreloaded.OMTReloaded;
import io.tomasborsje.omtreloaded.containers.SimpleTurretBaseContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SimpleTurretBaseScreen extends AbstractContainerScreen<SimpleTurretBaseContainer> {
    private final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(OMTReloaded.MODID, "textures/gui/simple_turret_base.png");

    public SimpleTurretBaseScreen(SimpleTurretBaseContainer container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.inventoryLabelY = this.imageHeight - 110;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        graphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }
}
