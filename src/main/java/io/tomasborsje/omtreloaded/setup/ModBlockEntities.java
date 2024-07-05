package io.tomasborsje.omtreloaded.setup;

import io.tomasborsje.omtreloaded.OMTReloaded;
import io.tomasborsje.omtreloaded.blockentities.MachineGunTurretEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, OMTReloaded.MODID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MachineGunTurretEntity>> SIMPLE_TURRET_ENTITY = BLOCK_ENTITIES.register("simple_turret",
            () -> BlockEntityType.Builder.of(MachineGunTurretEntity::new, ModBlocks.MACHINE_GUN_TURRET.get()).build(null));
}
