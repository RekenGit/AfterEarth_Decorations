package online.reken.afterearth.deco.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import online.reken.afterearth.deco.block.CustomBlocks;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static online.reken.afterearth.deco.block.CustomBlocks.*;

public class BrokenBlockModelProvider implements DataProvider {
    private final FabricDataOutput output;
    private final DataOutput.PathResolver blockstatesPathResolver;
    private final DataOutput.PathResolver modelsPathResolver;
    private final DataOutput.PathResolver itemsPathResolver;

    public BrokenBlockModelProvider(FabricDataOutput output) {
        this.output = output;
        this.blockstatesPathResolver = output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "blockstates");
        this.modelsPathResolver = output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "models");
        this.itemsPathResolver = output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "items");
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (Block block : ANDESITE_BRICK_BROKEN_BLOCKS) futures.addAll(generateBrokenCubeBlockFamily(writer, block, new int[]{5, 3, 2, 1, 2, 3, 3, 4}));
        for (Block block : QUARTZ_CHECKER_BROKEN_BLOCKS) futures.addAll(generateBrokenCubeBlockFamily(writer, block, new int[]{5, 4, 4, 4, 4, 4, 1, 1}));
        for (Block block : QUARTZ_TILE_BROKEN_BLOCKS) futures.addAll(generateBrokenCubeBlockFamily(writer, block, new int[]{5, 4, 4, 4, 4, 3, 3, 1}));

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Override
    public String getName() {
        return "Broken Block Model Provider";
    }

    private List<CompletableFuture<?>> generateBrokenCubeBlockFamily(
            DataWriter writer,
            Block resultBlock,
            //Identifier baseTextureId,
            int[] weights
    ) {
        if (weights.length != 8) {
            throw new IllegalArgumentException("weights must contain exactly 8 values: base + broken0..broken6");
        }

        String brokenTexturePrefix = texturePrefix(resultBlock);
        List<CompletableFuture<?>> futures = new ArrayList<>();

        Identifier resultBlockId = Registries.BLOCK.getId(resultBlock);
        String resultPath = resultBlockId.getPath();

        // Blockstate:
        // "afterearth_decorations:block/andesite_bricks"
        // "afterearth_decorations:block/andesite_bricks_broken0" ... broken6
        JsonObject blockstateJson = createWeightedBlockStateJson(
                //baseTextureId.getNamespace() + ":block/" + baseTextureId.getPath(),
                resultBlockId.getNamespace() + ":block/" + resultPath.replace("_broken", ""),
                resultBlockId.getNamespace() + ":block/" + resultPath,
                weights
        );

        Path blockstatePath = blockstatesPathResolver.resolveJson(resultBlockId);
        futures.add(DataProvider.writeToPath(writer, blockstateJson, blockstatePath));

        // Modele broken0..broken6:
        for (int i = 0; i < 7; i++) {
            Identifier modelId = Identifier.of(resultBlockId.getNamespace(), "block/" + resultPath + i);
            JsonObject modelJson = createCubeAllModelJson(brokenTexturePrefix + i);

            Path modelPath = modelsPathResolver.resolveJson(modelId);
            futures.add(DataProvider.writeToPath(writer, modelJson, modelPath));
        }

        Identifier itemId = resultBlockId;
        JsonObject itemJson = createItemModelJson(resultBlockId.getNamespace() + ":block/" + resultPath + "0");

        Path itemPath = itemsPathResolver.resolveJson(itemId);
        futures.add(DataProvider.writeToPath(writer, itemJson, itemPath));

        return futures;
    }

    private JsonObject createWeightedBlockStateJson(String baseModelId, String brokenModelPrefix, int[] weights) {
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();
        JsonArray variantList = new JsonArray();

        JsonObject baseEntry = new JsonObject();
        baseEntry.addProperty("model", baseModelId);
        baseEntry.addProperty("weight", weights[0]);
        variantList.add(baseEntry);

        for (int i = 0; i < 7; i++) {
            JsonObject brokenEntry = new JsonObject();
            brokenEntry.addProperty("model", brokenModelPrefix + i);
            brokenEntry.addProperty("weight", weights[i + 1]);
            variantList.add(brokenEntry);
        }

        variants.add("", variantList);
        root.add("variants", variants);
        return root;
    }

    private JsonObject createItemModelJson(String modelId) {
        JsonObject root = new JsonObject();
        JsonObject model = new JsonObject();

        model.addProperty("type", "minecraft:model");
        model.addProperty("model", modelId);

        root.add("model", model);
        return root;
    }

    private JsonObject createCubeAllModelJson(String textureId) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", "minecraft:block/cube_all");

        JsonObject textures = new JsonObject();
        textures.addProperty("all", textureId);

        root.add("textures", textures);
        return root;
    }

    /**
     * Zwraca ID tekstury bazowej na podstawie ID bloku.
     * Np. blok afterearth_decorations:andesite_bricks
     * -> Identifier("afterearth_decorations", "andesite_bricks")
     *
     * Potem w JSON zostanie to zapisane jako:
     * afterearth_decorations:block/andesite_bricks
     */
    private Identifier textureId(Block block) {
        Identifier blockId = Registries.BLOCK.getId(block);
        return Identifier.of(blockId.getNamespace(), blockId.getPath());
    }

    /**
     * Zwraca prefix tekstur broken:
     * np. afterearth_decorations:block/andesite_bricks_broken
     * żeby finalnie powstało:
     * afterearth_decorations:block/andesite_bricks_broken0
     * ...
     * afterearth_decorations:block/andesite_bricks_broken6
     */
    private String texturePrefix(Block brokenBlock) {
        Identifier blockId = Registries.BLOCK.getId(brokenBlock);
        return blockId.getNamespace() + ":block/" + blockId.getPath();
    }
}