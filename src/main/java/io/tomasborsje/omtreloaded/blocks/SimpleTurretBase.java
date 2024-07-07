package io.tomasborsje.omtreloaded.blocks;

import io.tomasborsje.omtreloaded.blockentities.SimpleTurretBaseEntity;
import io.tomasborsje.omtreloaded.containers.SimpleTurretBaseContainer;
import io.tomasborsje.omtreloaded.core.AbstractTurretBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SimpleTurretBase extends AbstractTurretBase {

    @Override
    protected AbstractContainerMenu createContainer(int id, Player player, BlockPos pos) {
        return new SimpleTurretBaseContainer(id, player, pos);
    }

    @Override
    protected String getScreenComponentName() {
        return "screen.omtreloaded.simple_turret_base";
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SimpleTurretBaseEntity(blockPos, blockState);
    }
}
