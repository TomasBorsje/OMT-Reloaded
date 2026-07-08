package com.tomasborsje.omtreloaded.core;

import com.tomasborsje.omtreloaded.network.ClientboundTurretSetLookAnglePacket;
import com.tomasborsje.omtreloaded.network.ServerboundRequestTurretLookAnglePacket;
import com.tomasborsje.omtreloaded.util.TurretUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.Item;
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

import java.util.Random;
import java.util.function.Predicate;

/**
 * The base class for all turret block entities.
 */
public abstract class AbstractTurretBlockEntity extends BlockEntity implements GeoBlockEntity {
    protected static final Predicate<LivingEntity> TARGET_HOSTILE_MOBS = (livingEntity -> livingEntity instanceof Enemy && !livingEntity.isDeadOrDying());

    protected static final int LINE_OF_SIGHT_CHECKS_PER_BLOCK = 10;
    protected static final int RANDOM_LOOK_COOLDOWN = 200;
    private static final Random lookRandom = new Random();

    protected final TurretBaseStats stats;
    private final TagKey<Item> validAmmoTag;
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private Entity targetEntity;
    private int attackCooldownRemaining = 0;
    private boolean clientSynced = false;

    // Random look
    private int randomLookCooldown = 20;
    private int randomMoveDuration;
    private int randomLookDelay;
    private int randomLookProgress;
    private float randomPreviousBarrelPitch;
    private float randomPreviousTurretYaw;
    private float randomBarrelPitchTarget;
    private float randomTurretYawTarget;

    // rendering
    private float turretYaw;
    private float barrelPitch;

