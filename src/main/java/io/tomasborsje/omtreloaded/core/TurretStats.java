package io.tomasborsje.omtreloaded.core;

import io.tomasborsje.omtreloaded.registration.ModItems;
import net.minecraft.world.item.Item;

import java.util.List;

// TODO: Move this into config file variables for each turret
public class TurretStats {
    private final int targetRange;
    private final float baseDamage;
    private final List<Item> ammoTypes;
    private final int energyPerShot;
    private final int ticksPerShot;

    public TurretStats(int targetRange, float damage, int ticksPerShot, int energyPerShot, List<Item> ammoTypes) {
        this.targetRange = targetRange;
        this.baseDamage = damage;
        // Ensure ticksPerShot is at least 1
        if (ticksPerShot < 1) {
            ticksPerShot = 1;
        }
        this.ticksPerShot = ticksPerShot;
        // Ensure energy per shot is not negative
        if (energyPerShot < 0) {
            energyPerShot = 0;
        }
        this.energyPerShot = energyPerShot;
        // Ensure ammoType is not null
        if (ammoTypes == null) {
            ammoTypes = List.of(ModItems.BULLET.get());
        }
        this.ammoTypes = ammoTypes;
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

    public int getEnergyPerShot() {
        return energyPerShot;
    }
    public List<Item> getAmmoTypes() {
        return ammoTypes;
    }
}
