package io.tomasborsje.omtreloaded.registration;

import io.tomasborsje.omtreloaded.OMTReloaded;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, OMTReloaded.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> MACHINE_GUN_TURRET_FIRE = SOUND_EVENTS.register(
            "machine_gun_turret_fire",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(OMTReloaded.MODID, "machine_gun_turret_fire"))
    );
}
