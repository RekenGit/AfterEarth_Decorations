package online.reken.afterearth.deco.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.ModelIds;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.TextureKey;
import net.minecraft.client.data.TextureMap;
import net.minecraft.client.data.TexturedModel;
import net.minecraft.client.data.VariantsBlockModelDefinitionCreator;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.AxisRotation;
import net.minecraft.util.math.Direction;
import online.reken.afterearth.deco.AfterEarth_Decorations;
import online.reken.afterearth.deco.block.CustomBlocks.IBlockFamily;
import online.reken.afterearth.deco.block.custom.VerticalSlabBlock;
import online.reken.afterearth.deco.block.custom.VerticalSlabType;

import java.util.List;

import static online.reken.afterearth.deco.block.CustomBlocks.*;

public class ModModelProvider extends FabricModelProvider {
    private static final List<String> BLOCK_SUFFIX_EXCEPTIONS = List.of("block/test", "block/purpur");

    private Block pendingGlassBlock;

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        for (IBlockFamily family : MODEL_TEXTURE_POOL_FAMILIES) {
            registerFamily(generator, family, true);
        }

        for (IBlockFamily family : MODEL_STANDALONE_BLOCK_FAMILIES) {
            registerFamily(generator, family, false);
        }

        generator.registerNorthDefaultHorizontalRotatable(Razor_Wire);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }

    private void registerFamily(BlockStateModelGenerator generator, IBlockFamily family, boolean useTexturePool) {
        pendingGlassBlock = null;

        Block baseBlock = family.getBaseBlock();
        if (useTexturePool)
            generator.registerCubeAllModelTexturePool(baseBlock);

        for (Block block : family.normal())
            if (!useTexturePool || block != baseBlock)
                registerBlock(generator, family, block);
    }

    private void registerBlock(BlockStateModelGenerator generator, IBlockFamily family, Block block) {
        switch (DatagenBlockKind.resolve(block)) {
            case CUBE -> generator.registerSimpleCubeAll(block);
            case SLAB -> registerSlab(generator, family, block);
            case VERTICAL_SLAB -> registerVerticalSlab(generator, family, block);
            case STAIRS -> registerStairs(generator, family, block);
            case WALL -> registerWall(generator, family, block);
            case PILLAR -> registerPillar(generator, family, block);
            case GLAZED_TERRACOTTA -> registerGlazedTerracotta(generator, block);
            case TRANSPARENT -> pendingGlassBlock = block;
            case PANE -> registerPane(generator, block);
            case DOOR -> generator.registerDoor(block);
            case TRAPDOOR -> generator.registerTrapdoor(block);
            case CARPET -> registerCarpet(generator, block);
            case LEAVES -> generator.registerTintedBlockAndItem(block, TexturedModel.LEAVES, 16777215);
        }
    }

    private void registerPane(BlockStateModelGenerator generator, Block pane) {
        if (pendingGlassBlock != null) {
            generator.registerGlassAndPane(pendingGlassBlock, pane);
            return;
        }

        AfterEarth_Decorations.LOGGER.error("Can't find matching glass block for pane: {}", pane.getName().getString());
    }

    private void registerPillar(BlockStateModelGenerator generator, IBlockFamily family, Block block) {
        Identifier baseBlockId = ModelIds.getBlockModelId(family.getBaseBlock());
        Identifier blockId = ModelIds.getBlockModelId(block);

        if ("minecraft".equals(baseBlockId.getNamespace())) {
            generator.createLogTexturePool(block).log(block);
            return;
        }

        String pilarTextureTop = baseBlockId.getPath();
        String pilarTextureSide = blockId.getPath();
        String pilarNamespaceTop = baseBlockId.getNamespace();
        if (blockId.getPath().contains("_pillar")) {
            pilarNamespaceTop = blockId.getNamespace();
            pilarTextureTop = blockId.getPath().replace("_pillar", "_pillar_top");
        }

        registerCustomLog(
                generator,
                block,
                blockId.getNamespace(),
                pilarTextureSide,
                pilarNamespaceTop,
                pilarTextureTop
        );
    }

    public static void registerGlazedTerracotta(BlockStateModelGenerator generator, Block block) {
        Identifier blockId = ModelIds.getBlockModelId(block);

        Identifier modelId = Models.TEMPLATE_GLAZED_TERRACOTTA.upload(
                block,
                new TextureMap().put(
                        TextureKey.PATTERN,
                        Identifier.of(blockId.getNamespace(), blockId.getPath())
                ),
                generator.modelCollector
        );

        WeightedVariant baseVariant = BlockStateModelGenerator.createWeightedVariant(modelId);

        generator.blockStateCollector.accept(
                VariantsBlockModelDefinitionCreator.of(block)
                        .with(
                                BlockStateVariantMap.models(Properties.HORIZONTAL_FACING)
                                        .register(Direction.NORTH, baseVariant)
                                        .register(Direction.EAST, baseVariant.apply(v -> v.withRotationY(AxisRotation.R90)))
                                        .register(Direction.SOUTH, baseVariant.apply(v -> v.withRotationY(AxisRotation.R180)))
                                        .register(Direction.WEST, baseVariant.apply(v -> v.withRotationY(AxisRotation.R270)))
                        )
        );

        generator.registerParentedItemModel(block, modelId);
    }

    public static void registerCustomLog(
            BlockStateModelGenerator generator, Block block,
            String sideNamespace, String sideTexture,
            String endNamespace, String endTexture
    ) {
        TextureMap textures = new TextureMap()
                .put(TextureKey.SIDE, Identifier.of(sideNamespace, sideTexture))
                .put(TextureKey.END, Identifier.of(endNamespace, endTexture));

        Identifier verticalModel = Models.CUBE_COLUMN.upload(block, textures, generator.modelCollector);
        Identifier horizontalModel = Models.CUBE_COLUMN_HORIZONTAL.upload(block, textures, generator.modelCollector);

        WeightedVariant verticalVariant = BlockStateModelGenerator.createWeightedVariant(verticalModel);
        WeightedVariant horizontalVariant = BlockStateModelGenerator.createWeightedVariant(horizontalModel);

        generator.blockStateCollector.accept(
                VariantsBlockModelDefinitionCreator.of(block)
                        .with(
                                BlockStateVariantMap.models(Properties.AXIS)
                                        .register(Direction.Axis.Y, verticalVariant)
                                        .register(
                                                Direction.Axis.X,
                                                horizontalVariant.apply(variant -> variant.withRotationY(AxisRotation.R90).withRotationX(AxisRotation.R90))
                                        )
                                        .register(
                                                Direction.Axis.Z,
                                                horizontalVariant.apply(variant -> variant.withRotationX(AxisRotation.R90))
                                        )
                        )
        );

        generator.registerParentedItemModel(block, verticalModel);
    }

    public static void registerCarpet(BlockStateModelGenerator generator, Block carpet) {
        Identifier carpetModel = TexturedModel.CARPET.upload(carpet, generator.modelCollector);
        WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(carpetModel);

        generator.blockStateCollector.accept(
                BlockStateModelGenerator.createSingletonBlockState(carpet, weightedVariant)
        );

        generator.registerParentedItemModel(carpet, carpetModel);
    }

    public static void registerWall(BlockStateModelGenerator generator, IBlockFamily family, Block block) {
        Identifier blockId = ModelIds.getBlockModelId(block);
        Identifier textureId = getTextureId(family, block);

        Identifier postModel = Identifier.of(blockId.getNamespace(), blockId.getPath() + "_post");
        Identifier sideModel = Identifier.of(blockId.getNamespace(), blockId.getPath() + "_side");
        Identifier sideTallModel = Identifier.of(blockId.getNamespace(), blockId.getPath() + "_side_tall");
        Identifier inventoryModel = Identifier.of(blockId.getNamespace(), blockId.getPath() + "_inventory");

        TextureMap textures = new TextureMap()
                .put(TextureKey.WALL, textureId)
                .put(TextureKey.PARTICLE, textureId);

        Models.TEMPLATE_WALL_POST.upload(postModel, textures, generator.modelCollector);
        Models.TEMPLATE_WALL_SIDE.upload(sideModel, textures, generator.modelCollector);
        Models.TEMPLATE_WALL_SIDE_TALL.upload(sideTallModel, textures, generator.modelCollector);
        Models.WALL_INVENTORY.upload(inventoryModel, textures, generator.modelCollector);

        WeightedVariant postVariant = BlockStateModelGenerator.createWeightedVariant(postModel);
        WeightedVariant sideVariant = BlockStateModelGenerator.createWeightedVariant(sideModel);
        WeightedVariant sideTallVariant = BlockStateModelGenerator.createWeightedVariant(sideTallModel);

        generator.blockStateCollector.accept(
                BlockStateModelGenerator.createWallBlockState(block, postVariant, sideVariant, sideTallVariant)
        );

        generator.registerParentedItemModel(block, inventoryModel);
    }

    public static void registerStairs(BlockStateModelGenerator generator, IBlockFamily family, Block block) {
        Identifier blockId = ModelIds.getBlockModelId(block);
        Identifier textureId = getTextureId(family, block);

        Identifier straightModel = Identifier.of(blockId.getNamespace(), blockId.getPath());
        Identifier innerModel = Identifier.of(blockId.getNamespace(), blockId.getPath() + "_inner");
        Identifier outerModel = Identifier.of(blockId.getNamespace(), blockId.getPath() + "_outer");

        TextureMap textures = createBlockTextureMap(textureId);

        Models.STAIRS.upload(straightModel, textures, generator.modelCollector);
        Models.INNER_STAIRS.upload(innerModel, textures, generator.modelCollector);
        Models.OUTER_STAIRS.upload(outerModel, textures, generator.modelCollector);

        WeightedVariant straightVariant = BlockStateModelGenerator.createWeightedVariant(straightModel);
        WeightedVariant innerVariant = BlockStateModelGenerator.createWeightedVariant(innerModel);
        WeightedVariant outerVariant = BlockStateModelGenerator.createWeightedVariant(outerModel);

        generator.blockStateCollector.accept(
                BlockStateModelGenerator.createStairsBlockState(block, innerVariant, straightVariant, outerVariant)
        );

        generator.registerParentedItemModel(block, straightModel);
    }

    public static void registerSlab(BlockStateModelGenerator generator, IBlockFamily family, Block block) {
        Identifier blockId = ModelIds.getBlockModelId(block);
        Identifier textureId = getTextureId(family, block);
        Identifier bottomModel = Identifier.of(blockId.getNamespace(), blockId.getPath());
        Identifier topModel = Identifier.of(blockId.getNamespace(), blockId.getPath() + "_top");
        Identifier fullBlockModel = getFullBlockModelId(family, block);

        TextureMap textures = createBlockTextureMap(textureId);

        Models.SLAB.upload(bottomModel, textures, generator.modelCollector);
        Models.SLAB_TOP.upload(topModel, textures, generator.modelCollector);

        WeightedVariant bottomVariant = BlockStateModelGenerator.createWeightedVariant(bottomModel);
        WeightedVariant topVariant = BlockStateModelGenerator.createWeightedVariant(topModel);
        WeightedVariant doubleVariant = BlockStateModelGenerator.createWeightedVariant(fullBlockModel);

        generator.blockStateCollector.accept(
                VariantsBlockModelDefinitionCreator.of(block)
                        .with(
                                BlockStateVariantMap.models(SlabBlock.TYPE)
                                        .register(net.minecraft.block.enums.SlabType.BOTTOM, bottomVariant)
                                        .register(net.minecraft.block.enums.SlabType.TOP, topVariant)
                                        .register(net.minecraft.block.enums.SlabType.DOUBLE, doubleVariant)
                        )
        );

        generator.registerParentedItemModel(block, bottomModel);
    }

    public static void registerVerticalSlab(BlockStateModelGenerator generator, IBlockFamily family, Block block) {
        Identifier blockId = ModelIds.getBlockModelId(block);
        Identifier textureId = getTextureId(family, block);
        Identifier singleModel = Identifier.of(blockId.getNamespace(), blockId.getPath());
        Identifier fullBlockModel = getFullBlockModelId(family, block);

        TextureMap textures = createBlockTextureMap(textureId);

        generator.modelCollector.accept(singleModel, () -> createVerticalSlabModelJson(textures));

        WeightedVariant northSingle = BlockStateModelGenerator.createWeightedVariant(singleModel);
        WeightedVariant eastSingle = northSingle.apply(v -> v.withRotationY(AxisRotation.R90));
        WeightedVariant southSingle = northSingle.apply(v -> v.withRotationY(AxisRotation.R180));
        WeightedVariant westSingle = northSingle.apply(v -> v.withRotationY(AxisRotation.R270));
        WeightedVariant doubleVariant = BlockStateModelGenerator.createWeightedVariant(fullBlockModel);

        generator.blockStateCollector.accept(
                VariantsBlockModelDefinitionCreator.of(block)
                        .with(
                                BlockStateVariantMap.models(
                                                HorizontalFacingBlock.FACING,
                                                VerticalSlabBlock.TYPE,
                                                Properties.WATERLOGGED
                                        )
                                        .register(Direction.NORTH, VerticalSlabType.SINGLE, false, northSingle)
                                        .register(Direction.EAST, VerticalSlabType.SINGLE, false, eastSingle)
                                        .register(Direction.SOUTH, VerticalSlabType.SINGLE, false, southSingle)
                                        .register(Direction.WEST, VerticalSlabType.SINGLE, false, westSingle)

                                        .register(Direction.NORTH, VerticalSlabType.SINGLE, true, northSingle)
                                        .register(Direction.EAST, VerticalSlabType.SINGLE, true, eastSingle)
                                        .register(Direction.SOUTH, VerticalSlabType.SINGLE, true, southSingle)
                                        .register(Direction.WEST, VerticalSlabType.SINGLE, true, westSingle)

                                        .register(Direction.NORTH, VerticalSlabType.DOUBLE, false, doubleVariant)
                                        .register(Direction.EAST, VerticalSlabType.DOUBLE, false, doubleVariant)
                                        .register(Direction.SOUTH, VerticalSlabType.DOUBLE, false, doubleVariant)
                                        .register(Direction.WEST, VerticalSlabType.DOUBLE, false, doubleVariant)

                                        .register(Direction.NORTH, VerticalSlabType.DOUBLE, true, doubleVariant)
                                        .register(Direction.EAST, VerticalSlabType.DOUBLE, true, doubleVariant)
                                        .register(Direction.SOUTH, VerticalSlabType.DOUBLE, true, doubleVariant)
                                        .register(Direction.WEST, VerticalSlabType.DOUBLE, true, doubleVariant)
                        )
        );

        generator.registerParentedItemModel(block, singleModel);
    }

    private static com.google.gson.JsonObject createVerticalSlabModelJson(TextureMap textures) {
        Identifier bottom = textures.getTexture(TextureKey.BOTTOM);
        Identifier top = textures.getTexture(TextureKey.TOP);
        Identifier side = textures.getTexture(TextureKey.SIDE);
        Identifier particle = textures.getTexture(TextureKey.PARTICLE);

        return com.google.gson.JsonParser.parseString("""
    {
      "parent": "block/block",
      "textures": {
        "bottom": "%s:%s",
        "top": "%s:%s",
        "side": "%s:%s",
        "particle": "%s:%s"
      },
      "elements": [
        {
          "from": [0, 0, 0],
          "to": [16, 16, 8],
          "faces": {
            "down":  { "texture": "#bottom" },
            "up":    { "texture": "#top" },
            "north": { "texture": "#side" },
            "south": { "texture": "#side" },
            "west":  { "texture": "#side" },
            "east":  { "texture": "#side" }
          }
        }
      ]
    }
    """.formatted(
                bottom.getNamespace(), bottom.getPath(),
                top.getNamespace(), top.getPath(),
                side.getNamespace(), side.getPath(),
                particle.getNamespace(), particle.getPath()
        )).getAsJsonObject();
    }

    private static TextureMap createBlockTextureMap(Identifier textureId) {
        return new TextureMap()
                .put(TextureKey.BOTTOM, textureId)
                .put(TextureKey.TOP, textureId)
                .put(TextureKey.SIDE, textureId)
                .put(TextureKey.PARTICLE, textureId)
                .put(TextureKey.ALL, textureId);
    }

    private static boolean useMinecraftNamespace(IBlockFamily family) {
        return family == ALL_VANILLA_SLAB_VERTICAL_VARIANTS || family == ALL_TERRACOTTA_VARIANTS;
    }

    private static Identifier getFullBlockModelId(IBlockFamily family, Block block) {
        Identifier blockId = ModelIds.getBlockModelId(block);
        String baseModelPath = getBasePathFromVariantModelPath(blockId.getPath());
        String baseModelNamespace = useMinecraftNamespace(family) ? "minecraft" : blockId.getNamespace();
        return Identifier.of(baseModelNamespace, baseModelPath);
    }

    private static Identifier getTextureId(IBlockFamily family, Block block) {
        Identifier blockId = ModelIds.getBlockModelId(block);
        String basePath = getBasePathFromVariantPath(blockId.getPath());
        String textureNamespace = useMinecraftNamespace(family) ? "minecraft" : blockId.getNamespace();
        return Identifier.of(textureNamespace, basePath);
    }

    private static String getBasePathFromVariantModelPath(String path) {
        return getBasePathFromVariantPath(path)
                .replace("_top", "")
                .replace("_bottom", "")
                .replace("_side", "");
    }

    private static String getBasePathFromVariantPath(String path) {
        String result = path
                .replace("_slab_vertical", "")
                .replace("_slab", "")
                .replace("_stairs", "")
                .replace("_wall", "")
                .replace("brick", "bricks")
                .replace("tile", "tiles")
                .replace("waxed_", "");

        if (BLOCK_SUFFIX_EXCEPTIONS.contains(result)) {
            result += "_block";
        }

        return switch (result) {
            case "block/quartz" -> "block/quartz_block_side";
            case "block/smooth_quartz" -> "block/quartz_block_bottom";
            case "block/smooth_sandstone" -> "block/sandstone_top";
            case "block/smooth_red_sandstone" -> "block/red_sandstone_top";
            default -> result;
        };
    }
}