package io.tomasborsje.omtreloaded.models;

import io.tomasborsje.omtreloaded.OMTReloaded;
import io.tomasborsje.omtreloaded.blockentities.SimpleTurretEntity;
import io.tomasborsje.omtreloaded.items.SimpleTurretItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class ModModels {
    public static final DefaultedBlockGeoModel<SimpleTurretEntity> SIMPLE_TURRET = new DefaultedBlockGeoModel<>(ResourceLocation.fromNamespaceAndPath(OMTReloaded.MODID, "simple_turret"));
    public static final DefaultedItemGeoModel<SimpleTurretItem> SIMPLE_TURRET_ITEM = new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(OMTReloaded.MODID, "simple_turret"));
}
