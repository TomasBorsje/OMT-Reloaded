package com.tomasborsje.omtreloaded.network;

import com.tomasborsje.omtreloaded.OMTReloaded;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record ClientboundTurretSetLookAnglePacket(Integer blockX, int blockY, int blockZ, float turretYaw, float barrelPitch) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClientboundTurretSetLookAnglePacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(OMTReloaded.MODID, "turret_set_look_angle_packet"));
    public static final StreamCodec<ByteBuf, ClientboundTurretSetLookAnglePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ClientboundTurretSetLookAnglePacket::blockX,
            ByteBufCodecs.INT,
            ClientboundTurretSetLookAnglePacket::blockY,
            ByteBufCodecs.INT,
            ClientboundTurretSetLookAnglePacket::blockZ,
            ByteBufCodecs.FLOAT,
            ClientboundTurretSetLookAnglePacket::turretYaw,
            ByteBufCodecs.FLOAT,
            ClientboundTurretSetLookAnglePacket::barrelPitch,
            ClientboundTurretSetLookAnglePacket::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
