package com.tomasborsje.omtreloaded.blocks;

import com.tomasborsje.omtreloaded.blockentities.BasicTurretBlockEntity;
import com.tomasborsje.omtreloaded.core.AbstractTurretBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BasicTurretBlock extends AbstractTurretBlock {
    public BasicTurretBlock(BlockBehaviour.Properties properties) {
        super(properties.noOcclusion(), BasicTurretBlockEntity::new);
    }
}
