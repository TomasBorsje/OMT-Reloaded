package io.tomasborsje.omtreloaded.models;

import io.tomasborsje.omtreloaded.OMTReloaded;
import io.tomasborsje.omtreloaded.blockentities.SimpleTurretEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class ModModels {
    public static final DefaultedBlockGeoModel<SimpleTurretEntity> SIMPLE_TURRET = new DefaultedBlockGeoModel<>(ResourceLocation.fromNamespaceAndPath(OMTReloaded.MODID, "simple_turret"));
}
