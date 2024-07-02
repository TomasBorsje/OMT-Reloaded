package io.tomasborsje.omtreloaded.datagen;

import io.tomasborsje.omtreloaded.setup.Registration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;

import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class OMTBlockTags extends BlockTagsProvider {
    public OMTBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(Registration.SIMPLE_TURRET_BASE.get(), Registration.SIMPLE_TURRET.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(Registration.SIMPLE_TURRET_BASE.get(), Registration.SIMPLE_TURRET.get());
    }
}
