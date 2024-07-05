package io.tomasborsje.omtreloaded.models;

import io.tomasborsje.omtreloaded.OMTReloaded;
import io.tomasborsje.omtreloaded.blockentities.MachineGunTurretEntity;
import io.tomasborsje.omtreloaded.items.MachineGunTurretItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class ModModels {
    public static final DefaultedBlockGeoModel<MachineGunTurretEntity> SIMPLE_TURRET = new DefaultedBlockGeoModel<>(ResourceLocation.fromNamespaceAndPath(OMTReloaded.MODID, "simple_turret"));
    public static final DefaultedItemGeoModel<MachineGunTurretItem> SIMPLE_TURRET_ITEM = new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(OMTReloaded.MODID, "simple_turret"));
}
