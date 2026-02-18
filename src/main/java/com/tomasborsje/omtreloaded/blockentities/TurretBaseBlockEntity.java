package com.tomasborsje.omtreloaded.blockentities;

import com.tomasborsje.omtreloaded.core.AbstractTurretBaseBlockEntity;
import com.tomasborsje.omtreloaded.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TurretBaseBlockEntity extends AbstractTurretBaseBlockEntity {
    public TurretBaseBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.TURRET_BASE_BLOCK_ENTITY.get(), pos, blockState, "menu.title.omtreloaded.basicturretbasemenu");
    }
}
