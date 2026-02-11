package com.tomasborsje.omtreloaded.renderers;

import com.tomasborsje.omtreloaded.blockentities.BasicTurretBlockEntity;
import com.tomasborsje.omtreloaded.registry.ModBlockEntityTypes;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
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
    public void addRenderData(BasicTurretBlockEntity animatable, @Nullable Void relatedObject, R renderState, float partialTick) {
        renderState.addGeckolibData(BasicTurretBlockEntity.TURRET_YAW, animatable.getTurretYaw());
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
    }
}
