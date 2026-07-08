package com.tomasborsje.omtreloaded.blockentities;

import com.tomasborsje.omtreloaded.OMTReloadedConfig;
import com.tomasborsje.omtreloaded.core.AbstractTurretBlockEntity;
import com.tomasborsje.omtreloaded.core.TurretBaseStats;
import com.tomasborsje.omtreloaded.registry.ModBlockEntityTypes;
import com.tomasborsje.omtreloaded.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ArrowTurretBlockEntity extends AbstractTurretBlockEntity {
    private static final float BULLET_VELOCITY = 3.5f;

    public ArrowTurretBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.ARROW_TURRET_BLOCK_ENTITY.get(), pos, blockState, ModTags.TURRET_ARROW_AMMO_TAG);
    }

    @Override
    protected TurretBaseStats getBaseStats() {
        return new TurretBaseStats(
                OMTReloadedConfig.ARROW_TURRET_ATTACK_COOLDOWN.getAsInt(),
                OMTReloadedConfig.ARROW_TURRET_ACQUISITION_RANGE.getAsInt(),
                OMTReloadedConfig.ARROW_TURRET_ENERGY_DRAIN.getAsInt(),
                OMTReloadedConfig.ARROW_TURRET_ATTACK_DAMAGE.getAsInt());
    }

    @Override
    protected boolean tryAttackTarget(@NotNull Entity target) {
        // Apply damage
        level.playSound(null, this.getBlockPos(), SoundEvents.ARROW_SHOOT, SoundSource.BLOCKS, 1, 1.5f);

        // TODO: This misses targets too far away
        var blockPos = this.getBlockPos().getCenter();
        var facingDir = target.getEyePosition().subtract(blockPos).normalize();
        var arrowSpawn = blockPos.add(facingDir.scale(0.75)); // Move the arrow just outside our hitbox
        var deltaV = facingDir.scale(BULLET_VELOCITY);
        var rot = deltaV.rotation(); // TODO: Calculate arrow look rot from facing direction properly

        // Shoot projectile
        Arrow arrow = new Arrow(level, arrowSpawn.x, arrowSpawn.y, arrowSpawn.z, new ItemStack(Items.ARROW), null);
        arrow.addDeltaMovement(deltaV);
        arrow.forceSetRotation(rot.y, false, rot.x, false);
        level.addFreshEntity(arrow);

        return true;
    }
}
