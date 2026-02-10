package com.tomasborsje.omtreloaded.network;

import com.tomasborsje.omtreloaded.OMTReloaded;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record DummyPacket(String message) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DummyPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(OMTReloaded.MODID, "dummy_packet"));
    public static final StreamCodec<ByteBuf, DummyPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            DummyPacket::message,
            DummyPacket::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
