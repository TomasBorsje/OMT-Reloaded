package com.tomasborsje.omtreloaded.network;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.core.AbstractTurretBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.io.IOException;

/**
 * When the client requests a target, send them a ClientboundTurretSetTargetPacket with the requested turret's current
 * target entity ID.
 */
public class ServerboundRequestTurretLookAnglePacketHandler {
    public static void handleDataOnMain(ServerboundRequestTurretLookAnglePacket packet, IPayloadContext context) {
        final ServerLevel level = (ServerLevel) context.player().level();
        final BlockPos blockPos = new BlockPos(packet.blockX(), packet.blockY(), packet.blockZ());
        final ChunkPos chunkPos = new ChunkPos(blockPos);
        if(!level.hasChunk(chunkPos.x, chunkPos.z)) { return; }

        BlockEntity be = level.getBlockEntity(blockPos);
        if(be instanceof AbstractTurretBlockEntity turretBlockEntity) {
            // Send the client a turret set look angle packet to sync
            final BlockPos pos = turretBlockEntity.getBlockPos();
            final ClientboundTurretSetLookAnglePacket turretTargetPacket;
            turretTargetPacket = new ClientboundTurretSetLookAnglePacket(pos.getX(), pos.getY(), pos.getZ(), turretBlockEntity.getTurretYaw(), turretBlockEntity.getBarrelPitch());
            PacketDistributor.sendToPlayer((ServerPlayer) context.player(), turretTargetPacket);
        }
    }
}
