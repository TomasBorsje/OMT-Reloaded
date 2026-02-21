package com.tomasborsje.omtreloaded.network;

import com.tomasborsje.omtreloaded.OMTReloaded;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record ServerboundRequestTurretLookAnglePacket(Integer blockX, int blockY, int blockZ) implements CustomPacketPayload {
    public static final Type<ServerboundRequestTurretLookAnglePacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(OMTReloaded.MODID, "request_turret_look_angle_packet"));
    public static final StreamCodec<ByteBuf, ServerboundRequestTurretLookAnglePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ServerboundRequestTurretLookAnglePacket::blockX,
            ByteBufCodecs.INT,
            ServerboundRequestTurretLookAnglePacket::blockY,
            ByteBufCodecs.INT,
            ServerboundRequestTurretLookAnglePacket::blockZ,
            ServerboundRequestTurretLookAnglePacket::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
