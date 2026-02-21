package com.tomasborsje.omtreloaded.network;

import com.tomasborsje.omtreloaded.core.AbstractTurretBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.io.IOException;

/**
 * When our client receives a ClientboundTurretClearTargetPacket, clear the turret at the given location's target.
 */
public class ClientboundTurretClearTargetPacketHandler {
    public static void handleDataOnMain(ClientboundTurretClearTargetPacket packet, IPayloadContext context) {
        try (var level = context.player().level()) {
            final BlockPos blockPos = new BlockPos(packet.blockX(), packet.blockY(), packet.blockZ());
            final ChunkPos chunkPos = new ChunkPos(blockPos);
            if(!level.hasChunk(chunkPos.x, chunkPos.z)) { return; }

            BlockEntity be = level.getBlockEntity(blockPos);
            if(be instanceof AbstractTurretBlockEntity turretBlockEntity) {
                turretBlockEntity.clearTargetClientside();
            }
        } catch (IOException e) {
            // Don't do anything
        }
    }
}
