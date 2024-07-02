package io.tomasborsje.omtreloaded.setup;

import com.mojang.logging.LogUtils;
import io.tomasborsje.omtreloaded.renderers.SimpleGeoTurretRenderer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.slf4j.Logger;

public class ClientSetup {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Register the renderer for the turret base

    }

    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Register the renderer for the turret base
        LOGGER.info("Registering OTM-R renderers");
        event.registerBlockEntityRenderer(Registration.SIMPLE_TURRET_ENTITY.get(), SimpleGeoTurretRenderer::new);
    }
}
