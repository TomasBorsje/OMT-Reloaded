package com.tomasborsje.omtreloaded.items;

import com.tomasborsje.omtreloaded.OMTReloadedConfig;
import com.tomasborsje.omtreloaded.core.AbstractTurretUpgradeItem;
import com.tomasborsje.omtreloaded.core.TurretPassiveEnergyGenUpgrade;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.component.ItemLore;

import java.util.List;

public class TurretMiniReactorUpgradeItem extends AbstractTurretUpgradeItem implements TurretPassiveEnergyGenUpgrade {
    public TurretMiniReactorUpgradeItem(Properties properties) {
        super(properties.component(DataComponents.LORE, new ItemLore(List.of(Component.translatable("ui.omtreloaded.lore.turret_mini_reactor_upgrade")))));
    }

    @Override
    public int getRfGeneratedPerTick() {
        return OMTReloadedConfig.MINI_REACTOR_POWER_GEN.get();
    }
}
