package io.tomasborsje.omtreloaded.datagen;

import io.tomasborsje.omtreloaded.setup.ModDamageTypes;
import io.tomasborsje.omtreloaded.setup.Registration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class OMTDamageTypes extends DamageTypeTagsProvider {
    public OMTDamageTypes(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(DamageTypeTags.IS_EXPLOSION).add(ModDamageTypes.TURRET_FIRE);
    }
}
