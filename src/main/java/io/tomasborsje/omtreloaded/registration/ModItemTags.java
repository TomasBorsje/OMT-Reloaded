package io.tomasborsje.omtreloaded.registration;

import io.tomasborsje.omtreloaded.OMTReloaded;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> AMMO = ItemTags.create(ResourceLocation.fromNamespaceAndPath(OMTReloaded.MODID, "ammo"));
    public static final TagKey<Item> ADDONS = ItemTags.create(ResourceLocation.fromNamespaceAndPath(OMTReloaded.MODID, "addons"));
}
