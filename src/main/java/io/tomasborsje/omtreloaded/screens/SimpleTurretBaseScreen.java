package io.tomasborsje.omtreloaded.screens;

import com.mojang.logging.LogUtils;
import io.tomasborsje.omtreloaded.OMTReloaded;
import io.tomasborsje.omtreloaded.blockentities.SimpleTurretBaseEntity;
import io.tomasborsje.omtreloaded.containers.SimpleTurretBaseContainer;
import io.tomasborsje.omtreloaded.network.SetTargetPlayerPacket;
import io.tomasborsje.omtreloaded.ui.OMTLabelButton;
import io.tomasborsje.omtreloaded.ui.OMTTogglePlayerTargetButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;

public class SimpleTurretBaseScreen extends AbstractContainerScreen<SimpleTurretBaseContainer> {
    private final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(OMTReloaded.MODID, "textures/gui/simple_turret_base.png");
    private final SimpleTurretBaseEntity entity;

    public SimpleTurretBaseScreen(SimpleTurretBaseContainer container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.inventoryLabelY = this.imageHeight - 110;
        this.entity = container.getEntity();
    }

    @Override
    protected void init() {
        super.init();

        // Add button
        OMTLabelButton button = new OMTTogglePlayerTargetButton(this.getTopLeftX() + 10, this.getTopLeftY() + 10, entity.isTargetPlayers(), (pButton) -> {
            // Toggle client-side block entity state
            entity.setTargetPlayers(!entity.isTargetPlayers());
            // Set message of button based on current state
            if (entity.isTargetPlayers()) {
                pButton.setMessage(Component.translatable("gui.omtreloaded.simple_turret_base.button1"));
            } else {
                pButton.setMessage(Component.translatable("gui.omtreloaded.simple_turret_base.button2"));
            }
            PacketDistributor.sendToServer(new SetTargetPlayerPacket(entity.getBlockPos().getX(), entity.getBlockPos().getY(), entity.getBlockPos().getZ(), entity.isTargetPlayers()));
        });
        this.addRenderableWidget(button);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        int relX = getTopLeftX();
        int relY = getTopLeftY();
        graphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    int getTopLeftX() {
        return (this.width - this.imageWidth) / 2;
    }
    int getTopLeftY() {
        return (this.height - this.imageHeight) / 2;
    }
}
