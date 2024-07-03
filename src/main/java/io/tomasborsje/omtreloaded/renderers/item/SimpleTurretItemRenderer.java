package io.tomasborsje.omtreloaded.renderers.item;

import io.tomasborsje.omtreloaded.items.SimpleTurretItem;
import io.tomasborsje.omtreloaded.models.ModModels;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SimpleTurretItemRenderer extends GeoItemRenderer<SimpleTurretItem> {
    // TODO: Remove this class in favour of new GeoItemRenderer<>(new DefaultedBlockGeoModel<>(new ResourceLocation(ExampleMod.MODID, "gecko_habitat"))); etc
    public SimpleTurretItemRenderer() {
        super(ModModels.SIMPLE_TURRET_ITEM);
    }
}
