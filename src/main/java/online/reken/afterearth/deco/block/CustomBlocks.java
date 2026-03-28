package online.reken.afterearth.deco.block;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import online.reken.afterearth.deco.AfterEarth_Decorations;
import online.reken.afterearth.deco.block.custom.RazorWireBlock;
import online.reken.afterearth.deco.block.custom.VerticalSlabBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CustomBlocks {
    //List of new blocks
    ///Test Blocks
    public static final Block Test_Block = registerBlock("test_block", AbstractBlock.Settings.create()
            .mapColor(MapColor.BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F)
            .nonOpaque().solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never));
    public static final Block Test_Slab = registerCustomBlock("test_slab", SlabBlock::new, AbstractBlock.Settings.copy(Test_Block));
    public static final Block Test_Slab_Vertical = registerCustomBlock("test_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Test_Block));
    public static final Block Test_Stairs = registerStairs(CustomBlocks.Test_Block,"test_stairs", AbstractBlock.Settings.copy(Test_Block));
    public static final Block Test_Wall = registerCustomBlock("test_wall", WallBlock::new, AbstractBlock.Settings.copy(Test_Block));
    public static final Block Test_Pillar = registerCustomBlock("test_pillar", PillarBlock::new, AbstractBlock.Settings.copy(Test_Block));
    public static final Block Test_Glass = registerCustomBlock("test_glass", TransparentBlock::new, AbstractBlock.Settings.copy(Test_Block));
    public static final Block Test_Pane = registerCustomBlock("test_pane", PaneBlock::new, AbstractBlock.Settings.copy(Test_Block));
    public static final Block Test_Door = registerDoor(BlockSetType.OAK, "test_door", AbstractBlock.Settings.copy(Test_Block));
    public static final Block Test_Trapdoor = registerTrapdoor(BlockSetType.OAK, "test_trapdoor", AbstractBlock.Settings.copy(Test_Block));
    public static final Block Test_Carpet = registerCarpet("test_carpet", AbstractBlock.Settings.copy(Test_Block));
    public static final Block Test_Leaves = registerLeaves("test_leaves", AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).mapColor(MapColor.BLUE).nonOpaque());

    /// All Vertical variants of normal vanila slab blocks
    public static final Block Brick_Slab_Vertical = registerCustomBlock("brick_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.BRICK_SLAB));
    public static final Block Stone_Slab_Vertical = registerCustomBlock("stone_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.STONE_SLAB));
    public static final Block Smooth_Stone_Slab_Vertical = registerCustomBlock("smooth_stone_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.SMOOTH_STONE_SLAB));
    public static final Block Sandstone_Slab_Vertical = registerCustomBlock("sandstone_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.SANDSTONE_SLAB));
    public static final Block Cut_Sandstone_Slab_Vertical = registerCustomBlock("cut_sandstone_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.CUT_SANDSTONE_SLAB));
    public static final Block Cobblestone_Slab_Vertical = registerCustomBlock("cobblestone_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.COBBLESTONE_SLAB));
    public static final Block Stone_Brick_Slab_Vertical = registerCustomBlock("stone_brick_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.STONE_BRICK_SLAB));
    public static final Block Mud_Brick_Slab_Vertical = registerCustomBlock("mud_brick_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.MUD_BRICK_SLAB));
    public static final Block Nether_Brick_Slab_Vertical = registerCustomBlock("nether_brick_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.NETHER_BRICK_SLAB));
    public static final Block Quartz_Slab_Vertical = registerCustomBlock("quartz_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.QUARTZ_SLAB));
    public static final Block Red_Sandstone_Slab_Vertical = registerCustomBlock("red_sandstone_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.RED_SANDSTONE_SLAB));
    public static final Block Cut_Red_Sandstone_Slab_Vertical = registerCustomBlock("cut_red_sandstone_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.CUT_RED_SANDSTONE_SLAB));
    public static final Block Purpur_Slab_Vertical = registerCustomBlock("purpur_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.PURPUR_SLAB));
    public static final Block Prismarine_Slab_Vertical = registerCustomBlock("prismarine_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.PRISMARINE_SLAB));
    public static final Block Prismarine_Brick_Slab_Vertical = registerCustomBlock("prismarine_brick_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.PRISMARINE_BRICK_SLAB));
    public static final Block Dark_Prismarine_Slab_Vertical = registerCustomBlock("dark_prismarine_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.DARK_PRISMARINE_SLAB));
    public static final Block Smooth_Quartz_Slab_Vertical = registerCustomBlock("smooth_quartz_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.SMOOTH_QUARTZ_SLAB));
    public static final Block Smooth_Red_Sandstone_Slab_Vertical = registerCustomBlock("smooth_red_sandstone_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.SMOOTH_RED_SANDSTONE_SLAB));
    public static final Block Smooth_Sandstone_Slab_Vertical = registerCustomBlock("smooth_sandstone_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.SMOOTH_SANDSTONE_SLAB));
    public static final Block Mossy_Cobblestone_Slab_Vertical = registerCustomBlock("mossy_cobblestone_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.MOSSY_COBBLESTONE_SLAB));
    public static final Block Mossy_Stone_Brick_Slab_Vertical = registerCustomBlock("mossy_stone_brick_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.MOSSY_STONE_BRICK_SLAB));
    public static final Block End_Stone_Brick_Slab_Vertical = registerCustomBlock("end_stone_brick_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.END_STONE_BRICK_SLAB));
    public static final Block Red_Nether_Brick_Slab_Vertical = registerCustomBlock("red_nether_brick_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.RED_NETHER_BRICK_SLAB));
    public static final Block Polished_Andesite_Slab_Vertical = registerCustomBlock("polished_andesite_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.POLISHED_ANDESITE_SLAB));
    public static final Block Polished_Diorite_Slab_Vertical = registerCustomBlock("polished_diorite_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE_SLAB));
    public static final Block Polished_Granite_Slab_Vertical = registerCustomBlock("polished_granite_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.POLISHED_GRANITE_SLAB));
    public static final Block Andesite_Slab_Vertical = registerCustomBlock("andesite_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.ANDESITE_SLAB));
    public static final Block Diorite_Slab_Vertical = registerCustomBlock("diorite_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.DIORITE_SLAB));
    public static final Block Granite_Slab_Vertical = registerCustomBlock("granite_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.GRANITE_SLAB));
    public static final Block Deepslate_Brick_Slab_Vertical = registerCustomBlock("deepslate_brick_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.DEEPSLATE_BRICK_SLAB));
    public static final Block Deepslate_Tile_Slab_Vertical = registerCustomBlock("deepslate_tile_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.DEEPSLATE_TILE_SLAB));
    public static final Block Cobbled_Deepslate_Slab_Vertical = registerCustomBlock("cobbled_deepslate_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.COBBLED_DEEPSLATE_SLAB));
    public static final Block Polished_Deepslate_Slab_Vertical = registerCustomBlock("polished_deepslate_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.POLISHED_DEEPSLATE_SLAB));
    public static final Block Blackstone_Slab_Vertical = registerCustomBlock("blackstone_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.BLACKSTONE_SLAB));
    public static final Block Polished_Blackstone_Slab_Vertical = registerCustomBlock("polished_blackstone_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.POLISHED_BLACKSTONE_SLAB));
    public static final Block Polished_Blackstone_Brick_Slab_Vertical = registerCustomBlock("polished_blackstone_brick_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB));
    public static final Block Tuff_Slab_Vertical = registerCustomBlock("tuff_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.TUFF_SLAB));
    public static final Block Polished_Tuff_Slab_Vertical = registerCustomBlock("polished_tuff_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.POLISHED_TUFF_SLAB));
    public static final Block Tuff_Brick_Slab_Vertical = registerCustomBlock("tuff_brick_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.TUFF_BRICK_SLAB));
    public static final Block Cut_Copper_Slab_Vertical = registerCustomBlock("cut_copper_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.CUT_COPPER_SLAB));
    public static final Block Exposed_Cut_Copper_Slab_Vertical = registerCustomBlock("exposed_cut_copper_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.EXPOSED_CUT_COPPER_SLAB));
    public static final Block Weathered_Cut_Copper_Slab_Vertical = registerCustomBlock("weathered_cut_copper_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WEATHERED_CUT_COPPER_SLAB));
    public static final Block Oxidized_Cut_Copper_Slab_Vertical = registerCustomBlock("oxidized_cut_copper_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.OXIDIZED_CUT_COPPER_SLAB));
    public static final Block Waxed_Cut_Copper_Slab_Vertical = registerCustomBlock("waxed_cut_copper_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WAXED_CUT_COPPER_SLAB));
    public static final Block Waxed_Exposed_Cut_Copper_Slab_Vertical = registerCustomBlock("waxed_exposed_cut_copper_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB));
    public static final Block Waxed_Weathered_Cut_Copper_Slab_Vertical = registerCustomBlock("waxed_weathered_cut_copper_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB));
    public static final Block Waxed_Oxidized_Cut_Copper_Slab_Vertical = registerCustomBlock("waxed_oxidized_cut_copper_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB));

    public static final Block Oak_Slab_Vertical = registerCustomBlock("oak_planks_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_SLAB));
    public static final Block Spruce_Slab_Vertical = registerCustomBlock("spruce_planks_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.SPRUCE_SLAB));
    public static final Block Birch_Slab_Vertical = registerCustomBlock("birch_planks_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.BIRCH_SLAB));
    public static final Block Jungle_Slab_Vertical = registerCustomBlock("jungle_planks_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.JUNGLE_SLAB));
    public static final Block Acacia_Slab_Vertical = registerCustomBlock("acacia_planks_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.ACACIA_SLAB));
    public static final Block Dark_Oak_Slab_Vertical = registerCustomBlock("dark_oak_planks_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.DARK_OAK_SLAB));
    public static final Block Mangrove_Slab_Vertical = registerCustomBlock("mangrove_planks_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.MANGROVE_SLAB));
    public static final Block Cherry_Slab_Vertical = registerCustomBlock("cherry_planks_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.CHERRY_SLAB));
    public static final Block Pale_Oak_Slab_Vertical = registerCustomBlock("pale_oak_planks_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.PALE_OAK_PLANKS));
    public static final Block Bamboo_Slab_Vertical = registerCustomBlock("bamboo_planks_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.BAMBOO_SLAB));
    public static final Block Bamboo_Mosaic_Slab_Vertical = registerCustomBlock("bamboo_mosaic_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.BAMBOO_MOSAIC_SLAB));
    public static final Block Crimson_Slab_Vertical = registerCustomBlock("crimson_planks_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.CRIMSON_SLAB));
    public static final Block Warped_Slab_Vertical = registerCustomBlock("warped_planks_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WARPED_SLAB));

    /// TERRACOTTA VARIANTS
    public static final Block Terracotta_Stairs = registerStairsShort("terracotta_stairs", Blocks.TERRACOTTA);
    public static final Block Terracotta_Slab = registerCustomBlock("terracotta_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.TERRACOTTA));
    public static final Block Terracotta_Slab_Vertical = registerCustomBlock("terracotta_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.TERRACOTTA));
    public static final Block White_Terracotta_Stairs = registerStairsShort("white_terracotta_stairs", Blocks.WHITE_TERRACOTTA);
    public static final Block White_Terracotta_Slab = registerCustomBlock("white_terracotta_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_TERRACOTTA));
    public static final Block White_Terracotta_Slab_Vertical = registerCustomBlock("white_terracotta_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.WHITE_TERRACOTTA));
    public static final Block Orange_Terracotta_Stairs = registerStairsShort("orange_terracotta_stairs", Blocks.ORANGE_TERRACOTTA);
    public static final Block Orange_Terracotta_Slab = registerCustomBlock("orange_terracotta_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.ORANGE_TERRACOTTA));
    public static final Block Orange_Terracotta_Slab_Vertical = registerCustomBlock("orange_terracotta_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.ORANGE_TERRACOTTA));
    public static final Block Magenta_Terracotta_Stairs = registerStairsShort("magenta_terracotta_stairs", Blocks.MAGENTA_TERRACOTTA);
    public static final Block Magenta_Terracotta_Slab = registerCustomBlock("magenta_terracotta_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.MAGENTA_TERRACOTTA));
    public static final Block Magenta_Terracotta_Slab_Vertical = registerCustomBlock("magenta_terracotta_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.MAGENTA_TERRACOTTA));
    public static final Block Light_Blue_Terracotta_Stairs = registerStairsShort("light_blue_terracotta_stairs", Blocks.LIGHT_BLUE_TERRACOTTA);
    public static final Block Light_Blue_Terracotta_Slab = registerCustomBlock("light_blue_terracotta_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.LIGHT_BLUE_TERRACOTTA));
    public static final Block Light_Blue_Terracotta_Slab_Vertical = registerCustomBlock("light_blue_terracotta_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.LIGHT_BLUE_TERRACOTTA));
    public static final Block Yellow_Terracotta_Stairs = registerStairsShort("yellow_terracotta_stairs", Blocks.YELLOW_TERRACOTTA);
    public static final Block Yellow_Terracotta_Slab = registerCustomBlock("yellow_terracotta_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.YELLOW_TERRACOTTA));
    public static final Block Yellow_Terracotta_Slab_Vertical = registerCustomBlock("yellow_terracotta_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.YELLOW_TERRACOTTA));
    public static final Block Lime_Terracotta_Stairs = registerStairsShort("lime_terracotta_stairs", Blocks.LIME_TERRACOTTA);
    public static final Block Lime_Terracotta_Slab = registerCustomBlock("lime_terracotta_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.LIME_TERRACOTTA));
    public static final Block Lime_Terracotta_Slab_Vertical = registerCustomBlock("lime_terracotta_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.LIME_TERRACOTTA));
    public static final Block Pink_Terracotta_Stairs = registerStairsShort("pink_terracotta_stairs", Blocks.PINK_TERRACOTTA);
    public static final Block Pink_Terracotta_Slab = registerCustomBlock("pink_terracotta_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.PINK_TERRACOTTA));
    public static final Block Pink_Terracotta_Slab_Vertical = registerCustomBlock("pink_terracotta_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.PINK_TERRACOTTA));
    public static final Block Gray_Terracotta_Stairs = registerStairsShort("gray_terracotta_stairs", Blocks.GRAY_TERRACOTTA);
    public static final Block Gray_Terracotta_Slab = registerCustomBlock("gray_terracotta_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.GRAY_TERRACOTTA));
    public static final Block Gray_Terracotta_Slab_Vertical = registerCustomBlock("gray_terracotta_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.GRAY_TERRACOTTA));
    public static final Block Light_Gray_Terracotta_Stairs = registerStairsShort("light_gray_terracotta_stairs", Blocks.LIGHT_GRAY_TERRACOTTA);
    public static final Block Light_Gray_Terracotta_Slab = registerCustomBlock("light_gray_terracotta_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.LIGHT_GRAY_TERRACOTTA));
    public static final Block Light_Gray_Terracotta_Slab_Vertical = registerCustomBlock("light_gray_terracotta_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.LIGHT_GRAY_TERRACOTTA));
    public static final Block Cyan_Terracotta_Stairs = registerStairsShort("cyan_terracotta_stairs", Blocks.CYAN_TERRACOTTA);
    public static final Block Cyan_Terracotta_Slab = registerCustomBlock("cyan_terracotta_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.CYAN_TERRACOTTA));
    public static final Block Cyan_Terracotta_Slab_Vertical = registerCustomBlock("cyan_terracotta_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.CYAN_TERRACOTTA));
    public static final Block Purple_Terracotta_Stairs = registerStairsShort("purple_terracotta_stairs", Blocks.PURPLE_TERRACOTTA);
    public static final Block Purple_Terracotta_Slab = registerCustomBlock("purple_terracotta_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.PURPLE_TERRACOTTA));
    public static final Block Purple_Terracotta_Slab_Vertical = registerCustomBlock("purple_terracotta_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.PURPLE_TERRACOTTA));
    public static final Block Blue_Terracotta_Stairs = registerStairsShort("blue_terracotta_stairs", Blocks.BLUE_TERRACOTTA);
    public static final Block Blue_Terracotta_Slab = registerCustomBlock("blue_terracotta_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BLUE_TERRACOTTA));
    public static final Block Blue_Terracotta_Slab_Vertical = registerCustomBlock("blue_terracotta_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.BLUE_TERRACOTTA));
    public static final Block Brown_Terracotta_Stairs = registerStairsShort("brown_terracotta_stairs", Blocks.BROWN_TERRACOTTA);
    public static final Block Brown_Terracotta_Slab = registerCustomBlock("brown_terracotta_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_TERRACOTTA));
    public static final Block Brown_Terracotta_Slab_Vertical = registerCustomBlock("brown_terracotta_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.BROWN_TERRACOTTA));
    public static final Block Green_Terracotta_Stairs = registerStairsShort("green_terracotta_stairs", Blocks.GREEN_TERRACOTTA);
    public static final Block Green_Terracotta_Slab = registerCustomBlock("green_terracotta_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.GREEN_TERRACOTTA));
    public static final Block Green_Terracotta_Slab_Vertical = registerCustomBlock("green_terracotta_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.GREEN_TERRACOTTA));
    public static final Block Red_Terracotta_Stairs = registerStairsShort("red_terracotta_stairs", Blocks.RED_TERRACOTTA);
    public static final Block Red_Terracotta_Slab = registerCustomBlock("red_terracotta_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.RED_TERRACOTTA));
    public static final Block Red_Terracotta_Slab_Vertical = registerCustomBlock("red_terracotta_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.RED_TERRACOTTA));
    public static final Block Black_Terracotta_Stairs = registerStairsShort("black_terracotta_stairs", Blocks.BLACK_TERRACOTTA);
    public static final Block Black_Terracotta_Slab = registerCustomBlock("black_terracotta_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.BLACK_TERRACOTTA));
    public static final Block Black_Terracotta_Slab_Vertical = registerCustomBlock("black_terracotta_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Blocks.BLACK_TERRACOTTA));

    ///Interior
    // quartz_checker
    public static final Block Quartz_Checker_Black = registerBlock("quartz_checker_black", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.BLACK));
    public static final Block Quartz_Checker_Black_Yellow = registerBlock("quartz_checker_black_yellow", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.YELLOW));
    public static final Block Quartz_Checker_Blue = registerBlock("quartz_checker_blue", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.DARK_AQUA));
    public static final Block Quartz_Checker_Brown = registerBlock("quartz_checker_brown", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.BROWN));
    public static final Block Quartz_Checker_Gray = registerBlock("quartz_checker_gray", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.GRAY));
    public static final Block Quartz_Checker_Green = registerBlock("quartz_checker_green", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.GREEN));
    public static final Block Quartz_Checker_Black_Broken = registerBlock("quartz_checker_black_broken", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.BLACK));
    public static final Block Quartz_Checker_Black_Yellow_Broken = registerBlock("quartz_checker_black_yellow_broken", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.YELLOW));
    public static final Block Quartz_Checker_Blue_Broken = registerBlock("quartz_checker_blue_broken", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.DARK_AQUA));
    public static final Block Quartz_Checker_Brown_Broken = registerBlock("quartz_checker_brown_broken", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.BROWN));
    public static final Block Quartz_Checker_Gray_Broken = registerBlock("quartz_checker_gray_broken", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.GRAY));
    public static final Block Quartz_Checker_Green_Broken = registerBlock("quartz_checker_green_broken", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.GREEN));
    // quartz_tile
    public static final Block Quartz_Tile_Blue = registerBlock("quartz_tile_blue", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.BLUE));
    public static final Block Quartz_Tile_Black = registerBlock("quartz_tile_black", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.BLACK));
    public static final Block Quartz_Tile_Green = registerBlock("quartz_tile_green", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.DARK_GREEN));
    public static final Block Quartz_Tile_Orange = registerBlock("quartz_tile_orange", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.ORANGE));
    public static final Block Quartz_Tile_Purple = registerBlock("quartz_tile_purple", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.PURPLE));
    public static final Block Quartz_Tile_White = registerBlock("quartz_tile_white", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.WHITE));
    public static final Block Quartz_Tile_Blue_Broken = registerBlock("quartz_tile_blue_broken", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.BLUE));
    public static final Block Quartz_Tile_Black_Broken = registerBlock("quartz_tile_black_broken", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.BLACK));
    public static final Block Quartz_Tile_Green_Broken = registerBlock("quartz_tile_green_broken", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.DARK_GREEN));
    public static final Block Quartz_Tile_Orange_Broken = registerBlock("quartz_tile_orange_broken", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.ORANGE));
    public static final Block Quartz_Tile_Purple_Broken = registerBlock("quartz_tile_purple_broken", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.PURPLE));
    public static final Block Quartz_Tile_White_Broken = registerBlock("quartz_tile_white_broken", AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.WHITE));

    ///Exterior
    // bricks
    public static final Block Bricks_Broken = registerBlock("bricks_broken", AbstractBlock.Settings.copy(Blocks.BRICKS));
    public static final Block Brick_Slab_Broken = registerCustomBlock("brick_slab_broken", SlabBlock::new, AbstractBlock.Settings.copy(Bricks_Broken));
    public static final Block Brick_Slab_Vertical_Broken = registerCustomBlock("brick_slab_vertical_broken", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Bricks_Broken));
    public static final Block Brick_Stairs_Broken = registerStairs(CustomBlocks.Bricks_Broken,"brick_stairs_broken", AbstractBlock.Settings.copy(Bricks_Broken));
    public static final Block Brick_Wall_Broken = registerCustomBlock("brick_wall_broken", WallBlock::new, AbstractBlock.Settings.copy(Bricks_Broken));

    // andesite bricks
    public static final Block Andesite_Bricks = registerBlock("andesite_bricks", AbstractBlock.Settings.copy(Blocks.POLISHED_ANDESITE));
    public static final Block Andesite_Brick_Slab = registerCustomBlock("andesite_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Andesite_Bricks));
    public static final Block Andesite_Brick_Slab_Vertical = registerCustomBlock("andesite_brick_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Andesite_Bricks));
    public static final Block Andesite_Brick_Stairs = registerStairs(CustomBlocks.Andesite_Bricks,"andesite_brick_stairs", AbstractBlock.Settings.copy(Andesite_Bricks));
    public static final Block Andesite_Brick_Wall = registerCustomBlock("andesite_brick_wall", WallBlock::new, AbstractBlock.Settings.copy(Andesite_Bricks));
    public static final Block Andesite_Bricks_Broken = registerBlock("andesite_bricks_broken", AbstractBlock.Settings.copy(Andesite_Bricks));
    public static final Block Andesite_Brick_Slab_Broken = registerCustomBlock("andesite_brick_slab_broken", SlabBlock::new, AbstractBlock.Settings.copy(Andesite_Bricks));
    public static final Block Andesite_Brick_Slab_Vertical_Broken = registerCustomBlock("andesite_brick_slab_vertical_broken", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Andesite_Bricks));
    public static final Block Andesite_Brick_Stairs_Broken = registerStairs(CustomBlocks.Andesite_Bricks,"andesite_brick_stairs_broken", AbstractBlock.Settings.copy(Andesite_Bricks));
    public static final Block Andesite_Brick_Wall_Broken = registerCustomBlock("andesite_brick_wall_broken", WallBlock::new, AbstractBlock.Settings.copy(Andesite_Bricks));

    // granite bricks
    public static final Block Granite_Bricks = registerBlock("granite_bricks", AbstractBlock.Settings.copy(Blocks.POLISHED_GRANITE));
    public static final Block Granite_Brick_Slab = registerCustomBlock("granite_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Granite_Bricks));
    public static final Block Granite_Brick_Slab_Vertical = registerCustomBlock("granite_brick_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Granite_Bricks));
    public static final Block Granite_Brick_Stairs = registerStairs(CustomBlocks.Granite_Bricks,"granite_brick_stairs", AbstractBlock.Settings.copy(Granite_Bricks));
    public static final Block Granite_Brick_Wall = registerCustomBlock("granite_brick_wall", WallBlock::new, AbstractBlock.Settings.copy(Granite_Bricks));
    public static final Block Granite_Bricks_Broken = registerBlock("granite_bricks_broken", AbstractBlock.Settings.copy(Granite_Bricks));
    public static final Block Granite_Brick_Slab_Broken = registerCustomBlock("granite_brick_slab_broken", SlabBlock::new, AbstractBlock.Settings.copy(Granite_Bricks));
    public static final Block Granite_Brick_Slab_Vertical_Broken = registerCustomBlock("granite_brick_slab_vertical_broken", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Granite_Bricks));
    public static final Block Granite_Brick_Stairs_Broken = registerStairs(CustomBlocks.Granite_Bricks,"granite_brick_stairs_broken", AbstractBlock.Settings.copy(Granite_Bricks));
    public static final Block Granite_Brick_Wall_Broken = registerCustomBlock("granite_brick_wall_broken", WallBlock::new, AbstractBlock.Settings.copy(Granite_Bricks));

    // diorite bricks
    public static final Block Diorite_Bricks = registerBlock("diorite_bricks", AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE));
    public static final Block Diorite_Brick_Slab = registerCustomBlock("diorite_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Diorite_Bricks));
    public static final Block Diorite_Brick_Slab_Vertical = registerCustomBlock("diorite_brick_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Diorite_Bricks));
    public static final Block Diorite_Brick_Stairs = registerStairs(CustomBlocks.Diorite_Bricks,"diorite_brick_stairs", AbstractBlock.Settings.copy(Diorite_Bricks));
    public static final Block Diorite_Brick_Wall = registerCustomBlock("diorite_brick_wall", WallBlock::new, AbstractBlock.Settings.copy(Diorite_Bricks));
    public static final Block Diorite_Bricks_Broken = registerBlock("diorite_bricks_broken", AbstractBlock.Settings.copy(Diorite_Bricks));
    public static final Block Diorite_Brick_Slab_Broken = registerCustomBlock("diorite_brick_slab_broken", SlabBlock::new, AbstractBlock.Settings.copy(Diorite_Bricks));
    public static final Block Diorite_Brick_Slab_Vertical_Broken = registerCustomBlock("diorite_brick_slab_vertical_broken", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Diorite_Bricks));
    public static final Block Diorite_Brick_Stairs_Broken = registerStairs(CustomBlocks.Diorite_Bricks,"diorite_brick_stairs_broken", AbstractBlock.Settings.copy(Diorite_Bricks));
    public static final Block Diorite_Brick_Wall_Broken = registerCustomBlock("diorite_brick_wall_broken", WallBlock::new, AbstractBlock.Settings.copy(Diorite_Bricks));

    // metal_sheet
    public static final Block Metal_Sheet = registerBlock("metal_sheet", AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block Metal_Sheet_Slab = registerCustomBlock("metal_sheet_slab", SlabBlock::new, AbstractBlock.Settings.copy(Metal_Sheet));
    public static final Block Metal_Sheet_Slab_Vertical = registerCustomBlock("metal_sheet_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Metal_Sheet));
    public static final Block Metal_Sheet_Stairs = registerStairs(CustomBlocks.Metal_Sheet,"metal_sheet_stairs", AbstractBlock.Settings.copy(Metal_Sheet));
    public static final Block Metal_Sheet_Wall = registerCustomBlock("metal_sheet_wall", WallBlock::new, AbstractBlock.Settings.copy(Metal_Sheet));
    public static final Block Exposed_Metal_Sheet = registerBlock("exposed_metal_sheet", AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block Exposed_Metal_Sheet_Slab = registerCustomBlock("exposed_metal_sheet_slab", SlabBlock::new, AbstractBlock.Settings.copy(Exposed_Metal_Sheet));
    public static final Block Exposed_Metal_Sheet_Slab_Vertical = registerCustomBlock("exposed_metal_sheet_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Exposed_Metal_Sheet));
    public static final Block Exposed_Metal_Sheet_Stairs = registerStairs(CustomBlocks.Exposed_Metal_Sheet,"exposed_metal_sheet_stairs", AbstractBlock.Settings.copy(Exposed_Metal_Sheet));
    public static final Block Exposed_Metal_Sheet_Wall = registerCustomBlock("exposed_metal_sheet_wall", WallBlock::new, AbstractBlock.Settings.copy(Exposed_Metal_Sheet));
    public static final Block Weathered_Metal_Sheet = registerBlock("weathered_metal_sheet", AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block Weathered_Metal_Sheet_Slab = registerCustomBlock("weathered_metal_sheet_slab", SlabBlock::new, AbstractBlock.Settings.copy(Weathered_Metal_Sheet));
    public static final Block Weathered_Metal_Sheet_Slab_Vertical = registerCustomBlock("weathered_metal_sheet_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Weathered_Metal_Sheet));
    public static final Block Weathered_Metal_Sheet_Stairs = registerStairs(CustomBlocks.Weathered_Metal_Sheet,"weathered_metal_sheet_stairs", AbstractBlock.Settings.copy(Weathered_Metal_Sheet));
    public static final Block Weathered_Metal_Sheet_Wall = registerCustomBlock("weathered_metal_sheet_wall", WallBlock::new, AbstractBlock.Settings.copy(Weathered_Metal_Sheet));
    public static final Block Rusted_Metal_Sheet = registerBlock("rusted_metal_sheet", AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block Rusted_Metal_Sheet_Slab = registerCustomBlock("rusted_metal_sheet_slab", SlabBlock::new, AbstractBlock.Settings.copy(Rusted_Metal_Sheet));
    public static final Block Rusted_Metal_Sheet_Slab_Vertical = registerCustomBlock("rusted_metal_sheet_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Rusted_Metal_Sheet));
    public static final Block Rusted_Metal_Sheet_Stairs = registerStairs(CustomBlocks.Rusted_Metal_Sheet,"rusted_metal_sheet_stairs", AbstractBlock.Settings.copy(Rusted_Metal_Sheet));
    public static final Block Rusted_Metal_Sheet_Wall = registerCustomBlock("rusted_metal_sheet_wall", WallBlock::new, AbstractBlock.Settings.copy(Rusted_Metal_Sheet));
    public static final Block Scrap_Metal_Sheet = registerBlock("scrap_metal_sheet", AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).nonOpaque());
    public static final Block Scrap_Metal_Sheet_Slab = registerCustomBlock("scrap_metal_sheet_slab", SlabBlock::new, AbstractBlock.Settings.copy(Scrap_Metal_Sheet));
    public static final Block Scrap_Metal_Sheet_Slab_Vertical = registerCustomBlock("scrap_metal_sheet_slab_vertical", VerticalSlabBlock::new, AbstractBlock.Settings.copy(Scrap_Metal_Sheet));
    public static final Block Scrap_Metal_Sheet_Stairs = registerStairs(CustomBlocks.Scrap_Metal_Sheet,"scrap_metal_sheet_stairs", AbstractBlock.Settings.copy(Scrap_Metal_Sheet));
    public static final Block Scrap_Metal_Sheet_Wall = registerCustomBlock("scrap_metal_sheet_wall", WallBlock::new, AbstractBlock.Settings.copy(Scrap_Metal_Sheet));

    // container
    public static final Block Container_Gray = registerCustomBlock("container_gray_pillar", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block Container_Darkgray = registerCustomBlock("container_darkgray_pillar", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block Container_Blue = registerCustomBlock("container_blue_pillar", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block Container_Red = registerCustomBlock("container_red_pillar", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block Container_Yellow = registerCustomBlock("container_yellow_pillar", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block Container_Green = registerCustomBlock("container_green_pillar", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block Container_Brown = registerCustomBlock("container_brown_pillar", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block Container_Purple = registerCustomBlock("container_purple_pillar", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block Container_Lightblue = registerCustomBlock("container_lightblue_pillar", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));

    // razor_wire
    public static final Block Razor_Wire = registerCustomBlock("razor_wire", RazorWireBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BARS)
            .solid().noCollision().pistonBehavior(PistonBehavior.DESTROY).nonOpaque());


    /// Nature
    // bush_carpet
    public static final Block Bush_Carpet = registerCustomBlock("bush_carpet", GlowLichenBlock::new, AbstractBlock.Settings.copy(Blocks.LEAF_LITTER)
            .mapColor(MapColor.IRON_GRAY)
            .replaceable()
            .noCollision()
            .strength(0.2F)
            .sounds(BlockSoundGroup.GRASS)
            .burnable()
            .pistonBehavior(PistonBehavior.DESTROY));

    // mold_carpet
    public static final Block Mold_Carpet = registerCustomBlock("mold_carpet", GlowLichenBlock::new, AbstractBlock.Settings.copy(Blocks.LEAF_LITTER)
            .mapColor(MapColor.IRON_GRAY)
            .replaceable()
            .noCollision()
            .strength(0.2F)
            .sounds(BlockSoundGroup.GRASS)
            .burnable()
            .pistonBehavior(PistonBehavior.DESTROY));

    ///Street
    public static final Block Street_Black_Gravel = registerBlock("street_black_gravel", AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE));
    public static final Block Street_Gray_Gravel = registerBlock("street_gray_gravel", AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE));
    // Black Street Yellow Lines
    public static final Block Street_Black_Gravel_Yellow_Diagonal_Lines = registerBlock("street_black_gravel_yellow_diagonal_lines", AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE).mapColor(MapColor.YELLOW));
    public static final Block Street_Black_Gravel_Yellow_Line = registerBlock("street_black_gravel_yellow_line", AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE).mapColor(MapColor.YELLOW));
    public static final Block Street_Black_Gravel_Yellow_Line_Dashed = registerBlock("street_black_gravel_yellow_line_dashed", AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE).mapColor(MapColor.YELLOW));
    public static final Block Street_Black_Gravel_Yellow_Line_Thick = registerBlock("street_black_gravel_yellow_line_thick", AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE).mapColor(MapColor.YELLOW));
    public static final Block Street_Black_Gravel_Yellow_Line_Thick_Dashed = registerBlock("street_black_gravel_yellow_line_thick_dashed", AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE).mapColor(MapColor.YELLOW));
    public static final Block Street_Black_Gravel_Yellow_Double_Line = registerCustomBlock("street_black_gravel_yellow_double_line", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE).mapColor(MapColor.YELLOW));
    public static final Block Street_Black_Gravel_Yellow_Double_Line_Dashed = registerCustomBlock("street_black_gravel_yellow_double_line_dashed", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE).mapColor(MapColor.YELLOW));
    public static final Block Street_Black_Gravel_Yellow_Double_Line_Dashed_Long = registerCustomBlock("street_black_gravel_yellow_double_line_dashed_long", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE).mapColor(MapColor.YELLOW));
    public static final Block Street_Black_Gravel_Yellow_Line_Cross = registerCustomBlock("street_black_gravel_yellow_line_cross", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE).mapColor(MapColor.YELLOW));
    public static final Block Street_Black_Gravel_Yellow_Line_Stop = registerCustomBlock("street_black_gravel_yellow_line_stop", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE));
    public static final Block Street_Black_Gravel_Yellow_Line_Stop_Dashed = registerCustomBlock("street_black_gravel_yellow_line_stop_dashed", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE));
    public static final Block Street_Black_Gravel_Yellow_Line_Triangles = registerCustomBlock("street_black_gravel_yellow_line_triangles", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE));
    // Black Street White Lines
    public static final Block Street_Black_Gravel_White_Diagonal_Lines = registerBlock("street_black_gravel_white_diagonal_lines", AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE).mapColor(MapColor.WHITE));
    public static final Block Street_Black_Gravel_White_Line = registerBlock("street_black_gravel_white_line", AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE).mapColor(MapColor.WHITE));
    public static final Block Street_Black_Gravel_White_Line_Dashed = registerBlock("street_black_gravel_white_line_dashed", AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE).mapColor(MapColor.WHITE));
    public static final Block Street_Black_Gravel_White_Line_Thick = registerBlock("street_black_gravel_white_line_thick", AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE).mapColor(MapColor.WHITE));
    public static final Block Street_Black_Gravel_White_Line_Thick_Dashed = registerBlock("street_black_gravel_white_line_thick_dashed", AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE).mapColor(MapColor.WHITE));
    public static final Block Street_Black_Gravel_White_Double_Line = registerCustomBlock("street_black_gravel_white_double_line", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE).mapColor(MapColor.WHITE));
    public static final Block Street_Black_Gravel_White_Double_Line_Dashed = registerCustomBlock("street_black_gravel_white_double_line_dashed", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE).mapColor(MapColor.WHITE));
    public static final Block Street_Black_Gravel_White_Double_Line_Dashed_Long = registerCustomBlock("street_black_gravel_white_double_line_dashed_long", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE).mapColor(MapColor.WHITE));
    public static final Block Street_Black_Gravel_White_Line_Cross = registerCustomBlock("street_black_gravel_white_line_cross", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE).mapColor(MapColor.WHITE));
    public static final Block Street_Black_Gravel_White_Line_Stop = registerCustomBlock("street_black_gravel_white_line_stop", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE));
    public static final Block Street_Black_Gravel_White_Line_Stop_Dashed = registerCustomBlock("street_black_gravel_white_line_stop_dashed", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE));
    public static final Block Street_Black_Gravel_White_Line_Triangles = registerCustomBlock("street_black_gravel_white_line_triangles", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.BLACK_CONCRETE));
    // Gray Street Yellow Lines
    public static final Block Street_Gray_Gravel_Yellow_Diagonal_Lines = registerBlock("street_gray_gravel_yellow_diagonal_lines", AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE).mapColor(MapColor.YELLOW));
    public static final Block Street_Gray_Gravel_Yellow_Line = registerBlock("street_gray_gravel_yellow_line", AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE).mapColor(MapColor.YELLOW));
    public static final Block Street_Gray_Gravel_Yellow_Line_Dashed = registerBlock("street_gray_gravel_yellow_line_dashed", AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE).mapColor(MapColor.YELLOW));
    public static final Block Street_Gray_Gravel_Yellow_Line_Thick = registerBlock("street_gray_gravel_yellow_line_thick", AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE).mapColor(MapColor.YELLOW));
    public static final Block Street_Gray_Gravel_Yellow_Line_Thick_Dashed = registerBlock("street_gray_gravel_yellow_line_thick_dashed", AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE).mapColor(MapColor.YELLOW));
    public static final Block Street_Gray_Gravel_Yellow_Double_Line = registerCustomBlock("street_gray_gravel_yellow_double_line", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE).mapColor(MapColor.YELLOW));
    public static final Block Street_Gray_Gravel_Yellow_Double_Line_Dashed = registerCustomBlock("street_gray_gravel_yellow_double_line_dashed", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE).mapColor(MapColor.YELLOW));
    public static final Block Street_Gray_Gravel_Yellow_Double_Line_Dashed_Long = registerCustomBlock("street_gray_gravel_yellow_double_line_dashed_long", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE).mapColor(MapColor.YELLOW));
    public static final Block Street_Gray_Gravel_Yellow_Line_Cross = registerCustomBlock("street_gray_gravel_yellow_line_cross", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE).mapColor(MapColor.YELLOW));
    public static final Block Street_Gray_Gravel_Yellow_Line_Stop = registerCustomBlock("street_gray_gravel_yellow_line_stop", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE));
    public static final Block Street_Gray_Gravel_Yellow_Line_Stop_Dashed = registerCustomBlock("street_gray_gravel_yellow_line_stop_dashed", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE));
    public static final Block Street_Gray_Gravel_Yellow_Line_Triangles = registerCustomBlock("street_gray_gravel_yellow_line_triangles", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE));
    // Gray Street White Lines
    public static final Block Street_Gray_Gravel_White_Diagonal_Lines = registerBlock("street_gray_gravel_white_diagonal_lines", AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE).mapColor(MapColor.WHITE));
    public static final Block Street_Gray_Gravel_White_Line = registerBlock("street_gray_gravel_white_line", AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE).mapColor(MapColor.WHITE));
    public static final Block Street_Gray_Gravel_White_Line_Dashed = registerBlock("street_gray_gravel_white_line_dashed", AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE).mapColor(MapColor.WHITE));
    public static final Block Street_Gray_Gravel_White_Line_Thick = registerBlock("street_gray_gravel_white_line_thick", AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE).mapColor(MapColor.WHITE));
    public static final Block Street_Gray_Gravel_White_Line_Thick_Dashed = registerBlock("street_gray_gravel_white_line_thick_dashed", AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE).mapColor(MapColor.WHITE));
    public static final Block Street_Gray_Gravel_White_Double_Line = registerCustomBlock("street_gray_gravel_white_double_line", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE).mapColor(MapColor.WHITE));
    public static final Block Street_Gray_Gravel_White_Double_Line_Dashed = registerCustomBlock("street_gray_gravel_white_double_line_dashed", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE).mapColor(MapColor.WHITE));
    public static final Block Street_Gray_Gravel_White_Double_Line_Dashed_Long = registerCustomBlock("street_gray_gravel_white_double_line_dashed_long", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE).mapColor(MapColor.WHITE));
    public static final Block Street_Gray_Gravel_White_Line_Cross = registerCustomBlock("street_gray_gravel_white_line_cross", PillarBlock::new, AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE).mapColor(MapColor.WHITE));
    public static final Block Street_Gray_Gravel_White_Line_Stop = registerCustomBlock("street_gray_gravel_white_line_stop", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE));
    public static final Block Street_Gray_Gravel_White_Line_Stop_Dashed = registerCustomBlock("street_gray_gravel_white_line_stop_dashed", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE));
    public static final Block Street_Gray_Gravel_White_Line_Triangles = registerCustomBlock("street_gray_gravel_white_line_triangles", GlazedTerracottaBlock::new, AbstractBlock.Settings.copy(Blocks.GRAY_CONCRETE));

    /// All Vertical variants of normal vanila slab blocks
    public static final BlockFamily ALL_VANILLA_SLAB_VERTICAL_VARIANTS = new BlockFamily(
            new Block[]{
                    CustomBlocks.Brick_Slab_Vertical,
                    CustomBlocks.Stone_Slab_Vertical,
                    CustomBlocks.Smooth_Stone_Slab_Vertical,
                    CustomBlocks.Sandstone_Slab_Vertical,
                    CustomBlocks.Cut_Sandstone_Slab_Vertical,
                    CustomBlocks.Cobblestone_Slab_Vertical,
                    CustomBlocks.Stone_Brick_Slab_Vertical,
                    CustomBlocks.Mud_Brick_Slab_Vertical,
                    CustomBlocks.Nether_Brick_Slab_Vertical,
                    CustomBlocks.Quartz_Slab_Vertical,
                    CustomBlocks.Red_Sandstone_Slab_Vertical,
                    CustomBlocks.Cut_Red_Sandstone_Slab_Vertical,
                    CustomBlocks.Purpur_Slab_Vertical,
                    CustomBlocks.Prismarine_Slab_Vertical,
                    CustomBlocks.Prismarine_Brick_Slab_Vertical,
                    CustomBlocks.Dark_Prismarine_Slab_Vertical,
                    CustomBlocks.Smooth_Quartz_Slab_Vertical,
                    CustomBlocks.Smooth_Red_Sandstone_Slab_Vertical,
                    CustomBlocks.Smooth_Sandstone_Slab_Vertical,
                    CustomBlocks.Mossy_Cobblestone_Slab_Vertical,
                    CustomBlocks.Mossy_Stone_Brick_Slab_Vertical,
                    CustomBlocks.End_Stone_Brick_Slab_Vertical,
                    CustomBlocks.Red_Nether_Brick_Slab_Vertical,
                    CustomBlocks.Polished_Andesite_Slab_Vertical,
                    CustomBlocks.Polished_Diorite_Slab_Vertical,
                    CustomBlocks.Polished_Granite_Slab_Vertical,
                    CustomBlocks.Andesite_Slab_Vertical,
                    CustomBlocks.Diorite_Slab_Vertical,
                    CustomBlocks.Granite_Slab_Vertical,
                    CustomBlocks.Deepslate_Brick_Slab_Vertical,
                    CustomBlocks.Deepslate_Tile_Slab_Vertical,
                    CustomBlocks.Cobbled_Deepslate_Slab_Vertical,
                    CustomBlocks.Polished_Deepslate_Slab_Vertical,
                    CustomBlocks.Blackstone_Slab_Vertical,
                    CustomBlocks.Polished_Blackstone_Slab_Vertical,
                    CustomBlocks.Polished_Blackstone_Brick_Slab_Vertical,
                    CustomBlocks.Tuff_Slab_Vertical,
                    CustomBlocks.Polished_Tuff_Slab_Vertical,
                    CustomBlocks.Tuff_Brick_Slab_Vertical,
                    CustomBlocks.Cut_Copper_Slab_Vertical,
                    CustomBlocks.Exposed_Cut_Copper_Slab_Vertical,
                    CustomBlocks.Weathered_Cut_Copper_Slab_Vertical,
                    CustomBlocks.Oxidized_Cut_Copper_Slab_Vertical,
                    CustomBlocks.Waxed_Cut_Copper_Slab_Vertical,
                    CustomBlocks.Waxed_Exposed_Cut_Copper_Slab_Vertical,
                    CustomBlocks.Waxed_Weathered_Cut_Copper_Slab_Vertical,
                    CustomBlocks.Waxed_Oxidized_Cut_Copper_Slab_Vertical,
                    CustomBlocks.Oak_Slab_Vertical,
                    CustomBlocks.Spruce_Slab_Vertical,
                    CustomBlocks.Birch_Slab_Vertical,
                    CustomBlocks.Jungle_Slab_Vertical,
                    CustomBlocks.Acacia_Slab_Vertical,
                    CustomBlocks.Dark_Oak_Slab_Vertical,
                    CustomBlocks.Mangrove_Slab_Vertical,
                    CustomBlocks.Cherry_Slab_Vertical,
                    CustomBlocks.Bamboo_Slab_Vertical,
                    CustomBlocks.Bamboo_Mosaic_Slab_Vertical,
                    CustomBlocks.Crimson_Slab_Vertical,
                    CustomBlocks.Warped_Slab_Vertical,
                    Pale_Oak_Slab_Vertical
            },
            new Block[0]
    );

    /// Terracota Variants
    public static final BlockFamily ALL_TERRACOTTA_VARIANTS = new BlockFamily(
            new Block[]{
                    CustomBlocks.Terracotta_Stairs,
                    CustomBlocks.Terracotta_Slab,
                    CustomBlocks.Terracotta_Slab_Vertical,
                    CustomBlocks.White_Terracotta_Stairs,
                    CustomBlocks.White_Terracotta_Slab,
                    CustomBlocks.White_Terracotta_Slab_Vertical,
                    CustomBlocks.Orange_Terracotta_Stairs,
                    CustomBlocks.Orange_Terracotta_Slab,
                    CustomBlocks.Orange_Terracotta_Slab_Vertical,
                    CustomBlocks.Magenta_Terracotta_Stairs,
                    CustomBlocks.Magenta_Terracotta_Slab,
                    CustomBlocks.Magenta_Terracotta_Slab_Vertical,
                    CustomBlocks.Light_Blue_Terracotta_Stairs,
                    CustomBlocks.Light_Blue_Terracotta_Slab,
                    CustomBlocks.Light_Blue_Terracotta_Slab_Vertical,
                    CustomBlocks.Yellow_Terracotta_Stairs,
                    CustomBlocks.Yellow_Terracotta_Slab,
                    CustomBlocks.Yellow_Terracotta_Slab_Vertical,
                    CustomBlocks.Lime_Terracotta_Stairs,
                    CustomBlocks.Lime_Terracotta_Slab,
                    CustomBlocks.Lime_Terracotta_Slab_Vertical,
                    CustomBlocks.Pink_Terracotta_Stairs,
                    CustomBlocks.Pink_Terracotta_Slab,
                    CustomBlocks.Pink_Terracotta_Slab_Vertical,
                    CustomBlocks.Gray_Terracotta_Stairs,
                    CustomBlocks.Gray_Terracotta_Slab,
                    CustomBlocks.Gray_Terracotta_Slab_Vertical,
                    CustomBlocks.Light_Gray_Terracotta_Stairs,
                    CustomBlocks.Light_Gray_Terracotta_Slab,
                    CustomBlocks.Light_Gray_Terracotta_Slab_Vertical,
                    CustomBlocks.Cyan_Terracotta_Stairs,
                    CustomBlocks.Cyan_Terracotta_Slab,
                    CustomBlocks.Cyan_Terracotta_Slab_Vertical,
                    CustomBlocks.Purple_Terracotta_Stairs,
                    CustomBlocks.Purple_Terracotta_Slab,
                    CustomBlocks.Purple_Terracotta_Slab_Vertical,
                    CustomBlocks.Blue_Terracotta_Stairs,
                    CustomBlocks.Blue_Terracotta_Slab,
                    CustomBlocks.Blue_Terracotta_Slab_Vertical,
                    CustomBlocks.Brown_Terracotta_Stairs,
                    CustomBlocks.Brown_Terracotta_Slab,
                    CustomBlocks.Brown_Terracotta_Slab_Vertical,
                    CustomBlocks.Green_Terracotta_Stairs,
                    CustomBlocks.Green_Terracotta_Slab,
                    CustomBlocks.Green_Terracotta_Slab_Vertical,
                    CustomBlocks.Red_Terracotta_Stairs,
                    CustomBlocks.Red_Terracotta_Slab,
                    CustomBlocks.Red_Terracotta_Slab_Vertical,
                    CustomBlocks.Black_Terracotta_Stairs,
                    CustomBlocks.Black_Terracotta_Slab,
                    CustomBlocks.Black_Terracotta_Slab_Vertical
            },
            new Block[0]
    );

    /// Broken_Bricks
    public static final BlockFamilyWeightedWithBase BRICK_BROKEN_FAMILY = new BlockFamilyWeightedWithBase(
            new Block[0],
            new Block[]{
                    CustomBlocks.Bricks_Broken,
                    CustomBlocks.Brick_Slab_Broken,
                    CustomBlocks.Brick_Slab_Vertical_Broken,
                    CustomBlocks.Brick_Stairs_Broken,
                    CustomBlocks.Brick_Wall_Broken
            },
            new int[]{5, 3, 2, 1, 2, 3, 3, 4},
            Blocks.BRICKS,
            "bricks_broken"
    );

    /// ANDESITE_BRICK
    public static final BlockFamilyWeightedWithBase ANDESITE_BRICK_FAMILY = new BlockFamilyWeightedWithBase(
            new Block[]{
                    CustomBlocks.Andesite_Bricks,
                    CustomBlocks.Andesite_Brick_Slab,
                    CustomBlocks.Andesite_Brick_Slab_Vertical,
                    CustomBlocks.Andesite_Brick_Stairs,
                    CustomBlocks.Andesite_Brick_Wall
            },
            new Block[]{
                    CustomBlocks.Andesite_Bricks_Broken,
                    CustomBlocks.Andesite_Brick_Slab_Broken,
                    CustomBlocks.Andesite_Brick_Slab_Vertical_Broken,
                    CustomBlocks.Andesite_Brick_Stairs_Broken,
                    CustomBlocks.Andesite_Brick_Wall_Broken
            },
            new int[]{5, 3, 2, 1, 2, 3, 3, 4},
            Andesite_Bricks,
            "andesite_bricks_broken"
    );

    /// GRANITE_BRICK
    public static final BlockFamilyWeightedWithBase GRANITE_BRICK_FAMILY = new BlockFamilyWeightedWithBase(
            new Block[]{
                    CustomBlocks.Granite_Bricks,
                    CustomBlocks.Granite_Brick_Slab,
                    CustomBlocks.Granite_Brick_Slab_Vertical,
                    CustomBlocks.Granite_Brick_Stairs,
                    CustomBlocks.Granite_Brick_Wall
            },
            new Block[]{
                    CustomBlocks.Granite_Bricks_Broken,
                    CustomBlocks.Granite_Brick_Slab_Broken,
                    CustomBlocks.Granite_Brick_Slab_Vertical_Broken,
                    CustomBlocks.Granite_Brick_Stairs_Broken,
                    CustomBlocks.Granite_Brick_Wall_Broken
            },
            new int[]{16, 1, 2, 2, 2, 3, 3, 3},
            Granite_Bricks,
            "granite_bricks_broken"
    );

    /// DIORITE_BRICK
    public static final BlockFamilyWeightedWithBase DIORITE_BRICK_FAMILY = new BlockFamilyWeightedWithBase(
            new Block[]{
                    CustomBlocks.Diorite_Bricks,
                    CustomBlocks.Diorite_Brick_Slab,
                    CustomBlocks.Diorite_Brick_Slab_Vertical,
                    CustomBlocks.Diorite_Brick_Stairs,
                    CustomBlocks.Diorite_Brick_Wall
            },
            new Block[]{
                    CustomBlocks.Diorite_Bricks_Broken,
                    CustomBlocks.Diorite_Brick_Slab_Broken,
                    CustomBlocks.Diorite_Brick_Slab_Vertical_Broken,
                    CustomBlocks.Diorite_Brick_Stairs_Broken,
                    CustomBlocks.Diorite_Brick_Wall_Broken
            },
            new int[]{5, 3, 2, 1, 2, 3, 3, 4},
            Diorite_Bricks,
            "diorite_bricks_broken"
    );

    /// METAL_SHEET
    public static final BlockFamily METAL_SHEET_FAMILY = new BlockFamily(
            new Block[]{
                    Metal_Sheet,
                    Metal_Sheet_Slab,
                    Metal_Sheet_Slab_Vertical,
                    Metal_Sheet_Stairs,
                    Metal_Sheet_Wall,
            },
            new Block[0]
    );
    public static final BlockFamily EXPOSED_METAL_SHEET_FAMILY = new BlockFamily(
            new Block[]{
                    Exposed_Metal_Sheet,
                    Exposed_Metal_Sheet_Slab,
                    Exposed_Metal_Sheet_Slab_Vertical,
                    Exposed_Metal_Sheet_Stairs,
                    Exposed_Metal_Sheet_Wall,
            },
            new Block[0]
    );
    public static final BlockFamily WEATHERED_METAL_FAMILY = new BlockFamily(
            new Block[]{
                    Weathered_Metal_Sheet,
                    Weathered_Metal_Sheet_Slab,
                    Weathered_Metal_Sheet_Slab_Vertical,
                    Weathered_Metal_Sheet_Stairs,
                    Weathered_Metal_Sheet_Wall
            },
            new Block[0]
    );
    public static final BlockFamilyWeightedWithBase RUSTED_METAL_SHEET_FAMILY = new BlockFamilyWeightedWithBase(
            new Block[0],
            new Block[]{
                    Rusted_Metal_Sheet,
                    Rusted_Metal_Sheet_Slab,
                    Rusted_Metal_Sheet_Slab_Vertical,
                    Rusted_Metal_Sheet_Stairs,
                    Rusted_Metal_Sheet_Wall
            },

            new int[]{35, 2, 3, 1, 2, 2, 4, 1},
            Rusted_Metal_Sheet,
            "rusted_metal_sheet"
    );
    public static final BlockFamilyWeightedWithBase SCRAP_METAL_SHEET_FAMILY = new BlockFamilyWeightedWithBase(
            new Block[0],
            new Block[]{
                    Scrap_Metal_Sheet,
                    Scrap_Metal_Sheet_Slab,
                    Scrap_Metal_Sheet_Slab_Vertical,
                    Scrap_Metal_Sheet_Stairs,
                    Scrap_Metal_Sheet_Wall
            },
            new int[]{10, 10, 3, 5, 1, 1, 4, 10},
            Scrap_Metal_Sheet,
            "scrap_metal_sheet"
    );

    /// CONTAINER
    public static final BlockFamily CONTAINER_FAMILY = new BlockFamily(
            new Block[]{
                    Container_Gray,
                    Container_Darkgray,
                    Container_Brown,
                    Container_Red,
                    Container_Yellow,
                    Container_Green,
                    Container_Lightblue,
                    Container_Blue,
                    Container_Purple
            },
            new Block[0]
    );

    /// QUARTZ_CHECKER
    public static final BlockFamilyWeighted QUARTZ_CHECKER_FAMILY = new BlockFamilyWeighted(
            new Block[]{
                    CustomBlocks.Quartz_Checker_Gray,
                    CustomBlocks.Quartz_Checker_Black,
                    CustomBlocks.Quartz_Checker_Brown,
                    CustomBlocks.Quartz_Checker_Green,
                    CustomBlocks.Quartz_Checker_Blue,
                    CustomBlocks.Quartz_Checker_Black_Yellow
            },
            new Block[]{
                    CustomBlocks.Quartz_Checker_Gray_Broken,
                    CustomBlocks.Quartz_Checker_Black_Broken,
                    CustomBlocks.Quartz_Checker_Brown_Broken,
                    CustomBlocks.Quartz_Checker_Green_Broken,
                    CustomBlocks.Quartz_Checker_Blue_Broken,
                    CustomBlocks.Quartz_Checker_Black_Yellow_Broken
            },
            new int[]{5, 4, 4, 1, 4, 4, 2, 1},
            true
    );

    /// QUARTZ_TILE
    public static final BlockFamilyWeighted QUARTZ_TILE_FAMILY = new BlockFamilyWeighted(
            new Block[]{
                    CustomBlocks.Quartz_Tile_White,
                    CustomBlocks.Quartz_Tile_Black,
                    CustomBlocks.Quartz_Tile_Orange,
                    CustomBlocks.Quartz_Tile_Green,
                    CustomBlocks.Quartz_Tile_Blue,
                    CustomBlocks.Quartz_Tile_Purple,
            },
            new Block[]{
                    CustomBlocks.Quartz_Tile_White_Broken,
                    CustomBlocks.Quartz_Tile_Black_Broken,
                    CustomBlocks.Quartz_Tile_Orange_Broken,
                    CustomBlocks.Quartz_Tile_Green_Broken,
                    CustomBlocks.Quartz_Tile_Blue_Broken,
                    CustomBlocks.Quartz_Tile_Purple_Broken,
            },
            new int[]{5, 4, 4, 4, 4, 3, 3, 1},
            true
    );

    /// STREET_LINE_BLACK_GRAVEL
    public static final BlockFamilyWithBase STREET_LINE_BLACK_GRAVEL_FAMILY = new BlockFamilyWithBase(
            new Block[]{
                    Street_Black_Gravel,
                    Street_Black_Gravel_Yellow_Line,
                    Street_Black_Gravel_Yellow_Line_Dashed,
                    Street_Black_Gravel_Yellow_Line_Thick,
                    Street_Black_Gravel_Yellow_Line_Thick_Dashed,
                    Street_Black_Gravel_Yellow_Double_Line,
                    Street_Black_Gravel_Yellow_Double_Line_Dashed,
                    Street_Black_Gravel_Yellow_Double_Line_Dashed_Long,
                    Street_Black_Gravel_Yellow_Line_Cross,
                    Street_Black_Gravel_Yellow_Diagonal_Lines,
                    Street_Black_Gravel_Yellow_Line_Stop,
                    Street_Black_Gravel_Yellow_Line_Stop_Dashed,
                    Street_Black_Gravel_Yellow_Line_Triangles,
                    Street_Black_Gravel_White_Line,
                    Street_Black_Gravel_White_Line_Dashed,
                    Street_Black_Gravel_White_Line_Thick,
                    Street_Black_Gravel_White_Line_Thick_Dashed,
                    Street_Black_Gravel_White_Double_Line,
                    Street_Black_Gravel_White_Double_Line_Dashed,
                    Street_Black_Gravel_White_Double_Line_Dashed_Long,
                    Street_Black_Gravel_White_Line_Cross,
                    Street_Black_Gravel_White_Diagonal_Lines,
                    Street_Black_Gravel_White_Line_Stop,
                    Street_Black_Gravel_White_Line_Stop_Dashed,
                    Street_Black_Gravel_White_Line_Triangles
            },
            new Block[0],
            Street_Black_Gravel
    );

    /// STREET_LINE_GRAY_GRAVEL
    public static final BlockFamilyWithBase STREET_LINE_GRAY_GRAVEL_FAMILY = new BlockFamilyWithBase(
            new Block[]{
                    Street_Gray_Gravel,
                    Street_Gray_Gravel_Yellow_Line,
                    Street_Gray_Gravel_Yellow_Line_Dashed,
                    Street_Gray_Gravel_Yellow_Line_Thick,
                    Street_Gray_Gravel_Yellow_Line_Thick_Dashed,
                    Street_Gray_Gravel_Yellow_Double_Line,
                    Street_Gray_Gravel_Yellow_Double_Line_Dashed,
                    Street_Gray_Gravel_Yellow_Double_Line_Dashed_Long,
                    Street_Gray_Gravel_Yellow_Line_Cross,
                    Street_Gray_Gravel_Yellow_Diagonal_Lines,
                    Street_Gray_Gravel_Yellow_Line_Stop,
                    Street_Gray_Gravel_Yellow_Line_Stop_Dashed,
                    Street_Gray_Gravel_Yellow_Line_Triangles,
                    Street_Gray_Gravel_White_Line,
                    Street_Gray_Gravel_White_Line_Dashed,
                    Street_Gray_Gravel_White_Line_Thick,
                    Street_Gray_Gravel_White_Line_Thick_Dashed,
                    Street_Gray_Gravel_White_Double_Line,
                    Street_Gray_Gravel_White_Double_Line_Dashed,
                    Street_Gray_Gravel_White_Double_Line_Dashed_Long,
                    Street_Gray_Gravel_White_Line_Cross,
                    Street_Gray_Gravel_White_Diagonal_Lines,
                    Street_Gray_Gravel_White_Line_Stop,
                    Street_Gray_Gravel_White_Line_Stop_Dashed,
                    Street_Gray_Gravel_White_Line_Triangles
            },
            new Block[0],
            Street_Gray_Gravel
    );

    /// TEST
    public static final BlockFamilyWithBase TEST_FAMILY = new BlockFamilyWithBase(
            new Block[]{
                    CustomBlocks.Test_Block,
                    CustomBlocks.Test_Slab,
                    CustomBlocks.Test_Slab_Vertical,
                    CustomBlocks.Test_Stairs,
                    CustomBlocks.Test_Wall,
                    CustomBlocks.Test_Pillar,
                    CustomBlocks.Test_Glass,
                    CustomBlocks.Test_Pane,
                    CustomBlocks.Test_Door,
                    CustomBlocks.Test_Trapdoor,
                    CustomBlocks.Test_Carpet,
                    CustomBlocks.Test_Leaves
            },
            new Block[0],
            CustomBlocks.Test_Block
    );

    ///  ALL BLOCKS
    public static final ArrayList<Block> ALL_MOD_BLOCKS = new ArrayList<Block>();

    private static Block registerBlock(String name, AbstractBlock.Settings blockSettings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(AfterEarth_Decorations.MOD_ID, name));
        Block block = new Block(blockSettings.registryKey(key));
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, key, block);
    }

    private static Block registerLeaves(String name, AbstractBlock.Settings blockSettings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(AfterEarth_Decorations.MOD_ID, name));
        LeavesBlock block = new LeavesBlock(0.1f, blockSettings.registryKey(key)) {
            @Override
            public MapCodec<? extends LeavesBlock> getCodec() {
                return null;
            }

            @Override
            protected void spawnLeafParticle(World world, BlockPos pos, Random random) {

            }
        };
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, key, block);
    }

    private static Block registerStairsShort(String name, Block baseBlock) {
        AbstractBlock.Settings blockSettings = AbstractBlock.Settings.copy(baseBlock);
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(AfterEarth_Decorations.MOD_ID, name));
        StairsBlock block = new StairsBlock(baseBlock.getDefaultState(), blockSettings.registryKey(key));
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, key, block);
    }

    private static Block registerStairs(Block baseBlock, String name, AbstractBlock.Settings blockSettings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(AfterEarth_Decorations.MOD_ID, name));
        StairsBlock block = new StairsBlock(baseBlock.getDefaultState(), blockSettings.registryKey(key));
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, key, block);
    }

    private static Block registerDoor(BlockSetType type, String name, AbstractBlock.Settings blockSettings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(AfterEarth_Decorations.MOD_ID, name));
        DoorBlock block = new DoorBlock(type, blockSettings.registryKey(key));
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, key, block);
    }

    private static Block registerCarpet(String name, AbstractBlock.Settings blockSettings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(AfterEarth_Decorations.MOD_ID, name));
        CarpetBlock block = new CarpetBlock(blockSettings.registryKey(key));
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, key, block);
    }

    private static Block registerTrapdoor(BlockSetType type, String name, AbstractBlock.Settings blockSettings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(AfterEarth_Decorations.MOD_ID, name));
        TrapdoorBlock block = new TrapdoorBlock(type, blockSettings.registryKey(key));
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, key, block);
    }

    private static <T extends Block> T registerCustomBlock(String name, Function<AbstractBlock.Settings, T> blockFactory, AbstractBlock.Settings blockSettings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(AfterEarth_Decorations.MOD_ID, name));
        T block = blockFactory.apply(blockSettings.registryKey(key));
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, key, block);
    }

    private static void registerBlockItem(String name, Block block) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(AfterEarth_Decorations.MOD_ID, name));
        BlockItem item = new BlockItem(block, new Item.Settings().registryKey(key));
        Registry.register(Registries.ITEM, key, item);
    }

    public static void registerModBlocks() {
        AfterEarth_Decorations.LOGGER.info("Registering Mod Blocks for" + AfterEarth_Decorations.MOD_ID);

        ALL_MOD_BLOCKS.addAll(List.of(ALL_VANILLA_SLAB_VERTICAL_VARIANTS.all()));
        ALL_MOD_BLOCKS.addAll(List.of(ALL_TERRACOTTA_VARIANTS.all()));
        ALL_MOD_BLOCKS.addAll(List.of(BRICK_BROKEN_FAMILY.all()));
        ALL_MOD_BLOCKS.addAll(List.of(ANDESITE_BRICK_FAMILY.all()));
        ALL_MOD_BLOCKS.addAll(List.of(GRANITE_BRICK_FAMILY.all()));
        ALL_MOD_BLOCKS.addAll(List.of(DIORITE_BRICK_FAMILY.all()));
        ALL_MOD_BLOCKS.addAll(List.of(METAL_SHEET_FAMILY.all()));
        ALL_MOD_BLOCKS.addAll(List.of(EXPOSED_METAL_SHEET_FAMILY.all()));
        ALL_MOD_BLOCKS.addAll(List.of(WEATHERED_METAL_FAMILY.all()));
        ALL_MOD_BLOCKS.addAll(List.of(RUSTED_METAL_SHEET_FAMILY.all()));
        ALL_MOD_BLOCKS.addAll(List.of(SCRAP_METAL_SHEET_FAMILY.all()));
        ALL_MOD_BLOCKS.addAll(List.of(CONTAINER_FAMILY.all()));
        ALL_MOD_BLOCKS.addAll(List.of(QUARTZ_CHECKER_FAMILY.all()));
        ALL_MOD_BLOCKS.addAll(List.of(QUARTZ_TILE_FAMILY.all()));
        ALL_MOD_BLOCKS.addAll(List.of(STREET_LINE_BLACK_GRAVEL_FAMILY.all()));
        ALL_MOD_BLOCKS.addAll(List.of(STREET_LINE_GRAY_GRAVEL_FAMILY.all()));
        ALL_MOD_BLOCKS.addAll(List.of(TEST_FAMILY.all()));
        ALL_MOD_BLOCKS.add(Bush_Carpet);
        ALL_MOD_BLOCKS.add(Mold_Carpet);
        ALL_MOD_BLOCKS.add(Razor_Wire);

        //Adding new blocks to the game
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            for (Block block : ALL_VANILLA_SLAB_VERTICAL_VARIANTS.all()) entries.add(block);
            for (Block block : ALL_TERRACOTTA_VARIANTS.all()) entries.add(block);
        });
    }

    private static Block[] concat(Block[] first, Block[] second) {
        Block[] result = new Block[first.length + second.length];

        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);

        return result;
    }

    public interface IBlockFamily {
        Block[] normal();
        Block[] broken();
        default boolean isMapped() {
            return false;
        }

        default Block[] all() {
            return concat(normal(), broken());
        }

        default Block getBaseBlock() {
            Block[] normal = normal();
            if (normal != null && normal.length > 0 && normal[0] != null) {
                return normal[0];
            }
            Block[] broken = broken();
            if (broken != null && broken.length > 0 && broken[0] != null) {
                return broken[0];
            }
            throw new IllegalStateException("Block family has no normal blocks.");
        }

        default String getTexturePoolPath() {
            Identifier id = Registries.BLOCK.getId(getBaseBlock());
            return id.getPath();
        }

        default Block getBaseBlockFor(Block block) {
            if (!isMapped()) return getBaseBlock();
            else {
                for (int i = 0; i < broken().length; i++) {
                    if (broken()[i] == block) {
                        return normal()[i];
                    }
                }

                for (Block normalBlock : normal()) {
                    if (normalBlock == block) {
                        return normalBlock;
                    }
                }

                return getBaseBlock();
            }
        }

        default String getTexturePoolPathFor(Block block) {
            if (!isMapped()) return getTexturePoolPath();
            else {
                Identifier id = Registries.BLOCK.getId(getBaseBlockFor(block));
                return id.getPath();
            }
        }

        default String getBrokenTexturePoolPathFor(Block block) {
            return getTexturePoolPath();
        }
    }

    public record BlockFamily(Block[] normal, Block[] broken) implements IBlockFamily { }

    public record BlockFamilyWeighted(Block[] normal, Block[] broken, int[] weights, boolean mapped) implements IBlockFamily {
        @Override
        public boolean isMapped() {
            return mapped;
        }

        @Override
        public String getBrokenTexturePoolPathFor(Block block) {
            if (!mapped) {
                return getTexturePoolPath();
            }

            return Registries.BLOCK.getId(block).getPath();
        }
    }

    public record BlockFamilyWeightedWithBase(Block[] normal, Block[] broken, int[] weights, Block baseBlock, String texturePoolPath) implements IBlockFamily {
        @Override
        public String getTexturePoolPath() {
            return texturePoolPath;
        }

        @Override
        public Block getBaseBlock() {
            if (baseBlock != null) {
                return baseBlock;
            }
            return IBlockFamily.super.getBaseBlock();
        }

        @Override
        public String getBrokenTexturePoolPathFor(Block block) {
            return texturePoolPath;
        }
    }

    public record BlockFamilyWithBase(Block[] normal, Block[] broken, Block baseBlock) implements IBlockFamily {
        @Override
        public Block getBaseBlock() {
            if (baseBlock != null) {
                return baseBlock;
            }
            return IBlockFamily.super.getBaseBlock();
        }
    }
}
