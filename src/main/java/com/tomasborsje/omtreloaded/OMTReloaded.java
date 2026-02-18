package com.tomasborsje.omtreloaded;

import com.mojang.logging.LogUtils;
import com.tomasborsje.omtreloaded.datagen.DataGenerators;
import com.tomasborsje.omtreloaded.network.TurretAcquireTargetPacket;
import com.tomasborsje.omtreloaded.registry.*;
import com.tomasborsje.omtreloaded.ui.TurretBaseMenuScreen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

@Mod(OMTReloaded.MODID)
public class OMTReloaded {
    public static final String MODID = "omtreloaded";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final String NETWORKING_VERSION = "1";

    public OMTReloaded(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerCommonPayloadHandlers);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::registerScreens);

        // Register our registers
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        ModMenus.MENUS.register(modEventBus);
        modEventBus.addListener(ModItems::addCreative);
        modEventBus.addListener(DataGenerators::gatherClientData);
        modEventBus.addListener(DataGenerators::gatherServerData);

        // Register ourselves for server and other game events we are interested in.
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    private void registerCommonPayloadHandlers(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(NETWORKING_VERSION);
        registrar.playToClient(TurretAcquireTargetPacket.TYPE, TurretAcquireTargetPacket.STREAM_CODEC);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.Energy.BLOCK,
                ModBlockEntityTypes.TURRET_BASE_BLOCK_ENTITY.get(),
                (be, side) -> be.getEnergyHandler());
    }

    private void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.TURRET_BASE_MENU.get(), TurretBaseMenuScreen::new);
    }
}
