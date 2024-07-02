package io.tomasborsje.omtreloaded.setup;

import io.tomasborsje.omtreloaded.OMTReloaded;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> TURRET_FIRE = register("turret_fire");
    private static ResourceKey<DamageType> register(String name)
    {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(OMTReloaded.MODID, name));
    }
}
