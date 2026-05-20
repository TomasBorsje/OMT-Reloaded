package com.tomasborsje.omtreloaded.items;

import com.tomasborsje.omtreloaded.core.AbstractTurretUpgradeItem;
import com.tomasborsje.omtreloaded.core.TurretPassiveEnergyGenUpgrade;
import com.tomasborsje.omtreloaded.core.TurretSolarPanelUpgrade;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.component.ItemLore;

import java.util.List;

public class TurretCreativeBatteryUpgradeItem extends AbstractTurretUpgradeItem implements TurretPassiveEnergyGenUpgrade {
    public TurretCreativeBatteryUpgradeItem(Properties properties) {
        super(properties.component(DataComponents.LORE, new ItemLore(List.of(Component.translatable("ui.omtreloaded.lore.turret_creative_battery_upgrade")))));
    }

    @Override
    public int getRfGeneratedPerTick() {
        return Integer.MAX_VALUE;
    }
}
