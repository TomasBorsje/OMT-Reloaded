package com.tomasborsje.omtreloaded.blocks;

import com.tomasborsje.omtreloaded.blockentities.TurretBaseBlockEntity;
import com.tomasborsje.omtreloaded.core.TurretBaseBlockEntityTicker;
import com.tomasborsje.omtreloaded.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class TurretBaseBlock extends Block implements EntityBlock {
    public TurretBaseBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new TurretBaseBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NonNull Level level, @NonNull BlockState state, @NonNull BlockEntityType<T> blockEntityType) {
        if(blockEntityType != ModBlockEntityTypes.TURRET_BASE_BLOCK_ENTITY.get()) { return null; }
        // Tick server and clientside respectively
        return level instanceof ServerLevel ? TurretBaseBlockEntityTicker::tickServer : TurretBaseBlockEntityTicker::tickClient;
    }

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if(blockEntity instanceof TurretBaseBlockEntity turretBaseBlockEntity) {
            return turretBaseBlockEntity;
        }
        return null;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult result) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(state.getMenuProvider(level, pos));
        }
        return InteractionResult.SUCCESS;
    }
}
