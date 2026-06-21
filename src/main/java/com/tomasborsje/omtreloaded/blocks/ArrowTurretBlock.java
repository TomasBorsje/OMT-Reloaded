package com.tomasborsje.omtreloaded.blocks;

import com.tomasborsje.omtreloaded.blockentities.ArrowTurretBlockEntity;
import com.tomasborsje.omtreloaded.blockentities.BasicTurretBlockEntity;
import com.tomasborsje.omtreloaded.core.AbstractTurretBlock;

public class ArrowTurretBlock extends AbstractTurretBlock {
    public ArrowTurretBlock(Properties properties) {
        super(properties.noOcclusion(), ArrowTurretBlockEntity::new);
    }
}
