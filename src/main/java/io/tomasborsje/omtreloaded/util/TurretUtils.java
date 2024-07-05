package io.tomasborsje.omtreloaded.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class TurretUtils {
    /**
     * Deals damage to an entity and applies knockback in the specified direction with the specified force.
     * @param entity The entity to damage
     * @param damageSource The source of the damage
     * @param damage The amount of damage to deal
     * @param knockbackDir The direction to apply knockback in
     * @param knockbackForce The force of the knockback
     */
    public static void HurtEntityWithKnockbackRatio(LivingEntity entity, DamageSource damageSource, float damage, Vec3 knockbackDir, float knockbackForce) {
        entity.invulnerableTime = 0; // Reset invulnerability time to allow immediate damage
        entity.hurt(damageSource, damage);
        entity.knockback(knockbackForce, knockbackDir.x, knockbackDir.z);
    }
}
