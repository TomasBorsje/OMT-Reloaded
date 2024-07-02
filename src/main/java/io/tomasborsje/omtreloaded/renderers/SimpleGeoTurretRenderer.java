package io.tomasborsje.omtreloaded.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import io.tomasborsje.omtreloaded.blockentities.SimpleTurretEntity;
import io.tomasborsje.omtreloaded.models.ModModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.joml.Math;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import java.util.Optional;

public class SimpleGeoTurretRenderer extends GeoBlockRenderer<SimpleTurretEntity> {
    public SimpleGeoTurretRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ModModels.SIMPLE_TURRET);
    }
    @Override
    public void render(SimpleTurretEntity turret, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        // Get the dynamic Y rotation angle
        float rotationY = turret.getYRotation();
        float rotationX = turret.getXRotation();

        // Get previous rotation angles
        float prevRotationY = turret.getPrevYRotation();
        float prevRotationX = turret.getPrevXRotation();

        // Access the 'gun' bone and set its rotation
        float interpolatedRotationY = Math.lerp(prevRotationY, rotationY, partialTick);
        float interpolatedRotationX = Math.lerp(prevRotationX, rotationX, partialTick);

        // TODO: ANGLE NORMALIZATION

        Optional<GeoBone> baseBone = this.getGeoModel().getBone("base");
        baseBone.ifPresent(geoBone -> geoBone.setRotY(Math.toRadians(interpolatedRotationY)));

        // Get the 'gun' bone and set X rot to getXRotation
        Optional<GeoBone> gunBone = this.getGeoModel().getBone("gun");
        gunBone.ifPresent(geoBone -> geoBone.setRotX(Math.toRadians(interpolatedRotationX)));

        super.render(turret, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
    }
}
