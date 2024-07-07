package io.tomasborsje.omtreloaded.blockentities;

import io.tomasborsje.omtreloaded.ModConfig;
import io.tomasborsje.omtreloaded.core.AbstractTurretBaseEntity;
import io.tomasborsje.omtreloaded.registration.ModBlockEntities;
import mcjty.theoneprobe.api.IProbeInfoAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SimpleTurretBaseEntity extends AbstractTurretBaseEntity {
    public SimpleTurretBaseEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SIMPLE_TURRET_BASE_ENTITY.get(), pPos, pBlockState, ModConfig.simpleTurretBaseStats);
    }
}
