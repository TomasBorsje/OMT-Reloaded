package io.tomasborsje.omtreloaded.core;

import net.neoforged.neoforge.energy.EnergyStorage;

/**
 * Energy storage for turrets.
 */
public class TurretEnergyStorage extends EnergyStorage {
    private final int baseCapacity;
    private int extraCapacity = 0;

    public TurretEnergyStorage(int capacity) {
        super(capacity);
        this.baseCapacity = capacity;
    }

    /**
     * Increase the capacity of the energy storage.
     * @param capacity The amount to increase the capacity by.
     */
    public void increaseCapacity(int capacity) {
        this.extraCapacity += capacity;
        this.capacity = this.baseCapacity + this.extraCapacity;
    }

    /**
     * Lower the capacity of the energy storage.
     * @param capacity The amount to lower the capacity by.
     */
    public void lowerCapacity(int capacity) {
        this.extraCapacity -= capacity;
        this.capacity = this.baseCapacity + this.extraCapacity;
        clampEnergy();
    }

    /**
     * Set the extra capacity of the energy storage. NB: This does not clamp the energy.
     * @param extraCapacity The extra capacity to set.
     */
    public void setExtraCapacity(int extraCapacity) {
        this.extraCapacity = extraCapacity;
        this.capacity = this.baseCapacity + this.extraCapacity;
    }

    /**
     * Ensures that the current energy is not higher than the capacity.
     */
    public void clampEnergy() {
        if(this.energy > this.capacity) {
            this.energy = this.capacity;
        }
    }

    public int getBaseCapacity() {
        return this.baseCapacity;
    }
}
