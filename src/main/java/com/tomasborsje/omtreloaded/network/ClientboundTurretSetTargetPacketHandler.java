package com.tomasborsje.omtreloaded.network;

import com.tomasborsje.omtreloaded.core.AbstractTurretBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.io.IOException;

/**
 * When our client receives a ClientboundTurretSetTargetPacket, set the turret at the given location's target to the
 * given target.
 */
public class ClientboundTurretSetTargetPacketHandler {
    public static void handleDataOnMain(ClientboundTurretSetTargetPacket packet, IPayloadContext context) {
        try (var level = context.player().level()) {
            final BlockPos blockPos = new BlockPos(packet.blockX(), packet.blockY(), packet.blockZ());
            final ChunkPos chunkPos = new ChunkPos(blockPos);
            if(!level.hasChunk(chunkPos.x, chunkPos.z)) { return; }

            BlockEntity be = level.getBlockEntity(blockPos);
            if(be instanceof AbstractTurretBlockEntity turretBlockEntity) {
                turretBlockEntity.setTargetByEntityId(packet.entityId());
            }
        } catch (IOException e) {
            // Don't do anything
        }
    }
}
