package online.reken.afterearth.deco.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

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

        for (Block block : BRICK_BROKEN_FAMILY.broken())
            futures.addAll(generateBrokenBlockFamily(writer, block, BRICK_BROKEN_FAMILY.weights(), BRICK_BROKEN_FAMILY.isVanilaFamily()));

        for (Block block : ANDESITE_BRICK_FAMILY.broken())
            futures.addAll(generateBrokenBlockFamily(writer, block, ANDESITE_BRICK_FAMILY.weights(), ANDESITE_BRICK_FAMILY.isVanilaFamily()));

        for (Block block : QUARTZ_CHECKER_FAMILY.broken())
            futures.addAll(generateBrokenBlockFamily(writer, block, QUARTZ_CHECKER_FAMILY.weights(), QUARTZ_CHECKER_FAMILY.isVanilaFamily()));

        for (Block block : QUARTZ_TILE_FAMILY.broken())
            futures.addAll(generateBrokenBlockFamily(writer, block, QUARTZ_TILE_FAMILY.weights(), QUARTZ_TILE_FAMILY.isVanilaFamily()));

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Override
    public String getName() {
        return "Broken Block Model Provider";
    }

    private enum BrokenBlockType {
        CUBE,
        SLAB,
        STAIRS,
        WALL
    }

    private List<CompletableFuture<?>> generateBrokenBlockFamily(
            DataWriter writer,
            Block brokenBlock,
            int[] weights,
            boolean isVanilla
    ) {
        if (weights.length != 8) {
            throw new IllegalArgumentException("weights must contain exactly 8 values: base + broken0..broken6");
        }

        BrokenBlockType type = resolveBlockType(brokenBlock);

        return switch (type) {
            case CUBE -> generateBrokenCubeBlockFamily(writer, brokenBlock, weights, isVanilla);
            case SLAB -> generateBrokenSlabBlockFamily(writer, brokenBlock, weights, isVanilla);
            case STAIRS -> generateBrokenStairsBlockFamily(writer, brokenBlock, weights, isVanilla);
            case WALL -> generateBrokenWallBlockFamily(writer, brokenBlock, weights, isVanilla);
        };
    }

    private BrokenBlockType resolveBlockType(Block block) {
        if (block instanceof SlabBlock) return BrokenBlockType.SLAB;
        if (block instanceof StairsBlock) return BrokenBlockType.STAIRS;
        if (block instanceof WallBlock) return BrokenBlockType.WALL;
        return BrokenBlockType.CUBE;
    }

    private List<CompletableFuture<?>> generateBrokenCubeBlockFamily(
            DataWriter writer,
            Block resultBlock,
            int[] weights,
            boolean isVanilla
    ) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        Identifier resultBlockId = Registries.BLOCK.getId(resultBlock);
        String resultPath = resultBlockId.getPath();

        String namespace = resultBlockId.getNamespace();
        String base = basePath(resultBlock);

        String baseNs = isVanilla ? "minecraft" : namespace;
        String baseModelId = baseNs + ":block/" + base;
        String brokenModelPrefix = namespace + ":block/" + resultPath;
        String brokenTexturePrefix = texturePrefix(resultBlock);

        JsonObject blockstateJson = createWeightedBlockStateJson(
                baseModelId,
                brokenModelPrefix,
                weights
        );

        futures.add(writeBlockstate(writer, resultBlockId, blockstateJson));

        for (int i = 0; i < 7; i++) {
            Identifier modelId = Identifier.of(namespace, "block/" + resultPath + i);
            JsonObject modelJson = createCubeAllModelJson(brokenTexturePrefix + i);
            futures.add(writeModel(writer, modelId, modelJson));
        }

        JsonObject itemJson = createItemModelJson(brokenModelPrefix + "0");
        futures.add(writeItem(writer, resultBlockId, itemJson));

        return futures;
    }

    private List<CompletableFuture<?>> generateBrokenSlabBlockFamily(
            DataWriter writer,
            Block resultBlock,
            int[] weights,
            boolean isVanilla
    ) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        Identifier resultBlockId = Registries.BLOCK.getId(resultBlock);
        String resultPath = resultBlockId.getPath();

        String base = basePath(resultBlock);
        String baseFullBlockPath = basePath(resultBlock);
        String namespace = resultBlockId.getNamespace();

        String baseNs = isVanilla ? "minecraft" : namespace;

        String baseBottomModel = baseNs + ":block/" + base;
        String baseTopModel = baseNs + ":block/" + base + "_top";
        String baseDoubleModel = baseNs + ":block/" + slabDoubleBasePath(resultBlock);

        String brokenBottomPrefix = namespace + ":block/" + resultPath;
        String brokenTopPrefix = namespace + ":block/" + resultPath + "_top";
        String brokenDoublePrefix = texturePrefix(resultBlock);

        String brokenTexturePrefix = texturePrefix(resultBlock);

        JsonObject blockstateJson = createWeightedSlabBlockStateJson(
                baseBottomModel, brokenBottomPrefix,
                baseTopModel, brokenTopPrefix,
                baseDoubleModel, brokenDoublePrefix,
                weights
        );

        futures.add(writeBlockstate(writer, resultBlockId, blockstateJson));

        for (int i = 0; i < 7; i++) {
            Identifier bottomModelId = Identifier.of(namespace, "block/" + resultPath + i);
            Identifier topModelId = Identifier.of(namespace, "block/" + resultPath + "_top" + i);

            futures.add(writeModel(writer, bottomModelId, createSlabModelJson(brokenTexturePrefix + i)));
            futures.add(writeModel(writer, topModelId, createSlabTopModelJson(brokenTexturePrefix + i)));
        }

        JsonObject itemJson = createItemModelJson(brokenBottomPrefix + "0");
        futures.add(writeItem(writer, resultBlockId, itemJson));

        return futures;
    }

    private List<CompletableFuture<?>> generateBrokenStairsBlockFamily(
            DataWriter writer,
            Block resultBlock,
            int[] weights,
            boolean isVanilla
    ) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        Identifier resultBlockId = Registries.BLOCK.getId(resultBlock);
        String resultPath = resultBlockId.getPath();

        String base = basePath(resultBlock);
        String namespace = resultBlockId.getNamespace();

        String baseNs = isVanilla ? "minecraft" : namespace;

        String baseStraightModel = baseNs + ":block/" + base;
        String baseInnerModel = baseNs + ":block/" + base + "_inner";
        String baseOuterModel = baseNs + ":block/" + base + "_outer";

        String brokenStraightPrefix = namespace + ":block/" + resultPath;
        String brokenInnerPrefix = namespace + ":block/" + resultPath + "_inner";
        String brokenOuterPrefix = namespace + ":block/" + resultPath + "_outer";

        String brokenTexturePrefix = texturePrefix(resultBlock);

        JsonObject blockstateJson = createWeightedStairsBlockStateJson(
                baseStraightModel, brokenStraightPrefix,
                baseInnerModel, brokenInnerPrefix,
                baseOuterModel, brokenOuterPrefix,
                weights
        );

        futures.add(writeBlockstate(writer, resultBlockId, blockstateJson));

        for (int i = 0; i < 7; i++) {
            Identifier straightModelId = Identifier.of(namespace, "block/" + resultPath + i);
            Identifier innerModelId = Identifier.of(namespace, "block/" + resultPath + "_inner" + i);
            Identifier outerModelId = Identifier.of(namespace, "block/" + resultPath + "_outer" + i);

            futures.add(writeModel(writer, straightModelId, createStairsModelJson(brokenTexturePrefix + i)));
            futures.add(writeModel(writer, innerModelId, createInnerStairsModelJson(brokenTexturePrefix + i)));
            futures.add(writeModel(writer, outerModelId, createOuterStairsModelJson(brokenTexturePrefix + i)));
        }

        JsonObject itemJson = createItemModelJson(brokenStraightPrefix + "0");
        futures.add(writeItem(writer, resultBlockId, itemJson));

        return futures;
    }

    private List<CompletableFuture<?>> generateBrokenWallBlockFamily(
            DataWriter writer,
            Block resultBlock,
            int[] weights,
            boolean isVanilla
    ) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        Identifier resultBlockId = Registries.BLOCK.getId(resultBlock);
        String resultPath = resultBlockId.getPath();

        String base = basePath(resultBlock);
        String namespace = resultBlockId.getNamespace();

        String baseNs = isVanilla ? "minecraft" : namespace;

        String basePostModel = baseNs + ":block/" + base + "_post";
        String baseSideModel = baseNs + ":block/" + base + "_side";
        String baseSideTallModel = baseNs + ":block/" + base + "_side_tall";
        String baseInventoryModel = baseNs + ":block/" + base + "_inventory";

        String brokenPostPrefix = namespace + ":block/" + resultPath + "_post";
        String brokenSidePrefix = namespace + ":block/" + resultPath + "_side";
        String brokenSideTallPrefix = namespace + ":block/" + resultPath + "_side_tall";

        String brokenTexturePrefix = texturePrefix(resultBlock);

        JsonObject blockstateJson = createWeightedWallBlockStateJson(
                basePostModel, brokenPostPrefix,
                baseSideModel, brokenSidePrefix,
                baseSideTallModel, brokenSideTallPrefix,
                weights
        );

        futures.add(writeBlockstate(writer, resultBlockId, blockstateJson));

        for (int i = 0; i < 7; i++) {
            Identifier postModelId = Identifier.of(namespace, "block/" + resultPath + "_post" + i);
            Identifier sideModelId = Identifier.of(namespace, "block/" + resultPath + "_side" + i);
            Identifier sideTallModelId = Identifier.of(namespace, "block/" + resultPath + "_side_tall" + i);

            futures.add(writeModel(writer, postModelId, createWallPostModelJson(brokenTexturePrefix + i)));
            futures.add(writeModel(writer, sideModelId, createWallSideModelJson(brokenTexturePrefix + i)));
            futures.add(writeModel(writer, sideTallModelId, createWallSideTallModelJson(brokenTexturePrefix + i)));
        }

        // only one inventory model (variant 0) — item points to this
        Identifier inventoryModelId = Identifier.of(namespace, "block/" + resultPath + "_inventory");
        futures.add(writeModel(writer, inventoryModelId, createWallInventoryModelJson(brokenTexturePrefix + "0")));

        JsonObject itemJson = createItemModelJson(namespace + ":block/" + resultPath + "_inventory");
        futures.add(writeItem(writer, resultBlockId, itemJson));

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

    private JsonObject createWeightedSlabBlockStateJson(
            String baseBottomModel, String brokenBottomPrefix,
            String baseTopModel, String brokenTopPrefix,
            String baseDoubleModel, String brokenDoublePrefix,
            int[] weights
    ) {
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();

        variants.add("type=bottom", createWeightedVariantArray(baseBottomModel, brokenBottomPrefix, weights));
        variants.add("type=top", createWeightedVariantArray(baseTopModel, brokenTopPrefix, weights));
        variants.add("type=double", createWeightedVariantArray(baseDoubleModel, brokenDoublePrefix, weights));

        root.add("variants", variants);
        return root;
    }

    private JsonObject createWeightedStairsBlockStateJson(
            String baseStraightModel, String brokenStraightPrefix,
            String baseInnerModel, String brokenInnerPrefix,
            String baseOuterModel, String brokenOuterPrefix,
            int[] weights
    ) {
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();

        // straight - bottom
        addWeightedStairsVariant(variants, "facing=east,half=bottom,shape=straight", baseStraightModel, brokenStraightPrefix, weights, 0, 0);
        addWeightedStairsVariant(variants, "facing=west,half=bottom,shape=straight", baseStraightModel, brokenStraightPrefix, weights, 0, 180);
        addWeightedStairsVariant(variants, "facing=south,half=bottom,shape=straight", baseStraightModel, brokenStraightPrefix, weights, 0, 90);
        addWeightedStairsVariant(variants, "facing=north,half=bottom,shape=straight", baseStraightModel, brokenStraightPrefix, weights, 0, 270);

        // straight - top
        addWeightedStairsVariant(variants, "facing=east,half=top,shape=straight", baseStraightModel, brokenStraightPrefix, weights, 180, 0);
        addWeightedStairsVariant(variants, "facing=west,half=top,shape=straight", baseStraightModel, brokenStraightPrefix, weights, 180, 180);
        addWeightedStairsVariant(variants, "facing=south,half=top,shape=straight", baseStraightModel, brokenStraightPrefix, weights, 180, 90);
        addWeightedStairsVariant(variants, "facing=north,half=top,shape=straight", baseStraightModel, brokenStraightPrefix, weights, 180, 270);

        // outer_right - bottom
        addWeightedStairsVariant(variants, "facing=east,half=bottom,shape=outer_right", baseOuterModel, brokenOuterPrefix, weights, 0, 0);
        addWeightedStairsVariant(variants, "facing=west,half=bottom,shape=outer_right", baseOuterModel, brokenOuterPrefix, weights, 0, 180);
        addWeightedStairsVariant(variants, "facing=south,half=bottom,shape=outer_right", baseOuterModel, brokenOuterPrefix, weights, 0, 90);
        addWeightedStairsVariant(variants, "facing=north,half=bottom,shape=outer_right", baseOuterModel, brokenOuterPrefix, weights, 0, 270);

        // outer_left - bottom
        addWeightedStairsVariant(variants, "facing=east,half=bottom,shape=outer_left", baseOuterModel, brokenOuterPrefix, weights, 0, 270);
        addWeightedStairsVariant(variants, "facing=west,half=bottom,shape=outer_left", baseOuterModel, brokenOuterPrefix, weights, 0, 90);
        addWeightedStairsVariant(variants, "facing=south,half=bottom,shape=outer_left", baseOuterModel, brokenOuterPrefix, weights, 0, 0);
        addWeightedStairsVariant(variants, "facing=north,half=bottom,shape=outer_left", baseOuterModel, brokenOuterPrefix, weights, 0, 180);

        // outer_right - top
        addWeightedStairsVariant(variants, "facing=east,half=top,shape=outer_right", baseOuterModel, brokenOuterPrefix, weights, 180, 90);
        addWeightedStairsVariant(variants, "facing=west,half=top,shape=outer_right", baseOuterModel, brokenOuterPrefix, weights, 180, 270);
        addWeightedStairsVariant(variants, "facing=south,half=top,shape=outer_right", baseOuterModel, brokenOuterPrefix, weights, 180, 180);
        addWeightedStairsVariant(variants, "facing=north,half=top,shape=outer_right", baseOuterModel, brokenOuterPrefix, weights, 180, 0);

        // outer_left - top
        addWeightedStairsVariant(variants, "facing=east,half=top,shape=outer_left", baseOuterModel, brokenOuterPrefix, weights, 180, 0);
        addWeightedStairsVariant(variants, "facing=west,half=top,shape=outer_left", baseOuterModel, brokenOuterPrefix, weights, 180, 180);
        addWeightedStairsVariant(variants, "facing=south,half=top,shape=outer_left", baseOuterModel, brokenOuterPrefix, weights, 180, 90);
        addWeightedStairsVariant(variants, "facing=north,half=top,shape=outer_left", baseOuterModel, brokenOuterPrefix, weights, 180, 270);

        // inner_right - bottom
        addWeightedStairsVariant(variants, "facing=east,half=bottom,shape=inner_right", baseInnerModel, brokenInnerPrefix, weights, 0, 0);
        addWeightedStairsVariant(variants, "facing=west,half=bottom,shape=inner_right", baseInnerModel, brokenInnerPrefix, weights, 0, 180);
        addWeightedStairsVariant(variants, "facing=south,half=bottom,shape=inner_right", baseInnerModel, brokenInnerPrefix, weights, 0, 90);
        addWeightedStairsVariant(variants, "facing=north,half=bottom,shape=inner_right", baseInnerModel, brokenInnerPrefix, weights, 0, 270);

        // inner_left - bottom
        addWeightedStairsVariant(variants, "facing=east,half=bottom,shape=inner_left", baseInnerModel, brokenInnerPrefix, weights, 0, 270);
        addWeightedStairsVariant(variants, "facing=west,half=bottom,shape=inner_left", baseInnerModel, brokenInnerPrefix, weights, 0, 90);
        addWeightedStairsVariant(variants, "facing=south,half=bottom,shape=inner_left", baseInnerModel, brokenInnerPrefix, weights, 0, 0);
        addWeightedStairsVariant(variants, "facing=north,half=bottom,shape=inner_left", baseInnerModel, brokenInnerPrefix, weights, 0, 180);

        // inner_right - top
        addWeightedStairsVariant(variants, "facing=east,half=top,shape=inner_right", baseInnerModel, brokenInnerPrefix, weights, 180, 90);
        addWeightedStairsVariant(variants, "facing=west,half=top,shape=inner_right", baseInnerModel, brokenInnerPrefix, weights, 180, 270);
        addWeightedStairsVariant(variants, "facing=south,half=top,shape=inner_right", baseInnerModel, brokenInnerPrefix, weights, 180, 180);
        addWeightedStairsVariant(variants, "facing=north,half=top,shape=inner_right", baseInnerModel, brokenInnerPrefix, weights, 180, 0);

        // inner_left - top
        addWeightedStairsVariant(variants, "facing=east,half=top,shape=inner_left", baseInnerModel, brokenInnerPrefix, weights, 180, 0);
        addWeightedStairsVariant(variants, "facing=west,half=top,shape=inner_left", baseInnerModel, brokenInnerPrefix, weights, 180, 180);
        addWeightedStairsVariant(variants, "facing=south,half=top,shape=inner_left", baseInnerModel, brokenInnerPrefix, weights, 180, 90);
        addWeightedStairsVariant(variants, "facing=north,half=top,shape=inner_left", baseInnerModel, brokenInnerPrefix, weights, 180, 270);

        root.add("variants", variants);
        return root;
    }

    private JsonObject createWeightedWallBlockStateJson(
            String basePostModel, String brokenPostPrefix,
            String baseSideModel, String brokenSidePrefix,
            String baseSideTallModel, String brokenSideTallPrefix,
            int[] weights
    ) {
        JsonObject root = new JsonObject();
        JsonArray multipart = new JsonArray();

        multipart.add(createWallMultipartPart(
                when("up", "true"),
                createWeightedVariantArray(basePostModel, brokenPostPrefix, weights)
        ));

        multipart.add(createWallMultipartPart(
                when("north", "low"),
                createWeightedVariantArray(baseSideModel, brokenSidePrefix, weights)
        ));

        multipart.add(createWallMultipartPart(
                when("east", "low"),
                createWeightedRotatedVariantArray(baseSideModel, brokenSidePrefix, weights, 90)
        ));

        multipart.add(createWallMultipartPart(
                when("south", "low"),
                createWeightedRotatedVariantArray(baseSideModel, brokenSidePrefix, weights, 180)
        ));

        multipart.add(createWallMultipartPart(
                when("west", "low"),
                createWeightedRotatedVariantArray(baseSideModel, brokenSidePrefix, weights, 270)
        ));

        multipart.add(createWallMultipartPart(
                when("north", "tall"),
                createWeightedVariantArray(baseSideTallModel, brokenSideTallPrefix, weights)
        ));

        multipart.add(createWallMultipartPart(
                when("east", "tall"),
                createWeightedRotatedVariantArray(baseSideTallModel, brokenSideTallPrefix, weights, 90)
        ));

        multipart.add(createWallMultipartPart(
                when("south", "tall"),
                createWeightedRotatedVariantArray(baseSideTallModel, brokenSideTallPrefix, weights, 180)
        ));

        multipart.add(createWallMultipartPart(
                when("west", "tall"),
                createWeightedRotatedVariantArray(baseSideTallModel, brokenSideTallPrefix, weights, 270)
        ));

        root.add("multipart", multipart);
        return root;
    }

    private JsonObject createWallInventoryModelJson(String textureId) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", "minecraft:block/wall_inventory");

        JsonObject textures = new JsonObject();
        textures.addProperty("wall", textureId);

        root.add("textures", textures);
        return root;
    }

    private JsonArray createWeightedVariantArray(String baseModelId, String brokenModelPrefix, int[] weights) {
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

        return variantList;
    }

    private JsonArray createWeightedRotatedVariantArray(String baseModelId, String brokenModelPrefix, int[] weights, int y) {
        JsonArray variantList = new JsonArray();

        JsonObject baseEntry = new JsonObject();
        baseEntry.addProperty("model", baseModelId);
        baseEntry.addProperty("weight", weights[0]);
        baseEntry.addProperty("y", y);
        baseEntry.addProperty("uvlock", true);
        variantList.add(baseEntry);

        for (int i = 0; i < 7; i++) {
            JsonObject brokenEntry = new JsonObject();
            brokenEntry.addProperty("model", brokenModelPrefix + i);
            brokenEntry.addProperty("weight", weights[i + 1]);
            brokenEntry.addProperty("y", y);
            brokenEntry.addProperty("uvlock", true);
            variantList.add(brokenEntry);
        }

        return variantList;
    }

    private void addWeightedStairsVariant(
            JsonObject variants,
            String key,
            String baseModelId,
            String brokenModelPrefix,
            int[] weights,
            int x,
            int y
    ) {
        JsonArray variantList = new JsonArray();

        JsonObject baseEntry = new JsonObject();
        baseEntry.addProperty("model", baseModelId);
        baseEntry.addProperty("weight", weights[0]);
        if (x != 0) baseEntry.addProperty("x", x);
        if (y != 0) baseEntry.addProperty("y", y);
        if (x != 0 || y != 0) baseEntry.addProperty("uvlock", true);
        variantList.add(baseEntry);

        for (int i = 0; i < 7; i++) {
            JsonObject brokenEntry = new JsonObject();
            brokenEntry.addProperty("model", brokenModelPrefix + i);
            brokenEntry.addProperty("weight", weights[i + 1]);
            if (x != 0) brokenEntry.addProperty("x", x);
            if (y != 0) brokenEntry.addProperty("y", y);
            if (x != 0 || y != 0) brokenEntry.addProperty("uvlock", true);
            variantList.add(brokenEntry);
        }

        variants.add(key, variantList);
    }

    private JsonObject createWallMultipartPart(JsonObject when, JsonArray apply) {
        JsonObject part = new JsonObject();
        part.add("when", when);
        part.add("apply", apply);
        return part;
    }

    private JsonObject when(String key, String value) {
        JsonObject when = new JsonObject();
        when.addProperty(key, value);
        return when;
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

    private JsonObject createSlabModelJson(String textureId) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", "minecraft:block/slab");

        JsonObject textures = new JsonObject();
        textures.addProperty("bottom", textureId);
        textures.addProperty("top", textureId);
        textures.addProperty("side", textureId);

        root.add("textures", textures);
        return root;
    }

    private JsonObject createSlabTopModelJson(String textureId) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", "minecraft:block/slab_top");

        JsonObject textures = new JsonObject();
        textures.addProperty("bottom", textureId);
        textures.addProperty("top", textureId);
        textures.addProperty("side", textureId);

        root.add("textures", textures);
        return root;
    }

    private JsonObject createStairsModelJson(String textureId) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", "minecraft:block/stairs");

        JsonObject textures = new JsonObject();
        textures.addProperty("bottom", textureId);
        textures.addProperty("top", textureId);
        textures.addProperty("side", textureId);

        root.add("textures", textures);
        return root;
    }

    private JsonObject createInnerStairsModelJson(String textureId) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", "minecraft:block/inner_stairs");

        JsonObject textures = new JsonObject();
        textures.addProperty("bottom", textureId);
        textures.addProperty("top", textureId);
        textures.addProperty("side", textureId);

        root.add("textures", textures);
        return root;
    }

    private JsonObject createOuterStairsModelJson(String textureId) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", "minecraft:block/outer_stairs");

        JsonObject textures = new JsonObject();
        textures.addProperty("bottom", textureId);
        textures.addProperty("top", textureId);
        textures.addProperty("side", textureId);

        root.add("textures", textures);
        return root;
    }

    private JsonObject createWallPostModelJson(String textureId) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", "minecraft:block/template_wall_post");

        JsonObject textures = new JsonObject();
        textures.addProperty("wall", textureId);

        root.add("textures", textures);
        return root;
    }

    private JsonObject createWallSideModelJson(String textureId) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", "minecraft:block/template_wall_side");

        JsonObject textures = new JsonObject();
        textures.addProperty("wall", textureId);

        root.add("textures", textures);
        return root;
    }

    private JsonObject createWallSideTallModelJson(String textureId) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", "minecraft:block/template_wall_side_tall");

        JsonObject textures = new JsonObject();
        textures.addProperty("wall", textureId);

        root.add("textures", textures);
        return root;
    }

    /**
     * Zwraca ścieżkę bazową na podstawie broken block id.
     * Przykłady:
     * andesite_bricks_broken -> andesite_bricks
     * andesite_brick_slab_broken -> andesite_brick_slab
     * andesite_brick_stairs_broken -> andesite_brick_stairs
     * andesite_brick_wall_broken -> andesite_brick_wall
     */
    private String basePath(Block brokenBlock) {
        Identifier blockId = Registries.BLOCK.getId(brokenBlock);
        String path = blockId.getPath();

        if (!path.endsWith("_broken")) {
            throw new IllegalArgumentException("Broken block path must end with '_broken': " + path);
        }

        return path.substring(0, path.length() - "_broken".length());
    }

    private String slabDoubleBasePath(Block brokenBlock) {
        return basePath(brokenBlock)
                .replace("brick", "bricks")
                .replace("_slab", "");
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
        String namespace = blockId.getNamespace();
        String path = blockId.getPath();

        String brokenTexturePath = path
                .replace("_slab_broken", "s_broken")
                .replace("_stairs_broken", "s_broken")
                .replace("_wall_broken", "s_broken");

        return namespace + ":block/" + brokenTexturePath;
    }

    private CompletableFuture<?> writeBlockstate(DataWriter writer, Identifier id, JsonObject json) {
        Path path = blockstatesPathResolver.resolveJson(id);
        return DataProvider.writeToPath(writer, json, path);
    }

    private CompletableFuture<?> writeModel(DataWriter writer, Identifier id, JsonObject json) {
        Path path = modelsPathResolver.resolveJson(id);
        return DataProvider.writeToPath(writer, json, path);
    }

    private CompletableFuture<?> writeItem(DataWriter writer, Identifier id, JsonObject json) {
        Path path = itemsPathResolver.resolveJson(id);
        return DataProvider.writeToPath(writer, json, path);
    }
}
