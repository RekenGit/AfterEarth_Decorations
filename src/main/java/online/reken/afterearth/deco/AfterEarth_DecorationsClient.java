package online.reken.afterearth.deco;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BushBlock;
import net.minecraft.block.GrassBlock;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.world.biome.GrassColors;
import online.reken.afterearth.deco.block.CustomBlocks;

public class AfterEarth_DecorationsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        //for (Block block : CustomBlocks.TEST_FAMILY.all()) BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT);
        for (Block block : CustomBlocks.TEST_FAMILY.all()) BlockRenderLayerMap.putBlock(block, BlockRenderLayer.TRANSLUCENT);
        for (Block block : CustomBlocks.SCRAP_METAL_SHEET_FAMILY.all()) BlockRenderLayerMap.putBlock(block, BlockRenderLayer.TRANSLUCENT);

        BlockRenderLayerMap.putBlock(CustomBlocks.Mold_Carpet, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(CustomBlocks.Razor_Wire, BlockRenderLayer.TRANSLUCENT);

        BlockRenderLayerMap.putBlock(CustomBlocks.Bush_Carpet, BlockRenderLayer.TRANSLUCENT);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
            // inventory / item form fallback (no world or no position)
            if (view == null || pos == null) {
                return 0x7FBF6A; // optional default preview color
            }

            // same grass-like biome tint behavior
            return BiomeColors.getGrassColor(view, pos);
        }, CustomBlocks.Bush_Carpet);
    }
}
