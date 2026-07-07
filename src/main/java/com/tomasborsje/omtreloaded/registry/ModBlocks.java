package com.tomasborsje.omtreloaded.registry;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.blocks.ArrowTurretBlock;
import com.tomasborsje.omtreloaded.blocks.BasicTurretBlock;
import com.tomasborsje.omtreloaded.blocks.SimpleTurretBaseBlock;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(OMTReloaded.MODID);

    public static final DeferredBlock<SimpleTurretBaseBlock> SIMPLE_TURRET_BASE = BLOCKS.registerBlock("simple_turret_base", SimpleTurretBaseBlock::new);
    public static final DeferredBlock<BasicTurretBlock> BASIC_TURRET = BLOCKS.registerBlock("basic_turret", BasicTurretBlock::new);
    public static final DeferredBlock<ArrowTurretBlock> ARROW_TURRET = BLOCKS.registerBlock("arrow_turret", ArrowTurretBlock::new);

}
