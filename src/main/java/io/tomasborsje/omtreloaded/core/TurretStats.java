package io.tomasborsje.omtreloaded.core;

public class TurretStats {
    private final int targetRange;
    private final float baseDamage;
    private final int ticksPerShot;
    public TurretStats (int targetRange, float damage, int ticksPerShot) {
        this.targetRange = targetRange;
        this.baseDamage = damage;
        // Ensure ticksPerShot is at least 1
        if (ticksPerShot < 1) {
            ticksPerShot = 1;
        }
        this.ticksPerShot = ticksPerShot;
    }
    public int getTargetRange() {
        return targetRange;
    }

    public float getBaseDamage() {
        return baseDamage;
    }

    public int getTicksPerShot() {
        return ticksPerShot;
    }
}
