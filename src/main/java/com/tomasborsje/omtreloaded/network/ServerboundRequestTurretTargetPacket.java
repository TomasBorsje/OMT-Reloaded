package com.tomasborsje.omtreloaded.network;

import com.tomasborsje.omtreloaded.OMTReloaded;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record ServerboundRequestTurretTargetPacket(Integer blockX, int blockY, int blockZ) implements CustomPacketPayload {
    public static final Type<ServerboundRequestTurretTargetPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(OMTReloaded.MODID, "request_turret_target_packet"));
    public static final StreamCodec<ByteBuf, ServerboundRequestTurretTargetPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ServerboundRequestTurretTargetPacket::blockX,
            ByteBufCodecs.INT,
            ServerboundRequestTurretTargetPacket::blockY,
            ByteBufCodecs.INT,
            ServerboundRequestTurretTargetPacket::blockZ,
            ServerboundRequestTurretTargetPacket::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
