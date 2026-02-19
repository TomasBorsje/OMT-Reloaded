package com.tomasborsje.omtreloaded.network;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.core.AbstractTurretBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerboundRequestTurretTargetPacketHandler {
    public static void handleDataOnMain(ServerboundRequestTurretTargetPacket packet, IPayloadContext context) {
        BlockEntity be = context.player().level().getBlockEntity(new BlockPos(packet.blockX(), packet.blockY(), packet.blockZ()));
        if(be instanceof AbstractTurretBlockEntity turretBlockEntity) {
            // Send the client a turret set target packet to sync
            final BlockPos pos = turretBlockEntity.getBlockPos();
            final Entity turretTarget = turretBlockEntity.getTargetEntity();
            final ClientboundTurretSetTargetPacket turretTargetPacket;
            if (turretTarget != null) {
                turretTargetPacket = new ClientboundTurretSetTargetPacket(pos.getX(), pos.getY(), pos.getZ(), turretBlockEntity.getTargetEntity().getId());
                PacketDistributor.sendToPlayer((ServerPlayer) context.player(), turretTargetPacket);
                OMTReloaded.LOGGER.info("Server: Client requested target, sent them the target");
            }
        }
    }
}
