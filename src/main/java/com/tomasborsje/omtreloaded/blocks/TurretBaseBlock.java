package com.tomasborsje.omtreloaded.blocks;

import com.tomasborsje.omtreloaded.blockentities.TurretBaseBlockEntity;
import com.tomasborsje.omtreloaded.core.AbstractTurretBaseBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class TurretBaseBlock extends AbstractTurretBaseBlock {
    public TurretBaseBlock(BlockBehaviour.Properties properties) {
        super(properties, TurretBaseBlockEntity::new);
    }
}
