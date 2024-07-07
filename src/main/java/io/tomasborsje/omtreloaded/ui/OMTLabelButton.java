package io.tomasborsje.omtreloaded.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class OMTLabelButton extends Button {
    public OMTLabelButton(int x, int y, Component component, Button.OnPress onPress) {
        super(x, y, getTextWidth(component)+10, getTextHeight()+10, component, onPress, Button.DEFAULT_NARRATION);
    }

    static int getTextWidth(Component comp) {
        return Minecraft.getInstance().font.width(comp);
    }
    static int getTextHeight() {
        return Minecraft.getInstance().font.lineHeight;
    }
}
