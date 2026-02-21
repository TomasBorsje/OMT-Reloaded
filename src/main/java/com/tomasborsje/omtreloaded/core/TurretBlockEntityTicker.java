package com.tomasborsje.omtreloaded.core;

import com.tomasborsje.omtreloaded.OMTReloaded;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * This class is used to tick block entities on an instance level so that the block tickers don't need to be defined for every subclass.
 */
public class TurretBlockEntityTicker {
    public static <T extends BlockEntity> void tickServer(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        try {
            final ChunkPos chunkPos = new ChunkPos(blockPos);
            if (!(level instanceof ServerLevel serverLevel) || !serverLevel.hasChunk(chunkPos.x, chunkPos.z) || !serverLevel.isPositionEntityTicking(blockPos)) {
                return;
            }
            if (!(blockEntity instanceof AbstractTurretBlockEntity turretBlockEntity)) {
                return;
            }
            turretBlockEntity.tickServer();
        } catch (Exception ex) {
            OMTReloaded.LOGGER.error(ex.toString());
            throw ex;
        }
    }

    public static <T extends BlockEntity> void tickClient(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if(!(level instanceof ClientLevel)) { return; }
        if (!(blockEntity instanceof AbstractTurretBlockEntity turretBlockEntity)) { return; }
        turretBlockEntity.tickClient();
    }
}
