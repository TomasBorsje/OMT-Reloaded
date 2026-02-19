package com.tomasborsje.omtreloaded.blockentities;

import com.tomasborsje.omtreloaded.Config;
import com.tomasborsje.omtreloaded.core.AbstractTurretBlockEntity;
import com.tomasborsje.omtreloaded.core.TurretBaseStats;
import com.tomasborsje.omtreloaded.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BasicTurretBlockEntity extends AbstractTurretBlockEntity {
    public BasicTurretBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.BASIC_TURRET_BLOCK_ENTITY.get(), pos, blockState, new TurretBaseStats(Config.BASIC_TURRET_ATTACK_COOLDOWN.getAsInt(), 5));
    }


    @Override
    protected boolean tryAttackTarget(@NotNull Entity target) {
        // Apply damage
        var dmg = new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.GENERIC),
                null,
                null,
                null);
        target.hurtServer((ServerLevel) level, dmg, 1);
        return true;
    }
}
