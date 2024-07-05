package io.tomasborsje.omtreloaded.datagen;

import io.tomasborsje.omtreloaded.OMTReloaded;
import io.tomasborsje.omtreloaded.setup.ModBlocks;
import io.tomasborsje.omtreloaded.setup.ModItems;
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
        singleTexture(ModItems.TURRET_RAIL.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/turret_rail"));
    }
}
