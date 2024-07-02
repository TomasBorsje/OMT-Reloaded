package io.tomasborsje.omtreloaded.blockentities;

import io.tomasborsje.omtreloaded.setup.ModDamageTypes;
import io.tomasborsje.omtreloaded.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SimpleTurretEntity extends BlockEntity {
    private final static int TICKS_PER_TARGETING_CHECK = 2;
    private final static int TICKS_PER_SHOOT = 40;
    private final static float TARGETING_RANGE = 10.0f;
    private final static float SHOOT_DAMAGE = 1.0f;
    private int ticks = 0;
    private float yRotation = 0;
    private LivingEntity target;

    public SimpleTurretEntity(BlockPos pos, BlockState state) {
        super(Registration.SIMPLE_TURRET_ENTITY.get(), pos, state);
    }

    public void tickServer() {
        if(level == null) return;

        if(checkShouldBreak()) return;

        // Get all nearby entities every TICKS_PER_TARGETING_CHECK ticks, and try to shoot them if specified to
        ticks++;
        if (ticks % TICKS_PER_TARGETING_CHECK == 0) {
            // Get all nearby entities within TARGETING_RANGE blocks that we should target
            AABB aabb = new AABB(worldPosition).inflate(TARGETING_RANGE);
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
            for(LivingEntity entity : entities) {
                if(canSeeEntity(entity)) {
                    target = entity;
                    foundTarget = true;

                    // Calculate the y rotation to the target, -180 is north, -90 is east, where 0 is south, and 90 is west
                    Vec3 targetPos = entity.position().add(0, entity.getEyeHeight(), 0);
                    Vec3 direction = targetPos.subtract(centerPos).normalize();
                    yRotation = (float)Math.toDegrees(Math.atan2(direction.x, direction.z)) - 90;
                    if (yRotation < -180) {
                        yRotation += 360;
                    }

                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
                    break;
                }
            }
            // If false, clear target
            if(!foundTarget) {
                target = null;
            }
        }
        if(ticks % TICKS_PER_SHOOT == 0) {
            // Try to shoot the first entity in the list
            if(target != null) {
                shootEntity(target);
            }
        }
    }

    /**
     * Checks if the turret should break, and breaks it if necessary.
     * @return True if the turret broke, false otherwise
     */
    private boolean checkShouldBreak() {
        if(level == null) return false;
        // Checks if the block below turret is a diamond block, if not, break the turret and drop as an item
        BlockPos belowPos = worldPosition.below();
        BlockState belowState = level.getBlockState(belowPos);
        if (belowState.getBlock() != Registration.SIMPLE_TURRET_BASE.get()) {
            // Break the turret
            level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
            // Drop the turret as an item
            ItemEntity itemEntity = new ItemEntity(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), Registration.SIMPLE_TURRET_ITEM.get().getDefaultInstance());
            level.addFreshEntity(itemEntity);
            return true;
        }
        return false;
    }

    /**
     * Check if the entity should be targeted by the turret.
     * @param entity The entity to check
     * @return True if the entity should be targeted, false otherwise
     */
    private boolean shouldTargetEntity(Entity entity) {
        return entity instanceof LivingEntity && !(entity instanceof Player) && entity.isAlive() && !entity.isInvisible();
    }

    /**
     * Tries to shoot the entity, dealing damage if the entity is in line-of-sight.
     * @param entity The entity to shoot
     */
    private boolean canSeeEntity(LivingEntity entity) {
        if(level == null) return false;

        Vec3 sourcePos = Vec3.atCenterOf(this.worldPosition);
        Vec3 targetPos = entity.position().add(0, entity.getEyeHeight(), 0);
        Vec3 direction = targetPos.subtract(sourcePos).normalize();
        // Move sourcePos out of the block to prevent hitting the block itself
        sourcePos = sourcePos.add(direction);

        // Check line of sight between the tile entity and the entity
        ClipContext ctx = new ClipContext(sourcePos, targetPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
        HitResult result = this.level.clip(ctx);

        if (result.getType() == HitResult.Type.MISS) {
            return true;
        }
        else {
            // Spawn particles at the hit position and log
            Vec3 hitPos = result.getLocation();
            ((ServerLevel)this.level).sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, hitPos.x, hitPos.y, hitPos.z, 10, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        return false;
    }

    /**
     * Shoot the entity, dealing SHOOT_DAMAGE damage with our custom damage type.
     * @param entity The entity to shoot
     */
    private void shootEntity(LivingEntity entity) {
        if(level == null) return;

        // Draw line of 20 particles from the source to the target
        Vec3 sourcePos = Vec3.atCenterOf(this.worldPosition);
        Vec3 targetPos = entity.position().add(0, entity.getEyeHeight(), 0);
        Vec3 offset = targetPos.subtract(sourcePos);
        for(int i = 0; i < 20; i++) {
            Vec3 pos = sourcePos.add(offset.scale((i + 1) / 20.0));
            ((ServerLevel)this.level).sendParticles(ParticleTypes.CRIT, pos.x, pos.y, pos.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }

        DamageSource source = new DamageSource(this.level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ModDamageTypes.TURRET_FIRE));
        entity.invulnerableTime = 0; // Reset invulnerability time to allow immediate damage
        entity.hurt(source, SHOOT_DAMAGE);
        // Play anvil landing sound to all players nearby
        level.playSound(null, worldPosition, net.minecraft.sounds.SoundEvents.ANVIL_LAND, net.minecraft.sounds.SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = super.getUpdateTag(provider);
        saveClientData(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider holders) {
        if (tag != null) {
            loadClientData(tag);
        }
    }

    private void loadClientData(CompoundTag tag) {
        yRotation = tag.getFloat("yRotation");
    }

    private void saveClientData(CompoundTag tag) {
        tag.putFloat("yRotation", yRotation);
    }

    // The getUpdatePacket()/onDataPacket() pair is used when a block update happens on the client
    // (a blockstate change or an explicit notificiation of a block update from the server). It's
    // easiest to implement them based on getUpdateTag()/handleUpdateTag()
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

    public float getYRotation() {
        return yRotation;
    }
}
