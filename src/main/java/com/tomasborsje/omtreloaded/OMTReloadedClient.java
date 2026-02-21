package com.tomasborsje.omtreloaded;

import com.tomasborsje.omtreloaded.network.ClientboundTurretSetLookAnglePacketHandler;
import com.tomasborsje.omtreloaded.network.ClientboundTurretSetLookAnglePacket;
import com.tomasborsje.omtreloaded.registry.ModBlockEntityTypes;
import com.tomasborsje.omtreloaded.renderers.TurretBlockRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;

@Mod(value = OMTReloaded.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = OMTReloaded.MODID, value = Dist.CLIENT)
public class OMTReloadedClient {
    public OMTReloadedClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // TODO: Any client setup code needed goes here
    }

    @SubscribeEvent
    static void registerClientPayloadHandlers(RegisterClientPayloadHandlersEvent event) {
        event.register(ClientboundTurretSetLookAnglePacket.TYPE, ClientboundTurretSetLookAnglePacketHandler::handleDataOnMain);
    }

    @SubscribeEvent
    static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Block entities
        event.registerBlockEntityRenderer(ModBlockEntityTypes.BASIC_TURRET_BLOCK_ENTITY.get(), context -> new TurretBlockRenderer<>());
        // Items
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {

    }
}
