package io.tomasborsje.omtreloaded.items;

import io.tomasborsje.omtreloaded.core.AbstractTurretBaseEntity;
import io.tomasborsje.omtreloaded.core.AbstractTurretEntity;
import io.tomasborsje.omtreloaded.core.TurretEnergyStorage;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractAddon extends Item {
    public AbstractAddon() {
        super(new Item.Properties());
    }

    public void applyToTurretBase(AbstractTurretBaseEntity turretBase) {

    }

    public void applyToTurret(AbstractTurretEntity turret) {

    }

    @Override
    public int getDefaultMaxStackSize() {
        return 1;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }
}
