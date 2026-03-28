package online.reken.afterearth.deco.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import online.reken.afterearth.deco.block.CustomBlocks;
import online.reken.afterearth.deco.block.CustomBlocks.BlockFamilyWeighted;
import online.reken.afterearth.deco.block.CustomBlocks.BlockFamilyWeightedWithBase;
import online.reken.afterearth.deco.block.custom.VerticalSlabBlock;
import online.reken.afterearth.deco.block.custom.VerticalSlabType;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.IntFunction;

import static online.reken.afterearth.deco.block.CustomBlocks.*;

public class ModBrokenModelProvider implements DataProvider {
    private static final int BROKEN_VARIANT_COUNT = 7;
    private static final int EXPECTED_WEIGHT_COUNT = BROKEN_VARIANT_COUNT + 1;

    private static final List<IBlockFamily> BROKEN_FAMILIES = List.of(
            BRICK_BROKEN_FAMILY,
            ANDESITE_BRICK_FAMILY,
            GRANITE_BRICK_FAMILY,
            DIORITE_BRICK_FAMILY,
            QUARTZ_CHECKER_FAMILY,
            QUARTZ_TILE_FAMILY,
            RUSTED_METAL_SHEET_FAMILY,
            SCRAP_METAL_SHEET_FAMILY
    );

    private final DataOutput.PathResolver blockstatesPathResolver;
    private final DataOutput.PathResolver modelsPathResolver;
    private final DataOutput.PathResolver itemsPathResolver;

