package online.reken.afterearth.deco.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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
    public static final Block Brick_Stairs_Broken = registerStairs(CustomBlocks.Bricks_Broken,"brick_stairs_broken", AbstractBlock.Settings.copy(Bricks_Broken));
    public static final Block Brick_Wall_Broken = registerCustomBlock("brick_wall_broken", WallBlock::new, AbstractBlock.Settings.copy(Bricks_Broken));

    // andesite bricks
    public static final Block Andesite_Bricks = registerBlock("andesite_bricks", AbstractBlock.Settings.copy(Blocks.POLISHED_ANDESITE));
    public static final Block Andesite_Brick_Slab = registerCustomBlock("andesite_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Andesite_Bricks));
    public static final Block Andesite_Brick_Stairs = registerStairs(CustomBlocks.Andesite_Bricks,"andesite_brick_stairs", AbstractBlock.Settings.copy(Andesite_Bricks));
    public static final Block Andesite_Brick_Wall = registerCustomBlock("andesite_brick_wall", WallBlock::new, AbstractBlock.Settings.copy(Andesite_Bricks));
    public static final Block Andesite_Bricks_Broken = registerBlock("andesite_bricks_broken", AbstractBlock.Settings.copy(Andesite_Bricks));
    public static final Block Andesite_Brick_Slab_Broken = registerCustomBlock("andesite_brick_slab_broken", SlabBlock::new, AbstractBlock.Settings.copy(Andesite_Bricks));
    public static final Block Andesite_Brick_Stairs_Broken = registerStairs(CustomBlocks.Andesite_Bricks,"andesite_brick_stairs_broken", AbstractBlock.Settings.copy(Andesite_Bricks));
    public static final Block Andesite_Brick_Wall_Broken = registerCustomBlock("andesite_brick_wall_broken", WallBlock::new, AbstractBlock.Settings.copy(Andesite_Bricks));

    // granite bricks
    public static final Block Granite_Bricks = registerBlock("granite_bricks", AbstractBlock.Settings.copy(Blocks.POLISHED_GRANITE));
    public static final Block Granite_Brick_Slab = registerCustomBlock("granite_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Granite_Bricks));
    public static final Block Granite_Brick_Stairs = registerStairs(CustomBlocks.Granite_Bricks,"granite_brick_stairs", AbstractBlock.Settings.copy(Granite_Bricks));
    public static final Block Granite_Brick_Wall = registerCustomBlock("granite_brick_wall", WallBlock::new, AbstractBlock.Settings.copy(Granite_Bricks));
    public static final Block Granite_Bricks_Broken = registerBlock("granite_bricks_broken", AbstractBlock.Settings.copy(Granite_Bricks));
    public static final Block Granite_Brick_Slab_Broken = registerCustomBlock("granite_brick_slab_broken", SlabBlock::new, AbstractBlock.Settings.copy(Granite_Bricks));
    public static final Block Granite_Brick_Stairs_Broken = registerStairs(CustomBlocks.Granite_Bricks,"granite_brick_stairs_broken", AbstractBlock.Settings.copy(Granite_Bricks));
    public static final Block Granite_Brick_Wall_Broken = registerCustomBlock("granite_brick_wall_broken", WallBlock::new, AbstractBlock.Settings.copy(Granite_Bricks));

    // diorite bricks
    public static final Block Diorite_Bricks = registerBlock("diorite_bricks", AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE));
    public static final Block Diorite_Brick_Slab = registerCustomBlock("diorite_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Diorite_Bricks));
    public static final Block Diorite_Brick_Stairs = registerStairs(CustomBlocks.Diorite_Bricks,"diorite_brick_stairs", AbstractBlock.Settings.copy(Diorite_Bricks));
    public static final Block Diorite_Brick_Wall = registerCustomBlock("diorite_brick_wall", WallBlock::new, AbstractBlock.Settings.copy(Diorite_Bricks));
    public static final Block Diorite_Bricks_Broken = registerBlock("diorite_bricks_broken", AbstractBlock.Settings.copy(Diorite_Bricks));
    public static final Block Diorite_Brick_Slab_Broken = registerCustomBlock("diorite_brick_slab_broken", SlabBlock::new, AbstractBlock.Settings.copy(Diorite_Bricks));
    public static final Block Diorite_Brick_Stairs_Broken = registerStairs(CustomBlocks.Diorite_Bricks,"diorite_brick_stairs_broken", AbstractBlock.Settings.copy(Diorite_Bricks));
    public static final Block Diorite_Brick_Wall_Broken = registerCustomBlock("diorite_brick_wall_broken", WallBlock::new, AbstractBlock.Settings.copy(Diorite_Bricks));

    // metal_sheet
    public static final Block Metal_Sheet = registerBlock("metal_sheet", AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block Metal_Sheet_Slab = registerCustomBlock("metal_sheet_slab", SlabBlock::new, AbstractBlock.Settings.copy(Metal_Sheet));
    public static final Block Metal_Sheet_Stairs = registerStairs(CustomBlocks.Metal_Sheet,"metal_sheet_stairs", AbstractBlock.Settings.copy(Metal_Sheet));
    public static final Block Metal_Sheet_Wall = registerCustomBlock("metal_sheet_wall", WallBlock::new, AbstractBlock.Settings.copy(Metal_Sheet));
    public static final Block Exposed_Metal_Sheet = registerBlock("exposed_metal_sheet", AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block Exposed_Metal_Sheet_Slab = registerCustomBlock("exposed_metal_sheet_slab", SlabBlock::new, AbstractBlock.Settings.copy(Exposed_Metal_Sheet));
    public static final Block Exposed_Metal_Sheet_Stairs = registerStairs(CustomBlocks.Exposed_Metal_Sheet,"exposed_metal_sheet_stairs", AbstractBlock.Settings.copy(Exposed_Metal_Sheet));
    public static final Block Exposed_Metal_Sheet_Wall = registerCustomBlock("exposed_metal_sheet_wall", WallBlock::new, AbstractBlock.Settings.copy(Exposed_Metal_Sheet));
    public static final Block Weathered_Metal_Sheet = registerBlock("weathered_metal_sheet", AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block Weathered_Metal_Sheet_Slab = registerCustomBlock("weathered_metal_sheet_slab", SlabBlock::new, AbstractBlock.Settings.copy(Weathered_Metal_Sheet));
    public static final Block Weathered_Metal_Sheet_Stairs = registerStairs(CustomBlocks.Weathered_Metal_Sheet,"weathered_metal_sheet_stairs", AbstractBlock.Settings.copy(Weathered_Metal_Sheet));
    public static final Block Weathered_Metal_Sheet_Wall = registerCustomBlock("weathered_metal_sheet_wall", WallBlock::new, AbstractBlock.Settings.copy(Weathered_Metal_Sheet));
    public static final Block Rusted_Metal_Sheet = registerBlock("rusted_metal_sheet", AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
    public static final Block Rusted_Metal_Sheet_Slab = registerCustomBlock("rusted_metal_sheet_slab", SlabBlock::new, AbstractBlock.Settings.copy(Rusted_Metal_Sheet));
    public static final Block Rusted_Metal_Sheet_Stairs = registerStairs(CustomBlocks.Rusted_Metal_Sheet,"rusted_metal_sheet_stairs", AbstractBlock.Settings.copy(Rusted_Metal_Sheet));
    public static final Block Rusted_Metal_Sheet_Wall = registerCustomBlock("rusted_metal_sheet_wall", WallBlock::new, AbstractBlock.Settings.copy(Rusted_Metal_Sheet));
    public static final Block Scrap_Metal_Sheet = registerBlock("scrap_metal_sheet", AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).nonOpaque());
    public static final Block Scrap_Metal_Sheet_Slab = registerCustomBlock("scrap_metal_sheet_slab", SlabBlock::new, AbstractBlock.Settings.copy(Scrap_Metal_Sheet));
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

    /// Broken_Bricks
    public static final BlockFamilyWeightedWithBase BRICK_BROKEN_FAMILY = new BlockFamilyWeightedWithBase(
            new Block[0],
            new Block[]{
                    CustomBlocks.Bricks_Broken,
                    CustomBlocks.Brick_Slab_Broken,
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
                    CustomBlocks.Andesite_Brick_Stairs,
                    CustomBlocks.Andesite_Brick_Wall
            },
            new Block[]{
                    CustomBlocks.Andesite_Bricks_Broken,
                    CustomBlocks.Andesite_Brick_Slab_Broken,
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
                    CustomBlocks.Granite_Brick_Stairs,
                    CustomBlocks.Granite_Brick_Wall
            },
            new Block[]{
                    CustomBlocks.Granite_Bricks_Broken,
                    CustomBlocks.Granite_Brick_Slab_Broken,
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
                    CustomBlocks.Diorite_Brick_Stairs,
                    CustomBlocks.Diorite_Brick_Wall
            },
            new Block[]{
                    CustomBlocks.Diorite_Bricks_Broken,
                    CustomBlocks.Diorite_Brick_Slab_Broken,
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
                    Metal_Sheet_Stairs,
                    Metal_Sheet_Wall,
            },
            new Block[0]
    );
    public static final BlockFamily EXPOSED_METAL_SHEET_FAMILY = new BlockFamily(
            new Block[]{
                    Exposed_Metal_Sheet,
                    Exposed_Metal_Sheet_Slab,
                    Exposed_Metal_Sheet_Stairs,
                    Exposed_Metal_Sheet_Wall,
            },
            new Block[0]
    );
    public static final BlockFamily WEATHERED_METAL_FAMILY = new BlockFamily(
            new Block[]{
                    Weathered_Metal_Sheet,
                    Weathered_Metal_Sheet_Slab,
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
//        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
//            for (Block block : BRICK_BROKEN_FAMILY.broken()) entries.add(block);
//            for (Block block : ANDESITE_BRICK_FAMILY.all()) entries.add(block);
//            for (Block block : GRANITE_BRICK_FAMILY.all()) entries.add(block);
//            for (Block block : DIORITE_BRICK_FAMILY.all()) entries.add(block);
//            for (Block block : METAL_SHEET_FAMILY.all()) entries.add(block);
//            for (Block block : QUARTZ_CHECKER_FAMILY.all()) entries.add(block);
//            for (Block block : QUARTZ_TILE_FAMILY.all()) entries.add(block);
//            for (Block block : STREET_LINE_BLACK_GRAVEL_FAMILY.all()) entries.add(block);
//            for (Block block : STREET_LINE_GRAY_GRAVEL_FAMILY.all()) entries.add(block);
//            for (Block block : TEST_FAMILY.all()) entries.add(block);
//        });
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
