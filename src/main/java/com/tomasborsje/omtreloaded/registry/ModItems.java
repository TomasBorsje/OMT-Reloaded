package com.tomasborsje.omtreloaded.registry;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.items.BasicTurretItem;
import com.tomasborsje.omtreloaded.items.GenericTurretBlockItem;
import com.tomasborsje.omtreloaded.items.TurretAttackSpeedUpgradeItem;
import com.tomasborsje.omtreloaded.items.TurretSolarPanelUpgradeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(OMTReloaded.MODID);

    // Block items
    public static final DeferredItem<BlockItem> TURRET_BASE_ITEM = ITEMS.registerItem("turret_base", (props) -> new GenericTurretBlockItem(ModBlocks.TURRET_BASE.get(), props));
    public static final DeferredItem<BasicTurretItem> BASIC_TURRET_ITEM = ITEMS.registerItem("basic_turret", BasicTurretItem::new);

    // Normal items
    public static final DeferredItem<TurretAttackSpeedUpgradeItem> TURRET_ATTACK_SPEED_UPGRADE_ITEM = ITEMS.registerItem("turret_attack_speed_upgrade", TurretAttackSpeedUpgradeItem::new);
    public static final DeferredItem<TurretSolarPanelUpgradeItem> TURRET_SOLAR_PANEL_UPGRADE_ITEM = ITEMS.registerItem("turret_solar_panel_upgrade", TurretSolarPanelUpgradeItem::new);
    public static final DeferredItem<Item> LIGHT_TURRET_AMMO = ITEMS.registerSimpleItem("light_turret_ammo");
}
