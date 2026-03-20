package online.reken.afterearth.deco.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
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

import java.util.List;

import static online.reken.afterearth.deco.block.CustomBlocks.*;

public class ModModelProvider2 extends FabricModelProvider {
    private static final List<IBlockFamily> TEXTURE_POOL_FAMILIES = List.of(
            TEST_FAMILY,
            ANDESITE_BRICK_FAMILY,
            GRANITE_BRICK_FAMILY,
            DIORITE_BRICK_FAMILY,
            METAL_SHEET_FAMILY,
            EXPOSED_METAL_SHEET_FAMILY,
            WEATHERED_METAL_FAMILY
    );

    private static final List<IBlockFamily> STANDALONE_BLOCK_FAMILIES = List.of(
            STREET_LINE_BLACK_GRAVEL_FAMILY,
            STREET_LINE_GRAY_GRAVEL_FAMILY,
            CONTAINER_FAMILY
    );

    private static final List<IBlockFamily> STANDALONE_CUBE_FAMILIES = List.of(
            QUARTZ_CHECKER_FAMILY,
            QUARTZ_TILE_FAMILY
    );

    private Block pendingGlassBlock;

    public ModModelProvider2(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        for (IBlockFamily family : TEXTURE_POOL_FAMILIES) {
            registerBlockFamily(generator, family);
        }

        for (IBlockFamily family : STANDALONE_CUBE_FAMILIES) {
            registerStandaloneCubeFamily(generator, family);
        }

        for (IBlockFamily family : STANDALONE_BLOCK_FAMILIES) {
            registerStandaloneFamily(generator, family);
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }

    private void registerStandaloneCubeFamily(BlockStateModelGenerator generator, IBlockFamily family) {
        for (Block block : family.normal()) {
            generator.registerSimpleCubeAll(block);
        }
    }

    private void registerStandaloneFamily(BlockStateModelGenerator generator, IBlockFamily family) {
        pendingGlassBlock = null;

        for (Block block : family.normal()) {
            registerStandaloneBlock(generator, family, block);
        }
    }

    private void registerStandaloneBlock(BlockStateModelGenerator generator, IBlockFamily family, Block block) {
        switch (DatagenBlockKind.resolve(block)) {
            case CUBE -> generator.registerSimpleCubeAll(block);
            case SLAB, STAIRS, WALL -> throw new IllegalStateException(
                    "Standalone family contains unsupported block type: "
                            + DatagenBlockKind.resolve(block)
                            + " for block " + ModelIds.getBlockModelId(block)
                            + ". Move it to a texture-pool family or add custom standalone handling."
            );
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

    private void registerBlockFamily(BlockStateModelGenerator generator, IBlockFamily family) {
        pendingGlassBlock = null;

        Block baseBlock = family.getBaseBlock();
        BlockStateModelGenerator.BlockTexturePool pool = generator.registerCubeAllModelTexturePool(baseBlock);

        for (Block block : family.normal()) {
            if (block == baseBlock) {
                continue;
            }
            registerFamilyMember(generator, family, pool, block);
        }
    }

    private void registerFamilyMember(
            BlockStateModelGenerator generator,
            IBlockFamily family,
            BlockStateModelGenerator.BlockTexturePool pool,
            Block block
    ) {
        switch (DatagenBlockKind.resolve(block)) {
            case SLAB -> pool.slab(block);
            case STAIRS -> pool.stairs(block);
            case WALL -> pool.wall(block);
            case CUBE -> generator.registerSimpleCubeAll(block);
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
        if (String.valueOf(block.getName()).contains("_pillar")){
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
            BlockStateModelGenerator generator,
            Block block,
            String sideNamespace,
            String sideTexture,
            String endNamespace,
            String endTexture
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
}