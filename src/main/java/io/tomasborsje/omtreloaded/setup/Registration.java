package io.tomasborsje.omtreloaded.setup;

import io.tomasborsje.omtreloaded.OMTReloaded;
import io.tomasborsje.omtreloaded.blockentities.SimpleTurretEntity;
import io.tomasborsje.omtreloaded.blocks.SimpleTurret;
import io.tomasborsje.omtreloaded.blocks.SimpleTurretBase;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static io.tomasborsje.omtreloaded.OMTReloaded.MODID;

public class Registration {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, OMTReloaded.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        CREATIVE_MODE_TABS.register(bus);
    }
    public static void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == OMT_RELOADED_TAB.getKey()) {
            event.accept(SIMPLE_TURRET_ITEM.get());
            event.accept(SIMPLE_TURRET_BASE_ITEM.get());
            event.accept(TURRET_RAIL.get());
        }
    }
    public static final DeferredHolder<Block, SimpleTurret> SIMPLE_TURRET = BLOCKS.register("simple_turret", SimpleTurret::new);
    public static final DeferredHolder<Item, Item> SIMPLE_TURRET_ITEM = ITEMS.register("simple_turret", () -> new BlockItem(SIMPLE_TURRET.get(), new Item.Properties()));
    public static final DeferredHolder<Block, SimpleTurretBase> SIMPLE_TURRET_BASE = BLOCKS.register("simple_turret_base", SimpleTurretBase::new);
    public static final DeferredHolder<Item, Item> SIMPLE_TURRET_BASE_ITEM = ITEMS.register("simple_turret_base", () -> new BlockItem(SIMPLE_TURRET_BASE.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> TURRET_RAIL = ITEMS.register("turret_rail", () -> new Item(new Item.Properties()));

    // Entity
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SimpleTurretEntity>> SIMPLE_TURRET_ENTITY = BLOCK_ENTITIES.register("simple_turret",
            () -> BlockEntityType.Builder.of(SimpleTurretEntity::new, SIMPLE_TURRET.get()).build(null));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> OMT_RELOADED_TAB = CREATIVE_MODE_TABS.register("omt_reloaded_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> TURRET_RAIL.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.omtreloaded"))
            .displayItems((parameters, output) -> {
                output.accept(TURRET_RAIL.get());
            }).build());

}
