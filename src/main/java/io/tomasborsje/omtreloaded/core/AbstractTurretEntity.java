package io.tomasborsje.omtreloaded.core;

import io.tomasborsje.omtreloaded.blockentities.SimpleTurretBaseEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Optional;

public abstract class AbstractTurretEntity extends BlockEntity implements GeoBlockEntity {
    private final static int TICKS_PER_TARGETING_CHECK = 5;
    private final TurretStats turretStats;
    private final AnimatableInstanceCache animCache = GeckoLibUtil.createInstanceCache(this);
    private LivingEntity target;
    private int ticks = 0;
    private float yRotation = 0;
    private float xRotation = 0;
    private float prevYRotation = 0;
    private float prevXRotation = 0;

    public AbstractTurretEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state, TurretStats turretStats) {
        super(blockEntityType, pos, state);
        this.turretStats = turretStats;
    }

    public void tickServer() {
        if (level == null) return;

        if (checkShouldBreak()) return;

        // If rotations are different, update prevRotation
        boolean sendUpdate = false;
        if (yRotation != prevYRotation) {
            prevYRotation = yRotation;
            sendUpdate = true;
        }
        if (xRotation != prevXRotation) {
            prevXRotation = xRotation;
            sendUpdate = true;
        }

        // If the turret base is not powered, do nothing
        if (!turretBaseIsPowered()) {
            if (sendUpdate) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
            return;
        }

        // Get all nearby entities every TICKS_PER_TARGETING_CHECK ticks, and try to shoot them if specified to
        ticks++;
        if (ticks % TICKS_PER_TARGETING_CHECK == 0) {
            // Get all nearby entities within TARGETING_RANGE blocks that we should target
            AABB aabb = new AABB(worldPosition).inflate(turretStats.getTargetRange());
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, aabb, this::shouldTargetEntity);

            // Sort by distance squared to the turret
            Vec3 centerPos = Vec3.atCenterOf(this.worldPosition);
            entities.sort((e1, e2) -> {
                double d1 = e1.position().distanceToSqr(centerPos);
                double d2 = e2.position().distanceToSqr(centerPos);
                return Double.compare(d1, d2);
            });

            // Check for targets in line of sight until we find one
            boolean foundTarget = false;
            for (LivingEntity entity : entities) {
                if (canSeeEntity(entity)) {
                    target = entity;
                    foundTarget = true;
                    sendUpdate = true;

                    // Calculate the y rotation to the target, 0 is north, 90 is east, 180 is south, and -90 is west
                    Vec3 targetPos = entity.position().add(0, entity.getEyeHeight(), 0);
                    Vec3 direction = targetPos.subtract(centerPos).normalize();
                    yRotation = (float) Math.toDegrees(Math.atan2(direction.x, direction.z));

                    // Adjust the yRotation to fit the desired range
                    if (yRotation < 0) {
                        yRotation += 360;
                    }

                    // Convert the rotation to have 0 as North and 180 as South
                    yRotation = (yRotation + 180) % 360;

                    // Calculate the x rotation to the target, 90 is up, -90 is down
                    xRotation = (float) Math.toDegrees(Math.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)));

                    // Adjust the xRotation to fit the desired range
                    if (xRotation < -90) {
                        xRotation += 180;
                    } else if (xRotation > 90) {
                        xRotation -= 180;
                    }

                    // Optional: Normalize to the range of -90 to 90 if necessary
                    xRotation = Math.max(-90, Math.min(90, xRotation));
                    break;
                }
            }
            // If false, clear target
            if (!foundTarget) {
                target = null;
            }
        }
        // Every TicksPerShot ticks, shoot the target if we have one
        if (ticks % turretStats.getTicksPerShot() == 0) {
            if (target != null) {
                // Try to consume ammo from our turret base and shoot
                Optional<SimpleTurretBaseEntity> turretBase = getTurretBase();
                if (turretBase.isPresent() && turretBase.get().tryConsumeAmmo(turretStats.getAmmoTypes())) {
                    shootEntity(target);
                }
            }
        }

        // If update is needed, send it to clients
        if (sendUpdate) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    private boolean turretBaseIsPowered() {
        Optional<SimpleTurretBaseEntity> turretBase = getTurretBase();

        if (turretBase.isEmpty()) {
            return false;
        }
        // Check if we can extract enough energy for a shot from the turret base
        return turretBase.get().getEnergyStorage().getEnergyStored() >= turretStats.getEnergyPerShot();
    }

    public void tickClient() {
        // TODO: Move turret head entity following client-side
    }

    /**
     * Check if the given entity is in line of sight of the turret.
     *
     * @param entity The entity to check
     * @return True if the entity is in line of sight, false otherwise
     */
    protected boolean canSeeEntity(LivingEntity entity) {
        if (level == null) return false;

        Vec3 sourcePos = Vec3.atCenterOf(this.worldPosition);
        Vec3 targetPos = entity.position().add(0, entity.getEyeHeight(), 0);
        Vec3 direction = targetPos.subtract(sourcePos).normalize();
        // Move sourcePos out of the block to prevent hitting the block itself
        sourcePos = sourcePos.add(direction.scale(0.45)); // sqrt(5/16**2 + 5/16**2) = 0.44
        // Set sourcePos y to the 0.5 of our world pos
        sourcePos = new Vec3(sourcePos.x, Vec3.atCenterOf(this.worldPosition).y, sourcePos.z);

        // Check line of sight between the tile entity and the entity
        ClipContext ctx = new ClipContext(sourcePos, targetPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
        HitResult result = this.level.clip(ctx);

        return result.getType() == HitResult.Type.MISS;
    }

    /**
     * Checks if the turret should break, and breaks it if necessary.
     *
     * @return True if the turret broke, false otherwise
     */
    boolean checkShouldBreak() {
        if (level == null) return false;
        if (getTurretBase().isEmpty()) {
            // Break the turret
            level.destroyBlock(worldPosition, true);
            return true;
        }
        return false;
    }

    /**
     * Check if the entity should be targeted by the turret.
     *
     * @param entity The entity to check
     * @return True if the entity should be targeted, false otherwise
     */
    protected boolean shouldTargetEntity(Entity entity) {
        // TODO: Infrared Sensor upgrade to detect invisible entities (they are many colours.)
        // TODO: Configurable target list (players, mobs, etc.)
        return entity instanceof LivingEntity && entity.isAlive() && !entity.isInvisible();
    }

    /**
     * Shoot the given entity. This must be implemented each turret subclass.
     *
     * @param entity The entity to shoot
     */
    protected abstract void shootEntity(LivingEntity entity);

    /**
     * Get the turret base below this turret.
     *
     * @return The turret base below this turret, if it exists
     */
    public Optional<SimpleTurretBaseEntity> getTurretBase() {
        if (level == null) return Optional.empty();
        BlockPos belowPos = worldPosition.below();
        BlockEntity belowEntity = level.getBlockEntity(belowPos);
        if (belowEntity instanceof SimpleTurretBaseEntity turretBase) {
            return Optional.of(turretBase);
        }
        return Optional.empty();
    }

    /**
     * Get the calculated damage of the turret.
     *
     * @return The calculated damage
     */
    protected float getFinalTurretDamage() {
        // TODO: Check for addons, etc.
        return turretStats.getBaseDamage();
    }

    protected void loadClientData(CompoundTag tag) {
        yRotation = tag.getFloat("yRotation");
        xRotation = tag.getFloat("xRotation");
        prevYRotation = tag.getFloat("prevYRotation");
        prevXRotation = tag.getFloat("prevXRotation");
    }

    protected void saveClientData(CompoundTag tag) {
        tag.putFloat("yRotation", yRotation);
        tag.putFloat("xRotation", xRotation);
        tag.putFloat("prevYRotation", prevYRotation);
        tag.putFloat("prevXRotation", prevXRotation);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = super.getUpdateTag(provider);
        saveClientData(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider holders) {
        loadClientData(tag);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookup) {
        // This is called client side
        CompoundTag tag = pkt.getTag();
        // This will call loadClientData()
        handleUpdateTag(tag, lookup);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        saveClientData(pTag);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        loadClientData(pTag);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animCache;
    }

    public float getYRotation() {
        return yRotation;
    }

    public float getXRotation() {
        return xRotation;
    }

    public float getPrevYRotation() {
        return prevYRotation;
    }

    public float getPrevXRotation() {
        return prevXRotation;
    }
}
