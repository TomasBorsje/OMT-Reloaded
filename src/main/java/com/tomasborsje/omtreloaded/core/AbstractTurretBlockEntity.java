package com.tomasborsje.omtreloaded.core;

import com.tomasborsje.omtreloaded.network.ServerboundRequestTurretLookAnglePacket;
import com.tomasborsje.omtreloaded.network.ClientboundTurretSetLookAnglePacket;
import com.tomasborsje.omtreloaded.registry.ModTags;
import com.tomasborsje.omtreloaded.util.TurretUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.ResourceHandlerUtil;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

/**
 * The base class for all turret block entities.
 */
public abstract class AbstractTurretBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private final TurretBaseStats stats;
    private Entity targetEntity;
    private int attackCooldownRemaining = 0;
    private boolean clientSynced = false;

    // rendering
    private float turretYaw;
    private float barrelPitch;

    public AbstractTurretBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState blockState, TurretBaseStats stats) {
        super(blockEntityType, pos, blockState);
        this.stats = stats;
    }

    /**
     * Called once per tick on the server.
     */
    protected void tickServer() {
        if(this.attackCooldownRemaining > 0) {
            this.attackCooldownRemaining--;
        }

        // Check upgrades
        applyUpgrades();

        this.validateTarget();

        // Consume energy before we do anything else
        if(!this.consumeTurretEnergy(true)) {
            return;
        }
        this.consumeTurretEnergy(false);

        this.tryAcquireTarget(); // Try look for another target in case someone has come closer, etc.
        if(this.hasTarget()) {
            this.lookAtTarget();
        }

        // Try attack and set cooldown if successful
        if (this.hasTarget() && this.attackCooldownRemaining == 0 && this.consumeTurretAmmo(true) && this.tryAttackTarget(this.targetEntity)) {
            // We attacked, consume resources and set cooldown
            this.consumeTurretAmmo(false);
            this.attackCooldownRemaining = this.stats.attackCooldown();
        }
    }

    /**
     * Calculate the look angles for the turret's current target and broadcast them to any clients tracking this chunk.
     */
    private void lookAtTarget() {
        if(targetEntity == null || this.level instanceof ClientLevel) { return; }
        Vec3 targetPos = targetEntity.getEyePosition();
        BlockPos blockPos = getBlockPos();
        Vec3 turretPos = getBlockPos().getCenter();

        Vec3 lookingDir = targetPos.subtract(turretPos).normalize();
        Vec3 turretHorizontalFeet = new Vec3(targetPos.x, turretPos.y, targetPos.z).subtract(turretPos);

        turretYaw = (float) (-Math.atan2(turretPos.z-targetPos.z, turretPos.x - targetPos.x) + Math.toRadians(90));
        barrelPitch = (float) Math.acos(turretHorizontalFeet.normalize().dot(lookingDir));
        if(targetPos.y < turretPos.y) { barrelPitch *= -1; }

        // Send packet to all clients tracking
        var packet = new ClientboundTurretSetLookAnglePacket(blockPos.getX(), blockPos.getY(), blockPos.getZ(), getTurretYaw(), getBarrelPitch());
        PacketDistributor.sendToPlayersTrackingChunk((ServerLevel)level, new ChunkPos(getBlockPos()), packet);
    }

    protected void applyUpgrades() {
        if(level == null) { return; }
        BlockPos basePos = getBlockPos().below();
        if(level.getBlockEntity(basePos) instanceof AbstractTurretBaseBlockEntity base) {
            var upgrades = base.getActiveTurretUpgrades();
            stats.resetToBaseStats();
            stats.applyUpgrades(upgrades);
        }
    }

    /**
     * Called once per tick on the client.
     */
    protected void tickClient() {
        if(!clientSynced) {
            // Request target once
            final BlockPos pos = this.getBlockPos();
            ClientPacketDistributor.sendToServer(new ServerboundRequestTurretLookAnglePacket(pos.getX(), pos.getY(), pos.getZ()));
            clientSynced = true;
        }
    }

    /**
     * Check if our current target is valid, and clear it if it is not.
     */
    protected void validateTarget() {
        if(targetEntity == null) { return; }
        final BlockPos pos = this.getBlockPos();
        if(!TurretUtil.WithinSquareDistance(pos, targetEntity.getEyePosition(), stats.targetAcquisitionRange()) || !targetEntity.isAlive()) {
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
        var possibleTargets = level.getEntities(
                EntityTypeTest.forClass(LivingEntity.class),
                new AABB(this.getBlockPos()).inflate(stats.targetAcquisitionRange()),
                livingEntity -> !livingEntity.isDeadOrDying());
        possibleTargets.sort((e1, e2) -> (int) (e1.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) - e2.distanceToSqr(pos.getX(), pos.getY(), pos.getZ())));
        if (!possibleTargets.isEmpty()) {
            this.targetEntity = possibleTargets.getFirst();
        }
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
    abstract protected boolean tryAttackTarget(@NotNull Entity target);

    /**
     * Returns true if we can consume the necessary resources to attack with this turret (energy, ammo).
     * @param simulate If true, no actual resources will be consumed.
     * @return True if we can consume the necessary resources to attack with this turret, else false.
     */
    boolean consumeTurretEnergy(boolean simulate) {
        if (level == null) {
            return false;
        }
        var energyHandler = level.getCapability(Capabilities.Energy.BLOCK, this.getBlockPos().below(), Direction.NORTH);
        if (energyHandler == null) {
            return false;
        }
        // Try to consume energy and ammo
        try (var tx = Transaction.openRoot()) {
            var extractedEnergy = energyHandler.extract(stats.getEnergyPerTickConsumption(), tx);
            if (extractedEnergy == stats.getEnergyPerTickConsumption()) {
                if(!simulate) {
                    this.setChanged();
                    tx.commit();
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if we can consume the necessary resources to attack with this turret (energy, ammo).
     * @param simulate If true, no actual resources will be consumed.
     * @return True if we can consume the necessary resources to attack with this turret, else false.
     */
    boolean consumeTurretAmmo(boolean simulate) {
        if (level == null) {
            return false;
        }
        var inventory = level.getCapability(Capabilities.Item.BLOCK, this.getBlockPos().below(), Direction.NORTH);
        if (inventory == null) {
            return false;
        }
        // Try to consume energy and ammo
        try (var tx = Transaction.openRoot()) {
            var extractedAmmo = ResourceHandlerUtil.extractFirst(inventory, res -> res.is(ModTags.LIGHT_TURRET_AMMO_TAG), 1, tx);
            if(extractedAmmo == null) { return false; }
            if (!extractedAmmo.isEmpty()) {
                if(!simulate) {
                    this.setChanged();
                    tx.commit();
                }
                return true;
            }
        }
        return false;
    }

    // Saving and loading
    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
        turretYaw = input.getFloatOr("turret_yaw", 0);
        barrelPitch = input.getFloatOr("barrel_pitch", 0);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);
        output.putFloat("turret_yaw", turretYaw);
        output.putFloat("barrel_pitch", barrelPitch);
    }

    @Override
    public void preRemoveSideEffects(@NonNull BlockPos pos, @NonNull BlockState state) {
        super.preRemoveSideEffects(pos, state);
        // TODO: Drop container items, etc.
    }

    // Rendering and GeckoLib
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) { }

    @Override
    public @NonNull AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    public float getTurretYaw() {
        return turretYaw;
    }

    public float getBarrelPitch() {
        return barrelPitch;
    }

    public void setTurretYaw(float turretYaw) {
        this.turretYaw = turretYaw;
    }

    public void setBarrelPitch(float barrelPitch) {
        this.barrelPitch = barrelPitch;
    }
}
