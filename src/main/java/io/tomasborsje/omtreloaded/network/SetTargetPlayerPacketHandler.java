package io.tomasborsje.omtreloaded.network;

import com.mojang.logging.LogUtils;
import io.tomasborsje.omtreloaded.blockentities.SimpleTurretBaseEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SetTargetPlayerPacketHandler {
    public static void handleClientsideData(final SetTargetPlayerPacket data, final IPayloadContext context) {
        LogUtils.getLogger().info("Client received packet with x={}, y={}, z={}, targetPlayers={}", data.x(), data.y(), data.z(), data.targetPlayers());
    }

    public static void handleServersideData(final SetTargetPlayerPacket data, final IPayloadContext context) {
        LogUtils.getLogger().info("Server received packet with x={}, y={}, z={}, targetPlayers={}", data.x(), data.y(), data.z(), data.targetPlayers());
        // Get level
        Level level = context.player().level();
        // Get block position
        BlockPos pos = new BlockPos(data.x(), data.y(), data.z());
        if(level.hasChunkAt(pos)) {
            // Get block entity and ensure it is a SimpleTurretBaseEntity
            if (level.getBlockEntity(pos) instanceof SimpleTurretBaseEntity entity) {
                // Set target players
                entity.setTargetPlayers(data.targetPlayers());
            }
        }
    }
}
