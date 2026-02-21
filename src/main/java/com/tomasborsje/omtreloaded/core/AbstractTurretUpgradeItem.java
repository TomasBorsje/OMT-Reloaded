package com.tomasborsje.omtreloaded.core;

import net.minecraft.world.item.Item;

public class AbstractTurretUpgradeItem extends Item implements TurretUpgrade {
    public AbstractTurretUpgradeItem(Properties properties) {
        super(properties.stacksTo(1));
    }
}
