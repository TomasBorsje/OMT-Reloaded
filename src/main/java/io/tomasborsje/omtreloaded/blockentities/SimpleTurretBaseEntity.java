package io.tomasborsje.omtreloaded.blockentities;

import io.tomasborsje.omtreloaded.registration.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
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
    private boolean targetPlayers = true;

    public SimpleTurretBaseEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SIMPLE_TURRET_BASE_ENTITY.get(), pPos, pBlockState);
    }

    /**
     * Tries to consume 1 item of the given ammo types from the turret base's inventory.
     *
     * @param ammoTypes The ammo types to consume
     * @return True if an item was consumed, false otherwise
     */
    public boolean tryConsumeAmmo(List<Item> ammoTypes) {
        // Try to extract 1 item of the given ammo types
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

    public IEnergyStorage getEnergyStorage() {
        return energyStorage.get();
    }

    public IItemHandler getItemHandler() {
        return itemHandler.get();
    }

    public void saveClientData(CompoundTag tag) {
        tag.putBoolean("targetPlayers", targetPlayers);
    }

    public void loadClientData(CompoundTag tag) {
        targetPlayers = tag.getBoolean("targetPlayers");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = super.getUpdateTag(provider);
        saveClientData(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider holders) {
        loadClientData(tag);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookup) {
        // This is called client side
        CompoundTag tag = pkt.getTag();
        // This will call loadClientData()
        handleUpdateTag(tag, lookup);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        saveClientData(pTag);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        loadClientData(pTag);
    }

    public boolean isTargetPlayers() {
        return targetPlayers;
    }

    public void setTargetPlayers(boolean targetPlayers) {
        this.targetPlayers = targetPlayers;
    }
}
