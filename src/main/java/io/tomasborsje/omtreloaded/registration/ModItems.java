package io.tomasborsje.omtreloaded.registration;

import io.tomasborsje.omtreloaded.items.MachineGunTurretItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static io.tomasborsje.omtreloaded.OMTReloaded.MODID;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    /**
     * Register items to the creative tab.
     *
     * @param event BuildCreativeModeTabContentsEvent event
     */
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == ModRegistration.OMT_RELOADED_CREATIVE_TAB.getKey()) {
            event.accept(SIMPLE_CIRCUIT_BOARD.get());
            event.accept(SIMPLE_TURRET_BASE_ITEM.get());
            event.accept(MACHINE_GUN_TURRET_ITEM.get());
            event.accept(BULLET.get());
            event.accept(THROWING_GRENADE.get());
        }
    }

    // Items
    public static final DeferredHolder<Item, Item> MACHINE_GUN_TURRET_ITEM = ITEMS.register("machine_gun_turret", MachineGunTurretItem::new);
    public static final DeferredHolder<Item, Item> SIMPLE_TURRET_BASE_ITEM = ITEMS.register("simple_turret_base", () -> new BlockItem(ModBlocks.SIMPLE_TURRET_BASE.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> SIMPLE_CIRCUIT_BOARD = ITEMS.register("simple_circuit_board", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> BULLET = ITEMS.register("bullet", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> THROWING_GRENADE = ITEMS.register("throwing_grenade", () -> new Item(new Item.Properties()));

}
