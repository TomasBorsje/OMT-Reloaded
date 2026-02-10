package com.tomasborsje.omtreloaded.registry;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.blocks.TurretBaseBlock;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(OMTReloaded.MODID);

    public static final DeferredBlock<TurretBaseBlock> TURRET_BASE = BLOCKS.registerBlock("turret_base", TurretBaseBlock::new);
}
