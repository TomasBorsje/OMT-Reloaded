package io.tomasborsje.omtreloaded.datagen;

import io.tomasborsje.omtreloaded.OMTReloaded;
import io.tomasborsje.omtreloaded.registration.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class OMTRecipes extends RecipeProvider {
    public OMTRecipes(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        // Simple Circuit Board
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SIMPLE_CIRCUIT_BOARD.get())
                .pattern(" r ")
                .pattern("rpr")
                .pattern(" r ")
                .define('r', Items.REDSTONE)
                .define('p', ItemTags.PLANKS)
                .group(OMTReloaded.MODID)
                .unlockedBy("has_redstone", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(Items.REDSTONE).build()))
                .save(consumer);
        // Simple Turret base
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SIMPLE_TURRET_BASE_ITEM.get())
                .pattern("sws")
                .pattern("wcw")
                .pattern("sws")
                .define('s', ItemTags.STONE_CRAFTING_MATERIALS)
                .define('w', ItemTags.PLANKS)
                .define('c', ModItems.SIMPLE_CIRCUIT_BOARD.get())
                .group(OMTReloaded.MODID)
                .unlockedBy("has_circuit_board", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(ModItems.SIMPLE_CIRCUIT_BOARD.get()).build()))
                .save(consumer);
        // Bullet
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BULLET.get(), 24)
                .pattern("gc")
                .pattern("c ")
                .define('c', Items.COPPER_INGOT)
                .define('g', Items.GUNPOWDER)
                .group(OMTReloaded.MODID)
                .unlockedBy("has_gunpowder", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(Items.GUNPOWDER).build()))
                .save(consumer);

        // Throwing Grenade, gunpowder surrounded by 4 iron ingots
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.THROWING_GRENADE.get(), 12)
                .pattern(" i ")
                .pattern("igi")
                .pattern(" i ")
                .define('i', Items.IRON_INGOT)
                .define('g', Items.GUNPOWDER)
                .group(OMTReloaded.MODID)
                .unlockedBy("has_gunpowder", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(Items.GUNPOWDER).build()))
                .save(consumer);
    }
}
