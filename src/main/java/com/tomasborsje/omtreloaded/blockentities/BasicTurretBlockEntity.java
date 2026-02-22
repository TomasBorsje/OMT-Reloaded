package com.tomasborsje.omtreloaded.blockentities;

import com.tomasborsje.omtreloaded.OMTReloadedConfig;
import com.tomasborsje.omtreloaded.core.AbstractTurretBlockEntity;
import com.tomasborsje.omtreloaded.core.TurretBaseStats;
import com.tomasborsje.omtreloaded.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BasicTurretBlockEntity extends AbstractTurretBlockEntity {
    public BasicTurretBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.BASIC_TURRET_BLOCK_ENTITY.get(), pos, blockState, new TurretBaseStats(OMTReloadedConfig.BASIC_TURRET_ATTACK_COOLDOWN.getAsInt(), 5, 1, 4));
    }

    @Override
    protected boolean tryAttackTarget(@NotNull Entity target) {
        // Apply damage
        var dmg = new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.GENERIC),
                null,
                null,
                null);
        target.invulnerableTime = 0; // Bypass invulnerability ticks
        target.hurtServer((ServerLevel) level, dmg, stats.getDamage());
        target.addDeltaMovement(target.getPosition(0).subtract(this.getBlockPos().getCenter()).normalize().horizontal().add(0, 1, 0).scale(0.3f));
        level.playSound(null, this.getBlockPos(), SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 1, 1.5f);
        return true;
    }
}
