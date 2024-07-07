package io.tomasborsje.omtreloaded.registration;

import io.tomasborsje.omtreloaded.OMTReloaded;
import io.tomasborsje.omtreloaded.blockentities.MachineGunTurretEntity;
import io.tomasborsje.omtreloaded.blockentities.SimpleTurretBaseEntity;
import io.tomasborsje.omtreloaded.core.AbstractTurretBaseEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, OMTReloaded.MODID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MachineGunTurretEntity>> SIMPLE_TURRET_ENTITY = BLOCK_ENTITIES.register("machine_gun_turret",
            () -> BlockEntityType.Builder.of(MachineGunTurretEntity::new, ModBlocks.MACHINE_GUN_TURRET.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SimpleTurretBaseEntity>> SIMPLE_TURRET_BASE_ENTITY = BLOCK_ENTITIES.register("simple_turret_base",
            () -> BlockEntityType.Builder.of(SimpleTurretBaseEntity::new, ModBlocks.SIMPLE_TURRET_BASE.get()).build(null));
}
