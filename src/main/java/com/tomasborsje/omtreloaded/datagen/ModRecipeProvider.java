package com.tomasborsje.omtreloaded.datagen;

import com.tomasborsje.omtreloaded.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        this.shaped(RecipeCategory.MISC, ModItems.LIGHT_TURRET_AMMO, 32)
                .define('g', Items.GUNPOWDER)
                .define('n', Items.IRON_NUGGET)
                .pattern(" n ")
                .pattern("ngn")
                .pattern(" n ")
                .unlockedBy("has_gunpowder", this.has(Items.GUNPOWDER))
                .save(this.output);

        this.shaped(RecipeCategory.MISC, ModItems.SIMPLE_CIRCUIT_BOARD)
                .define('c', ItemTags.COPPER_TOOL_MATERIALS)
                .define('r', Items.REDSTONE)
                .pattern(" r ")
                .pattern("rcr")
                .pattern(" r ")
                .unlockedBy("has_redstone", this.has(Items.REDSTONE))
                .save(this.output);

        this.shaped(RecipeCategory.MISC, ModItems.SIMPLE_TURRET_BASE_ITEM)
                .define('w', ItemTags.PLANKS)
                .define('s', ItemTags.STONE_TOOL_MATERIALS)
                .define('c', ModItems.SIMPLE_CIRCUIT_BOARD)
                .pattern("sws")
                .pattern("wcw")
                .pattern("sws")
                .unlockedBy("has_circuit_board", this.has(ModItems.SIMPLE_CIRCUIT_BOARD))
                .save(this.output);

        this.shaped(RecipeCategory.MISC, ModItems.SIMPLE_GUN_BARREL)
                .define('s', ItemTags.STONE_TOOL_MATERIALS)
                .define('r', Items.REDSTONE)
                .pattern("sss")
                .pattern("r  ")
                .pattern("sss")
                .unlockedBy("has_redstone", this.has(Items.REDSTONE))
                .save(this.output);

        // Turrets
        this.shaped(RecipeCategory.MISC, ModItems.BASIC_TURRET_ITEM)
                .define('s', ItemTags.STONE_TOOL_MATERIALS)
                .define('b', ModItems.SIMPLE_GUN_BARREL)
                .define('c', ModItems.SIMPLE_CIRCUIT_BOARD)
                .pattern("ss ")
                .pattern("scb")
                .pattern("ss ")
                .unlockedBy("has_barrel", this.has(ModItems.SIMPLE_CIRCUIT_BOARD))
                .save(this.output);
        this.shaped(RecipeCategory.MISC, ModItems.ARROW_TURRET_ITEM)
                .define('s', ItemTags.STONE_TOOL_MATERIALS)
                .define('t', Items.STICK)
                .define('i', ModItems.SIMPLE_CIRCUIT_BOARD)
                .define('r', Items.CROSSBOW)
                .pattern("   ")
                .pattern("trt")
                .pattern("sis")
                .unlockedBy("has_barrel", this.has(ModItems.SIMPLE_CIRCUIT_BOARD))
                .save(this.output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new ModRecipeProvider(provider, output);
        }

        @Override
        public @NonNull String getName() {
            return "omtreloaded:recipes";
        }
    }
}
