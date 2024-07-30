package net.sienna.mccourse.data.recipe;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.fml.common.Mod;
import net.sienna.mccourse.block.ModBlocks;
import net.sienna.mccourse.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    private List<ItemLike> ALEXANDRITE_SMELTABLES = List.of(
            ModItems.RAW_ALEXANDRITE.get(),
            ModBlocks.ALEXANDRITE_ORE.get(),
            ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get(),
            ModBlocks.NETHER_ALEXANDRITE_ORE.get(),
            ModBlocks.END_STONE_ALEXANDRITE_ORE.get()
    );

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, ModItems.ALEXANDRITE.get(), RecipeCategory.MISC, ModBlocks.ALEXANDRITE_BLOCK.get());
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, ModItems.RAW_ALEXANDRITE.get(), RecipeCategory.MISC, ModBlocks.RAW_ALEXANDRITE_BLOCK.get());

        stairBuilder(ModBlocks.ALEXANDRITE_STAIRS.get(), Ingredient.of(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .unlockedBy("has_alexandrite", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.ALEXANDRITE.get()).build()))
                .save(recipeOutput);
        slabBuilder(RecipeCategory.MISC, ModBlocks.ALEXANDRITE_SLAB.get(), Ingredient.of(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .unlockedBy("has_alexandrite", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.ALEXANDRITE.get()).build()))
                .save(recipeOutput);
        buttonBuilder(ModBlocks.ALEXANDRITE_BUTTON.get(), Ingredient.of(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .unlockedBy("has_alexandrite", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.ALEXANDRITE.get()).build()))
                .save(recipeOutput);
        pressurePlateBuilder(RecipeCategory.MISC, ModBlocks.ALEXANDRITE_PRESSURE_PLATE.get(), Ingredient.of(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .unlockedBy("has_alexandrite", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.ALEXANDRITE.get()).build()))
                .save(recipeOutput);
        fenceBuilder(ModBlocks.ALEXANDRITE_FENCE.get(), Ingredient.of(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .unlockedBy("has_alexandrite", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.ALEXANDRITE.get()).build()))
                .save(recipeOutput);
        fenceGateBuilder(ModBlocks.ALEXANDRITE_FENCE_GATE.get(), Ingredient.of(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .unlockedBy("has_alexandrite", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.ALEXANDRITE.get()).build()))
                .save(recipeOutput);
        wallBuilder(RecipeCategory.MISC, ModBlocks.ALEXANDRITE_WALL.get(), Ingredient.of(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .unlockedBy("has_alexandrite", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.ALEXANDRITE.get()).build()))
                .save(recipeOutput);
        doorBuilder(ModBlocks.ALEXANDRITE_DOOR.get(), Ingredient.of(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .unlockedBy("has_alexandrite", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.ALEXANDRITE.get()).build()))
                .save(recipeOutput);
        trapdoorBuilder(ModBlocks.ALEXANDRITE_TRAPDOOR.get(), Ingredient.of(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .unlockedBy("has_alexandrite", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.ALEXANDRITE.get()).build()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOUND_BLOCK.get())
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Blocks.WHITE_WOOL)
                .define('B', ModItems.ALEXANDRITE)
                .unlockedBy("has_alexandrite", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.ALEXANDRITE.get()).build()))
                .save(recipeOutput);

        oreSmelting(recipeOutput, ALEXANDRITE_SMELTABLES, RecipeCategory.MISC, ModItems.ALEXANDRITE.get(), 5, 200, "alexandrite");
        oreBlasting(recipeOutput, ALEXANDRITE_SMELTABLES, RecipeCategory.MISC, ModItems.ALEXANDRITE.get(), 5, 100, "alexandrite");




    }
}
