package com.tomasborsje.omtreloaded.registry;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.items.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(OMTReloaded.MODID);

    // Block items
    public static final DeferredItem<BlockItem> SIMPLE_TURRET_BASE_ITEM = ITEMS.registerItem("simple_turret_base", (props) -> new GenericTurretBlockItem(ModBlocks.TURRET_BASE.get(), props));
    public static final DeferredItem<BasicTurretItem> BASIC_TURRET_ITEM = ITEMS.registerItem("basic_turret", BasicTurretItem::new);
    public static final DeferredItem<ArrowTurretItem> ARROW_TURRET_ITEM = ITEMS.registerItem("arrow_turret", ArrowTurretItem::new);

    // Upgrades
    public static final DeferredItem<TurretAttackSpeedUpgradeItem> TURRET_ATTACK_SPEED_UPGRADE_ITEM = ITEMS.registerItem("turret_attack_speed_upgrade", TurretAttackSpeedUpgradeItem::new);
    public static final DeferredItem<TurretSolarPanelUpgradeItem> TURRET_SOLAR_PANEL_UPGRADE_ITEM = ITEMS.registerItem("turret_solar_panel_upgrade", TurretSolarPanelUpgradeItem::new);
    public static final DeferredItem<TurretCreativeBatteryUpgradeItem> TURRET_CREATIVE_BATTERY_UPGRADE_ITEM = ITEMS.registerItem("turret_creative_battery_upgrade", TurretCreativeBatteryUpgradeItem::new);
    public static final DeferredItem<TurretMiniReactorUpgradeItem> TURRET_MINI_REACTOR_UPGRADE_ITEM = ITEMS.registerItem("turret_mini_reactor_upgrade", TurretMiniReactorUpgradeItem::new);

    // Simple items
    public static final DeferredItem<Item> LIGHT_TURRET_AMMO = ITEMS.registerSimpleItem("light_turret_ammo");
    public static final DeferredItem<Item> SIMPLE_CIRCUIT_BOARD = ITEMS.registerSimpleItem("simple_circuit_board");
    public static final DeferredItem<Item> SIMPLE_GUN_BARREL = ITEMS.registerSimpleItem("simple_gun_barrel");
}
