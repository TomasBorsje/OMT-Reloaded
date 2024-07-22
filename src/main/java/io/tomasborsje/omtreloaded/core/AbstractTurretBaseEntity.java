package io.tomasborsje.omtreloaded.core;

import io.tomasborsje.omtreloaded.OMTReloaded;
import io.tomasborsje.omtreloaded.items.AbstractAddon;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.jarjar.nio.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

// TODO: Pull up into an abstract base class with addons + GUI etc.
public abstract class AbstractTurretBaseEntity extends BlockEntity implements IProbeInfoProvider {
    private final Lazy<TurretEnergyStorage> energyStorage = Lazy.of(this::createEnergyStorage);
    private final Lazy<TurretBaseItemHandler> itemHandler = Lazy.of(this::createItemStackHandler);
    private final TurretBaseStats stats;
    private boolean targetPlayers = false;

    public AbstractTurretBaseEntity(BlockEntityType<?> blockEntityType, BlockPos pPos, BlockState pBlockState, TurretBaseStats stats) {
        super(blockEntityType, pPos, pBlockState);
        this.stats = stats;
    }

    /**
     * Reset the stats of the turret base to the base stats.
     * This should not be called directly. Use {@link #calculateDynamicStats()} instead.
     */
    protected void resetDynamicStats() {
        energyStorage.get().setExtraCapacity(0);
        // Note that the item handler can't change size, so we don't need to reset it
    }

    /**
     * Calculate the stats of the turret base using the current addons in the item handler.
     */
    protected void calculateDynamicStats() {
        resetDynamicStats();
        for (AbstractAddon addon : getInstalledAddons()) {
            addon.applyToTurretBase(this);
        }
        energyStorage.get().clampEnergy();
    }

    /**
     * Get a list of all installed addons in the turret base.
     * @return A list of all installed addons
     */
    public List<AbstractAddon> getInstalledAddons() {
        List<AbstractAddon> addons = new ArrayList<>();
        for (int slot = itemHandler.get().ADDON_SLOT_START; slot < itemHandler.get().ADDON_SLOT_START + itemHandler.get().ADDON_SLOT_COUNT; slot++) {
            ItemStack stack = itemHandler.get().getStackInSlot(slot);
            if (stack.getItem() instanceof AbstractAddon addon) {
                addons.add(addon);
            }
        }
        return addons;
    }

    private TurretBaseItemHandler createItemStackHandler() {
        return new TurretBaseItemHandler(stats.getAmmoSlotCount(), stats.getAddonSlotCount()) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                // If an addon, recalculate stats
                if (slot >= ADDON_SLOT_START && slot < ADDON_SLOT_START + ADDON_SLOT_COUNT) {
                    calculateDynamicStats();
                }
                if (level != null) {
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
                }
                super.onContentsChanged(slot);
            }

            @Override
            public void setStackInSlot(int slot, ItemStack stack) {
                // If an addon, recalculate stats
                if (slot >= ADDON_SLOT_START && slot < ADDON_SLOT_START + ADDON_SLOT_COUNT) {
                    calculateDynamicStats();
                }
                super.setStackInSlot(slot, stack);
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                // If an addon and not simulate, recalculate stats
                if (slot >= ADDON_SLOT_START && slot < ADDON_SLOT_START + ADDON_SLOT_COUNT && !simulate) {
                    calculateDynamicStats();
                }
                return super.extractItem(slot, amount, simulate);
            }
        };
    }

    private TurretEnergyStorage createEnergyStorage() {
        return new TurretEnergyStorage(stats.getEnergyCapacity());
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

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData iProbeHitData) {
        iProbeInfo.text(Component.translatable(targetPlayers ? "gui.omtreloaded.simple_turret_base.target_players_true" : "gui.omtreloaded.simple_turret_base.target_players_false"));
    }

    @Override
    public ResourceLocation getID() {
        return ResourceLocation.fromNamespaceAndPath(OMTReloaded.MODID, "turret_base_provider");
    }

    public TurretEnergyStorage getEnergyStorage() {
        return energyStorage.get();
    }

    public IItemHandler getItemHandler() {
        return itemHandler.get();
    }

    public void saveClientData(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putBoolean("targetPlayers", targetPlayers);
        tag.put("items", itemHandler.get().serializeNBT(provider));
        tag.put("energy", energyStorage.get().serializeNBT(provider));
    }

    public void loadClientData(CompoundTag tag, HolderLookup.Provider provider) {
        targetPlayers = tag.getBoolean("targetPlayers");
        itemHandler.get().deserializeNBT(provider, tag.getCompound("items"));
        energyStorage.get().deserializeNBT(provider, tag.get("energy"));
        calculateDynamicStats();
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = super.getUpdateTag(provider);
        saveClientData(tag, provider);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider holders) {
        loadClientData(tag, holders);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookup) {
        CompoundTag tag = pkt.getTag();
        handleUpdateTag(tag, lookup);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        saveClientData(pTag, pRegistries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        loadClientData(pTag, pRegistries);
    }

    public boolean isTargetPlayers() {
        return targetPlayers;
    }

    public void setTargetPlayers(boolean targetPlayers) {
        this.targetPlayers = targetPlayers;
    }
}
