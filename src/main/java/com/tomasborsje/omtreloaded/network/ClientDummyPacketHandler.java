package com.tomasborsje.omtreloaded.network;

import com.tomasborsje.omtreloaded.OMTReloaded;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientDummyPacketHandler {
    public static void handleDataOnMain(DummyPacket dummyPacket, IPayloadContext payloadContext) {
        OMTReloaded.LOGGER.info("Client received message: {}", dummyPacket.message());
    }
}
