package io.tomasborsje.omtreloaded;

import io.tomasborsje.omtreloaded.core.TurretBaseStats;
import io.tomasborsje.omtreloaded.core.TurretStats;
import io.tomasborsje.omtreloaded.registration.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

@EventBusSubscriber(modid = OMTReloaded.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // ---- Simple Turret Base ----
    public static TurretBaseStats simpleTurretBaseStats;
    public static int simpleTurretBaseEnergyCapacity;
    private static final ModConfigSpec.IntValue SIMPLE_TURRET_BASE_ENERGY_CAPACITY = BUILDER
            .comment("The amount of FE the Simple Turret Base can store.")
            .defineInRange("simpleTurretBaseEnergyCapacity", 50000, 1, Integer.MAX_VALUE);

    // ---- Machine Gun Turret ----
    public static TurretStats machineGunTurretStats;
    public static int machineGunTurretEnergyPerShot;
    private static final ModConfigSpec.IntValue MACHINE_GUN_TURRET_ENERGY_PER_SHOT = BUILDER
            .comment("The amount of FE the Machine Gun Turret uses per shot.")
            .defineInRange("machineGunTurretEnergyPerShot", 1000, 0, Integer.MAX_VALUE);

    public static int machineGunTurretShootCooldown;
    private static final ModConfigSpec.IntValue MACHINE_GUN_TURRET_SHOOT_COOLDOWN = BUILDER
            .comment("The amount of ticks between each Machine Gun Turret shot.")
            .defineInRange("machineGunTurretShootCooldown", 10, 1, Integer.MAX_VALUE);

    public static int machineGunTurretDamage;
    private static final ModConfigSpec.IntValue MACHINE_GUN_TURRET_DAMAGE = BUILDER
            .comment("The amount of damage the Machine Gun Turret does per shot, in half hearts.")
            .defineInRange("machineGunTurretDamage", 4, 1, Integer.MAX_VALUE);

    public static int machineGunTurretRange;
    private static final ModConfigSpec.IntValue MACHINE_GUN_TURRET_RANGE = BUILDER
            .comment("The range of the Machine Gun Turret, in blocks.")
            .defineInRange("machineGunTurretRange", 10, 1, 32);

    static final ModConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        // Simple Turret Base
        simpleTurretBaseEnergyCapacity = SIMPLE_TURRET_BASE_ENERGY_CAPACITY.get();
        simpleTurretBaseStats = new TurretBaseStats(simpleTurretBaseEnergyCapacity, 4);

        // Machine Gun Turret
        machineGunTurretEnergyPerShot = MACHINE_GUN_TURRET_ENERGY_PER_SHOT.get();
        machineGunTurretShootCooldown = MACHINE_GUN_TURRET_SHOOT_COOLDOWN.get();
        machineGunTurretDamage = MACHINE_GUN_TURRET_DAMAGE.get();
        machineGunTurretRange = MACHINE_GUN_TURRET_RANGE.get();
        machineGunTurretStats = new TurretStats(machineGunTurretRange, machineGunTurretDamage, machineGunTurretShootCooldown, machineGunTurretEnergyPerShot, List.of(ModItems.BULLET.get()));
    }

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }
}
