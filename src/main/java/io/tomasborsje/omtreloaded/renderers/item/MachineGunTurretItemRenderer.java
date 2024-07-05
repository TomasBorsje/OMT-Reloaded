package io.tomasborsje.omtreloaded.renderers.item;

import io.tomasborsje.omtreloaded.items.MachineGunTurretItem;
import io.tomasborsje.omtreloaded.models.ModModels;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class MachineGunTurretItemRenderer extends GeoItemRenderer<MachineGunTurretItem> {
    // TODO: Remove this class in favour of new GeoItemRenderer<>(new DefaultedBlockGeoModel<>(new ResourceLocation(ExampleMod.MODID, "gecko_habitat"))); etc
    public MachineGunTurretItemRenderer() {
        super(ModModels.SIMPLE_TURRET_ITEM);
    }
}
