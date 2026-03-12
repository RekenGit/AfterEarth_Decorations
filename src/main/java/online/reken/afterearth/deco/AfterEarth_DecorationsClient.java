package online.reken.afterearth.deco;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;
import online.reken.afterearth.deco.block.CustomBlocks;

public class AfterEarth_DecorationsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlock(CustomBlocks.Andesite_Brick_Door, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(CustomBlocks.Andesite_Brick_Trapdoor, BlockRenderLayer.CUTOUT);
    }
}
