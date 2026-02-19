package com.tomasborsje.omtreloaded.core;

public record TurretBaseStats(int attackCooldown, int targetAcquisitionRange) {
    /**
     * Create a new TurretBaseStats object that defines a turret's base stats.
     *
     * @param attackCooldown         The time between attacks, in ticks.
     * @param targetAcquisitionRange The maximum range in blocks before a target is considered out of range.
     */
    public TurretBaseStats {
        if (attackCooldown <= 0) {
            throw new IllegalArgumentException("Turret base attack cooldown must be above 0!");
        }
    }
}
