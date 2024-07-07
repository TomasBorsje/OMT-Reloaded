package io.tomasborsje.omtreloaded.core;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.IItemHandler;

public abstract class AbstractTurretBase extends Block implements EntityBlock {

    public AbstractTurretBase() {
        super(Properties.of().strength(3.5F).requiresCorrectToolForDrops().sound(SoundType.METAL).randomTicks());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level level, BlockPos pos, Player player, BlockHitResult pHitResult) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof AbstractTurretBaseEntity) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return Component.translatable(getScreenComponentName());
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return createContainer(windowId, playerEntity, pos);
                    }
                };
                player.openMenu(containerProvider, buf -> buf.writeBlockPos(pos));
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof AbstractTurretBaseEntity) {
                if (pLevel instanceof ServerLevel) {
                    // Drop all items in the turret base's inventory
                    IItemHandler itemHandler = ((AbstractTurretBaseEntity) blockentity).getItemHandler();
                    for (int i = 0; i < itemHandler.getSlots(); i++) {
                        ItemStack stack = itemHandler.getStackInSlot(i);
                        if (!stack.isEmpty()) {
                            Block.popResource(pLevel, pPos, stack);
                        }
                    }
                }
                super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            } else {
                super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
            }
        }
    }

    protected abstract AbstractContainerMenu createContainer(int id, Player player, BlockPos pos);

    protected abstract String getScreenComponentName();
}
