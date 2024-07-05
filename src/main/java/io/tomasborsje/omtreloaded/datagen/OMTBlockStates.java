package io.tomasborsje.omtreloaded.datagen;

import io.tomasborsje.omtreloaded.OMTReloaded;
import io.tomasborsje.omtreloaded.registration.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;


public class OMTBlockStates extends BlockStateProvider {
    public OMTBlockStates(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, OMTReloaded.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Simple Blocks
        simpleBlock(ModBlocks.MACHINE_GUN_TURRET.get());
        simpleBlock(ModBlocks.SIMPLE_TURRET_BASE.get());
    }
}
