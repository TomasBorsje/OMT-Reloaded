package com.tomasborsje.omtreloaded.network;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.core.AbstractTurretBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.io.IOException;

/**
 * When our client receives a ClientboundTurretSetTargetPacket, set the turret at the given location's target to the
 * given target.
 */
public class ClientboundTurretSetLookAnglePacketHandler {
    public static void handleDataOnMain(ClientboundTurretSetLookAnglePacket packet, IPayloadContext context) {
        try (var level = context.player().level()) {
            final BlockPos blockPos = new BlockPos(packet.blockX(), packet.blockY(), packet.blockZ());
            final ChunkPos chunkPos = new ChunkPos(blockPos);
            if(!level.hasChunk(chunkPos.x, chunkPos.z)) { return; }

            BlockEntity be = level.getBlockEntity(blockPos);
            if(be instanceof AbstractTurretBlockEntity turretBlockEntity) {
                turretBlockEntity.setTurretYaw(packet.turretYaw());
                turretBlockEntity.setBarrelPitch(packet.barrelPitch());
            }
        } catch (IOException e) {
            // Don't do anything
        }
    }
}
