package io.tomasborsje.omtreloaded.setup;

import com.mojang.logging.LogUtils;
import io.tomasborsje.omtreloaded.renderers.block.MachineGunTurretRenderer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.slf4j.Logger;

public class ClientSetup {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static void onClientSetup(FMLClientSetupEvent event) {

    }
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        LOGGER.info("Registering OTM-R renderers");
        event.registerBlockEntityRenderer(ModBlockEntities.SIMPLE_TURRET_ENTITY.get(), MachineGunTurretRenderer::new);
    }
}
