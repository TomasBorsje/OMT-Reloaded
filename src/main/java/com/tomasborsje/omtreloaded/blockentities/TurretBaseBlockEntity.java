package com.tomasborsje.omtreloaded.blockentities;

import com.tomasborsje.omtreloaded.registry.ModBlockEntityTypes;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.energy.SimpleEnergyHandler;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class TurretBaseBlockEntity extends BlockEntity implements MenuProvider {
    private final EnergyHandler energyHandler = new SimpleEnergyHandler(50_000);
    private final ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(5);
    private final SimpleContainerData data = new SimpleContainerData(3);

    public TurretBaseBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.TURRET_BASE_BLOCK_ENTITY.get(), pos, blockState);
    }

    public static <T extends BlockEntity> void tickServer(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (!(blockEntity instanceof TurretBaseBlockEntity turretBaseBlockEntity)) { return; }

        // TODO: Detect item changes, etc.
        turretBaseBlockEntity.setChanged();

        try (var tx = Transaction.openRoot()) {
            if(turretBaseBlockEntity.energyHandler.insert(1, tx) == 1) {
                tx.commit();
            }
        }
    }

    public static <T extends BlockEntity> void tickClient(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (!(blockEntity instanceof TurretBaseBlockEntity turretBaseBlockEntity)) { return; }
    }

    // Capabilities
    public @NotNull EnergyHandler getEnergyHandler() {
        return energyHandler;
    }

    // Saving and loading
    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
        inventory.deserialize(input);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);
        inventory.serialize(output);
    }

    @Override
    public void preRemoveSideEffects(@NonNull BlockPos pos, @NonNull BlockState state) {
        super.preRemoveSideEffects(pos, state);
        // TODO: Drop container items, etc.
    }

    public ItemStacksResourceHandler getInventory() {
        return inventory;
    }

    @Override
    public void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer) {
        // TODO: Codec to read/write object from buffer
        buffer.writeInt(energyHandler.getAmountAsInt());
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("blah");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new TurretBaseMenu(containerId, playerInventory, ContainerLevelAccess.create(this.level, this.getBlockPos()), this.getInventory(), this.data);
    }
}
