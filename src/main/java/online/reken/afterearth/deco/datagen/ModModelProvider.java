package online.reken.afterearth.deco.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import online.reken.afterearth.deco.block.CustomBlocks;

import java.util.ArrayList;

import static online.reken.afterearth.deco.block.CustomBlocks.*;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        //blockStateModelGenerator.registerSimpleCubeAll(CustomBlocks.Andesite_Bricks);

        //for (Block block : ANDESITE_BRICK_BLOCKS) blockStateModelGenerator.registerSimpleCubeAll(block);
        for (Block block : QUARTZ_CHECKER_BLOCKS) blockStateModelGenerator.registerSimpleCubeAll(block);
        for (Block block : QUARTZ_TILE_BLOCKS) blockStateModelGenerator.registerSimpleCubeAll(block);

        BlockStateModelGenerator.BlockTexturePool andesiteBrickPool = blockStateModelGenerator.registerCubeAllModelTexturePool(Andesite_Bricks);
        andesiteBrickPool.slab(Andesite_Brick_Slab);
        andesiteBrickPool.stairs(Andesite_Brick_Stairs);
        andesiteBrickPool.wall(Andesite_Brick_Wall);

        //BlockStateModelGenerator.BlockTexturePool andesiteBrickBrokenPool = blockStateModelGenerator.registerCubeAllModelTexturePool(Andesite_Bricks_Broken);
        //andesiteBrickBrokenPool.slab(Andesite_Brick_Slab_Broken);
        //andesiteBrickBrokenPool.stairs(Andesite_Brick_Stairs_Broken);
        //andesiteBrickBrokenPool.wall(Andesite_Brick_Wall_Broken);

        blockStateModelGenerator.registerDoor(Andesite_Brick_Door);
        blockStateModelGenerator.registerTrapdoor(Andesite_Brick_Trapdoor);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        //itemModelGenerator.register(CustomItem.Item_Name, Models.GENERATED);
    }
}
