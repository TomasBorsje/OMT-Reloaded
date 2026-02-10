package com.tomasborsje.omtreloaded.datagen;

import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGenerators {
    public static void gatherClientData(GatherDataEvent.Client event) {
        event.createProvider(ModLanguageProvider::new);
        event.createProvider(ModModelProvider::new);
    }

    public static void gatherServerData(GatherDataEvent.Server event) {
    }
}
