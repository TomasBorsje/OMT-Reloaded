package com.tomasborsje.omtreloaded.ui;

import com.tomasborsje.omtreloaded.OMTReloaded;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class TurretBaseMenuScreen extends AbstractContainerScreen<TurretBaseMenu> {
    private static final Identifier BACKGROUND_LOCATION = Identifier.fromNamespaceAndPath(OMTReloaded.MODID, "textures/gui/container/turret_base_menu_screen.png");

    public TurretBaseMenuScreen(TurretBaseMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.titleLabelX = 10;
        this.inventoryLabelX = 10;
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        // TODO: Tick stuff here
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);

        // Assume we have some Component 'label'
        // 'label' is drawn at 'labelX' and 'labelY'
        // The color is an ARGB value
        // The final boolean renders the drop shadow when true
        //graphics.drawString(this.font, this.label, this.labelX, this.labelY, 0xFF404040, false);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        // Submits the background texture. 'leftPos' and 'topPos' should
        // already represent the top left corner of where the texture
        // should be rendered as it was precomputed from the 'imageWidth'
        // and 'imageHeight'. The two zeros represent the integer u/v
        // coordinates inside the PNG file, whose size is represented by
        // the last two integers (typically 256 x 256).
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                BACKGROUND_LOCATION,
                this.leftPos, this.topPos,
                0, 0,
                this.imageWidth, this.imageHeight,
                256, 256
        );
    }
}
