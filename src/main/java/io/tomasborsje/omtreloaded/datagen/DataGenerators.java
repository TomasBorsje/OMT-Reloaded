package io.tomasborsje.omtreloaded.datagen;

import io.tomasborsje.omtreloaded.OMTReloaded;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class DataGenerators {
    public static void generateData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();


        generator.addProvider(event.includeClient(), new OMTItemModels(packOutput, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new OMTLanguageProvider(packOutput, "en_us"));

        generator.addProvider(event.includeServer(), new OMTBlockStates(packOutput, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new OMTBlockTags(packOutput, lookupProvider, OMTReloaded.MODID, event.getExistingFileHelper()));
    }
}
