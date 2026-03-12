package online.reken.afterearth.deco.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
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
        addDrop(CustomBlocks.Bricks_Broken);

        //for (Block block : ANDESITE_BRICK) addDrop(block);
        addDrop(Andesite_Bricks);
        addDrop(Andesite_Bricks_Broken);
        addDrop(Andesite_Brick_Stairs);
        addDrop(Andesite_Brick_Stairs_Broken);
        addDrop(Andesite_Brick_Wall);
        addDrop(Andesite_Brick_Wall_Broken);
        addDrop(Andesite_Brick_Trapdoor);

        addDrop(Andesite_Brick_Slab, slabDrops(Andesite_Brick_Slab));
        addDrop(Andesite_Brick_Slab_Broken, slabDrops(Andesite_Brick_Slab_Broken));
        addDrop(Andesite_Brick_Door, doorDrops(Andesite_Brick_Door));

        for (Block block : QUARTZ_CHECKER) addDrop(block);
        for (Block block : QUARTZ_TILE) addDrop(block);

        //addDrop(CustomBlocks.Bricks_Broken, oreDrops(CustomBlocks.Andesite_Bricks, CustomItem.Item_Name));
    }
}
