package online.reken.afterearth.deco.block.custom;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class VerticalSlabBlock extends HorizontalFacingBlock implements Waterloggable {
    public static final MapCodec<VerticalSlabBlock> CODEC = createCodec(VerticalSlabBlock::new);

    public static final EnumProperty<VerticalSlabType> TYPE =
            EnumProperty.of("type", VerticalSlabType.class);

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 8.0);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 16.0, 16.0);
    private static final VoxelShape WEST_SHAPE  = Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 16.0, 16.0);
    private static final VoxelShape EAST_SHAPE  = Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 16.0, 16.0);

    public VerticalSlabBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(TYPE, VerticalSlabType.SINGLE)
                .with(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE, WATERLOGGED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        BlockState current = ctx.getWorld().getBlockState(pos);
        FluidState fluidState = ctx.getWorld().getFluidState(pos);

        // jeśli klikamy już istniejący vertical slab tego samego typu bloku -> zamień na DOUBLE
        if (current.isOf(this)) {
            if (current.get(TYPE) == VerticalSlabType.SINGLE) {
                return current.with(TYPE, VerticalSlabType.DOUBLE).with(WATERLOGGED, false);
            }
            return null;
        }

        Direction facing = getPlacementFacing(ctx);

        return this.getDefaultState()
                .with(FACING, facing)
                .with(TYPE, VerticalSlabType.SINGLE)
                .with(WATERLOGGED, fluidState.isOf(Fluids.WATER));
    }

    private Direction getPlacementFacing(ItemPlacementContext ctx) {
        Direction side = ctx.getSide();

        if (side.getAxis().isHorizontal()) {
            return side.getOpposite();
        }

        // jeśli klik od góry/dołu, wybierz połowę na podstawie miejsca trafienia
        double hitX = ctx.getHitPos().x - ctx.getBlockPos().getX();
        double hitZ = ctx.getHitPos().z - ctx.getBlockPos().getZ();

        double dx = hitX - 0.5;
        double dz = hitZ - 0.5;

        if (Math.abs(dx) > Math.abs(dz)) {
            return dx > 0 ? Direction.EAST : Direction.WEST;
        } else {
            return dz > 0 ? Direction.SOUTH : Direction.NORTH;
        }
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (state.get(TYPE) == VerticalSlabType.DOUBLE) {
            return false;
        }

        if (!context.getStack().isOf(this.asItem())) {
            return false;
        }

        Direction side = context.getSide();

        // klikając w pustą połówkę albo z odpowiedniej strony można dołożyć drugą część
        Direction facing = state.get(FACING);

        if (side.getAxis().isHorizontal()) {
            return side == facing.getOpposite();
        }

        double hitX = context.getHitPos().x - context.getBlockPos().getX();
        double hitZ = context.getHitPos().z - context.getBlockPos().getZ();

        return switch (facing) {
            case NORTH -> hitZ >= 0.5;
            case SOUTH -> hitZ < 0.5;
            case WEST  -> hitX >= 0.5;
            case EAST  -> hitX < 0.5;
            default -> false;
        };
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(TYPE) == VerticalSlabType.DOUBLE) {
            return VoxelShapes.fullCube();
        }

        return switch (state.get(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> VoxelShapes.fullCube();
        };
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getOutlineShape(state, world, pos, context);
    }

    @Override
    protected boolean hasSidedTransparency(BlockState state) {
        return state.get(TYPE) != VerticalSlabType.DOUBLE;
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state,
            WorldView world,
            ScheduledTickView tickView,
            BlockPos pos,
            Direction direction,
            BlockPos neighborPos,
            BlockState neighborState,
            Random random
    ) {
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }
}