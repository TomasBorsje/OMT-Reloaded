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
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ArrowTurretBlockEntity extends AbstractTurretBlockEntity {
    public ArrowTurretBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.ARROW_TURRET_BLOCK_ENTITY.get(), pos, blockState,
                new TurretBaseStats(OMTReloadedConfig.BASIC_TURRET_ATTACK_COOLDOWN.getAsInt(), 5, 1, 4));
    }

    @Override
    protected boolean tryAttackTarget(@NotNull Entity target) {
        // Apply damage
        level.playSound(null, this.getBlockPos(), SoundEvents.ARROW_SHOOT, SoundSource.BLOCKS, 1, 1.5f);

        var pos = this.getBlockPos().above().getCenter();
        Arrow arrow = new Arrow(level, pos.x, pos.y, pos.z, ItemStack.EMPTY, null);
        arrow.addDeltaMovement(new Vec3(1, 1, 1));
        level.addFreshEntity(arrow);

        return true;
    }
}
