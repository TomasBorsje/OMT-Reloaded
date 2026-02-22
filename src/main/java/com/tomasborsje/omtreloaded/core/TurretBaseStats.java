package com.tomasborsje.omtreloaded.core;

import java.util.List;

public final class TurretBaseStats {
    private final float baseDamage;
    private final int baseAttackCooldown;
    private final int baseTargetAcquisitionRange;
    private final int baseEnergyPerTickConsumption;
    private float damage;
    private int attackCooldown;
    private int targetAcquisitionRange;
    private int energyPerTickConsumption;


    /**
     * Create a new TurretBaseStats object that defines a turret's base stats.
     *
     * @param baseAttackCooldown         The time between attacks, in ticks.
     * @param baseTargetAcquisitionRange The maximum range in blocks before a target is considered out of range.
     */
    public TurretBaseStats(int baseAttackCooldown, int baseTargetAcquisitionRange, int baseEnergyPerTickConsumption, float baseDamage) {
        if (baseAttackCooldown < 0) {
            throw new IllegalArgumentException("Turret base attack cooldown must be 0 or above!");
        }
        if (baseEnergyPerTickConsumption<0) {
            throw new IllegalArgumentException("Turret energy consumption must be 0 or above!");
        }
        this.baseAttackCooldown = baseAttackCooldown;
        this.baseTargetAcquisitionRange = baseTargetAcquisitionRange;
        this.baseEnergyPerTickConsumption = baseEnergyPerTickConsumption;
        this.baseDamage = baseDamage;
        resetToBaseStats();
    }

    public void applyUpgrades(List<TurretUpgrade> upgrades) {
        for(TurretUpgrade upgrade : upgrades) {
            if(!(upgrade instanceof TurretStatUpgrade statUpgrade)) { continue; }
            this.attackCooldown = (int)(this.attackCooldown * statUpgrade.getAttackCooldownMultiplier());
        }
        if(this.attackCooldown < 0) { this.attackCooldown = 0; }
    }

    public void resetToBaseStats() {
        this.attackCooldown = this.baseAttackCooldown;
        this.targetAcquisitionRange = this.baseTargetAcquisitionRange;
        this.energyPerTickConsumption = this.baseEnergyPerTickConsumption;
        this.damage = this.baseDamage;
    }

    public int attackCooldown() {
        return attackCooldown;
    }

    public int targetAcquisitionRange() {
        return targetAcquisitionRange;
    }

    public int getEnergyPerTickConsumption() {
        return energyPerTickConsumption;
    }

    public float getDamage() {
        return damage;
    }
}
