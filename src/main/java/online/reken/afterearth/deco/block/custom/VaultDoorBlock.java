package online.reken.afterearth.deco.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.tick.ScheduledTickView;
import online.reken.afterearth.deco.block.custom.enums.VaultDoorHinge;
import org.jetbrains.annotations.Nullable;

/**
 * Starter implementation of a 2x2 vault door.
 *
 * Layout for a NORTH facing closed door:
 * [UPPER_LEFT ][UPPER_RIGHT]
 * [LOWER_LEFT ][LOWER_RIGHT]
 *
 * The placed/root position is always LOWER_LEFT.
 * Clicking any part toggles all 4 parts.
 * Breaking one part removes the whole structure.
 *
 * This version uses blockstates only (no smooth animation yet).
 */

public class VaultDoorBlock extends HorizontalFacingBlock {
    public static final MapCodec<VaultDoorBlock> CODEC = createCodec(VaultDoorBlock::new);

    public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty OPEN = Properties.OPEN;
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final EnumProperty<VaultDoorPartX> PART_X = EnumProperty.of("part_x", VaultDoorPartX.class);
    public static final EnumProperty<VaultDoorPartY> PART_Y = EnumProperty.of("part_y", VaultDoorPartY.class);
    public static final EnumProperty<VaultDoorHinge> HINGE = EnumProperty.of("hinge", VaultDoorHinge.class);
    private static final VoxelShape CLOSED_NORTH = Block.createCuboidShape(0, 0, 13, 16, 16, 16);
    private static final VoxelShape CLOSED_SOUTH = Block.createCuboidShape(0, 0, 0, 16, 16, 3);
    private static final VoxelShape CLOSED_WEST  = Block.createCuboidShape(13, 0, 0, 16, 16, 16);
    private static final VoxelShape CLOSED_EAST  = Block.createCuboidShape(0, 0, 0, 3, 16, 16);
    private static final VoxelShape OPEN_NORTH_LEFT  = Block.createCuboidShape(0, 0, 0, 3, 16, 16);
    private static final VoxelShape OPEN_NORTH_RIGHT = Block.createCuboidShape(13, 0, 0, 16, 16, 16);
    private static final VoxelShape OPEN_SOUTH_LEFT  = Block.createCuboidShape(13, 0, 0, 16, 16, 16);
    private static final VoxelShape OPEN_SOUTH_RIGHT = Block.createCuboidShape(0, 0, 0, 3, 16, 16);
    private static final VoxelShape OPEN_WEST_LEFT   = Block.createCuboidShape(0, 0, 13, 16, 16, 16);
    private static final VoxelShape OPEN_WEST_RIGHT  = Block.createCuboidShape(0, 0, 0, 16, 16, 3);
    private static final VoxelShape OPEN_EAST_LEFT   = Block.createCuboidShape(0, 0, 0, 16, 16, 3);
    private static final VoxelShape OPEN_EAST_RIGHT  = Block.createCuboidShape(0, 0, 13, 16, 16, 16);

