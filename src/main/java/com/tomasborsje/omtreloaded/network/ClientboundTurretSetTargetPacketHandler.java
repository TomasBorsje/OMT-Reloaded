package com.tomasborsje.omtreloaded.network;

import com.tomasborsje.omtreloaded.core.AbstractTurretBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientboundTurretSetTargetPacketHandler {
    public static void handleDataOnMain(ClientboundTurretSetTargetPacket packet, IPayloadContext context) {
        BlockEntity be = context.player().level().getBlockEntity(new BlockPos(packet.blockX(), packet.blockY(), packet.blockZ()));
        if(be instanceof AbstractTurretBlockEntity turretBlockEntity) {
            turretBlockEntity.setTargetByEntityId(packet.entityId());
        }
    }
}
