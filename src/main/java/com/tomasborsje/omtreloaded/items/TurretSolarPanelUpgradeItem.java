package com.tomasborsje.omtreloaded.items;

import com.tomasborsje.omtreloaded.core.AbstractTurretUpgradeItem;
import com.tomasborsje.omtreloaded.core.TurretSolarPanelUpgrade;
import com.tomasborsje.omtreloaded.core.TurretStatUpgrade;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.component.ItemLore;

import java.util.List;

public class TurretSolarPanelUpgradeItem extends AbstractTurretUpgradeItem implements TurretSolarPanelUpgrade {
    public TurretSolarPanelUpgradeItem(Properties properties) {
        super(properties.component(DataComponents.LORE, new ItemLore(List.of(Component.translatable("ui.omtreloaded.lore.turret_solar_panel_upgrade")))));
    }

    @Override
    public int getRfGeneratedPerTick() {
        return 20;
    }
}
