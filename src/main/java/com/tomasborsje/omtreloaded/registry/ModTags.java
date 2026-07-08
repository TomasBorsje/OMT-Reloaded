package com.tomasborsje.omtreloaded.registry;

import com.tomasborsje.omtreloaded.OMTReloaded;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static final TagKey<Item> TURRET_LIGHT_AMMO_TAG = TagKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath(OMTReloaded.MODID, "turret_light_ammo_tag")
    );

    public static final TagKey<Item> TURRET_ARROW_AMMO_TAG = TagKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath(OMTReloaded.MODID, "turret_arrow_ammo_tag")
    );

    public static final TagKey<Item> TURRET_UPGRADE_TAG = TagKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath(OMTReloaded.MODID, "turret_upgrade_tag")
    );
}
