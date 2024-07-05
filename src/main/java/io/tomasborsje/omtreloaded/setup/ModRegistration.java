package io.tomasborsje.omtreloaded.setup;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static io.tomasborsje.omtreloaded.OMTReloaded.MODID;

public class ModRegistration {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static void register(IEventBus bus) {
        ModBlocks.BLOCKS.register(bus);
        ModItems.ITEMS.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
        ModSoundEvents.SOUND_EVENTS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
    }
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> OMT_RELOADED_CREATIVE_TAB = CREATIVE_MODE_TABS.register("omt_reloaded_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.TURRET_RAIL.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.omtreloaded"))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.TURRET_RAIL.get());
            }).build());

}
