package io.tomasborsje.omtreloaded.registration;

import com.mojang.logging.LogUtils;
import io.tomasborsje.omtreloaded.network.SetTargetPlayerPacket;
import io.tomasborsje.omtreloaded.network.SetTargetPlayerPacketHandler;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

public class CommonSetup {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void setup(final FMLCommonSetupEvent event) {
        // TODO: Needed?
    }

    public static void registerPayloadHandlers(final RegisterPayloadHandlersEvent event) {
        LogUtils.getLogger().info("Registering OMT:R payload handlers");
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
                SetTargetPlayerPacket.TYPE,
                SetTargetPlayerPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        SetTargetPlayerPacketHandler::handleClientsideData,
                        SetTargetPlayerPacketHandler::handleServersideData
                )
        );
    }
}
