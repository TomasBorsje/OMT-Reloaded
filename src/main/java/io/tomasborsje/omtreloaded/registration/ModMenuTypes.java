package io.tomasborsje.omtreloaded.registration;

import io.tomasborsje.omtreloaded.containers.SimpleTurretBaseContainer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static io.tomasborsje.omtreloaded.OMTReloaded.MODID;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, MODID);

    public static final Supplier<MenuType<SimpleTurretBaseContainer>> SIMPLE_TURRET_BASE_CONTAINER = MENU_TYPES.register("processor_block",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> new SimpleTurretBaseContainer(windowId, inv.player, data.readBlockPos())));
}
