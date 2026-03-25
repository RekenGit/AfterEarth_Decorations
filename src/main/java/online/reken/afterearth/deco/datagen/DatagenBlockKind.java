package online.reken.afterearth.deco.datagen;

import net.minecraft.block.Block;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.block.WallBlock;
import online.reken.afterearth.deco.block.custom.VerticalSlabBlock;
import online.reken.afterearth.deco.block.custom.VerticalSlabType;

public enum DatagenBlockKind {
    CUBE,
    SLAB,
    VERTICAL_SLAB,
    STAIRS,
    WALL,
    PILLAR,
    GLAZED_TERRACOTTA,
    TRANSPARENT,
    PANE,
    DOOR,
    TRAPDOOR,
    CARPET,
    LEAVES;

    public static DatagenBlockKind resolve(Block block) {
        if (block instanceof SlabBlock) return SLAB;
        if (block instanceof VerticalSlabBlock) return VERTICAL_SLAB;
        if (block instanceof StairsBlock) return STAIRS;
        if (block instanceof WallBlock) return WALL;
        if (block instanceof PillarBlock) return PILLAR;
        if (block instanceof GlazedTerracottaBlock) return GLAZED_TERRACOTTA;
        if (block instanceof PaneBlock) return PANE;
        if (block instanceof TransparentBlock) return TRANSPARENT;
        if (block instanceof DoorBlock) return DOOR;
        if (block instanceof TrapdoorBlock) return TRAPDOOR;
        if (block instanceof CarpetBlock) return CARPET;
        if (block instanceof LeavesBlock) return LEAVES;
        return CUBE;
    }

    public boolean supportsBrokenWeightedGeneration() {
        return switch (this) {
            case CUBE, SLAB, STAIRS, WALL -> true;
            default -> false;
        };
    }

    public boolean usesCubeTexturePool() {
        return switch (this) {
            case CUBE, SLAB, VERTICAL_SLAB, STAIRS, WALL -> true;
            default -> false;
        };
    }
}
