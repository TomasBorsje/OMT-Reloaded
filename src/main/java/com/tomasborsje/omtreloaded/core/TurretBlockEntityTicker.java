package com.tomasborsje.omtreloaded.core;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * This class is used to tick block entities on an instance level so that the block tickers don't need to be defined for every subclass.
 */
public class TurretBlockEntityTicker {
    public static <T extends BlockEntity> void tickServer(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if(level == null || level.getServer() == null || level.getServer().isStopped() || level.getServer().isShutdown()) { return; }
        if (!(blockEntity instanceof AbstractTurretBlockEntity turretBlockEntity)) { return; }
        turretBlockEntity.tickServer();
    }

    public static <T extends BlockEntity> void tickClient(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if(!(level instanceof ClientLevel)) { return; }
        if (!(blockEntity instanceof AbstractTurretBlockEntity turretBlockEntity)) { return; }
        turretBlockEntity.tickClient();
    }
}
