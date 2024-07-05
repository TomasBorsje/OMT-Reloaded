package io.tomasborsje.omtreloaded.items;

import io.tomasborsje.omtreloaded.core.TurretBase;
import io.tomasborsje.omtreloaded.renderers.item.MachineGunTurretItemRenderer;
import io.tomasborsje.omtreloaded.registration.ModBlocks;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class MachineGunTurretItem extends BlockItem implements GeoItem {
    private final static Item.Properties properties = new Item.Properties();
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public MachineGunTurretItem() { super(ModBlocks.MACHINE_GUN_TURRET.get(), properties); }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    protected boolean canPlace(BlockPlaceContext pContext, BlockState pState) {
        // Check the block below the one being clicked is a TurretBase
        if(pContext.getLevel().getBlockState(pContext.getClickedPos().below()).getBlock() instanceof TurretBase) {
            return super.canPlace(pContext, pState);
        }
        return false;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private MachineGunTurretItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new MachineGunTurretItemRenderer();

                return this.renderer;
            }
        });
    }
}
