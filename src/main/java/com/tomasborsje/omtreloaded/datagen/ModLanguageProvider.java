package com.tomasborsje.omtreloaded.datagen;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.registry.ModBlocks;
import com.tomasborsje.omtreloaded.registry.ModItems;
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
    }

    private void addBlockTranslations() {
        add(ModBlocks.TURRET_BASE.get(), "Turret Base");
    }

    private void addUiTranslations() {

    }

    private void addMiscTranslations() {
        add("ui.omtreloaded.lore.turret_attack_speed_upgrade", "Increases a turret's attack speed by 20%.");
    }
}
