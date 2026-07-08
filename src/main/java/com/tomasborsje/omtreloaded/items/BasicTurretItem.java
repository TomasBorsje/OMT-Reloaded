package com.tomasborsje.omtreloaded.items;

import com.tomasborsje.omtreloaded.OMTReloadedConfig;
import com.tomasborsje.omtreloaded.core.TurretTooltipData;
import com.tomasborsje.omtreloaded.registry.ModBlocks;

public class BasicTurretItem extends AbstractTurretItem {
    public BasicTurretItem(Properties properties) {
        super(ModBlocks.BASIC_TURRET.get(), properties);
    }

    @Override
    protected TurretTooltipData getTooltipData() {
        return new TurretTooltipData(
                OMTReloadedConfig.BASIC_TURRET_ATTACK_DAMAGE.getAsInt(),
                OMTReloadedConfig.BASIC_TURRET_ATTACK_COOLDOWN.getAsInt(),
                OMTReloadedConfig.BASIC_TURRET_ACQUISITION_RANGE.getAsInt(),
                OMTReloadedConfig.BASIC_TURRET_ENERGY_DRAIN.getAsInt(),
                "ui.omtreloaded.lore.arrow_turret.description");
    }
}