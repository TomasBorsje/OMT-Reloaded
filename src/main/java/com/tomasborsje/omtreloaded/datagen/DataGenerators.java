package com.tomasborsje.omtreloaded.datagen;

import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGenerators {
    public static void gatherData(GatherDataEvent event) {
        event.createProvider(ModLanguageProvider::new);
        event.createProvider(ModItemModelProvider::new);
    }
}
