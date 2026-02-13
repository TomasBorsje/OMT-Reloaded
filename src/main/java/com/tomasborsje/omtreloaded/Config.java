package com.tomasborsje.omtreloaded;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue BASIC_TURRET_ATTACK_COOLDOWN = BUILDER
            .comment("Basic Turret's attack cooldown in ticks")
            .defineInRange("basicTurretAttackCooldown", 60, 1, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();
}
