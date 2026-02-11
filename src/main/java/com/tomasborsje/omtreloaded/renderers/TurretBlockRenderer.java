package com.tomasborsje.omtreloaded.renderers;

import com.tomasborsje.omtreloaded.blockentities.BasicTurretBlockEntity;
import com.tomasborsje.omtreloaded.registry.ModBlockEntityTypes;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.base.BoneSnapshots;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.RenderPassInfo;

public class TurretBlockRenderer<R extends BlockEntityRenderState & GeoRenderState> extends GeoBlockRenderer<BasicTurretBlockEntity, R> {
    private static final String TURRET_BONE = "turret";
    private static final String BARREL_BONE = "barrel";

    public TurretBlockRenderer() {
        super(ModBlockEntityTypes.BASIC_TURRET_BLOCK_ENTITY.get());
    }

    /**
     * Add the angle of the turret's barrel for use in rendering, grabbed from the block entity.
     */
    @Override
    public void addRenderData(BasicTurretBlockEntity be, @Nullable Void relatedObject, R renderState, float partialTick) {
        if(!be.hasLevel() || be.getTargetEntity() == null) { return; }
        var targetEntity = be.getTargetEntity();
        Vec3 targetPos = targetEntity.getEyePosition();
        Vec3 turretPos = be.getBlockPos().getCenter();

        Vec3 lookingDir = targetPos.subtract(turretPos).normalize();
        Vec3 turretHorizontalFeet = new Vec3(targetPos.x, turretPos.y, targetPos.z).subtract(turretPos);

        var turretYaw = (float) (-Math.atan2(turretPos.z-targetPos.z, turretPos.x - targetPos.x) + Math.toRadians(90));

        float barrelPitch = (float) Math.acos(turretHorizontalFeet.normalize().dot(lookingDir));
        if(targetPos.y < turretPos.y) { barrelPitch *= -1; }

        renderState.addGeckolibData(BasicTurretBlockEntity.TURRET_YAW, turretYaw);
        renderState.addGeckolibData(BasicTurretBlockEntity.BARREL_PITCH, barrelPitch);
    }

    /**
     * Rotate the turret's barrel to match the block entity's barrel angle value.
     */
    @Override
    public void adjustModelBonesForRender(RenderPassInfo<R> renderPassInfo, BoneSnapshots snapshots) {
        Float turretYawBoxed = renderPassInfo.getGeckolibData(BasicTurretBlockEntity.TURRET_YAW);
        final float turretYaw = (turretYawBoxed == null) ? 0 : turretYawBoxed;
        snapshots.ifPresent(TURRET_BONE, bone -> {
            bone.setRotY(turretYaw);
        });

        Float barrelPitchBoxed = renderPassInfo.getGeckolibData(BasicTurretBlockEntity.BARREL_PITCH);
        final float barrelPitch = (barrelPitchBoxed == null) ? 0 : barrelPitchBoxed;
        snapshots.ifPresent(BARREL_BONE, bone -> {
            bone.setRotX(barrelPitch);
        });
    }
}
