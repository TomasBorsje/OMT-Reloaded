package io.tomasborsje.omtreloaded.datagen;

import io.tomasborsje.omtreloaded.registration.ModItemTags;
import io.tomasborsje.omtreloaded.registration.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class OMTItemTags extends ItemTagsProvider {
    public OMTItemTags(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModItemTags.AMMO)
                .add(ModItems.BULLET.get())
                .add(ModItems.THROWING_GRENADE.get())
                .add(Items.COBBLESTONE);
    }
}
