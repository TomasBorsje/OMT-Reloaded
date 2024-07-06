package io.tomasborsje.omtreloaded.renderers.block;

import com.mojang.blaze3d.vertex.PoseStack;
import io.tomasborsje.omtreloaded.blockentities.MachineGunTurretEntity;
import io.tomasborsje.omtreloaded.models.ModModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.joml.Math;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import java.util.Optional;

public class MachineGunTurretRenderer extends GeoBlockRenderer<MachineGunTurretEntity> {
    public MachineGunTurretRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ModModels.MACHINE_GUN_TURRET);
    }

    @Override
    public void render(MachineGunTurretEntity turret, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        // Get turret rotations
        float rotationY = turret.getYRotation();
        float rotationX = turret.getXRotation();
        float prevRotationY = turret.getPrevYRotation();
        float prevRotationX = turret.getPrevXRotation();

        float interpolatedRotationY = lerp(prevRotationY, rotationY, partialTick);
        float interpolatedRotationX = lerp(prevRotationX, rotationX, partialTick);

        // Access the 'gun' bone and set its rotation
        Optional<GeoBone> baseBone = this.getGeoModel().getBone("gun");
        baseBone.ifPresent(geoBone -> geoBone.setRotY(Math.toRadians(interpolatedRotationY)));

        // Get the 'barrel' bone and set X rot to getXRotation
        Optional<GeoBone> gunBone = this.getGeoModel().getBone("barrel");
        gunBone.ifPresent(geoBone -> geoBone.setRotX(Math.toRadians(interpolatedRotationX)));

        super.render(turret, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
    }

    /**
     * Linearly interpolates between two angles, ensuring the shortest path is taken.
     *
     * @param start The starting angle
     * @param end   The ending angle
     * @param t     The interpolation factor
     * @return The interpolated angle
     */
    public static float lerp(float start, float end, float t) {
        float difference = end - start;
        float shortestAngle = (difference + 180) % 360 - 180;
        return start + shortestAngle * t;
    }
}
