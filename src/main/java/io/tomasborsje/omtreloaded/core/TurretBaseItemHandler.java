package io.tomasborsje.omtreloaded.core;

import io.tomasborsje.omtreloaded.items.AbstractAddon;
import io.tomasborsje.omtreloaded.registration.ModItemTags;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class TurretBaseItemHandler extends ItemStackHandler {
    public final int AMMO_SLOT_START = 0;
    public final int AMMO_SLOT_COUNT;
    public final int ADDON_SLOT_START;
    public final int ADDON_SLOT_COUNT;


    public TurretBaseItemHandler(int ammoSlotCount, int addonSlotCount) {
        super(ammoSlotCount + addonSlotCount);
        AMMO_SLOT_COUNT = ammoSlotCount;
        ADDON_SLOT_START = AMMO_SLOT_START + AMMO_SLOT_COUNT;
        ADDON_SLOT_COUNT = addonSlotCount;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        // If this is an ammo slot, only allow ammo types
        if (slot < AMMO_SLOT_START + AMMO_SLOT_COUNT) {
            return stack.is(ModItemTags.AMMO);
        }
        // Else if this is an addon slot, only allow addons
        else if (slot >= ADDON_SLOT_START && slot < ADDON_SLOT_START + ADDON_SLOT_COUNT) {
            return stack.getItem() instanceof AbstractAddon;
        }
        return false;
    }

}
