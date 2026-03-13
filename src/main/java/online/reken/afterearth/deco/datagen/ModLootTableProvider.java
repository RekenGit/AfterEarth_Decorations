package online.reken.afterearth.deco.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.registry.RegistryWrapper;
import online.reken.afterearth.deco.block.CustomBlocks;

import java.util.concurrent.CompletableFuture;

import static online.reken.afterearth.deco.block.CustomBlocks.*;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        //addDrop(CustomBlocks.Bricks_Broken);

        //for (Block block : ANDESITE_BRICK) addDrop(block);
//        addDrop(Andesite_Bricks);
//        addDrop(Andesite_Bricks_Broken);
//        addDrop(Andesite_Brick_Stairs);
//        addDrop(Andesite_Brick_Stairs_Broken);
//        addDrop(Andesite_Brick_Wall);
//        addDrop(Andesite_Brick_Wall_Broken);
//        addDrop(Andesite_Brick_Trapdoor);
//
//        addDrop(Andesite_Brick_Slab, slabDrops(Andesite_Brick_Slab));
//        addDrop(Andesite_Brick_Slab_Broken, slabDrops(Andesite_Brick_Slab_Broken));
//        addDrop(Andesite_Brick_Door, doorDrops(Andesite_Brick_Door));

        for (Block block : BRICK_BROKEN_FAMILY.all()) addTypedDrop(block);
        for (Block block : ANDESITE_BRICK_FAMILY.all()) addTypedDrop(block);
        for (Block block : QUARTZ_CHECKER_FAMILY.all()) addDrop(block);
        for (Block block : QUARTZ_TILE_FAMILY.all()) addDrop(block);

        //addDrop(CustomBlocks.Bricks_Broken, oreDrops(CustomBlocks.Andesite_Bricks, CustomItem.Item_Name));
    }

    private void addTypedDrop(Block block) {
        if (block instanceof SlabBlock) {
            addDrop(block, slabDrops(block));
        } else if (block instanceof DoorBlock) {
            addDrop(block, doorDrops(block));
        } else {
            addDrop(block);
        }
    }

}