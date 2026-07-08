package com.tomasborsje.omtreloaded;

import net.neoforged.neoforge.common.ModConfigSpec;

public class OMTReloadedConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // --- Turrets ---
    // Basic Turret
    public static final ModConfigSpec.IntValue BASIC_TURRET_ATTACK_COOLDOWN = BUILDER
            .comment("Basic Turret's attack cooldown in ticks")
            .defineInRange("basicTurretAttackCooldown", 20, 1, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue BASIC_TURRET_ATTACK_DAMAGE = BUILDER
            .comment("Basic Turret's attack damage in half hearts")
            .defineInRange("basicTurretAttackDamage", 5, 1, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue BASIC_TURRET_ACQUISITION_RANGE = BUILDER
            .comment("Basic Turret's target acquisition range in blocks")
            .defineInRange("basicTurretAcquisitionRange", 5, 1, 100);
    public static final ModConfigSpec.IntValue BASIC_TURRET_ENERGY_DRAIN = BUILDER
            .comment("Basic Turret's energy drain in RF/tick")
            .defineInRange("basicTurretEnergyDrain", 1, 1, Integer.MAX_VALUE);

    // Arrow Turret
    public static final ModConfigSpec.IntValue ARROW_TURRET_ATTACK_COOLDOWN = BUILDER
            .comment("Arrow Turret's attack cooldown in ticks")
            .defineInRange("arrowTurretAttackCooldown", 20, 1, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue ARROW_TURRET_ATTACK_DAMAGE = BUILDER
            .comment("Arrow Turret's attack damage in half hearts")
            .defineInRange("arrowTurretAttackDamage", 5, 1, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue ARROW_TURRET_ACQUISITION_RANGE = BUILDER
            .comment("Arrow Turret's target acquisition range in blocks")
            .defineInRange("arrowTurretAcquisitionRange", 5, 1, 100);
    public static final ModConfigSpec.IntValue ARROW_TURRET_ENERGY_DRAIN = BUILDER
            .comment("Arrow Turret's energy drain in RF/tick")
            .defineInRange("arrowTurretEnergyDrain", 1, 1, Integer.MAX_VALUE);

    // --- Upgrades ---
    public static final ModConfigSpec.IntValue MINI_REACTOR_POWER_GEN = BUILDER
            .comment("Mini Reactor Upgrade's power gen per tick")
            .defineInRange("miniReactorPowerGen", 100, 1, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();
}
