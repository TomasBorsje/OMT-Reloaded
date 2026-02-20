package com.tomasborsje.omtreloaded.items;

import com.google.common.base.Suppliers;
import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.registry.ModBlocks;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
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

public class BasicTurretItem extends BlockItem implements GeoItem {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public BasicTurretItem(Properties properties) {
        super(ModBlocks.BASIC_TURRET.get(), properties);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            // Defer creation of our renderer then cache it so that it doesn't get instantiated too early
            private final Supplier<GeoItemRenderer<BasicTurretItem>> renderer = Suppliers.memoize(
                    () -> new GeoItemRenderer<BasicTurretItem>(new DefaultedBlockGeoModel<>(Identifier.fromNamespaceAndPath(OMTReloaded.MODID, "basic_turret"))));

            @Nullable
            @Override
            public GeoItemRenderer<BasicTurretItem> getGeoItemRenderer() {
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