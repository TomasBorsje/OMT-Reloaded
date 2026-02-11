package com.tomasborsje.omtreloaded.renderers;

import com.tomasborsje.omtreloaded.blockentities.BasicTurretBlockEntity;
import com.tomasborsje.omtreloaded.registry.ModBlockEntityTypes;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class TurretBlockRenderer<R extends BlockEntityRenderState & GeoRenderState> extends GeoBlockRenderer<BasicTurretBlockEntity, R> {
    public TurretBlockRenderer() {
        super(ModBlockEntityTypes.BASIC_TURRET_BLOCK_ENTITY.get());
    }
}
