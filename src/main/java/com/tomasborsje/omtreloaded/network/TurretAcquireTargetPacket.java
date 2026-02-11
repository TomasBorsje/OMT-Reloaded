package com.tomasborsje.omtreloaded.network;

import com.tomasborsje.omtreloaded.OMTReloaded;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record TurretAcquireTargetPacket(Integer blockX, int blockY, int blockZ, int entityId) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<TurretAcquireTargetPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(OMTReloaded.MODID, "turret_acquire_target_packet"));
    public static final StreamCodec<ByteBuf, TurretAcquireTargetPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            TurretAcquireTargetPacket::blockX,
            ByteBufCodecs.INT,
            TurretAcquireTargetPacket::blockY,
            ByteBufCodecs.INT,
            TurretAcquireTargetPacket::blockZ,
            ByteBufCodecs.INT,
            TurretAcquireTargetPacket::entityId,
            TurretAcquireTargetPacket::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
