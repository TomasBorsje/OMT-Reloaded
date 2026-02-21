package com.tomasborsje.omtreloaded.network;

import com.tomasborsje.omtreloaded.OMTReloaded;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record ClientboundTurretClearTargetPacket(Integer blockX, int blockY, int blockZ) implements CustomPacketPayload {
    public static final Type<ClientboundTurretClearTargetPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(OMTReloaded.MODID, "turret_clear_target_packet"));
    public static final StreamCodec<ByteBuf, ClientboundTurretClearTargetPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ClientboundTurretClearTargetPacket::blockX,
            ByteBufCodecs.INT,
            ClientboundTurretClearTargetPacket::blockY,
            ByteBufCodecs.INT,
            ClientboundTurretClearTargetPacket::blockZ,
            ClientboundTurretClearTargetPacket::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
