package com.tomasborsje.omtreloaded.network;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.blockentities.BasicTurretBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class TurretAcquireTargetPacketHandler {
    public static void handleDataOnMain(TurretAcquireTargetPacket packet, IPayloadContext context) {
        BlockEntity be = context.player().level().getBlockEntity(new BlockPos(packet.blockX(), packet.blockY(), packet.blockZ()));
        if(be instanceof BasicTurretBlockEntity turretBlockEntity) {
            turretBlockEntity.setTargetByEntityId(packet.entityId());
        }
    }
}
