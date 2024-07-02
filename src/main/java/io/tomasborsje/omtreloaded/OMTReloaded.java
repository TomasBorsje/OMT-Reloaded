package io.tomasborsje.omtreloaded;

import io.tomasborsje.omtreloaded.datagen.DataGenerators;
import io.tomasborsje.omtreloaded.setup.ClientSetup;
import io.tomasborsje.omtreloaded.setup.CommonSetup;
import io.tomasborsje.omtreloaded.setup.Registration;
import net.neoforged.fml.ModLoadingContext;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(OMTReloaded.MODID)
public class OMTReloaded
{
    public static final String MODID = "omtreloaded";
    private static final Logger LOGGER = LogUtils.getLogger();

    public OMTReloaded(IEventBus modEventBus, ModContainer modContainer)
    {
        Registration.register(modEventBus);

        // Setup common and client code during mod construction
        modEventBus.addListener(Registration::addCreative);
        modEventBus.addListener(CommonSetup::setup);
        modEventBus.addListener(ClientSetup::onClientSetup);
        modEventBus.addListener(ClientSetup::onRegisterRenderers);
        modEventBus.addListener(DataGenerators::generateData);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
