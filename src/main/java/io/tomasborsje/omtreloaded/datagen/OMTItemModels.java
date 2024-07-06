package io.tomasborsje.omtreloaded.datagen;

import io.tomasborsje.omtreloaded.OMTReloaded;
import io.tomasborsje.omtreloaded.registration.ModBlocks;
import io.tomasborsje.omtreloaded.registration.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;


public class OMTItemModels extends ItemModelProvider {
    public OMTItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, OMTReloaded.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Blocks
        withExistingParent(ModBlocks.SIMPLE_TURRET_BASE.getId().getPath(), modLoc("block/simple_turret_base"));

        // Items
        singleTexture(ModItems.SIMPLE_CIRCUIT_BOARD.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/simple_circuit_board"));
        singleTexture(ModItems.BULLET.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/bullet"));
        singleTexture(ModItems.THROWING_GRENADE.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/throwing_grenade"));
    }
}
