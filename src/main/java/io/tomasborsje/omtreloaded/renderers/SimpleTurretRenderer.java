package io.tomasborsje.omtreloaded.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.tomasborsje.omtreloaded.blockentities.SimpleTurretEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SimpleTurretRenderer implements BlockEntityRenderer<SimpleTurretEntity> {
    ItemStack stack = new ItemStack(Items.BOW);

    public SimpleTurretRenderer(BlockEntityRendererProvider.Context ctx) { }

    @Override
    public void render(SimpleTurretEntity be, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();


        poseStack.pushPose();
        poseStack.scale(.5f, .5f, .5f);
        poseStack.translate(1f, 2.8f, 1f);
        float angle = be.getYRotation();
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));
        itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, LightTexture.FULL_BRIGHT, combinedOverlay, poseStack, bufferSource, Minecraft.getInstance().level, 0);
        poseStack.popPose();
    }
}
