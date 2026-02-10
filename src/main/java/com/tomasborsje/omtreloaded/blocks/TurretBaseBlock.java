package com.tomasborsje.omtreloaded.blocks;

import com.tomasborsje.omtreloaded.blockentities.TurretBaseBlockEntity;
import com.tomasborsje.omtreloaded.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class TurretBaseBlock extends Block implements EntityBlock {
    public TurretBaseBlock(BlockBehaviour.Properties properties) {
        super(properties.destroyTime(60));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TurretBaseBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(blockEntityType != ModBlockEntityTypes.TURRET_BASE_BLOCK_ENTITY.get()) { return null; }
        // Tick server and clientside respectively
        return level instanceof ServerLevel ? TurretBaseBlockEntity::tickServer : TurretBaseBlockEntity::tickClient;
    }
}
