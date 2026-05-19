package com.tomasborsje.omtreloaded.items;

import com.tomasborsje.omtreloaded.core.AbstractTurretBaseBlockEntity;
import com.tomasborsje.omtreloaded.registry.ModBlocks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;

public class AbstractTurretBlockItem extends BlockItem {
    public AbstractTurretBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        InteractionResult result = super.place(context);
        if (result == InteractionResult.SUCCESS && context.getLevel() instanceof ServerLevel serverLevel) {
            var blockEntity = serverLevel.getBlockEntity(context.getClickedPos());
            if (blockEntity instanceof AbstractTurretBaseBlockEntity baseBlockEntity
                    && context.getPlayer() instanceof ServerPlayer player) {
                baseBlockEntity.setOwnerDetails(player.nameAndId());
            }
        }
        return result;
    }
}
