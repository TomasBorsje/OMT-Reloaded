package com.tomasborsje.omtreloaded.registry;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.items.BasicTurretItem;
import com.tomasborsje.omtreloaded.items.TurretAttackSpeedUpgradeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(OMTReloaded.MODID);

    public static final DeferredItem<BlockItem> TURRET_BASE_ITEM = ITEMS.registerSimpleBlockItem("turret_base", ModBlocks.TURRET_BASE);
    public static final DeferredItem<BasicTurretItem> BASIC_TURRET_ITEM = ITEMS.registerItem("basic_turret", BasicTurretItem::new);
    public static final DeferredItem<TurretAttackSpeedUpgradeItem> TURRET_ATTACK_SPEED_UPGRADE_ITEM = ITEMS.registerItem("turret_attack_speed_upgrade", TurretAttackSpeedUpgradeItem::new);

    public static final DeferredItem<Item> LIGHT_TURRET_AMMO = ITEMS.registerSimpleItem("light_turret_ammo");

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(TURRET_BASE_ITEM);
            event.accept(BASIC_TURRET_ITEM);
        }
    }
}
