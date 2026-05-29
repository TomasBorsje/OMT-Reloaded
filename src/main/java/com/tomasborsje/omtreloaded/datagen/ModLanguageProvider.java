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
    }

    private void addItemTranslations() {
        add(ModItems.LIGHT_TURRET_AMMO.get(), "Light Turret Ammo");
        add(ModItems.BASIC_TURRET_ITEM.get(), "Basic Turret");
        add(ModItems.TURRET_ATTACK_SPEED_UPGRADE_ITEM.get(), "Attack Speed Upgrade");
        add(ModItems.TURRET_SOLAR_PANEL_UPGRADE_ITEM.get(), "Solar Panel Upgrade");
        add(ModItems.TURRET_CREATIVE_BATTERY_UPGRADE_ITEM.get(), "Creative Battery Upgrade");
        add(ModItems.TURRET_MINI_REACTOR_UPGRADE_ITEM.get(), "Mini Reactor Upgrade");
        add(ModItems.SIMPLE_CIRCUIT_BOARD.get(), "Simple Circuit Board");
        add(ModItems.SIMPLE_GUN_BARREL.get(), "Simple Gun Barrel");
    }

    private void addBlockTranslations() {
        add(ModBlocks.TURRET_BASE.get(), "Turret Base");
    }

    private void addUiTranslations() {
        add("itemGroup.omtreloaded", "OMT Reloaded");
        add("menu.title.omtreloaded.basicturretbasemenu", "Basic Turret Base");
        add(ModTags.TURRET_UPGRADE_TAG, "Turret Upgrades");
        add(ModTags.TURRET_LIGHT_AMMO_TAG, "Light Turret Ammo");
    }

    private void addMiscTranslations() {
        add("ui.omtreloaded.lore.turret_attack_speed_upgrade", "Increases all attached turrets' attack speed by 20% when placed in a turret base.");
        add("ui.omtreloaded.lore.turret_solar_panel_upgrade", "Generates 20RF/t while exposed to the sun when placed in a turret base.");
        add("ui.omtreloaded.lore.turret_creative_battery_upgrade", "Generates infinite energy when placed in a turret base.");
        add("ui.omtreloaded.lore.turret_mini_reactor_upgrade", "Generates power when placed in a turret base.");
    }
}
