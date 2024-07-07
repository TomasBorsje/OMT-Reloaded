package io.tomasborsje.omtreloaded.core;

public class TurretBaseStats {
    private final int energyCapacity;
    private final int inventorySize;

    public TurretBaseStats(int energyCapacity, int inventorySize) {
        this.energyCapacity = energyCapacity;
        this.inventorySize = inventorySize;
    }

    public int getEnergyCapacity() {
        return energyCapacity;
    }

    public int getInventorySize() {
        return inventorySize;
    }
}
