package io.tomasborsje.omtreloaded.datagen;

import io.tomasborsje.omtreloaded.OMTReloaded;
import io.tomasborsje.omtreloaded.setup.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;

import java.util.stream.Collectors;

public class OMTLootTables extends VanillaBlockLoot {

    public OMTLootTables(HolderLookup.Provider provider) {
        super(provider);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.MACHINE_GUN_TURRET.get());
        dropSelf(ModBlocks.SIMPLE_TURRET_BASE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.holders()
                .filter(e -> e.key().location().getNamespace().equals(OMTReloaded.MODID))
                .map(Holder.Reference::value)
                .collect(Collectors.toList());
    }
}
