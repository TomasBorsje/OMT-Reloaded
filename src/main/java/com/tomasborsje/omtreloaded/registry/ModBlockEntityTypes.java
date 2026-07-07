package com.tomasborsje.omtreloaded.registry;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.blockentities.ArrowTurretBlockEntity;
import com.tomasborsje.omtreloaded.blockentities.BasicTurretBlockEntity;
import com.tomasborsje.omtreloaded.blockentities.TurretBaseBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, OMTReloaded.MODID);

    public static final Supplier<BlockEntityType<TurretBaseBlockEntity>> TURRET_BASE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "turret_base_block_entity",
            // The block entity type.
            () -> new BlockEntityType<>(
                    TurretBaseBlockEntity::new,
                    false,
                    ModBlocks.SIMPLE_TURRET_BASE.get()
            )
    );

    public static final Supplier<BlockEntityType<BasicTurretBlockEntity>> BASIC_TURRET_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "basic_turret",
            // The block entity type.
            () -> new BlockEntityType<>(
                    BasicTurretBlockEntity::new,
                    false,
                    ModBlocks.BASIC_TURRET.get()
            )
    );

    public static final Supplier<BlockEntityType<ArrowTurretBlockEntity>> ARROW_TURRET_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "arrow_turret",
            // The block entity type.
            () -> new BlockEntityType<>(
                    ArrowTurretBlockEntity::new,
                    false,
                    ModBlocks.ARROW_TURRET.get()
            )
    );
}
