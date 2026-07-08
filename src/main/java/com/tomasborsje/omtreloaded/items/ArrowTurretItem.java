package com.tomasborsje.omtreloaded.items;

import com.tomasborsje.omtreloaded.OMTReloadedConfig;
import com.tomasborsje.omtreloaded.core.TurretTooltipData;
import com.tomasborsje.omtreloaded.registry.ModBlocks;

public class ArrowTurretItem extends AbstractTurretItem {
    public ArrowTurretItem(Properties properties) {
        super(ModBlocks.ARROW_TURRET.get(), properties);
    }

    @Override
    protected TurretTooltipData getTooltipData() {
        return new TurretTooltipData(
                OMTReloadedConfig.ARROW_TURRET_ATTACK_DAMAGE.getAsInt(),
                OMTReloadedConfig.ARROW_TURRET_ATTACK_COOLDOWN.getAsInt(),
                OMTReloadedConfig.ARROW_TURRET_ACQUISITION_RANGE.getAsInt(),
                OMTReloadedConfig.ARROW_TURRET_ENERGY_DRAIN.getAsInt(),
                "ui.omtreloaded.lore.arrow_turret.description");
    }
}