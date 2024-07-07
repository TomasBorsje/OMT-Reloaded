package io.tomasborsje.omtreloaded.ui;

import net.minecraft.network.chat.Component;

public class OMTTogglePlayerTargetButton extends OMTLabelButton {
    public OMTTogglePlayerTargetButton(int x, int y, boolean targetPlayers, OnPress onPress) {
        super(x, y, targetPlayers ? Component.translatable("gui.omtreloaded.simple_turret_base.button1") : Component.translatable("gui.omtreloaded.simple_turret_base.button2"), onPress);
    }
}