    public VaultDoorBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(OPEN, false)
                .with(POWERED, false)
                .with(PART_X, VaultDoorPartX.LEFT)
                .with(PART_Y, VaultDoorPartY.LOWER)
                .with(HINGE, VaultDoorHinge.LEFT));
    }

    @Override
    public MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, POWERED, PART_X, PART_Y, HINGE);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction facing = ctx.getHorizontalPlayerFacing().getOpposite();
        BlockPos rootPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        VaultDoorHinge hinge = getPlacementHinge(ctx);

        if (!canPlaceStructureAt(world, rootPos, facing)) {
            return null;
        }

        return this.getDefaultState()
                .with(FACING, facing)
                .with(OPEN, false)
                .with(POWERED, world.isReceivingRedstonePower(rootPos))
                .with(PART_X, VaultDoorPartX.LEFT)
                .with(PART_Y, VaultDoorPartY.LOWER)
                .with(HINGE, hinge);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getDoorShape(state);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getDoorShape(state);
    }

    @Override
    protected VoxelShape getCullingShape(BlockState state) {
        return getDoorShape(state);
    }

    private VoxelShape getDoorShape(BlockState state) {
        Direction facing = state.get(FACING);
        boolean open = state.get(OPEN);
        VaultDoorHinge hinge = state.get(HINGE);

        if (!open) {
            return switch (facing) {
                case NORTH -> CLOSED_NORTH;
                case SOUTH -> CLOSED_SOUTH;
                case WEST  -> CLOSED_WEST;
                case EAST  -> CLOSED_EAST;
                default -> VoxelShapes.fullCube();
            };
        }

        return switch (facing) {
            case NORTH -> hinge == VaultDoorHinge.LEFT ? OPEN_NORTH_LEFT : OPEN_NORTH_RIGHT;
            case SOUTH -> hinge == VaultDoorHinge.LEFT ? OPEN_SOUTH_LEFT : OPEN_SOUTH_RIGHT;
            case WEST  -> hinge == VaultDoorHinge.LEFT ? OPEN_WEST_LEFT : OPEN_WEST_RIGHT;
            case EAST  -> hinge == VaultDoorHinge.LEFT ? OPEN_EAST_LEFT : OPEN_EAST_RIGHT;
            default -> VoxelShapes.fullCube();
        };
    }

    private VaultDoorHinge getPlacementHinge(ItemPlacementContext ctx) {
        Direction facing = ctx.getHorizontalPlayerFacing().getOpposite();
        Vec3d hit = ctx.getHitPos();
        BlockPos pos = ctx.getBlockPos();

        double localX = hit.x - pos.getX();
        double localZ = hit.z - pos.getZ();

        return switch (facing) {
            case NORTH -> localX < 0.5 ? VaultDoorHinge.LEFT : VaultDoorHinge.RIGHT;
            case SOUTH -> localX < 0.5 ? VaultDoorHinge.RIGHT : VaultDoorHinge.LEFT;
            case WEST -> localZ < 0.5 ? VaultDoorHinge.RIGHT : VaultDoorHinge.LEFT;
            case EAST -> localZ < 0.5 ? VaultDoorHinge.LEFT : VaultDoorHinge.RIGHT;
            default -> VaultDoorHinge.LEFT;
        };
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.isClient()) return;

        Direction facing = state.get(FACING);
        VaultDoorHinge hinge = state.get(HINGE);
        boolean powered = world.isReceivingRedstonePower(pos);

        setPart(world, pos, state
                .with(PART_X, VaultDoorPartX.LEFT)
                .with(PART_Y, VaultDoorPartY.LOWER)
                .with(POWERED, powered)
                .with(HINGE, hinge));

        setPart(world, getUpperLeftPos(pos), state
                .with(PART_X, VaultDoorPartX.LEFT)
                .with(PART_Y, VaultDoorPartY.UPPER)
                .with(POWERED, powered)
                .with(HINGE, hinge));

        setPart(world, getRightPos(pos, facing), state
                .with(PART_X, VaultDoorPartX.RIGHT)
                .with(PART_Y, VaultDoorPartY.LOWER)
                .with(POWERED, powered)
                .with(HINGE, hinge));

        setPart(world, getUpperRightPos(pos, facing), state
                .with(PART_X, VaultDoorPartX.RIGHT)
                .with(PART_Y, VaultDoorPartY.UPPER)
                .with(POWERED, powered)
                .with(HINGE, hinge));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }

        BlockPos rootPos = getRootPos(pos, state);
        BlockState rootState = world.getBlockState(rootPos);
        if (!isSameDoor(rootState)) {
            return ActionResult.PASS;
        }

        boolean open = !rootState.get(OPEN);
        setOpen(world, rootPos, rootState, open, true);

        return ActionResult.CONSUME;
    }

    @Override
    protected void neighborUpdate(
            BlockState state,
            World world,
            BlockPos pos,
            Block sourceBlock,
            @Nullable WireOrientation wireOrientation,
            boolean notify
    ) {
        if (world.isClient()) return;

        BlockPos rootPos = getRootPos(pos, state);
        BlockState rootState = world.getBlockState(rootPos);
        if (!isSameDoor(rootState)) {
            return;
        }

        Direction facing = rootState.get(FACING);

        // podczas stawiania ignorujemy chwilowy stan niekompletny
        if (!isStructureComplete(world, rootPos, facing)) {
            return;
        }

        boolean powered = isAnyPartPowered(world, rootPos, facing);
        boolean currentPowered = rootState.get(POWERED);

        if (powered != currentPowered) {
            setOpen(world, rootPos, rootState.with(POWERED, powered), powered, true);
        } else if (!isStructureValid(world, rootPos, rootState)) {
            breakAllParts(world, rootPos, rootState, false);
        }
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
        return state;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient()) {
            BlockPos rootPos = getRootPos(pos, state);
            BlockState rootState = world.getBlockState(rootPos);
            if (isSameDoor(rootState)) {
                boolean drop = rootPos.equals(pos);
                breakAllParts(world, rootPos, rootState, drop);
            }
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos rootPos = getRootPos(pos, state);

        if (!world.getBlockState(rootPos.down()).isSideSolidFullSquare(world, rootPos.down(), Direction.UP)) {
            return false;
        }

        return true;
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        BlockState mirrored = state.rotate(mirror.getRotation(state.get(FACING)));

        return mirrored.with(HINGE,
                state.get(HINGE) == VaultDoorHinge.LEFT
                        ? VaultDoorHinge.RIGHT
                        : VaultDoorHinge.LEFT
        );
    }

    private void setOpen(World world, BlockPos rootPos, BlockState rootState, boolean open, boolean fromRedstone) {
        Direction facing = rootState.get(FACING);
        boolean powered = fromRedstone ? open : rootState.get(POWERED);

        for (BlockPos partPos : getAllPartPositions(rootPos, facing)) {
            BlockState partState = world.getBlockState(partPos);
            if (isSameDoor(partState)) {
                world.setBlockState(partPos, partState.with(OPEN, open).with(POWERED, powered), Block.NOTIFY_ALL);
            }
        }

        world.playSound(null, rootPos, open ? SoundEvents.BLOCK_IRON_DOOR_OPEN : SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    private void setPart(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state, Block.NOTIFY_ALL);
    }

    private void breakAllParts(WorldAccess world, BlockPos rootPos, BlockState rootState, boolean dropRoot) {
        Direction facing = rootState.get(FACING);
        for (BlockPos partPos : getAllPartPositions(rootPos, facing)) {
            BlockState partState = world.getBlockState(partPos);
            if (isSameDoor(partState)) {
                if (dropRoot && partPos.equals(rootPos) && world instanceof World actualWorld) {
                    Block.dropStacks(partState, actualWorld, partPos);
                }
                world.setBlockState(partPos, net.minecraft.block.Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
            }
        }
    }

    private boolean canPlaceStructureAt(WorldView world, BlockPos rootPos, Direction facing) {
        if (!world.getBlockState(rootPos.down()).isSideSolidFullSquare(world, rootPos.down(), Direction.UP)) {
            return false;
        }

        for (BlockPos pos : getAllPartPositions(rootPos, facing)) {
            BlockState state = world.getBlockState(pos);
            if (!state.isAir() && !state.isReplaceable()) {
                return false;
            }
        }

        return true;
    }

    private boolean isStructureComplete(WorldView world, BlockPos rootPos, Direction facing) {
        for (BlockPos partPos : getAllPartPositions(rootPos, facing)) {
            if (!isSameDoor(world.getBlockState(partPos))) {
                return false;
            }
        }
        return true;
    }

    private boolean isStructureValid(WorldView world, BlockPos rootPos, BlockState referenceState) {
        Direction facing = referenceState.get(FACING);

        if (!world.getBlockState(rootPos.down()).isSideSolidFullSquare(world, rootPos.down(), Direction.UP)) {
            return false;
        }

        for (BlockPos partPos : getAllPartPositions(rootPos, facing)) {
            BlockState partState = world.getBlockState(partPos);
            if (!isSameDoor(partState)) {
                return false;
            }
            if (partState.get(FACING) != facing) {
                return false;
            }
        }

        return true;
    }

    private boolean isAnyPartPowered(World world, BlockPos rootPos, Direction facing) {
        for (BlockPos partPos : getAllPartPositions(rootPos, facing)) {
            if (world.isReceivingRedstonePower(partPos)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSameDoor(BlockState state) {
        return state.getBlock() instanceof VaultDoorBlock;
    }

    private BlockPos getRootPos(BlockPos pos, BlockState state) {
        Direction facing = state.get(FACING);
        BlockPos result = pos;

        if (state.get(PART_Y) == VaultDoorPartY.UPPER) {
            result = result.down();
        }
        if (state.get(PART_X) == VaultDoorPartX.RIGHT) {
            result = result.offset(getRightDirection(facing).getOpposite());
        }

        return result;
    }

    private BlockPos[] getAllPartPositions(BlockPos rootPos, Direction facing) {
        return new BlockPos[] {
                rootPos,
                getUpperLeftPos(rootPos),
                getRightPos(rootPos, facing),
                getUpperRightPos(rootPos, facing)
        };
    }

    private BlockPos getUpperLeftPos(BlockPos rootPos) {
        return rootPos.up();
    }

    private BlockPos getRightPos(BlockPos rootPos, Direction facing) {
        return rootPos.offset(getRightDirection(facing));
    }

    private BlockPos getUpperRightPos(BlockPos rootPos, Direction facing) {
        return rootPos.up().offset(getRightDirection(facing));
    }

    private Direction getRightDirection(Direction facing) {
        return switch (facing) {
            case NORTH -> Direction.EAST;
            case SOUTH -> Direction.WEST;
            case EAST -> Direction.SOUTH;
            case WEST -> Direction.NORTH;
            default -> Direction.EAST;
        };
    }

    public enum VaultDoorPartX implements StringIdentifiable {
        LEFT("left"),
        RIGHT("right");

        private final String name;

        VaultDoorPartX(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public enum VaultDoorPartY implements StringIdentifiable {
        LOWER("lower"),
        UPPER("upper");

        private final String name;

        VaultDoorPartY(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
