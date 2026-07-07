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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ArrowTurretBlockEntity extends AbstractTurretBlockEntity {
    private static final float BULLET_VELOCITY = 1f;

    public ArrowTurretBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.ARROW_TURRET_BLOCK_ENTITY.get(), pos, blockState,
                new TurretBaseStats(OMTReloadedConfig.BASIC_TURRET_ATTACK_COOLDOWN.getAsInt(), 5, 1, 4));
    }

    @Override
    protected boolean tryAttackTarget(@NotNull Entity target) {
        // Apply damage
        level.playSound(null, this.getBlockPos(), SoundEvents.ARROW_SHOOT, SoundSource.BLOCKS, 1, 1.5f);

        var blockPos = this.getBlockPos().getCenter();
        var facingDir = target.getEyePosition().subtract(blockPos).normalize();
        var arrowSpawn = blockPos.add(facingDir.scale(0.75)); // Move the arrow just outside our hitbox
        var deltaV = facingDir.scale(BULLET_VELOCITY);
        var rot = deltaV.rotation();

        // Shoot projectile
        Arrow arrow = new Arrow(level, arrowSpawn.x, arrowSpawn.y, arrowSpawn.z, new ItemStack(Items.ARROW), null);
        arrow.addDeltaMovement(deltaV);
        arrow.forceSetRotation(rot.y, false, rot.x, false);
        level.addFreshEntity(arrow);

        return true;
    }
}
