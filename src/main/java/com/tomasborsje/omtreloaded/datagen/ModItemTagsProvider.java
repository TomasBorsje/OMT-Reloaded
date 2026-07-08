package com.tomasborsje.omtreloaded.datagen;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.registry.ModItems;
import com.tomasborsje.omtreloaded.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, OMTReloaded.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModTags.TURRET_LIGHT_AMMO_TAG)
                .add(ModItems.LIGHT_TURRET_AMMO.get());

        this.tag(ModTags.TURRET_ARROW_AMMO_TAG)
                .add(Items.ARROW, Items.SPECTRAL_ARROW, Items.TIPPED_ARROW);

        this.tag(ModTags.TURRET_UPGRADE_TAG)
                .add(ModItems.TURRET_ATTACK_SPEED_UPGRADE_ITEM.get())
                .add(ModItems.TURRET_MINI_REACTOR_UPGRADE_ITEM.get())
                .add(ModItems.TURRET_CREATIVE_BATTERY_UPGRADE_ITEM.get())
                .add(ModItems.TURRET_SOLAR_PANEL_UPGRADE_ITEM.get());
    }
}
