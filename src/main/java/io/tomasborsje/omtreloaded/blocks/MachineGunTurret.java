package io.tomasborsje.omtreloaded.blocks;

import io.tomasborsje.omtreloaded.blockentities.MachineGunTurretEntity;
import io.tomasborsje.omtreloaded.core.AbstractTurret;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MachineGunTurret extends AbstractTurret {
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MachineGunTurretEntity(blockPos, blockState);
    }
}
