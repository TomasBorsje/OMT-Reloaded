package com.tomasborsje.omtreloaded.datagen;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.registry.ModBlocks;
import com.tomasborsje.omtreloaded.registry.ModItems;
import com.tomasborsje.omtreloaded.registry.ModTags;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, OMTReloaded.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addItemTranslations();
        addBlockTranslations();
        addUiTranslations();
        addMiscTranslations();
        addTurretDescriptions();
    }

    private void addItemTranslations() {
        // Turrets
        add(ModItems.BASIC_TURRET_ITEM.get(), "§eBasic Turret");
        add(ModItems.ARROW_TURRET_ITEM.get(), "§eArrow Turret");

        // Ammo
        add(ModItems.LIGHT_TURRET_AMMO.get(), "Light Turret Ammo");

        // Upgrades
        add(ModItems.TURRET_ATTACK_SPEED_UPGRADE_ITEM.get(), "§eAttack Speed Upgrade");
        add(ModItems.TURRET_SOLAR_PANEL_UPGRADE_ITEM.get(), "§eSolar Panel Upgrade");
        add(ModItems.TURRET_CREATIVE_BATTERY_UPGRADE_ITEM.get(), "§eCreative Battery Upgrade");
        add(ModItems.TURRET_MINI_REACTOR_UPGRADE_ITEM.get(), "§eMini Reactor Upgrade");

        // Crafting mats
        add(ModItems.SIMPLE_CIRCUIT_BOARD.get(), "Simple Circuit Board");
        add(ModItems.SIMPLE_GUN_BARREL.get(), "Simple Gun Barrel");
    }

    private void addBlockTranslations() {
        add(ModBlocks.SIMPLE_TURRET_BASE.get(), "Turret Base");
        add(ModBlocks.BASIC_TURRET.get(), "§eBasic Turret");
        add(ModBlocks.ARROW_TURRET.get(), "§eArrow Turret");
    }

    private void addUiTranslations() {
        add("itemGroup.omtreloaded", "OMT Reloaded");
        add("menu.title.omtreloaded.basicturretbasemenu", "Basic Turret Base");
        add(ModTags.TURRET_UPGRADE_TAG, "Turret Upgrades");
        add(ModTags.TURRET_LIGHT_AMMO_TAG, "Light Turret Ammo");
        add(ModTags.TURRET_ARROW_AMMO_TAG, "Arrow Turret Ammo");
    }

    private void addTurretDescriptions() {
        add("ui.omtreloaded.lore.generic.tier1", "§f- Tier I -");
        add("ui.omtreloaded.lore.generic.tier2", "§f- Tier II -");
        add("ui.omtreloaded.lore.generic.tier3", "§f- Tier III -");
        add("ui.omtreloaded.lore.generic.tier4", "§f- Tier IV -");
        add("ui.omtreloaded.lore.generic.tier5", "§f- Tier V -");
        add("ui.omtreloaded.lore.generic.damage", "§fDamage: %s");
        add("ui.omtreloaded.lore.generic.fire_rate", "§fFire Rate: %sRPM");
        add("ui.omtreloaded.lore.generic.range", "§fRange: %sm");
        add("ui.omtreloaded.lore.generic.energy_drain", "§fEnergy Drain: %sRF/t");
        add("ui.omtreloaded.lore.arrow_turret.description", "§7Fires arrows at nearby enemies.");
    }

    private void addMiscTranslations() {
        add("ui.omtreloaded.lore.turret_attack_speed_upgrade", "§7Increases all attached turrets' attack speed by §f20%§7 when placed in a turret base.");
        add("ui.omtreloaded.lore.turret_solar_panel_upgrade", "§7Generates §f20RF/t§7 while exposed to the sun when placed in a turret base.");
        add("ui.omtreloaded.lore.turret_creative_battery_upgrade", "§7Generates §finfinite energy§7 when placed in a turret base.");
        add("ui.omtreloaded.lore.turret_mini_reactor_upgrade", "§7Generates §f100RF/t§7 when placed in a turret base.");
    }
}
