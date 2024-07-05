package io.tomasborsje.omtreloaded.datagen;

import io.tomasborsje.omtreloaded.setup.ModDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class OMTDamageTypeTags extends DamageTypeTagsProvider {
    public OMTDamageTypeTags(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // No cooldown on turret fire
        tag(DamageTypeTags.BYPASSES_COOLDOWN).add(ModDamageTypes.TURRET_FIRE);
        tag(DamageTypeTags.PANIC_CAUSES).add(ModDamageTypes.TURRET_FIRE);
        tag(DamageTypeTags.NO_KNOCKBACK).add(ModDamageTypes.TURRET_FIRE);
    }
}
