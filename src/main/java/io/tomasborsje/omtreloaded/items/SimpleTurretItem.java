package io.tomasborsje.omtreloaded.items;

import io.tomasborsje.omtreloaded.renderers.item.SimpleTurretItemRenderer;
import io.tomasborsje.omtreloaded.setup.Registration;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class SimpleTurretItem extends BlockItem implements GeoItem {
    private final static Item.Properties properties = new Item.Properties();
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public SimpleTurretItem() {
        super(Registration.SIMPLE_TURRET.get(), properties);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private SimpleTurretItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new SimpleTurretItemRenderer();

                return this.renderer;
            }
        });
    }
}
