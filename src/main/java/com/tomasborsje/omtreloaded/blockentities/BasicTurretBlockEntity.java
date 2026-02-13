package com.tomasborsje.omtreloaded.blockentities;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.network.TurretAcquireTargetPacket;
import com.tomasborsje.omtreloaded.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BasicTurretBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private Entity targetEntity;
    private int attackCooldown = 60;
    private int attackCooldownRemaining = 0;

    public BasicTurretBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.BASIC_TURRET_BLOCK_ENTITY.get(), pos, blockState);
    }

    public static <T extends BlockEntity> void tickServer(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (!(blockEntity instanceof BasicTurretBlockEntity turret)) {
            return;
        }

        if(turret.attackCooldownRemaining > 0) {
            turret.attackCooldownRemaining--;
        }

        turret.validateTarget();
        if(!turret.hasTarget()) {
            turret.tryAcquireTarget();
        }

        if (turret.hasTarget() && turret.consumeTurretBaseResources(true)) {
            turret.attackTarget();
        }
    }

    public static <T extends BlockEntity> void tickClient(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (!(blockEntity instanceof BasicTurretBlockEntity turret)) {
            return;
        }
    }

    /**
     * Check if our current target is valid, and clear it if it is not.
     */
    protected void validateTarget() {
        if(targetEntity == null) { return; }
        if(!targetEntity.isAlive()) {
            targetEntity = null;
            return;
        }
        if(targetEntity.asLivingEntity() instanceof LivingEntity livingEntity && livingEntity.isDeadOrDying()) {
            targetEntity = null;
            return;
        }
    }

    /**
     * Try to acquire a target.
     */
    protected void tryAcquireTarget() {
        if(level == null) { return; }
        final BlockPos pos = this.getBlockPos();
        var nearestPlayer = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, true);
        if (nearestPlayer != null) {
            this.setTargetEntityServerside(nearestPlayer);
        }
    }

    /**
     * Set the target entity to the given entity, broadcasting the target acquisition to any tracking clients.
     * @param entity The entity to set as our target.
     */
    protected void setTargetEntityServerside(Entity entity) {
        if(entity == null || !(entity.level() instanceof ServerLevel serverLevel)) { return; }
        targetEntity = entity;

        // Update target clientside
        final BlockPos pos = this.getBlockPos();
        final TurretAcquireTargetPacket turretTargetPacket = new TurretAcquireTargetPacket(pos.getX(), pos.getY(), pos.getZ(), entity.getId());
        PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) level, new ChunkPos(pos), turretTargetPacket);
        OMTReloaded.LOGGER.info("Server found new target with ID {} and name {}!", entity.getId(), entity.getName());
    }

    /**
     * Whether this turret has a target.
     * @return Whether this turret has a target.
     */
    protected boolean hasTarget() {
        return targetEntity != null;
    }

    /**
     * Attack the current target, if any.
     */
    protected void attackTarget() {
        if(!consumeTurretBaseResources(false)) { return; }
        if(this.targetEntity == null || level == null) { return; }
        // Apply damage
        var dmg = new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.GENERIC),
                null,
                null,
                null);
        targetEntity.hurtServer((ServerLevel) level, dmg, 1);
        // Set cooldown
        this.attackCooldownRemaining = this.attackCooldown;
    }

    /**
     * Returns true if we can consume the necessary resources to attack with this turret (energy, ammo).
     * @param simulate If true, no actual resources will be consumed.
     * @return True if we can consume the necessary resources to attack with this turret, else false.
     */
    boolean consumeTurretBaseResources(boolean simulate) {
        if (level == null) {
            return false;
        }
        var energyHandler = level.getCapability(Capabilities.Energy.BLOCK, this.getBlockPos().below(), Direction.NORTH);
        if (energyHandler == null) {
            return false;
        }
        OMTReloaded.LOGGER.info("{}", energyHandler.getAmountAsInt());
        try (var tx = Transaction.openRoot()) {
            if (energyHandler.extract(60, tx) == 60) {
                if(!simulate) { tx.commit(); }
                return true;
            }
            return false;
        }
    }

    /**
     * Sets the target entity by entity ID. This is only used client-side to update the target to the server's target.
     * @param entityId The ID of the entity to set as our target.
     */
    public void setTargetByEntityId(int entityId) {
        if (this.level == null) { return; }
        this.targetEntity = this.level.getEntity(entityId);
    }

    // Saving and loading
    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);
    }

    @Override
    public void preRemoveSideEffects(@NonNull BlockPos pos, @NonNull BlockState state) {
        super.preRemoveSideEffects(pos, state);
        // TODO: Drop container items, etc.
    }

    // Rendering and GeckoLib
    public @Nullable Entity getTargetEntity() {
        return targetEntity;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public @NonNull AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}
