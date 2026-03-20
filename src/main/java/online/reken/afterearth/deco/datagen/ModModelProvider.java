//package online.reken.afterearth.deco.datagen;
//
//import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
//import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
//import net.minecraft.block.*;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.data.*;
//import net.minecraft.client.render.model.json.WeightedVariant;
//import net.minecraft.network.packet.CustomPayload;
//import net.minecraft.state.property.Properties;
//import net.minecraft.util.Identifier;
//import net.minecraft.util.math.AxisRotation;
//import net.minecraft.util.math.Direction;
//import online.reken.afterearth.deco.AfterEarth_Decorations;
//import online.reken.afterearth.deco.block.CustomBlocks;
//
//import static online.reken.afterearth.deco.block.CustomBlocks.*;
//
//public class ModModelProvider extends FabricModelProvider {
//    public ModModelProvider(FabricDataOutput output) {
//        super(output);
//    }
//
//    @Override
//    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
//        //for (Block block : TEST_FAMILY.normal()) registerBlocks(blockStateModelGenerator, TEST_FAMILY, block);
//        registerBlockFamily(blockStateModelGenerator, TEST_FAMILY);
//
//        for (Block block : QUARTZ_CHECKER_FAMILY.normal())
//            blockStateModelGenerator.registerSimpleCubeAll(block);
//
//        for (Block block : QUARTZ_TILE_FAMILY.normal())
//            blockStateModelGenerator.registerSimpleCubeAll(block);
//
//        registerBlockFamily(blockStateModelGenerator, ANDESITE_BRICK_FAMILY);
//        registerBlockFamily(blockStateModelGenerator, GRANITE_BRICK_FAMILY);
//        registerBlockFamily(blockStateModelGenerator, DIORITE_BRICK_FAMILY);
//        registerBlockFamily(blockStateModelGenerator, METAL_SHEET_FAMILY);
//
//        for (Block block : STREET_LINE_BLACK_GRAVEL_FAMILY.normal()) registerBlocks(blockStateModelGenerator, STREET_LINE_BLACK_GRAVEL_FAMILY, block);
//        for (Block block : STREET_LINE_GRAY_GRAVEL_FAMILY.normal()) registerBlocks(blockStateModelGenerator, STREET_LINE_GRAY_GRAVEL_FAMILY, block);
//    }
//
//    @Override
//    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
//        // itemModelGenerator.register(...);
//    }
//
//    Block glassBlock;
//    private void registerBlocks(BlockStateModelGenerator blockStateModelGenerator, CustomBlocks.IBlockFamily family, Block block) {
//        if (block instanceof PillarBlock) {
//            if (family.isVanillaFamily())
//                blockStateModelGenerator.createLogTexturePool(block).log(block);
//            else {
//                registerCustomLog(
//                        blockStateModelGenerator,
//                        block,
//                        ModelIds.getBlockModelId(block).getPath(),
//                        ModelIds.getBlockModelId(family.getFamilyBaseModel()).getPath()
//                );
//            }
//        } else if (block instanceof GlazedTerracottaBlock) {
//            registerGlazedTerracotta(blockStateModelGenerator, block);
//        } else if (block instanceof TransparentBlock) {
//            glassBlock = block;
//        } else if (block instanceof PaneBlock) {
//            if (glassBlock != null)
//                blockStateModelGenerator.registerGlassAndPane(glassBlock, block);
//            else
//                AfterEarth_Decorations.LOGGER.error("Cant find glass block, for: " + block.getName());
//        } else if (block instanceof DoorBlock) {
//            blockStateModelGenerator.registerDoor(block);
//        } else if (block instanceof TrapdoorBlock) {
//            blockStateModelGenerator.registerTrapdoor(block);
//        } else if (block instanceof CarpetBlock) {
//            registerCarpet(blockStateModelGenerator, block);
//        } else if (block instanceof LeavesBlock) {
//            blockStateModelGenerator.registerTintedBlockAndItem(block, TexturedModel.LEAVES, 16777215);
//        } else {
//            AfterEarth_Decorations.LOGGER.error("Cant find block type, for: " + block.getName());
//            blockStateModelGenerator.registerSimpleCubeAll(block);
//        }
//    }
//
//    public static void registerGlazedTerracotta(BlockStateModelGenerator generator, Block block) {
//        Identifier modelId = Models.TEMPLATE_GLAZED_TERRACOTTA.upload(
//                block,
//                new TextureMap().put(TextureKey.PATTERN, Identifier.of(AfterEarth_Decorations.MOD_ID, ModelIds.getBlockModelId(block).getPath())),
//                generator.modelCollector
//        );
//
//        WeightedVariant baseVariant = BlockStateModelGenerator.createWeightedVariant(modelId);
//
//        generator.blockStateCollector.accept(
//                VariantsBlockModelDefinitionCreator.of(block)
//                        .with(
//                                BlockStateVariantMap.models(Properties.HORIZONTAL_FACING)
//                                        .register(Direction.NORTH, baseVariant)
//                                        .register(Direction.EAST,  baseVariant.apply(v -> v.withRotationY(AxisRotation.R90)))
//                                        .register(Direction.SOUTH, baseVariant.apply(v -> v.withRotationY(AxisRotation.R180)))
//                                        .register(Direction.WEST,  baseVariant.apply(v -> v.withRotationY(AxisRotation.R270)))
//                        )
//        );
//
//        generator.registerParentedItemModel(block, modelId);
//    }
//
//    public static void registerCustomLog(BlockStateModelGenerator generator, Block block, String sideTexture, String endTexture) {
//        TextureMap textures = new TextureMap()
//                .put(TextureKey.SIDE, Identifier.of(AfterEarth_Decorations.MOD_ID, sideTexture))
//                .put(TextureKey.END, Identifier.of(AfterEarth_Decorations.MOD_ID, endTexture));
//
//        Identifier verticalModel = Models.CUBE_COLUMN.upload(
//                block,
//                textures,
//                generator.modelCollector
//        );
//
//        Identifier horizontalModel = Models.CUBE_COLUMN_HORIZONTAL.upload(
//                block,
//                textures,
//                generator.modelCollector
//        );
//
//        WeightedVariant verticalVariant = BlockStateModelGenerator.createWeightedVariant(verticalModel);
//        WeightedVariant horizontalVariant = BlockStateModelGenerator.createWeightedVariant(horizontalModel);
//
//        generator.blockStateCollector.accept(
//                VariantsBlockModelDefinitionCreator.of(block)
//                        .with(
//                                BlockStateVariantMap.models(Properties.AXIS)
//                                        .register(Direction.Axis.Y, verticalVariant)
//                                        .register(Direction.Axis.X,
//                                                horizontalVariant.apply(variant -> variant.withRotationY(AxisRotation.R90).withRotationX(AxisRotation.R90)))
//                                        .register(Direction.Axis.Z,
//                                                horizontalVariant.apply(variant -> variant.withRotationX(AxisRotation.R90)))
//                        )
//        );
//
//        generator.registerParentedItemModel(block, verticalModel);
//    }
//
//    public final void registerCarpet(BlockStateModelGenerator blockStateModelGenerator, Block carpet) {
//        WeightedVariant weightedVariant = BlockStateModelGenerator.createWeightedVariant(TexturedModel.CARPET.upload(carpet, blockStateModelGenerator.modelCollector));
//        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(carpet, weightedVariant));
//    }
//
//    private void registerBlockFamily(
//            BlockStateModelGenerator blockStateModelGenerator,
//            IBlockFamily family
//    ) {
//        Block _baseBlock = family.getFamilyBaseModel();
//        BlockStateModelGenerator.BlockTexturePool pool =
//                blockStateModelGenerator.registerCubeAllModelTexturePool(_baseBlock);
//
//        for (Block block : family.normal()) {
//            if (block == _baseBlock) {
//                continue;
//            }
//
//            if (block instanceof SlabBlock) {
//                pool.slab(block);
//            } else if (block instanceof StairsBlock) {
//                pool.stairs(block);
//            } else if (block instanceof WallBlock) {
//                pool.wall(block);
//            } else if (block instanceof PillarBlock) {
//                blockStateModelGenerator.createLogTexturePool(block).log(block);
//            } else if (block instanceof TransparentBlock) {
//                glassBlock = block;
//            } else if (block instanceof PaneBlock) {
//                if (glassBlock != null)
//                    blockStateModelGenerator.registerGlassAndPane(glassBlock, block);
//                else
//                    AfterEarth_Decorations.LOGGER.error("Cant find glass block, for: " + block.getName());
//            } else if (block instanceof DoorBlock) {
//                blockStateModelGenerator.registerDoor(block);
//            } else if (block instanceof TrapdoorBlock) {
//                blockStateModelGenerator.registerTrapdoor(block);
//            } else if (block instanceof CarpetBlock) {
//                registerCarpet(blockStateModelGenerator, block);
//            } else if (block instanceof LeavesBlock) {
//                blockStateModelGenerator.registerTintedBlockAndItem(block, TexturedModel.LEAVES, 16777215);
//            } else {
//                blockStateModelGenerator.registerSimpleCubeAll(block);
//            }
//        }
//    }
//}
