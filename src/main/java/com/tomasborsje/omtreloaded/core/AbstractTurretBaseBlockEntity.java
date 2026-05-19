package com.tomasborsje.omtreloaded.core;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.blockentities.TurretBaseBlockEntity;
import com.tomasborsje.omtreloaded.ui.TurretBaseMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.players.NameAndId;
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
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * The base class for all 'turret base' block entities.
 */
public abstract class AbstractTurretBaseBlockEntity extends BlockEntity implements MenuProvider {
    protected final List<AbstractTurretBlockEntity> connectedTurrets = new ArrayList<AbstractTurretBlockEntity>();
    protected final SimpleEnergyHandler energyHandler = new SimpleEnergyHandler(50_000);
    protected final ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(5);
    protected final SimpleContainerData data = new SimpleContainerData(3);
    protected final String menuLabelKey;
    protected @Nullable UUID ownerUuid = null;
    protected @Nullable String ownerUsername = null;

    public AbstractTurretBaseBlockEntity(BlockEntityType<? extends TurretBaseBlockEntity> type, BlockPos pos, BlockState blockState, String menuLabelKey) {
        super(type, pos, blockState);
        this.menuLabelKey = menuLabelKey;
    }

    protected void tickServer() {
        // TODO: Detect item changes, etc.
        incrementEnergy();
    }

    private void incrementEnergy() {
        // Check if we're generating power
        int energyToGenerate = 0;
        for(int i = 0; i < inventory.size(); i++) {
            ItemResource item = inventory.getResource(i);
            if(item.getItem() instanceof TurretSolarPanelUpgrade solarPanelUpgrade) {
                energyToGenerate += solarPanelUpgrade.getRfPerTickGenerated();
            }
        }

        if(energyToGenerate > 0) {
            try (var tx = Transaction.openRoot()) {
                if(this.energyHandler.insert(energyToGenerate, tx) > 0) {
                    tx.commit();
                }
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
        energyHandler.deserialize(input);
        inventory.deserialize(input);
        var ownerUuidOpt = input.getString("ownerUuid");
        if(ownerUuidOpt.isPresent()) {
            try {
                this.ownerUuid = UUID.fromString(ownerUuidOpt.get());
            } catch(Exception ignored) { }
        }
        ownerUsername = input.getStringOr("ownerUsername", null);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);
        energyHandler.serialize(output);
        inventory.serialize(output);
        if(ownerUuid != null) {
            output.putString("ownerUuid", ownerUuid.toString());
        }
        if (ownerUsername != null) {
            output.putString("ownerUsername", ownerUsername);
        }
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

    public void setOwnerDetails(NameAndId ownerDetails) {
        this.ownerUsername = ownerDetails.name();
        this.ownerUuid = ownerDetails.id();
        OMTReloaded.LOGGER.info("Placed block with owner {}", this.ownerUsername);
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
