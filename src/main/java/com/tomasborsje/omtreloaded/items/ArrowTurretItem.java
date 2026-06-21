package com.tomasborsje.omtreloaded.items;

import com.google.common.base.Suppliers;
import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.registry.ModBlocks;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import org.jspecify.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ArrowTurretItem extends BlockItem implements GeoItem {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public ArrowTurretItem(Properties properties) {
        super(ModBlocks.ARROW_TURRET.get(), properties);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            // Defer creation of our renderer then cache it so that it doesn't get instantiated too early
            private final Supplier<GeoItemRenderer<ArrowTurretItem>> renderer = Suppliers.memoize(
                    () -> new GeoItemRenderer<ArrowTurretItem>(new DefaultedBlockGeoModel<>(Identifier.fromNamespaceAndPath(OMTReloaded.MODID, "arrow_turret"))));

            @Nullable
            @Override
            public GeoItemRenderer<ArrowTurretItem> getGeoItemRenderer() {
                return this.renderer.get();
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}