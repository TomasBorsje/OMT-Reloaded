package io.tomasborsje.omtreloaded.setup;

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
     * @param event BuildCreativeModeTabContentsEvent event
     */
    public static void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == ModRegistration.OMT_RELOADED_CREATIVE_TAB.getKey()) {
            event.accept(SIMPLE_TURRET_ITEM.get());
            event.accept(SIMPLE_TURRET_BASE_ITEM.get());
        }
    }

    // Items
    public static final DeferredHolder<Item, Item> SIMPLE_TURRET_ITEM = ITEMS.register("simple_turret", MachineGunTurretItem::new);
    public static final DeferredHolder<Item, Item> SIMPLE_TURRET_BASE_ITEM = ITEMS.register("simple_turret_base", () -> new BlockItem(ModBlocks.SIMPLE_TURRET_BASE.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> TURRET_RAIL = ITEMS.register("turret_rail", () -> new Item(new Item.Properties()));

}
