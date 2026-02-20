package com.tomasborsje.omtreloaded.renderers;

import com.tomasborsje.omtreloaded.items.BasicTurretItem;
import com.tomasborsje.omtreloaded.registry.ModItems;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class TurretItemRenderer extends GeoItemRenderer<BasicTurretItem> {
    public TurretItemRenderer() {
        super(ModItems.BASIC_TURRET_ITEM.get());
    }
}