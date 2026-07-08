package com.tomasborsje.omtreloaded.items;

import com.google.common.base.Suppliers;
import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.core.AbstractTurretBlock;
import com.tomasborsje.omtreloaded.core.TurretTooltipData;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
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

public abstract class AbstractTurretItem extends BlockItem implements GeoItem {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public AbstractTurretItem(AbstractTurretBlock block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            // Defer creation of our renderer then cache it so that it doesn't get instantiated too early
            private final Supplier<GeoItemRenderer<AbstractTurretItem>> renderer = Suppliers.memoize(
                    () -> new GeoItemRenderer<AbstractTurretItem>(new DefaultedBlockGeoModel<>(Identifier.fromNamespaceAndPath(OMTReloaded.MODID, "arrow_turret"))));

            @Nullable
            @Override
            public GeoItemRenderer<AbstractTurretItem> getGeoItemRenderer() {
                return this.renderer.get();
            }
        });
    }

    protected abstract TurretTooltipData getTooltipData();

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        TurretTooltipData data = getTooltipData();

        tooltipAdder.accept(Component.translatable("ui.omtreloaded.lore.generic.tier1"));
        tooltipAdder.accept(Component.translatable("ui.omtreloaded.lore.generic.damage", data.attackDamage()));
        tooltipAdder.accept(Component.translatable("ui.omtreloaded.lore.generic.fire_rate", 1200/data.attackCooldown()));
        tooltipAdder.accept(Component.translatable("ui.omtreloaded.lore.generic.range", data.range()));
        tooltipAdder.accept(Component.translatable("ui.omtreloaded.lore.generic.energy_drain", data.energyDrain()));
        tooltipAdder.accept(Component.empty());
        tooltipAdder.accept(Component.translatable(data.descriptionKey()));

        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}