    public AbstractTurretBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState blockState, TagKey<Item> validAmmoTag) {
        super(blockEntityType, pos, blockState);
        this.validAmmoTag = validAmmoTag;
        this.stats = getBaseStats();
    }

    /**
     * Called once per tick on the server.
     */
    protected void tickServer() {
        if (this.attackCooldownRemaining > 0) {
            this.attackCooldownRemaining--;
        }

        // Check upgrades
        applyUpgrades();

        this.validateTarget();

        // Consume energy before we do anything else
        if (!this.consumeTurretEnergy(true)) {
            return;
        }
        this.consumeTurretEnergy(false);

        this.tryAcquireTarget(); // Try look for another target in case someone has come closer, etc.
        if (this.hasTarget()) {
            this.lookAtTarget();

            // Try attack and set cooldown if successful
            if (this.attackCooldownRemaining == 0 && this.consumeTurretAmmo(true) && this.tryAttackTarget(this.targetEntity)) {
                // We attacked, consume resources and set cooldown
                this.consumeTurretAmmo(false);
                this.attackCooldownRemaining = this.stats.attackCooldown();
            }
        }
    }

    /**
     * Calculate the look angles for the turret's current target and broadcast them to any clients tracking this chunk.
     */
    private void lookAtTarget() {
        if (targetEntity == null || this.level instanceof ClientLevel) {
            return;
        }
        Vec3 targetPos = targetEntity.getEyePosition();
        Vec3 turretPos = getBlockPos().getCenter();

        Vec3 lookingDir = targetPos.subtract(turretPos).normalize();
        Vec3 turretHorizontalFeet = new Vec3(targetPos.x, turretPos.y, targetPos.z).subtract(turretPos);

        turretYaw = (float) (-Math.atan2(turretPos.z - targetPos.z, turretPos.x - targetPos.x) + Math.toRadians(90));
        barrelPitch = (float) Math.acos(turretHorizontalFeet.normalize().dot(lookingDir));
        if (targetPos.y < turretPos.y) {
            barrelPitch *= -1;
        }

        sendLookUpdate();
    }

    /**
     * Send a clientbound packet to set the turret look angle to every client tracking this chunk.
     */
    private void sendLookUpdate() {
        // Send packet to all clients tracking
        BlockPos blockPos = getBlockPos();
        var packet = new ClientboundTurretSetLookAnglePacket(blockPos.getX(), blockPos.getY(), blockPos.getZ(), getTurretYaw(), getBarrelPitch());
        PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) level, new ChunkPos(getBlockPos()), packet);
    }

    protected abstract TurretBaseStats getBaseStats();

    protected void applyUpgrades() {
        if (level == null) {
            return;
        }
        BlockPos basePos = getBlockPos().below();
        if (level.getBlockEntity(basePos) instanceof AbstractTurretBaseBlockEntity base) {
            var upgrades = base.getActiveTurretUpgrades();
            stats.resetToBaseStats();
            stats.applyUpgrades(upgrades);
        }
    }

    /**
     * Called once per tick on the client.
     */
    protected void tickClient() {
        if (!clientSynced) {
            // Request target once
            final BlockPos pos = this.getBlockPos();
            ClientPacketDistributor.sendToServer(new ServerboundRequestTurretLookAnglePacket(pos.getX(), pos.getY(), pos.getZ()));
            clientSynced = true;
        }
        // Look around randomly if we haven't targeted anything in a while
        if(randomLookCooldown > 0) {
            randomLookCooldown--;
        }
        if(randomLookCooldown == 0) {
            // If we're about to start looking at something
            if(randomLookProgress == 0) {
                randomPreviousBarrelPitch = barrelPitch;
                randomPreviousTurretYaw = turretYaw;
                randomTurretYawTarget = turretYaw + (float) Math.toRadians(lookRandom.nextInt(-135, 135+1));
                randomBarrelPitchTarget = (float) Math.toRadians(lookRandom.nextInt(-10, 10+1));
                randomMoveDuration = lookRandom.nextInt(15, 60);
                randomLookDelay = lookRandom.nextInt(5, 150);
                randomLookProgress++;
            }
            else if (randomLookProgress < randomMoveDuration) {
                // Lerp towards the target
                barrelPitch = Mth.lerp((float) randomLookProgress / randomMoveDuration, randomPreviousBarrelPitch, randomBarrelPitchTarget);
                turretYaw = Mth.lerp((float) randomLookProgress / randomMoveDuration, randomPreviousTurretYaw, randomTurretYawTarget);
                randomLookProgress++;
            }
            else if (randomLookProgress >= randomMoveDuration + randomLookDelay) {
                // After duration + delay ticks, reset and look somewhere else
                randomLookProgress = 0;
            }
            else {
                randomLookProgress++;
            }
        }
    }

    /**
     * Check if our current target is valid, and clear it if it is not.
     */
    protected void validateTarget() {
        if (targetEntity == null) {
            return;
        }
        final BlockPos pos = this.getBlockPos();
        if (!TurretUtil.WithinSquareDistance(pos, targetEntity.getEyePosition(), stats.targetAcquisitionRange()) || !targetEntity.isAlive()) {
            targetEntity = null;
            return;
        }

        // Stop targeting if we can't see the target
        if (!canSeeEntity(targetEntity)) {
            targetEntity = null;
            return;
        }

        if (targetEntity.asLivingEntity() instanceof LivingEntity livingEntity && livingEntity.isDeadOrDying()) {
            targetEntity = null;
        }
    }

    /**
     * Try to acquire a target.
     */
    protected void tryAcquireTarget() {
        if (level == null) {
            return;
        }
        final BlockPos pos = this.getBlockPos();
        var possibleTargets = level.getEntities(
                EntityTypeTest.forClass(LivingEntity.class),
                new AABB(this.getBlockPos()).inflate(stats.targetAcquisitionRange()),
                TARGET_HOSTILE_MOBS);
        possibleTargets.sort((e1, e2) -> (int) (e1.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) - e2.distanceToSqr(pos.getX(), pos.getY(), pos.getZ())));
        for (LivingEntity possibleTarget : possibleTargets) {
            // Check LOS
            if (canSeeEntity(possibleTarget)) {
                targetEntity = possibleTarget;
                break;
            }
        }
    }

    /**
     * Performs a line of sight check to the other entity, returning true if it can be seen.
     * @param other The entity to check visibility of
     * @return True if we can see the given entity
     */
    protected boolean canSeeEntity(Entity other) {
        if (level == null) {
            return false;
        }
        final var pos = this.getBlockPos();
        var start = new Vec3(pos);
        var target = other.position();
        int checks = (int)(start.distanceTo(target) * LINE_OF_SIGHT_CHECKS_PER_BLOCK);
        for (int i = 0; i < checks; i++) {
            // Get pos at X % of the way
            var checkPos = start.lerp(target, i / (double)checks);
            var checkBlockPos = new BlockPos((int) checkPos.x, (int) checkPos.y, (int) checkPos.z);

            if (!checkBlockPos.equals(pos) && !level.getBlockState(BlockPos.containing(checkPos)).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Whether this turret has a target.
     *
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
     *
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
                if (!simulate) {
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
     *
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
            var extractedAmmo = ResourceHandlerUtil.extractFirst(inventory, res -> res.is(validAmmoTag), 1, tx);
            if (extractedAmmo == null) {
                return false;
            }
            if (!extractedAmmo.isEmpty()) {
                if (!simulate) {
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
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

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

    public void resetRandomLookCooldown() {
        this.randomLookCooldown = RANDOM_LOOK_COOLDOWN;
    }
}
