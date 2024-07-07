package io.tomasborsje.omtreloaded;

import com.mojang.logging.LogUtils;
import io.tomasborsje.omtreloaded.datagen.DataGenerators;
import io.tomasborsje.omtreloaded.registration.ClientSetup;
import io.tomasborsje.omtreloaded.registration.CommonSetup;
import io.tomasborsje.omtreloaded.registration.ModItems;
import io.tomasborsje.omtreloaded.registration.ModRegistration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(OMTReloaded.MODID)
public class OMTReloaded {
    public static final String MODID = "omtreloaded";
    private static final Logger LOGGER = LogUtils.getLogger();

    public OMTReloaded(IEventBus modEventBus, ModContainer modContainer) {
        ModRegistration.register(modEventBus);

        // Setup common and client code during mod construction
        modEventBus.addListener(CommonSetup::setup);
        modEventBus.addListener(CommonSetup::registerPayloadHandlers);
        modEventBus.addListener(ModItems::addCreative);
        modEventBus.addListener(ModRegistration::registerCapabilities);
        modEventBus.addListener(ClientSetup::onClientSetup);
        modEventBus.addListener(ClientSetup::onRegisterRenderers);
        modEventBus.addListener(ClientSetup::onRegisterScreens);
        modEventBus.addListener(DataGenerators::generateData);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
