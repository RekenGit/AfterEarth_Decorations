package online.reken.afterearth.deco.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import online.reken.afterearth.deco.block.CustomBlocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

import static online.reken.afterearth.deco.block.CustomBlocks.*;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        valueLookupBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(CustomBlocks.Bricks_Broken);

        for (Block block : ANDESITE_BRICK) valueLookupBuilder(BlockTags.PICKAXE_MINEABLE).add(block);
        for (Block block : QUARTZ_CHECKER) valueLookupBuilder(BlockTags.PICKAXE_MINEABLE).add(block);
        for (Block block : QUARTZ_TILE) valueLookupBuilder(BlockTags.PICKAXE_MINEABLE).add(block);

        valueLookupBuilder(BlockTags.WALLS)
                .add(Andesite_Brick_Wall)
                .add(Andesite_Brick_Wall_Broken);

        //valueLookupBuilder(BlockTags.NEEDS_IRON_TOOL)
        //        .add(CustomBlocks.Andesite_Bricks)
    }
}
