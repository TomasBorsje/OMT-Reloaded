package io.tomasborsje.omtreloaded.datagen;

import io.tomasborsje.omtreloaded.OMTReloaded;
import io.tomasborsje.omtreloaded.setup.ModSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class OMTSoundEvents extends SoundDefinitionsProvider {
    protected OMTSoundEvents(PackOutput output, String modId, ExistingFileHelper helper) {
        super(output, modId, helper);
    }

    @Override
    public void registerSounds() {
        add(ModSoundEvents.MACHINE_GUN_TURRET_FIRE, definition()
                .subtitle("sound.omtreloaded.machine_gun_turret_fire")
                .with(sound(ResourceLocation.fromNamespaceAndPath(OMTReloaded.MODID, "machine_gun_turret_fire")))
        );
    }
}
