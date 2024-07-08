package io.tomasborsje.omtreloaded.core;

public class TurretBaseStats {
    private final int energyCapacity;
    private final int ammoSlotCount;
    private final int addonSlotCount;

    public TurretBaseStats(int energyCapacity, int ammoSlotCount, int addonSlotCount) {
        this.energyCapacity = energyCapacity;
        this.ammoSlotCount = ammoSlotCount;
        this.addonSlotCount = addonSlotCount;
    }

    public int getEnergyCapacity() {
        return energyCapacity;
    }

    public int getAmmoSlotCount() {
        return ammoSlotCount;
    }

    public int getAddonSlotCount() {
        return addonSlotCount;
    }
}
