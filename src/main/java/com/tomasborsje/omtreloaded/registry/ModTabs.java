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
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> OMT_RELOADED_CREATIVE_TAB = CREATIVE_MODE_TABS.register("omt_reloaded_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.omtreloaded"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.LIGHT_TURRET_AMMO.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                ModItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()));
            }).build());
}