    public ModBrokenModelProvider(FabricDataOutput output) {
        this.blockstatesPathResolver = output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "blockstates");
        this.modelsPathResolver = output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "models");
        this.itemsPathResolver = output.getResolver(DataOutput.OutputType.RESOURCE_PACK, "items");
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (IBlockFamily family : BROKEN_FAMILIES) {
            futures.addAll(generateBlocksFamily(writer, family));
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Broken Block Model Provider";
    }

    private List<CompletableFuture<?>> generateBlocksFamily(DataWriter writer, IBlockFamily family) {
        validateWeights(family);
        validateFamilyStructure(family);

        List<CompletableFuture<?>> futures = new ArrayList<>();
        for (Block block : family.broken()) {
            futures.addAll(generateBrokenBlockFamily(writer, block, family));
        }
        return futures;
    }

    private List<CompletableFuture<?>> generateBrokenBlockFamily(
            DataWriter writer,
            Block brokenBlock,
            IBlockFamily family
    ) {
        DatagenBlockKind kind = DatagenBlockKind.resolve(brokenBlock);
        if (!kind.supportsBrokenWeightedGeneration()) {
            throw new IllegalArgumentException("Unsupported broken block kind for " + Registries.BLOCK.getId(brokenBlock) + ": " + kind);
        }

        BrokenContext ctx = BrokenContext.of(brokenBlock, family);

        return switch (kind) {
            case CUBE -> generateCube(writer, ctx);
            case SLAB -> generateSlab(writer, ctx);
            case VERTICAL_SLAB -> generateVerticalSlab(writer, ctx);
            case STAIRS -> generateStairs(writer, ctx);
            case WALL -> generateWall(writer, ctx);
            default -> throw new IllegalStateException("Unexpected value: " + kind);
        };
    }

    private List<CompletableFuture<?>> generateCube(DataWriter writer, BrokenContext ctx) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        String baseModelId = ctx.cleanNamespace() + ":block/" + ctx.cleanBasePath();
        String brokenModelPrefix = ctx.namespace() + ":block/" + ctx.brokenBasePath();

        futures.add(writeBlockstate(
                writer,
                ctx.blockId(),
                createSimpleWeightedBlockStateJson(baseModelId, brokenModelPrefix, ctx.weights())
        ));

        // base model without index
        futures.add(writeModel(
                writer,
                Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath()),
                createModelJson("minecraft:block/cube_all", ctx.texturePoolPrefix(), "all")
        ));

        // indexed models 0..6
        futures.addAll(writeRepeatedModels(
                writer,
                i -> Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath() + i),
                i -> createModelJson("minecraft:block/cube_all", ctx.texturePoolPrefix() + i, "all")
        ));

        futures.add(writeItem(
                writer,
                ctx.blockId(),
                createItemModelJson(brokenModelPrefix + "0")
        ));

        return futures;
    }

    private List<CompletableFuture<?>> generateSlab(DataWriter writer, BrokenContext ctx) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        String baseBottomModel = ctx.cleanNamespace() + ":block/" + ctx.cleanBasePath();
        String baseTopModel = ctx.cleanNamespace() + ":block/" + ctx.cleanBasePath() + "_top";
        String baseDoubleModel = ctx.fullBlockNamespace() + ":block/" + ctx.fullBlockPath();

        String brokenBottomPrefix = ctx.namespace() + ":block/" + ctx.brokenBasePath();
        String brokenTopPrefix = ctx.namespace() + ":block/" + ctx.brokenBasePath() + "_top";
        String brokenDoublePrefix = ctx.texturePoolPrefix();

        futures.add(writeBlockstate(
                writer,
                ctx.blockId(),
                createSlabWeightedBlockStateJson(
                        baseBottomModel, brokenBottomPrefix,
                        baseTopModel, brokenTopPrefix,
                        baseDoubleModel, brokenDoublePrefix,
                        ctx.weights()
                )
        ));

        // base models without index
        futures.add(writeModel(
                writer,
                Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath()),
                createModelJson("minecraft:block/slab", ctx.texturePoolPrefix(), "bottom", "top", "side")
        ));

        futures.add(writeModel(
                writer,
                Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath() + "_top"),
                createModelJson("minecraft:block/slab_top", ctx.texturePoolPrefix(), "bottom", "top", "side")
        ));

        // indexed models 0..6
        futures.addAll(writeRepeatedModels(
                writer,
                i -> Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath() + i),
                i -> createModelJson("minecraft:block/slab", ctx.texturePoolPrefix() + i, "bottom", "top", "side")
        ));

        futures.addAll(writeRepeatedModels(
                writer,
                i -> Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath() + "_top" + i),
                i -> createModelJson("minecraft:block/slab_top", ctx.texturePoolPrefix() + i, "bottom", "top", "side")
        ));

        futures.add(writeItem(
                writer,
                ctx.blockId(),
                createItemModelJson(brokenBottomPrefix + "0")
        ));

        return futures;
    }

    private static String getBrokenFullBlockPath(String brokenVariantPath) {
        String path = brokenVariantPath;

        path = path.replace("_slab_vertical_broken", "_broken");
        path = path.replace("_slab_broken", "_broken");
        path = path.replace("_stairs_broken", "_broken");
        path = path.replace("_wall_broken", "_broken");

        path = path.replace("brick_broken", "bricks_broken");
        path = path.replace("tile_broken", "tiles_broken");

        return path;
    }

    private List<CompletableFuture<?>> generateVerticalSlab(DataWriter writer, BrokenContext ctx) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        String baseSingleModel = ctx.cleanNamespace() + ":block/" + ctx.cleanBasePath();
        String brokenSinglePrefix = ctx.namespace() + ":block/" + ctx.brokenBasePath();

        String baseDoubleModel = ctx.fullBlockNamespace() + ":block/" + ctx.fullBlockPath();
        String brokenDoublePrefix = ctx.namespace() + ":block/" + getBrokenFullBlockPath(ctx.brokenBasePath());

        futures.add(writeBlockstate(
                writer,
                ctx.blockId(),
                createVerticalSlabWeightedBlockStateJson(
                        baseSingleModel, brokenSinglePrefix,
                        baseDoubleModel, brokenDoublePrefix,
                        ctx.weights()
                )
        ));

        // model single bez indeksu
        futures.add(writeModel(
                writer,
                Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath()),
                createVerticalSlabSingleModelJson(ctx.texturePoolPrefix())
        ));

        // modele single 0..6
        futures.addAll(writeRepeatedModels(
                writer,
                i -> Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath() + i),
                i -> createVerticalSlabSingleModelJson(ctx.texturePoolPrefix() + i)
        ));

        futures.add(writeItem(
                writer,
                ctx.blockId(),
                createItemModelJson(brokenSinglePrefix + "0")
        ));

        return futures;
    }

    private static String getVerticalSlabDoubleModelPath(String verticalSlabPath) {
        return verticalSlabPath.replace("_slab", "");
    }

    private JsonObject createVerticalSlabSingleModelJson(String textureId) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", "block/block");

        JsonObject textures = new JsonObject();
        textures.addProperty("bottom", textureId);
        textures.addProperty("top", textureId);
        textures.addProperty("side", textureId);
        textures.addProperty("particle", textureId);
        root.add("textures", textures);

        JsonArray elements = new JsonArray();
        JsonObject element = new JsonObject();

        JsonArray from = new JsonArray();
        from.add(0);
        from.add(0);
        from.add(0);

        JsonArray to = new JsonArray();
        to.add(16);
        to.add(16);
        to.add(8);

        element.add("from", from);
        element.add("to", to);

        JsonObject faces = new JsonObject();
        faces.add("down", createFace("#bottom"));
        faces.add("up", createFace("#top"));
        faces.add("north", createFace("#side"));
        faces.add("south", createFace("#side"));
        faces.add("west", createFace("#side"));
        faces.add("east", createFace("#side"));

        element.add("faces", faces);
        elements.add(element);
        root.add("elements", elements);

        return root;
    }

    private JsonObject createFace(String texture) {
        JsonObject face = new JsonObject();
        face.addProperty("texture", texture);
        return face;
    }

    private JsonObject createVerticalSlabWeightedBlockStateJson(
            String baseSingleModel, String brokenSinglePrefix,
            String baseDoubleModel, String brokenDoublePrefix,
            int[] weights
    ) {
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();

        // SINGLE
        addVerticalSlabVariant(variants, "facing=north,type=single,waterlogged=false", baseSingleModel, brokenSinglePrefix, weights, null);
        addVerticalSlabVariant(variants, "facing=east,type=single,waterlogged=false",  baseSingleModel, brokenSinglePrefix, weights, 90);
        addVerticalSlabVariant(variants, "facing=south,type=single,waterlogged=false", baseSingleModel, brokenSinglePrefix, weights, 180);
        addVerticalSlabVariant(variants, "facing=west,type=single,waterlogged=false",  baseSingleModel, brokenSinglePrefix, weights, 270);

        addVerticalSlabVariant(variants, "facing=north,type=single,waterlogged=true", baseSingleModel, brokenSinglePrefix, weights, null);
        addVerticalSlabVariant(variants, "facing=east,type=single,waterlogged=true",  baseSingleModel, brokenSinglePrefix, weights, 90);
        addVerticalSlabVariant(variants, "facing=south,type=single,waterlogged=true", baseSingleModel, brokenSinglePrefix, weights, 180);
        addVerticalSlabVariant(variants, "facing=west,type=single,waterlogged=true",  baseSingleModel, brokenSinglePrefix, weights, 270);

        // DOUBLE — zawsze pełny blok, bez rotacji
        addVerticalSlabVariant(variants, "facing=north,type=double,waterlogged=false", baseDoubleModel, brokenDoublePrefix, weights, null);
        addVerticalSlabVariant(variants, "facing=east,type=double,waterlogged=false",  baseDoubleModel, brokenDoublePrefix, weights, null);
        addVerticalSlabVariant(variants, "facing=south,type=double,waterlogged=false", baseDoubleModel, brokenDoublePrefix, weights, null);
        addVerticalSlabVariant(variants, "facing=west,type=double,waterlogged=false",  baseDoubleModel, brokenDoublePrefix, weights, null);

        addVerticalSlabVariant(variants, "facing=north,type=double,waterlogged=true", baseDoubleModel, brokenDoublePrefix, weights, null);
        addVerticalSlabVariant(variants, "facing=east,type=double,waterlogged=true",  baseDoubleModel, brokenDoublePrefix, weights, null);
        addVerticalSlabVariant(variants, "facing=south,type=double,waterlogged=true", baseDoubleModel, brokenDoublePrefix, weights, null);
        addVerticalSlabVariant(variants, "facing=west,type=double,waterlogged=true",  baseDoubleModel, brokenDoublePrefix, weights, null);

        root.add("variants", variants);
        return root;
    }

    private void addVerticalSlabVariant(
            JsonObject variants,
            String key,
            String baseModelId,
            String brokenModelPrefix,
            int[] weights,
            Integer y
    ) {
        boolean uvlock = y != null;
        variants.add(key, createBrokenVariantArray(baseModelId, brokenModelPrefix, weights, null, y, uvlock));
    }

    private List<CompletableFuture<?>> generateStairs(DataWriter writer, BrokenContext ctx) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        String baseStraightModel = ctx.cleanNamespace() + ":block/" + ctx.cleanBasePath();
        String baseInnerModel = ctx.cleanNamespace() + ":block/" + ctx.cleanBasePath() + "_inner";
        String baseOuterModel = ctx.cleanNamespace() + ":block/" + ctx.cleanBasePath() + "_outer";

        String brokenStraightPrefix = ctx.namespace() + ":block/" + ctx.brokenBasePath();
        String brokenInnerPrefix = ctx.namespace() + ":block/" + ctx.brokenBasePath() + "_inner";
        String brokenOuterPrefix = ctx.namespace() + ":block/" + ctx.brokenBasePath() + "_outer";

        futures.add(writeBlockstate(
                writer,
                ctx.blockId(),
                createStairsWeightedBlockStateJson(
                        baseStraightModel, brokenStraightPrefix,
                        baseInnerModel, brokenInnerPrefix,
                        baseOuterModel, brokenOuterPrefix,
                        ctx.weights()
                )
        ));

        // base models without index
        futures.add(writeModel(
                writer,
                Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath()),
                createModelJson("minecraft:block/stairs", ctx.texturePoolPrefix(), "bottom", "top", "side")
        ));

        futures.add(writeModel(
                writer,
                Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath() + "_inner"),
                createModelJson("minecraft:block/inner_stairs", ctx.texturePoolPrefix(), "bottom", "top", "side")
        ));

        futures.add(writeModel(
                writer,
                Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath() + "_outer"),
                createModelJson("minecraft:block/outer_stairs", ctx.texturePoolPrefix(), "bottom", "top", "side")
        ));

        // indexed models 0..6
        futures.addAll(writeRepeatedModels(
                writer,
                i -> Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath() + i),
                i -> createModelJson("minecraft:block/stairs", ctx.texturePoolPrefix() + i, "bottom", "top", "side")
        ));

        futures.addAll(writeRepeatedModels(
                writer,
                i -> Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath() + "_inner" + i),
                i -> createModelJson("minecraft:block/inner_stairs", ctx.texturePoolPrefix() + i, "bottom", "top", "side")
        ));

        futures.addAll(writeRepeatedModels(
                writer,
                i -> Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath() + "_outer" + i),
                i -> createModelJson("minecraft:block/outer_stairs", ctx.texturePoolPrefix() + i, "bottom", "top", "side")
        ));

        futures.add(writeItem(
                writer,
                ctx.blockId(),
                createItemModelJson(brokenStraightPrefix + "0")
        ));

        return futures;
    }

    private List<CompletableFuture<?>> generateWall(DataWriter writer, BrokenContext ctx) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        String basePostModel = ctx.cleanNamespace() + ":block/" + ctx.cleanBasePath() + "_post";
        String baseSideModel = ctx.cleanNamespace() + ":block/" + ctx.cleanBasePath() + "_side";
        String baseSideTallModel = ctx.cleanNamespace() + ":block/" + ctx.cleanBasePath() + "_side_tall";

        String brokenPostPrefix = ctx.namespace() + ":block/" + ctx.brokenBasePath() + "_post";
        String brokenSidePrefix = ctx.namespace() + ":block/" + ctx.brokenBasePath() + "_side";
        String brokenSideTallPrefix = ctx.namespace() + ":block/" + ctx.brokenBasePath() + "_side_tall";

        futures.add(writeBlockstate(
                writer,
                ctx.blockId(),
                createWallWeightedBlockStateJson(
                        basePostModel, brokenPostPrefix,
                        baseSideModel, brokenSidePrefix,
                        baseSideTallModel, brokenSideTallPrefix,
                        ctx.weights()
                )
        ));

        // base models without index
        futures.add(writeModel(
                writer,
                Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath() + "_post"),
                createModelJson("minecraft:block/template_wall_post", ctx.texturePoolPrefix(), "wall")
        ));

        futures.add(writeModel(
                writer,
                Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath() + "_side"),
                createModelJson("minecraft:block/template_wall_side", ctx.texturePoolPrefix(), "wall")
        ));

        futures.add(writeModel(
                writer,
                Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath() + "_side_tall"),
                createModelJson("minecraft:block/template_wall_side_tall", ctx.texturePoolPrefix(), "wall")
        ));

        futures.addAll(writeRepeatedModels(
                writer,
                i -> Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath() + "_post" + i),
                i -> createModelJson("minecraft:block/template_wall_post", ctx.texturePoolPrefix() + i, "wall")
        ));

        futures.addAll(writeRepeatedModels(
                writer,
                i -> Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath() + "_side" + i),
                i -> createModelJson("minecraft:block/template_wall_side", ctx.texturePoolPrefix() + i, "wall")
        ));

        futures.addAll(writeRepeatedModels(
                writer,
                i -> Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath() + "_side_tall" + i),
                i -> createModelJson("minecraft:block/template_wall_side_tall", ctx.texturePoolPrefix() + i, "wall")
        ));

        Identifier inventoryModelId = Identifier.of(ctx.namespace(), "block/" + ctx.brokenBasePath() + "_inventory");
        futures.add(writeModel(
                writer,
                inventoryModelId,
                createModelJson("minecraft:block/wall_inventory", ctx.texturePoolPrefix() + "0", "wall")
        ));

        futures.add(writeItem(
                writer,
                ctx.blockId(),
                createItemModelJson(ctx.namespace() + ":block/" + ctx.brokenBasePath() + "_inventory")
        ));

        return futures;
    }

    private List<CompletableFuture<?>> writeRepeatedModels(
            DataWriter writer,
            IntFunction<Identifier> idFactory,
            IntFunction<JsonObject> jsonFactory
    ) {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        for (int i = 0; i < BROKEN_VARIANT_COUNT; i++) {
            futures.add(writeModel(writer, idFactory.apply(i), jsonFactory.apply(i)));
        }
        return futures;
    }

    private void validateWeights(IBlockFamily family) {
        int[] weights = getWeights(family);
        if (weights.length != EXPECTED_WEIGHT_COUNT) {
            throw new IllegalArgumentException(
                    "weights must contain exactly " + EXPECTED_WEIGHT_COUNT + " values: base + broken0..broken" + (BROKEN_VARIANT_COUNT - 1)
            );
        }
    }

    private void validateFamilyStructure(IBlockFamily family) {
        Block[] normal = family.normal();
        Block[] broken = family.broken();

        if (normal.length != broken.length && !(normal.length == 0 || broken.length == 0)) {
            throw new IllegalStateException(
                    "Tablica normal() oraz broken() dla rodziny bloku " + family.getBaseBlock().getName() +
                            " nie zawiera tej samej ilości bloków. " +
                            "normal() = " + normal.length + ", broken() = " + broken.length
            );
        }
    }

    private JsonObject createSimpleWeightedBlockStateJson(String baseModelId, String brokenModelPrefix, int[] weights) {
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();
        variants.add("", createBrokenVariantArray(baseModelId, brokenModelPrefix, weights, null, null, false));
        root.add("variants", variants);
        return root;
    }

    private JsonObject createSlabWeightedBlockStateJson(
            String baseBottomModel, String brokenBottomPrefix,
            String baseTopModel, String brokenTopPrefix,
            String baseDoubleModel, String brokenDoublePrefix,
            int[] weights
    ) {
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();

        variants.add("type=bottom", createBrokenVariantArray(baseBottomModel, brokenBottomPrefix, weights, null, null, false));
        variants.add("type=top", createBrokenVariantArray(baseTopModel, brokenTopPrefix, weights, null, null, false));
        variants.add("type=double", createBrokenVariantArray(baseDoubleModel, brokenDoublePrefix, weights, null, null, false));

        root.add("variants", variants);
        return root;
    }

    private JsonObject createStairsWeightedBlockStateJson(
            String baseStraightModel, String brokenStraightPrefix,
            String baseInnerModel, String brokenInnerPrefix,
            String baseOuterModel, String brokenOuterPrefix,
            int[] weights
    ) {
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();

        addStairsVariant(variants, "facing=east,half=bottom,shape=straight", baseStraightModel, brokenStraightPrefix, weights, 0, 0);
        addStairsVariant(variants, "facing=west,half=bottom,shape=straight", baseStraightModel, brokenStraightPrefix, weights, 0, 180);
        addStairsVariant(variants, "facing=south,half=bottom,shape=straight", baseStraightModel, brokenStraightPrefix, weights, 0, 90);
        addStairsVariant(variants, "facing=north,half=bottom,shape=straight", baseStraightModel, brokenStraightPrefix, weights, 0, 270);

        addStairsVariant(variants, "facing=east,half=top,shape=straight", baseStraightModel, brokenStraightPrefix, weights, 180, 0);
        addStairsVariant(variants, "facing=west,half=top,shape=straight", baseStraightModel, brokenStraightPrefix, weights, 180, 180);
        addStairsVariant(variants, "facing=south,half=top,shape=straight", baseStraightModel, brokenStraightPrefix, weights, 180, 90);
        addStairsVariant(variants, "facing=north,half=top,shape=straight", baseStraightModel, brokenStraightPrefix, weights, 180, 270);

        addStairsVariant(variants, "facing=east,half=bottom,shape=outer_right", baseOuterModel, brokenOuterPrefix, weights, 0, 0);
        addStairsVariant(variants, "facing=west,half=bottom,shape=outer_right", baseOuterModel, brokenOuterPrefix, weights, 0, 180);
        addStairsVariant(variants, "facing=south,half=bottom,shape=outer_right", baseOuterModel, brokenOuterPrefix, weights, 0, 90);
        addStairsVariant(variants, "facing=north,half=bottom,shape=outer_right", baseOuterModel, brokenOuterPrefix, weights, 0, 270);

        addStairsVariant(variants, "facing=east,half=bottom,shape=outer_left", baseOuterModel, brokenOuterPrefix, weights, 0, 270);
        addStairsVariant(variants, "facing=west,half=bottom,shape=outer_left", baseOuterModel, brokenOuterPrefix, weights, 0, 90);
        addStairsVariant(variants, "facing=south,half=bottom,shape=outer_left", baseOuterModel, brokenOuterPrefix, weights, 0, 0);
        addStairsVariant(variants, "facing=north,half=bottom,shape=outer_left", baseOuterModel, brokenOuterPrefix, weights, 0, 180);

        addStairsVariant(variants, "facing=east,half=top,shape=outer_right", baseOuterModel, brokenOuterPrefix, weights, 180, 90);
        addStairsVariant(variants, "facing=west,half=top,shape=outer_right", baseOuterModel, brokenOuterPrefix, weights, 180, 270);
        addStairsVariant(variants, "facing=south,half=top,shape=outer_right", baseOuterModel, brokenOuterPrefix, weights, 180, 180);
        addStairsVariant(variants, "facing=north,half=top,shape=outer_right", baseOuterModel, brokenOuterPrefix, weights, 180, 0);

        addStairsVariant(variants, "facing=east,half=top,shape=outer_left", baseOuterModel, brokenOuterPrefix, weights, 180, 0);
        addStairsVariant(variants, "facing=west,half=top,shape=outer_left", baseOuterModel, brokenOuterPrefix, weights, 180, 180);
        addStairsVariant(variants, "facing=south,half=top,shape=outer_left", baseOuterModel, brokenOuterPrefix, weights, 180, 90);
        addStairsVariant(variants, "facing=north,half=top,shape=outer_left", baseOuterModel, brokenOuterPrefix, weights, 180, 270);

        addStairsVariant(variants, "facing=east,half=bottom,shape=inner_right", baseInnerModel, brokenInnerPrefix, weights, 0, 0);
        addStairsVariant(variants, "facing=west,half=bottom,shape=inner_right", baseInnerModel, brokenInnerPrefix, weights, 0, 180);
        addStairsVariant(variants, "facing=south,half=bottom,shape=inner_right", baseInnerModel, brokenInnerPrefix, weights, 0, 90);
        addStairsVariant(variants, "facing=north,half=bottom,shape=inner_right", baseInnerModel, brokenInnerPrefix, weights, 0, 270);

        addStairsVariant(variants, "facing=east,half=bottom,shape=inner_left", baseInnerModel, brokenInnerPrefix, weights, 0, 270);
        addStairsVariant(variants, "facing=west,half=bottom,shape=inner_left", baseInnerModel, brokenInnerPrefix, weights, 0, 90);
        addStairsVariant(variants, "facing=south,half=bottom,shape=inner_left", baseInnerModel, brokenInnerPrefix, weights, 0, 0);
        addStairsVariant(variants, "facing=north,half=bottom,shape=inner_left", baseInnerModel, brokenInnerPrefix, weights, 0, 180);

        addStairsVariant(variants, "facing=east,half=top,shape=inner_right", baseInnerModel, brokenInnerPrefix, weights, 180, 90);
        addStairsVariant(variants, "facing=west,half=top,shape=inner_right", baseInnerModel, brokenInnerPrefix, weights, 180, 270);
        addStairsVariant(variants, "facing=south,half=top,shape=inner_right", baseInnerModel, brokenInnerPrefix, weights, 180, 180);
        addStairsVariant(variants, "facing=north,half=top,shape=inner_right", baseInnerModel, brokenInnerPrefix, weights, 180, 0);

        addStairsVariant(variants, "facing=east,half=top,shape=inner_left", baseInnerModel, brokenInnerPrefix, weights, 180, 0);
        addStairsVariant(variants, "facing=west,half=top,shape=inner_left", baseInnerModel, brokenInnerPrefix, weights, 180, 180);
        addStairsVariant(variants, "facing=south,half=top,shape=inner_left", baseInnerModel, brokenInnerPrefix, weights, 180, 90);
        addStairsVariant(variants, "facing=north,half=top,shape=inner_left", baseInnerModel, brokenInnerPrefix, weights, 180, 270);

        root.add("variants", variants);
        return root;
    }

    private JsonObject createWallWeightedBlockStateJson(
            String basePostModel, String brokenPostPrefix,
            String baseSideModel, String brokenSidePrefix,
            String baseSideTallModel, String brokenSideTallPrefix,
            int[] weights
    ) {
        JsonObject root = new JsonObject();
        JsonArray multipart = new JsonArray();

        multipart.add(createWallMultipartPart(
                when("up", "true"),
                createBrokenVariantArray(basePostModel, brokenPostPrefix, weights, null, null, false)
        ));

        multipart.add(createWallMultipartPart(
                when("north", "low"),
                createBrokenVariantArray(baseSideModel, brokenSidePrefix, weights, null, null, false)
        ));

        multipart.add(createWallMultipartPart(
                when("east", "low"),
                createBrokenVariantArray(baseSideModel, brokenSidePrefix, weights, null, 90, true)
        ));

        multipart.add(createWallMultipartPart(
                when("south", "low"),
                createBrokenVariantArray(baseSideModel, brokenSidePrefix, weights, null, 180, true)
        ));

        multipart.add(createWallMultipartPart(
                when("west", "low"),
                createBrokenVariantArray(baseSideModel, brokenSidePrefix, weights, null, 270, true)
        ));

        multipart.add(createWallMultipartPart(
                when("north", "tall"),
                createBrokenVariantArray(baseSideTallModel, brokenSideTallPrefix, weights, null, null, false)
        ));

        multipart.add(createWallMultipartPart(
                when("east", "tall"),
                createBrokenVariantArray(baseSideTallModel, brokenSideTallPrefix, weights, null, 90, true)
        ));

        multipart.add(createWallMultipartPart(
                when("south", "tall"),
                createBrokenVariantArray(baseSideTallModel, brokenSideTallPrefix, weights, null, 180, true)
        ));

        multipart.add(createWallMultipartPart(
                when("west", "tall"),
                createBrokenVariantArray(baseSideTallModel, brokenSideTallPrefix, weights, null, 270, true)
        ));

        root.add("multipart", multipart);
        return root;
    }

    private void addStairsVariant(
            JsonObject variants,
            String key,
            String baseModelId,
            String brokenModelPrefix,
            int[] weights,
            int x,
            int y
    ) {
        Integer rotX = x == 0 ? null : x;
        Integer rotY = y == 0 ? null : y;
        boolean uvlock = rotX != null || rotY != null;

        variants.add(key, createBrokenVariantArray(baseModelId, brokenModelPrefix, weights, rotX, rotY, uvlock));
    }

    private JsonArray createBrokenVariantArray(
            String baseModelId,
            String brokenModelPrefix,
            int[] weights,
            Integer x,
            Integer y,
            boolean uvlock
    ) {
        JsonArray variantList = new JsonArray();

        variantList.add(createVariantEntry(baseModelId, weights[0], x, y, uvlock));

        for (int i = 0; i < BROKEN_VARIANT_COUNT; i++) {
            variantList.add(createVariantEntry(brokenModelPrefix + i, weights[i + 1], x, y, uvlock));
        }

        return variantList;
    }

    private JsonObject createVariantEntry(String modelId, int weight, Integer x, Integer y, boolean uvlock) {
        JsonObject entry = new JsonObject();
        entry.addProperty("model", modelId);
        entry.addProperty("weight", weight);

        if (x != null) {
            entry.addProperty("x", x);
        }
        if (y != null) {
            entry.addProperty("y", y);
        }
        if (uvlock) {
            entry.addProperty("uvlock", true);
        }

        return entry;
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

    private JsonObject createModelJson(String parent, String textureId, String... textureKeys) {
        JsonObject root = new JsonObject();
        root.addProperty("parent", parent);

        JsonObject textures = new JsonObject();
        for (String textureKey : textureKeys) {
            textures.addProperty(textureKey, textureId);
        }

        root.add("textures", textures);
        return root;
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

    private static int[] getWeights(IBlockFamily family) {
        if (family instanceof BlockFamilyWeighted weighted) {
            return weighted.weights();
        }
        if (family instanceof BlockFamilyWeightedWithBase weightedWithBase) {
            return weightedWithBase.weights();
        }
        throw new IllegalArgumentException("Family " + family.getBaseBlock().getName() + " dont have weights set.");
    }

    private static Block getNormalCounterpart(Block brokenBlock, IBlockFamily family) {
        Block[] broken = family.broken();
        Block[] normal = family.normal();

        if (normal != null && broken != null && normal.length == broken.length) {
            for (int i = 0; i < broken.length; i++) {
                if (broken[i] == brokenBlock) {
                    return normal[i];
                }
            }
        }

        if (family == BRICK_BROKEN_FAMILY) {
            return switch (DatagenBlockKind.resolve(brokenBlock)) {
                case SLAB -> Blocks.BRICK_SLAB;
                case VERTICAL_SLAB -> CustomBlocks.Brick_Slab_Vertical;
                case STAIRS -> Blocks.BRICK_STAIRS;
                case WALL -> Blocks.BRICK_WALL;
                default -> Blocks.BRICKS;
            };
        }

        if (family == RUSTED_METAL_SHEET_FAMILY) {
            return switch (DatagenBlockKind.resolve(brokenBlock)) {
                case SLAB -> Rusted_Metal_Sheet_Slab;
                case VERTICAL_SLAB -> Rusted_Metal_Sheet_Slab_Vertical;
                case STAIRS -> Rusted_Metal_Sheet_Stairs;
                case WALL -> Rusted_Metal_Sheet_Wall;
                default -> Rusted_Metal_Sheet;
            };
        }

        if (family == SCRAP_METAL_SHEET_FAMILY) {
            return switch (DatagenBlockKind.resolve(brokenBlock)) {
                case SLAB -> Scrap_Metal_Sheet_Slab;
                case VERTICAL_SLAB -> Scrap_Metal_Sheet_Slab_Vertical;
                case STAIRS -> Scrap_Metal_Sheet_Stairs;
                case WALL -> Scrap_Metal_Sheet_Wall;
                default -> Scrap_Metal_Sheet;
            };
        }

        return family.getBaseBlock();
    }

    private static String getBrokenTexturePoolPath(Block brokenBlock, IBlockFamily family) {
        if (family instanceof BlockFamilyWeightedWithBase weightedWithBase) {
            return weightedWithBase.texturePoolPath();
        }

        return Registries.BLOCK.getId(brokenBlock).getPath();
    }

    private static String getFullBlockPath(Block brokenBlock, IBlockFamily family) {
        if (family == BRICK_BROKEN_FAMILY) {
            return "bricks";
        }

        Identifier id = Registries.BLOCK.getId(getNormalCounterpart(brokenBlock, family));
        String path = id.getPath();

        if (path.endsWith("_slab")) {
            return path.substring(0, path.length() - "_slab".length())
                    .replace("brick", "bricks");
        }

        return path;
    }

    private static String getFullBlockNamespace(Block brokenBlock, IBlockFamily family) {
        if (family == BRICK_BROKEN_FAMILY) {
            return "minecraft";
        }

        Identifier id = Registries.BLOCK.getId(getNormalCounterpart(brokenBlock, family));
        return id.getNamespace();
    }

    private record BrokenContext(
            Block resultBlock,
            IBlockFamily family,
            Identifier blockId,
            String namespace,
            String resultPath,
            String cleanBasePath,
            String cleanNamespace,
            String brokenBasePath,
            String texturePoolPrefix,
            String fullBlockPath,
            String fullBlockNamespace,
            int[] weights
    ) {
        static BrokenContext of(Block resultBlock, IBlockFamily family) {
            Identifier blockId = Registries.BLOCK.getId(resultBlock);
            String namespace = blockId.getNamespace();
            String resultPath = blockId.getPath();

            Block cleanBlock = getNormalCounterpart(resultBlock, family);
            Identifier cleanId = Registries.BLOCK.getId(cleanBlock);

            String cleanBasePath = cleanId.getPath();
            String cleanNamespace = cleanId.getNamespace();

            String brokenBasePath = blockId.getPath();
            String texturePoolPrefix = blockId.getNamespace() + ":block/" + getBrokenTexturePoolPath(resultBlock, family);

            String fullBlockPath = getFullBlockPath(resultBlock, family);
            String fullBlockNamespace = getFullBlockNamespace(resultBlock, family);

            int[] weights = getWeights(family);

            return new BrokenContext(
                    resultBlock,
                    family,
                    blockId,
                    namespace,
                    resultPath,
                    cleanBasePath,
                    cleanNamespace,
                    brokenBasePath,
                    texturePoolPrefix,
                    fullBlockPath,
                    fullBlockNamespace,
                    weights
            );
        }
    }
}