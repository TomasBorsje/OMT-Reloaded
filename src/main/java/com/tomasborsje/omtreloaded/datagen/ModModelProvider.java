package com.tomasborsje.omtreloaded.datagen;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.registry.ModBlocks;
import com.tomasborsje.omtreloaded.registry.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import org.jspecify.annotations.NonNull;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, OMTReloaded.MODID);
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {
        registerBlockModels(blockModels);
        registerItemModels(itemModels);
    }

    private void registerBlockModels(BlockModelGenerators blockModels) {
        blockModels.createTrivialCube(ModBlocks.TURRET_BASE.get());
        //blockModels.createNonTemplateModelBlock(ModBlocks.BASIC_TURRET.get());
    }

    private void registerItemModels(ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.LIGHT_TURRET_AMMO.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TURRET_ATTACK_SPEED_UPGRADE_ITEM.get(), ModelTemplates.FLAT_ITEM);
    }
}
