package io.tomasborsje.omtreloaded.items;

import io.tomasborsje.omtreloaded.core.AbstractTurretBaseEntity;
import io.tomasborsje.omtreloaded.core.TurretEnergyStorage;

/**
 * Addon that increases the energy capacity of a turret base by 10%.
 */
public class EnergyCapacityAddon extends AbstractAddon {
    @Override
    public void applyToTurretBase(AbstractTurretBaseEntity turretBase) {
        TurretEnergyStorage energyStorage = turretBase.getEnergyStorage();
        energyStorage.increaseCapacity((int) (energyStorage.getBaseCapacity() * 0.1));
    }
}
