package online.reken.afterearth.deco.block;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
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

import java.util.function.Function;

public class CustomBlocks {
    //List of new blocks
    ///Interior
    // quartz_checker
    public static final Block Quartz_Checker_Black = registerBlock("quartz_checker_black",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Checker_Black_Yellow = registerBlock("quartz_checker_black_yellow",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Checker_Blue = registerBlock("quartz_checker_blue",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Checker_Brown = registerBlock("quartz_checker_brown",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Checker_Gray = registerBlock("quartz_checker_gray",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Checker_Green = registerBlock("quartz_checker_green",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Checker_Black_Broken = registerBlock("quartz_checker_black_broken",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Checker_Black_Yellow_Broken = registerBlock("quartz_checker_black_yellow_broken",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Checker_Blue_Broken = registerBlock("quartz_checker_blue_broken",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Checker_Brown_Broken = registerBlock("quartz_checker_brown_broken",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Checker_Gray_Broken = registerBlock("quartz_checker_gray_broken",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Checker_Green_Broken = registerBlock("quartz_checker_green_broken",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    // quartz_tile
    public static final Block Quartz_Tile_Blue = registerBlock("quartz_tile_blue",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Tile_Black = registerBlock("quartz_tile_black",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Tile_Green = registerBlock("quartz_tile_green",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Tile_Orange = registerBlock("quartz_tile_orange",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Tile_Purple = registerBlock("quartz_tile_purple",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Tile_White = registerBlock("quartz_tile_white",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Tile_Blue_Broken = registerBlock("quartz_tile_blue_broken",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Tile_Black_Broken = registerBlock("quartz_tile_black_broken",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Tile_Green_Broken = registerBlock("quartz_tile_green_broken",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Tile_Orange_Broken = registerBlock("quartz_tile_orange_broken",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Tile_Purple_Broken = registerBlock("quartz_tile_purple_broken",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));
    public static final Block Quartz_Tile_White_Broken = registerBlock("quartz_tile_white_broken",
            AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(0.8F));

    ///Exterior
    // bricks
    public static final Block Bricks_Broken = registerBlock("bricks_broken",
            AbstractBlock.Settings.create().mapColor(MapColor.RED).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F));
    public static final Block Brick_Slab_Broken = registerCustomBlock("brick_slab_broken", SlabBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.RED).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F));
    public static final Block Brick_Stairs_Broken = registerStairs(CustomBlocks.Bricks_Broken,"brick_stairs_broken",
            AbstractBlock.Settings.create().mapColor(MapColor.RED).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F));
    public static final Block Brick_Wall_Broken = registerCustomBlock("brick_wall_broken", WallBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.RED).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F));

    public static final Block Andesite_Bricks = registerBlock("andesite_bricks",
            AbstractBlock.Settings.create().mapColor(MapColor.GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F));
    public static final Block Andesite_Brick_Slab = registerCustomBlock("andesite_brick_slab", SlabBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F));
    public static final Block Andesite_Brick_Stairs = registerStairs(CustomBlocks.Andesite_Bricks,"andesite_brick_stairs",
            AbstractBlock.Settings.create().mapColor(MapColor.GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F));
    public static final Block Andesite_Brick_Wall = registerCustomBlock("andesite_brick_wall", WallBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F));
    public static final Block Andesite_Bricks_Broken = registerBlock("andesite_bricks_broken",
            AbstractBlock.Settings.create().mapColor(MapColor.GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F));
    public static final Block Andesite_Brick_Slab_Broken = registerCustomBlock("andesite_brick_slab_broken", SlabBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F));
    public static final Block Andesite_Brick_Stairs_Broken = registerStairs(CustomBlocks.Andesite_Bricks,"andesite_brick_stairs_broken",
            AbstractBlock.Settings.create().mapColor(MapColor.GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F));
    public static final Block Andesite_Brick_Wall_Broken = registerCustomBlock("andesite_brick_wall_broken", WallBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F));

    //Test Blocks
    public static final Block Test_Block = registerBlock("test_block",
            AbstractBlock.Settings.create().mapColor(MapColor.BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F)
                    .nonOpaque().solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never));
    public static final Block Test_Slab = registerCustomBlock("test_slab", SlabBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F)
                    .nonOpaque().solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never));
    public static final Block Test_Stairs = registerStairs(CustomBlocks.Test_Block,"test_stairs",
            AbstractBlock.Settings.create().mapColor(MapColor.BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F)
                    .nonOpaque().solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never));
    public static final Block Test_Wall = registerCustomBlock("test_wall", WallBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F)
                    .nonOpaque().solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never));
    public static final Block Test_Pillar = registerCustomBlock("test_pillar", PillarBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F)
                    .nonOpaque().solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never));
    public static final Block Test_Glass = registerCustomBlock("test_glass", TransparentBlock::new,
            AbstractBlock.Settings.copy(Blocks.GLASS).mapColor(MapColor.BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F).nonOpaque());
    public static final Block Test_Pane = registerCustomBlock("test_pane", PaneBlock::new,
            AbstractBlock.Settings.copy(Blocks.GLASS_PANE).mapColor(MapColor.BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F).nonOpaque());

    public static final Block Test_Door = registerDoor(BlockSetType.OAK, "test_door",
            AbstractBlock.Settings.create().mapColor(MapColor.BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F).nonOpaque());
    public static final Block Test_Trapdoor = registerTrapdoor(BlockSetType.OAK, "test_trapdoor",
            AbstractBlock.Settings.create().mapColor(MapColor.BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F).nonOpaque());
    public static final Block Test_Carpet = registerCarpet("test_carpet",
            AbstractBlock.Settings.create().mapColor(MapColor.BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F).nonOpaque());
    public static final Block Test_Leaves = registerLeaves("test_leaves",
            AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).mapColor(MapColor.BLUE).nonOpaque());

    /// ANDESITE_BRICK
    public static final BlockFamilyWeighted TEST_FAMILY = new BlockFamilyWeighted(
            new Block[]{
                    CustomBlocks.Test_Block,
                    CustomBlocks.Test_Slab,
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
            false,
            new int[]{1, 1, 1, 1, 1, 1, 1, 1}
    );

    /// Broken_Bricks
    public static final BlockFamilyWeighted BRICK_BROKEN_FAMILY = new BlockFamilyWeighted(
            new Block[0],
            new Block[]{
                    CustomBlocks.Bricks_Broken,
                    CustomBlocks.Brick_Slab_Broken,
                    CustomBlocks.Brick_Stairs_Broken,
                    CustomBlocks.Brick_Wall_Broken
            },
            true,
            new int[]{5, 3, 2, 1, 2, 3, 3, 4}
    );

    /// ANDESITE_BRICK
    public static final BlockFamilyWeighted ANDESITE_BRICK_FAMILY = new BlockFamilyWeighted(
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
            false,
            new int[]{5, 3, 2, 1, 2, 3, 3, 4}
    );

    /// QUARTZ_CHECKER
    public static final BlockFamilyWeighted QUARTZ_CHECKER_FAMILY = new BlockFamilyWeighted(
            new Block[]{
                    CustomBlocks.Quartz_Checker_Black,
                    CustomBlocks.Quartz_Checker_Black_Yellow,
                    CustomBlocks.Quartz_Checker_Blue,
                    CustomBlocks.Quartz_Checker_Brown,
                    CustomBlocks.Quartz_Checker_Gray,
                    CustomBlocks.Quartz_Checker_Green
            },
            new Block[]{
                    CustomBlocks.Quartz_Checker_Black_Broken,
                    CustomBlocks.Quartz_Checker_Black_Yellow_Broken,
                    CustomBlocks.Quartz_Checker_Blue_Broken,
                    CustomBlocks.Quartz_Checker_Brown_Broken,
                    CustomBlocks.Quartz_Checker_Gray_Broken,
                    CustomBlocks.Quartz_Checker_Green_Broken
            },
            false,
            new int[]{5, 4, 4, 1, 4, 4, 2, 1}
    );

    /// QUARTZ_TILE
    public static final BlockFamilyWeighted QUARTZ_TILE_FAMILY = new BlockFamilyWeighted(
            new Block[]{
                    CustomBlocks.Quartz_Tile_Black,
                    CustomBlocks.Quartz_Tile_Blue,
                    CustomBlocks.Quartz_Tile_Purple,
                    CustomBlocks.Quartz_Tile_Orange,
                    CustomBlocks.Quartz_Tile_Green,
                    CustomBlocks.Quartz_Tile_White
            },
            new Block[]{
                    CustomBlocks.Quartz_Tile_Black_Broken,
                    CustomBlocks.Quartz_Tile_Blue_Broken,
                    CustomBlocks.Quartz_Tile_Purple_Broken,
                    CustomBlocks.Quartz_Tile_Orange_Broken,
                    CustomBlocks.Quartz_Tile_Green_Broken,
                    CustomBlocks.Quartz_Tile_White_Broken
            },
            false,
            new int[]{5, 4, 4, 4, 4, 3, 3, 1}
    );

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

        //Adding new blocks to the game
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            for (Block block : BRICK_BROKEN_FAMILY.broken()) entries.add(block);
            for (Block block : ANDESITE_BRICK_FAMILY.all()) entries.add(block);
            for (Block block : QUARTZ_CHECKER_FAMILY.all()) entries.add(block);
            for (Block block : QUARTZ_TILE_FAMILY.all()) entries.add(block);
            for (Block block : TEST_FAMILY.all()) entries.add(block);
        });
    }

    private static Block[] concat(Block[] first, Block[] second) {
        Block[] result = new Block[first.length + second.length];

        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);

        return result;
    }

    public record BlockFamily(Block[] normal, Block[] broken, Boolean isVanilaFamily) {
        public Block[] all() {
            return concat(normal, broken);
        }
    }

    public record BlockFamilyWeighted(Block[] normal, Block[] broken, Boolean isVanilaFamily, int[] weights) {
        public Block[] all() {
            return concat(normal, broken);
        }
    }
}
