package com.tomasborsje.omtreloaded.network;

import com.tomasborsje.omtreloaded.OMTReloaded;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record ClientboundTurretSetTargetPacket(Integer blockX, int blockY, int blockZ, int entityId) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClientboundTurretSetTargetPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(OMTReloaded.MODID, "turret_set_target_packet"));
    public static final StreamCodec<ByteBuf, ClientboundTurretSetTargetPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ClientboundTurretSetTargetPacket::blockX,
            ByteBufCodecs.INT,
            ClientboundTurretSetTargetPacket::blockY,
            ByteBufCodecs.INT,
            ClientboundTurretSetTargetPacket::blockZ,
            ByteBufCodecs.INT,
            ClientboundTurretSetTargetPacket::entityId,
            ClientboundTurretSetTargetPacket::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
