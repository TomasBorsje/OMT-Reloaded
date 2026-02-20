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
        add(ModItems.EXAMPLE_ITEM.get(), "Example Item");
        add(ModItems.BASIC_TURRET_ITEM.get(), "Basic Turret");
    }

    private void addBlockTranslations() {
        add(ModBlocks.TURRET_BASE.get(), "Turret Base");
    }

    private void addUiTranslations() {

    }

    private void addMiscTranslations() {

    }
}
