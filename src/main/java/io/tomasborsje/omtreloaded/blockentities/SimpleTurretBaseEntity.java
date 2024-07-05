package io.tomasborsje.omtreloaded.blockentities;

import io.tomasborsje.omtreloaded.registration.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.jarjar.nio.util.Lazy;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class SimpleTurretBaseEntity extends BlockEntity {
    private int energy;
    private final Lazy<IEnergyStorage> energyStorage = Lazy.of(this::createEnergyStorage);

    public SimpleTurretBaseEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SIMPLE_TURRET_BASE_ENTITY.get(), pPos, pBlockState);
    }
    public IEnergyStorage getEnergyStorage() {
        return energyStorage.get();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        saveClientData(pTag);
    }

    private void saveClientData(CompoundTag tag) {
        tag.putInt("energy", energy);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        loadClientData(pTag);
    }

    private void loadClientData(CompoundTag tag) {
        if (tag.contains("energy")) {
            energy = tag.getInt("energy");
        }
    }

    private IEnergyStorage createEnergyStorage() {
        return new IEnergyStorage() {
            @Override
            public int receiveEnergy(int i, boolean simulate) {
                if(simulate) {
                    return Math.min(i, 5000000 - energy);
                }
                energy = Math.min(5000000, energy + i);
                setChanged();
                return Math.min(i, 5000000 - energy);
            }

            @Override
            public int extractEnergy(int i, boolean simulate) {
                return 0;
            }

            @Override
            public int getEnergyStored() {
                return energy;
            }

            @Override
            public int getMaxEnergyStored() {
                return 5000000;
            }

            @Override
            public boolean canExtract() {
                return false;
            }

            @Override
            public boolean canReceive() {
                return true;
            }
        };
    }
}
