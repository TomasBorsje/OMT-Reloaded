package com.tomasborsje.omtreloaded.datagen;

import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGenerators {
    public static void gatherClientData(GatherDataEvent.Client event) {
        event.createProvider(ModLanguageProvider::new);
        event.createProvider(ModModelProvider::new);
        event.createProvider(ModItemTagsProvider::new);
    }

    public static void gatherServerData(GatherDataEvent.Server event) {
    }
}
