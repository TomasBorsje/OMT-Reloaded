package com.tomasborsje.omtreloaded.core;

import com.tomasborsje.omtreloaded.blockentities.TurretBaseBlockEntity;
import com.tomasborsje.omtreloaded.ui.TurretBaseMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * The base class for all 'turret base' block entities.
 */
public abstract class AbstractTurretBaseBlockEntity extends BlockEntity implements MenuProvider {
    private final EnergyHandler energyHandler = new SimpleEnergyHandler(50_000);
    private final ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(5);
    private final SimpleContainerData data = new SimpleContainerData(3);
    private final String menuLabelKey;

    public AbstractTurretBaseBlockEntity(BlockEntityType<? extends TurretBaseBlockEntity> type, BlockPos pos, BlockState blockState, String menuLabelKey) {
        super(type, pos, blockState);
        this.menuLabelKey = menuLabelKey;
    }

    protected void tickServer() {
        // TODO: Detect item changes, etc.
        this.setChanged();

        try (var tx = Transaction.openRoot()) {
            if(this.energyHandler.insert(70, tx) > 0) {
                tx.commit();
            }
        }
    }

    protected void tickClient() { }

    // Capabilities
    public @NotNull EnergyHandler getEnergyHandler() {
        return energyHandler;
    }

    public @NotNull ItemStacksResourceHandler getInventory() {
        return inventory;
    }

    public @NotNull List<TurretUpgrade> getActiveTurretUpgrades() {
        List<TurretUpgrade> upgrades = new java.util.ArrayList<TurretUpgrade>();
        for(int i = 0; i < inventory.size(); i++) {
            var res = inventory.getResource(i);
            if(res.getItem() instanceof TurretUpgrade upgrade) {
                upgrades.add(upgrade);
            }
        }
        return upgrades;
    }

    // Saving and loading
    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
        final var energy = input.getInt("energy");
        energy.ifPresent(integer -> energyHandler.insert(integer, Transaction.openRoot()));
        inventory.deserialize(input);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("energy", energyHandler.getAmountAsInt());
        inventory.serialize(output);
    }

    @Override
    public void preRemoveSideEffects(@NonNull BlockPos pos, @NonNull BlockState state) {
        super.preRemoveSideEffects(pos, state);
        // TODO: Drop container items, etc.
    }

    @Override
    public void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer) {
        // TODO: Codec to read/write object from buffer
        buffer.writeInt(energyHandler.getAmountAsInt());
    }

    // Menu
    @Override
    public Component getDisplayName() {
        return Component.translatable(menuLabelKey);
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new TurretBaseMenu(containerId, playerInventory, ContainerLevelAccess.create(this.level, this.getBlockPos()), this.getInventory(), this.data);
    }
}
