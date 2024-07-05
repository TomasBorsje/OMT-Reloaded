package io.tomasborsje.omtreloaded.registration;

import io.tomasborsje.omtreloaded.blocks.MachineGunTurret;
import io.tomasborsje.omtreloaded.blocks.SimpleTurretBase;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static io.tomasborsje.omtreloaded.OMTReloaded.MODID;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredHolder<Block, MachineGunTurret> MACHINE_GUN_TURRET = BLOCKS.register("machine_gun_turret", MachineGunTurret::new);
    public static final DeferredHolder<Block, SimpleTurretBase> SIMPLE_TURRET_BASE = BLOCKS.register("simple_turret_base", SimpleTurretBase::new);
}
