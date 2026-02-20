package com.tomasborsje.omtreloaded.registry;

import com.tomasborsje.omtreloaded.OMTReloaded;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OMTReloaded.MODID);

    @SuppressWarnings("unused")
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.omtreloaded")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.LIGHT_TURRET_AMMO.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.LIGHT_TURRET_AMMO.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());
}
