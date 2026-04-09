package net.mcreator.mofusbetterend.block;

import com.mojang.serialization.MapCodec;
import net.mcreator.mofusbetterend.block.entity.MechCreationStationTileEntity;
import net.mcreator.mofusbetterend.init.MofusBetterEndModBlockEntities;
import net.mcreator.mofusbetterend.procedures.MechCreationStationUpdateTickProcedure;
import net.mcreator.mofusbetterend.world.inventory.MechCreationStationGUIMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class MechCreationStationBlock extends BaseEntityBlock {
    public static final IntegerProperty ANIMATION = IntegerProperty.create("animation", 0, 4);
    public static final MapCodec<MechCreationStationBlock> CODEC = simpleCodec(properties -> new MechCreationStationBlock());

    public MechCreationStationBlock() {
        super(Properties.of()
                .sound(SoundType.METAL)
                .strength(3.0F, 40.0F)
                .lightLevel(state -> 10)
                .noOcclusion());
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MechCreationStationTileEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ANIMATION);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState();
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        level.scheduleTick(pos, this, 20);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
        MechCreationStationUpdateTickProcedure.execute(level, pos.getX(), pos.getY(), pos.getZ());
        level.scheduleTick(pos, this, 20);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        System.out.println("[制造站] 右键点击，坐标: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider(
                    (id, inv, p) -> new MechCreationStationGUIMenu(id, inv, pos),
                    Component.literal("Mech Creation Station")
            ));
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return null;
    }
}