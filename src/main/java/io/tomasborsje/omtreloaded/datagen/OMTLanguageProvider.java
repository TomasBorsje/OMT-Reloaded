package io.tomasborsje.omtreloaded.datagen;

import io.tomasborsje.omtreloaded.OMTReloaded;
import io.tomasborsje.omtreloaded.setup.Registration;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class OMTLanguageProvider extends LanguageProvider {
    public OMTLanguageProvider(PackOutput output, String locale) {
        super(output, OMTReloaded.MODID, locale);
    }
    @Override
    protected void addTranslations() {
        addBlocks();
        addItems();
        addUi();
        addDamageSources();
    }

    private void addBlocks() {
        add(Registration.SIMPLE_TURRET.get(), "Simple Turret");
        add(Registration.SIMPLE_TURRET_BASE.get(), "Simple Turret Base");
    }
    private void addItems() {
        add(Registration.TURRET_RAIL.get(), "Turret Rail");
    }
    private void addUi() {
        add("itemGroup.omtreloaded", "OMT: Reloaded");
    }
    private void addDamageSources() {
        add("death.attack.turret_fire", "%1$s was gunned down");
        add("death.attack.turret_player", "%1$s was gunned down whilst trying to escape %2$s");
    }
}
