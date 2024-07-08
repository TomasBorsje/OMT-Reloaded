package io.tomasborsje.omtreloaded.datagen;

import io.tomasborsje.omtreloaded.OMTReloaded;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DataGenerators {
    public static void generateData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Client-side data generators
        generator.addProvider(event.includeClient(), new OMTBlockStates(packOutput, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new OMTItemModels(packOutput, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new OMTLanguageProvider(packOutput, "en_us"));
        generator.addProvider(event.includeClient(), new OMTSoundEvents(packOutput, OMTReloaded.MODID, event.getExistingFileHelper()));

        // Server-side data generators
        OMTBlockTags blockTags = new OMTBlockTags(packOutput, lookupProvider, OMTReloaded.MODID, event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new OMTDamageTypeTags(packOutput, lookupProvider, OMTReloaded.MODID, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput,
                Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(OMTLootTables::new, LootContextParamSets.BLOCK)),
                lookupProvider));
        generator.addProvider(event.includeServer(), new OMTRecipes(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new OMTItemTags(packOutput, lookupProvider, blockTags.contentsGetter(), OMTReloaded.MODID, event.getExistingFileHelper()));
    }
}
