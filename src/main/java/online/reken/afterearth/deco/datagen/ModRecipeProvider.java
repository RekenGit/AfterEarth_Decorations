package online.reken.afterearth.deco.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataOutput;
import net.minecraft.data.recipe.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;
import online.reken.afterearth.deco.AfterEarth_Decorations;
import online.reken.afterearth.deco.block.CustomBlocks;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup,
                                                 RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                /*offerReversibleCompactingRecipes(
                    RecipeCategory.MISC,
                    ModItems.PINK_GARNET,
                    RecipeCategory.BUILDING_BLOCKS,
                    ModBlocks.PINK_GARNET_BLOCK
                );

                ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_PINK_GARNET_BLOCK)
                    .pattern("RRR")
                    .pattern("RRR")
                    .pattern("RRR")
                    .input('R', ModItems.RAW_PINK_GARNET)
                    .criterion(hasItem(ModItems.RAW_PINK_GARNET), conditionsFromItem(ModItems.RAW_PINK_GARNET))
                    .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_PINK_GARNET, 9)
                    .input(ModBlocks.RAW_PINK_GARNET_BLOCK)
                    .criterion(hasItem(ModBlocks.RAW_PINK_GARNET_BLOCK), conditionsFromItem(ModBlocks.RAW_PINK_GARNET_BLOCK))
                    .offerTo(exporter);

                ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_PINK_GARNET, 32)
                    .input(ModBlocks.MAGIC_BLOCK)
                    .criterion(hasItem(ModBlocks.MAGIC_BLOCK), conditionsFromItem(ModBlocks.MAGIC_BLOCK))
                    .offerTo(
                        exporter,
                        RegistryKey.of(
                            RegistryKeys.RECIPE,
                            Identifier.of(TutorialMod.MOD_ID, "raw_pink_garnet_from_magic_block")
                        )
                    );*/


                GenerateStonecutterRecipeBothWays(Blocks.BRICKS, CustomBlocks.Bricks_Broken);

                //for (int i = 0; i < CustomBlocks.ANDESITE_BRICK_BLOCKS.length; i++)
                //    GenerateStonecutterRecipeForBlocks(Blocks.ANDESITE, CustomBlocks.ANDESITE_BRICK_BLOCKS[i], CustomBlocks.ANDESITE_BRICK_BROKEN_BLOCKS[i]);
                for (int i = 0; i < CustomBlocks.QUARTZ_CHECKER_FAMILY.normal().length; i++)
                    GenerateStonecutterRecipeForBlocks(Blocks.QUARTZ_BLOCK, CustomBlocks.QUARTZ_CHECKER_FAMILY.normal()[i], CustomBlocks.QUARTZ_CHECKER_FAMILY.broken()[i]);
                for (int i = 0; i < CustomBlocks.QUARTZ_TILE_FAMILY.normal().length; i++)
                    GenerateStonecutterRecipeForBlocks(Blocks.QUARTZ_BLOCK, CustomBlocks.QUARTZ_TILE_FAMILY.normal()[i], CustomBlocks.QUARTZ_TILE_FAMILY.broken()[i]);

                GenerateStonecutterRecipeForBlocks(Blocks.ANDESITE, CustomBlocks.Andesite_Bricks, CustomBlocks.Andesite_Bricks_Broken);

            }

            private void GenerateStonecutterRecipeForBlocks(Block baseBlock, Block firstBlock, Block secondBlock) {
                GenerateStonecutterRecipeFromBaseToBlocks(baseBlock, firstBlock, secondBlock);
                GenerateStonecutterRecipeBothWays(firstBlock, secondBlock);
            }

            private void GenerateStonecutterRecipeFromBaseToBlocks(Block baseBlock, Block firstBlock, Block secondBlock) {
                StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(baseBlock), RecipeCategory.BUILDING_BLOCKS, firstBlock)
                        .criterion(hasItem(baseBlock), conditionsFromItem(baseBlock))
                        .offerTo(exporter, RegistryKey.of(
                                RegistryKeys.RECIPE,
                                Identifier.of(AfterEarth_Decorations.MOD_ID, Registries.BLOCK.getId(firstBlock).getPath() + "_from_" + Registries.BLOCK.getId(baseBlock).getPath() + "_stonecutting")
                        ));

                StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(baseBlock), RecipeCategory.BUILDING_BLOCKS, secondBlock)
                        .criterion(hasItem(baseBlock), conditionsFromItem(baseBlock))
                        .offerTo(exporter, RegistryKey.of(
                                RegistryKeys.RECIPE,
                                Identifier.of(AfterEarth_Decorations.MOD_ID, Registries.BLOCK.getId(secondBlock).getPath() + "_from_" + Registries.BLOCK.getId(baseBlock).getPath() + "_stonecutting")
                        ));
            }

            private void GenerateStonecutterRecipeBothWays(Block firstBlock, Block secondBlock) {
                StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(firstBlock), RecipeCategory.BUILDING_BLOCKS, secondBlock)
                        .criterion(hasItem(firstBlock), conditionsFromItem(firstBlock))
                        .offerTo(exporter, RegistryKey.of(
                                RegistryKeys.RECIPE,
                                Identifier.of(AfterEarth_Decorations.MOD_ID, Registries.BLOCK.getId(secondBlock).getPath() + "_from_" + Registries.BLOCK.getId(firstBlock).getPath() + "_stonecutting")
                        ));

                StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(secondBlock), RecipeCategory.BUILDING_BLOCKS, firstBlock)
                        .criterion(hasItem(secondBlock), conditionsFromItem(secondBlock))
                        .offerTo(exporter, RegistryKey.of(
                                RegistryKeys.RECIPE,
                                Identifier.of(AfterEarth_Decorations.MOD_ID, Registries.BLOCK.getId(firstBlock).getPath() + "_from_" + Registries.BLOCK.getId(secondBlock).getPath() + "_stonecutting")
                        ));
            }
        };
    }

    @Override
    public String getName() {
        return "ModRecipeProvider";
    }
}

