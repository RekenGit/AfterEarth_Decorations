package online.reken.afterearth.deco.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;

import static online.reken.afterearth.deco.block.CustomBlocks.*;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

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
            } else if (block instanceof DoorBlock) {
                blockStateModelGenerator.registerDoor(block);
            } else if (block instanceof TrapdoorBlock) {
                blockStateModelGenerator.registerTrapdoor(block);
            } else {
                blockStateModelGenerator.registerSimpleCubeAll(block);
            }
        }
    }
}
