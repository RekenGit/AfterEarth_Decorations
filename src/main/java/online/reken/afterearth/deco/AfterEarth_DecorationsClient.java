package online.reken.afterearth.deco;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.BlockRenderLayer;
import online.reken.afterearth.deco.block.CustomBlocks;

public class AfterEarth_DecorationsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        //for (Block block : CustomBlocks.TEST_FAMILY.all()) BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT);
        for (Block block : CustomBlocks.TEST_FAMILY.all()) BlockRenderLayerMap.putBlock(block, BlockRenderLayer.TRANSLUCENT);
    }
}
