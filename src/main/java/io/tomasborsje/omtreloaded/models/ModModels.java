package io.tomasborsje.omtreloaded.models;

import io.tomasborsje.omtreloaded.OMTReloaded;
import io.tomasborsje.omtreloaded.blockentities.MachineGunTurretEntity;
import io.tomasborsje.omtreloaded.items.MachineGunTurretItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class ModModels {
    public static final DefaultedBlockGeoModel<MachineGunTurretEntity> MACHINE_GUN_TURRET = new DefaultedBlockGeoModel<>(ResourceLocation.fromNamespaceAndPath(OMTReloaded.MODID, "machine_gun_turret"));
    public static final DefaultedItemGeoModel<MachineGunTurretItem> MACHINE_GUN_TURRET_ITEM = new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(OMTReloaded.MODID, "machine_gun_turret"));
}
