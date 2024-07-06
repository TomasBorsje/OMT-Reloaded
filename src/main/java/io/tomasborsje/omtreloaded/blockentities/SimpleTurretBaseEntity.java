package io.tomasborsje.omtreloaded.blockentities;

import io.tomasborsje.omtreloaded.registration.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.jarjar.nio.util.Lazy;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;

// TODO: Pull up into an abstract base class with addons + GUI etc.
public class SimpleTurretBaseEntity extends BlockEntity {
    private final Lazy<IEnergyStorage> energyStorage = Lazy.of(new EnergyStorage(500000));
    private final Lazy<IItemHandler> itemHandler = Lazy.of(new ItemStackHandler(4));

    public SimpleTurretBaseEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SIMPLE_TURRET_BASE_ENTITY.get(), pPos, pBlockState);
    }

    public IEnergyStorage getEnergyStorage() {
        return energyStorage.get();
    }

    public IItemHandler getItemHandler() {
        return itemHandler.get();
    }

    public boolean tryConsumeAmmo(List<Item> ammoTypes) {
        // Try to extract 1 TURRET_RAIL from the item handler
        // For each stack, check if it is of TURRET_RAIL and if so, remove 1 item
        for (int slot = 0; slot < getItemHandler().getSlots(); slot++) {
            if (ammoTypes.contains(getItemHandler().getStackInSlot(slot).getItem())) {
                ItemStack extracted = getItemHandler().extractItem(slot, 1, false);
                if (extracted.getCount() == 1) {
                    return true;
                }
            }
        }
        return false;
    }
}
