package io.tomasborsje.omtreloaded.items;

import com.mojang.logging.LogUtils;
import io.tomasborsje.omtreloaded.blocks.SimpleTurretBase;
import io.tomasborsje.omtreloaded.renderers.item.MachineGunTurretItemRenderer;
import io.tomasborsje.omtreloaded.setup.ModBlocks;
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
        // Log block type
        LogUtils.getLogger().info(pContext.getLevel().getBlockState(pContext.getClickedPos()).getBlock().getClass().getName());
        // Log pState
        LogUtils.getLogger().info(pState.getBlock().getClass().getName());
        // Ensure the player is trying to place on a TurretBase block
        return pContext.getLevel().getBlockState(pContext.getClickedPos()).getBlock() instanceof SimpleTurretBase;
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
