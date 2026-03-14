package online.reken.afterearth.deco.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.*;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.TexturedModel;
import net.minecraft.client.render.model.json.WeightedVariant;
import online.reken.afterearth.deco.AfterEarth_Decorations;
import org.joml.sampling.BestCandidateSampling;

import static online.reken.afterearth.deco.block.CustomBlocks.*;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        //for (Block block : TEST_FAMILY.normal()) registerBlocks(blockStateModelGenerator, block);
        registerBlockFamily(blockStateModelGenerator, Test_Block, TEST_FAMILY.normal());

        for (Block block : QUARTZ_CHECKER_FAMILY.normal())
            blockStateModelGenerator.registerSimpleCubeAll(block);

        for (Block block : QUARTZ_TILE_FAMILY.normal())
            blockStateModelGenerator.registerSimpleCubeAll(block);

        registerBlockFamily(blockStateModelGenerator, Andesite_Bricks, ANDESITE_BRICK_FAMILY.normal());
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // itemModelGenerator.register(...);
    }

    Block glassBlock;
    private void registerBlocks(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        if (block instanceof PillarBlock) {
            blockStateModelGenerator.createLogTexturePool(Test_Pillar)
                    .log(Test_Pillar);
        } else if (block instanceof TransparentBlock) {
            glassBlock = block;
        } else if (block instanceof PaneBlock) {
            if (glassBlock != null)
                blockStateModelGenerator.registerGlassAndPane(glassBlock, block);
            else
                AfterEarth_Decorations.LOGGER.error("Cant find glass block, for: " + block.getName());
        } else if (block instanceof DoorBlock) {
            blockStateModelGenerator.registerDoor(block);
        } else if (block instanceof TrapdoorBlock) {
            blockStateModelGenerator.registerTrapdoor(block);
        } else if (block instanceof CarpetBlock) {
            registerCarpet(blockStateModelGenerator, block);
        } else if (block instanceof LeavesBlock) {
            blockStateModelGenerator.registerTintedBlockAndItem(block, TexturedModel.LEAVES, 16777215);
        } else {
            AfterEarth_Decorations.LOGGER.error("Cant find block type, for: " + block.getName());
            blockStateModelGenerator.registerSimpleCubeAll(block);
        }
    }

    public final void registerCarpet(BlockStateModelGenerator blockStateModelGenerator, Block carpet) {
        WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(TexturedModel.CARPET.upload(carpet, blockStateModelGenerator.modelCollector));
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(carpet, weightedVariant));
    }

    private void registerBlockFamily(
            BlockStateModelGenerator blockStateModelGenerator,
            Block baseCubeBlock,
            Block[] familyBlocks
    ) {
        BlockStateModelGenerator.BlockTexturePool pool =
                blockStateModelGenerator.registerCubeAllModelTexturePool(baseCubeBlock);

        for (Block block : familyBlocks) {
            if (block == baseCubeBlock) {
                continue;
            }

            if (block instanceof SlabBlock) {
                pool.slab(block);
            } else if (block instanceof StairsBlock) {
                pool.stairs(block);
            } else if (block instanceof WallBlock) {
                pool.wall(block);
            } else if (block instanceof PillarBlock) {
                blockStateModelGenerator.createLogTexturePool(Test_Pillar)
                        .log(Test_Pillar);
            } else if (block instanceof TransparentBlock) {
                glassBlock = block;
            } else if (block instanceof PaneBlock) {
                if (glassBlock != null)
                    blockStateModelGenerator.registerGlassAndPane(glassBlock, block);
                else
                    AfterEarth_Decorations.LOGGER.error("Cant find glass block, for: " + block.getName());
            } else if (block instanceof DoorBlock) {
                blockStateModelGenerator.registerDoor(block);
            } else if (block instanceof TrapdoorBlock) {
                blockStateModelGenerator.registerTrapdoor(block);
            } else if (block instanceof CarpetBlock) {
                registerCarpet(blockStateModelGenerator, block);
            } else if (block instanceof LeavesBlock) {
                blockStateModelGenerator.registerTintedBlockAndItem(block, TexturedModel.LEAVES, 16777215);
            } else {
                blockStateModelGenerator.registerSimpleCubeAll(block);
            }
        }
    }
}
