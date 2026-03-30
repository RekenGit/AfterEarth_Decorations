package online.reken.afterearth.deco.block.custom;

import com.mojang.serialization.MapCodec;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import online.reken.afterearth.deco.AfterEarth_Decorations;

public class SideBarrierBlock extends MultifaceBlock {
    public static final MapCodec<SideBarrierBlock> CODEC = createCodec(SideBarrierBlock::new);
    private final MultifaceGrower grower = new MultifaceGrower(this);

    @Override
    public MapCodec<SideBarrierBlock> getCodec() {
        return CODEC;
    }

    public SideBarrierBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    protected boolean isTransparent(BlockState state) {
        return state.getFluidState().isEmpty();
    }

    @Override
    protected boolean canReplace(BlockState state, ItemPlacementContext context) {
        return context.getStack().isOf(this.asItem()) && isNotFullBlock(state);
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
        int parts = countParts(state);
        return List.of(new ItemStack(this.asItem(), parts));
    }

    private int countParts(BlockState state) {
        int count = 0;

        for (Direction dir : Direction.values()) {
            BooleanProperty property = getProperty(dir);
            if (property != null && state.contains(property) && state.get(property)) {
                count++;
            }
        }

        return Math.max(1, count);
    }

    private static boolean isNotFullBlock(BlockState state) {
        for (Direction direction : DIRECTIONS) {
            if (!hasDirection(state, direction)) {
                return true;
            }
        }

        return false;
    }
}
