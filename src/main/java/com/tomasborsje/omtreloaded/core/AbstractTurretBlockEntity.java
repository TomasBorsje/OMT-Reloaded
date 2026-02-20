package com.tomasborsje.omtreloaded.core;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.network.ServerboundRequestTurretTargetPacket;
import com.tomasborsje.omtreloaded.network.ClientboundTurretSetTargetPacket;
import com.tomasborsje.omtreloaded.registry.ModItems;
import com.tomasborsje.omtreloaded.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.ResourceHandlerUtil;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

/**
 * The base class for all turret block entities.
 */
public abstract class AbstractTurretBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private final TurretBaseStats baseStats;
    private Entity targetEntity;
    private int attackCooldownRemaining = 0;
    private boolean clientSynced = false;

    public AbstractTurretBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState blockState, TurretBaseStats stats) {
        super(blockEntityType, pos, blockState);
        this.baseStats = stats;
    }

    /**
     * Called once per tick on the server.
     */
    protected void tickServer() {
        if(this.attackCooldownRemaining > 0) {
            this.attackCooldownRemaining--;
        }

        // TODO: Send current target packet upon client joining server
        this.validateTarget();
        if(!this.hasTarget()) {
            this.tryAcquireTarget();
        }

        // Try attack and set cooldown if successful
        if (this.hasTarget() && this.consumeTurretBaseResources(true) && this.tryAttackTarget(this.targetEntity)) {
            // We attacked, consume resources and set cooldown
            this.consumeTurretBaseResources(false);
            this.attackCooldownRemaining = this.baseStats.attackCooldown();
        }
    }

    /**
     * Called once per tick on the client.
     */
    protected void tickClient() {
        if(!clientSynced) {
            // Request target once
            final BlockPos pos = this.getBlockPos();
            ClientPacketDistributor.sendToServer(new ServerboundRequestTurretTargetPacket(pos.getX(), pos.getY(), pos.getZ()));
            clientSynced = true;
        }
    }

    /**
     * Check if our current target is valid, and clear it if it is not.
     */
    protected void validateTarget() {
        if(targetEntity == null) { return; }
        final BlockPos pos = this.getBlockPos();
        if(targetEntity.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) > Math.pow(this.baseStats.targetAcquisitionRange(), 2) || !targetEntity.isAlive()) {
            clearTarget();
            return;
        }
        if(targetEntity.asLivingEntity() instanceof LivingEntity livingEntity && livingEntity.isDeadOrDying()) {
            clearTarget();
            return;
        }
    }

    protected void clearTarget() {
        targetEntity = null;
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
        final ClientboundTurretSetTargetPacket turretTargetPacket = new ClientboundTurretSetTargetPacket(pos.getX(), pos.getY(), pos.getZ(), entity.getId());
        PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) level, new ChunkPos(pos), turretTargetPacket);
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
    boolean consumeTurretBaseResources(boolean simulate) {
        if (level == null) {
            return false;
        }
        var energyHandler = level.getCapability(Capabilities.Energy.BLOCK, this.getBlockPos().below(), Direction.NORTH);
        var inventory = level.getCapability(Capabilities.Item.BLOCK, this.getBlockPos().below(), Direction.NORTH);
        if (energyHandler == null || inventory == null) {
            return false;
        }
        // Try to consume energy and ammo
        try (var tx = Transaction.openRoot()) {
            var extractedAmmo = ResourceHandlerUtil.extractFirst(inventory, res -> res.is(ModTags.LIGHT_TURRET_AMMO_TAG), 1, tx);
            var extractedEnergy = energyHandler.extract(60, tx);
            if(extractedAmmo == null) { return false; }
            if (extractedEnergy == 60 && !extractedAmmo.isEmpty()) {
                if(!simulate) {
                    tx.commit();
                }
                return true;
            }
        }
        return false;
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
