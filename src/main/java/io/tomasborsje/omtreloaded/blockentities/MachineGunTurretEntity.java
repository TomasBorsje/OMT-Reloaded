package io.tomasborsje.omtreloaded.blockentities;

import io.tomasborsje.omtreloaded.core.AbstractTurretEntity;
import io.tomasborsje.omtreloaded.core.TurretStats;
import io.tomasborsje.omtreloaded.registration.ModBlockEntities;
import io.tomasborsje.omtreloaded.registration.ModDamageTypes;
import io.tomasborsje.omtreloaded.registration.ModSoundEvents;
import io.tomasborsje.omtreloaded.util.TurretUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MachineGunTurretEntity extends AbstractTurretEntity {
    private static final TurretStats STATS = new TurretStats(10, 2, 4);
    public MachineGunTurretEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SIMPLE_TURRET_ENTITY.get(), pos, state, STATS);
    }
    @Override
    protected void shootEntity(LivingEntity entity) {
        if(level == null) return;

        // Draw line of 20 particles from the source to the target
        Vec3 sourcePos = Vec3.atCenterOf(this.worldPosition);
        Vec3 targetPos = entity.position().add(0, entity.getEyeHeight(), 0);
        Vec3 direction = targetPos.subtract(sourcePos).normalize();;
        Vec3 offset = targetPos.subtract(sourcePos);
        sourcePos = sourcePos.add(direction.scale(0.45)); // sqrt(5/16**2 + 5/16**2) = 0.44, stops us from seeing through missing block corners
        for(int i = 0; i < 20; i++) {
            Vec3 pos = sourcePos.add(offset.scale((i + 1) / 20.0));
            ((ServerLevel)this.level).sendParticles(ParticleTypes.CRIT, pos.x, pos.y, pos.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }

        DamageSource dmgSource = new DamageSource(this.level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ModDamageTypes.TURRET_FIRE));
        TurretUtils.HurtEntityWithKnockbackRatio(entity, dmgSource, getCalculatedDamage(), direction.reverse(), 0.1f);

        // Play machine gun turret fire sound to all players nearby
        level.playSound(null, worldPosition, ModSoundEvents.MACHINE_GUN_TURRET_FIRE.get(), net.minecraft.sounds.SoundSource.BLOCKS, 1.0f, 1.0f);
    }
}
