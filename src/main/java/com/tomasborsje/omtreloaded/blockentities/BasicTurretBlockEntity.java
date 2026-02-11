package com.tomasborsje.omtreloaded.blockentities;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.network.TurretAcquireTargetPacket;
import com.tomasborsje.omtreloaded.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jspecify.annotations.NonNull;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BasicTurretBlockEntity extends BlockEntity implements GeoBlockEntity {
    public static final DataTicket<Float> TURRET_YAW = DataTicket.create("angle", Float.class);
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private float turretYaw = 0;
    private Entity targetEntity;

    public BasicTurretBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.BASIC_TURRET_BLOCK_ENTITY.get(), pos, blockState);
    }

    public static <T extends BlockEntity> void tickServer(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (!(blockEntity instanceof BasicTurretBlockEntity basicTurretBlockEntity)) { return; }

        final BlockPos pos = basicTurretBlockEntity.getBlockPos();
        var nearestPlayer = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, true);
        if(nearestPlayer != null) {
            final TurretAcquireTargetPacket turretTargetPacket = new TurretAcquireTargetPacket(pos.getX(), pos.getY(), pos.getZ(), nearestPlayer.getId());
            PacketDistributor.sendToPlayersTrackingChunk((ServerLevel)level, new ChunkPos(blockPos), turretTargetPacket);
            OMTReloaded.LOGGER.info("Server found new target with ID {} and name {}!", nearestPlayer.getId(), nearestPlayer.getName().toString());
        }
    }

    public static <T extends BlockEntity> void tickClient(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (!(blockEntity instanceof BasicTurretBlockEntity basicTurretBlockEntity)) { return; }
        if(basicTurretBlockEntity.targetEntity != null) {
            basicTurretBlockEntity.calculateYawAndPitch();
        }
    }

    public void setTargetByEntityId(int entityId) {
        if(this.level == null) { return; }
        this.targetEntity = this.level.getEntity(entityId);

        OMTReloaded.LOGGER.info("Set target to entity ID {} - exists? {}", entityId, targetEntity != null);
    }

    private void calculateYawAndPitch() {
        // TODO: Do this on render thread?
        if(!this.hasLevel() || this.targetEntity == null) { return; }
        Vec3 targetPos = targetEntity.getEyePosition();
        Vec3 turretPos = this.getBlockPos().getCenter();
        this.turretYaw = (float) (-Math.atan2(turretPos.z-targetPos.z, turretPos.x - targetPos.x) + Math.toRadians(90));
        OMTReloaded.LOGGER.info("Calculated new yaw of {}", turretYaw);
    }

    // Saving and loading
    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
        this.turretYaw = input.getFloatOr("turret_yaw", 0);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);
        output.putFloat("turret_yaw", this.turretYaw);
    }

    @Override
    public void preRemoveSideEffects(@NonNull BlockPos pos, @NonNull BlockState state) {
        super.preRemoveSideEffects(pos, state);
        // TODO: Drop container items, etc.
    }

    // Rendering and GeckoLib
    public float getTurretYaw() {
        return turretYaw;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public @NonNull AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}
