package io.tomasborsje.omtreloaded.network;

import io.netty.buffer.ByteBuf;
import io.tomasborsje.omtreloaded.OMTReloaded;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SetTargetPlayerPacket(int x, int y, int z, boolean targetPlayers) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SetTargetPlayerPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(OMTReloaded.MODID, "toggle_player_target"));

    // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
    // 'name' will be encoded and decoded as a string
    // 'age' will be encoded and decoded as an integer
    // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
    public static final StreamCodec<ByteBuf, SetTargetPlayerPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            SetTargetPlayerPacket::x,
            ByteBufCodecs.VAR_INT,
            SetTargetPlayerPacket::y,
            ByteBufCodecs.VAR_INT,
            SetTargetPlayerPacket::z,
            ByteBufCodecs.BOOL,
            SetTargetPlayerPacket::targetPlayers,
            SetTargetPlayerPacket::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
