package io.tomasborsje.omtreloaded.blocks;

import io.tomasborsje.omtreloaded.blockentities.SimpleTurretBaseEntity;
import io.tomasborsje.omtreloaded.core.TurretBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SimpleTurretBase extends Block implements TurretBase, EntityBlock {
    public SimpleTurretBase() {
        super(Properties.of()
                .strength(3.5F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL)
                .randomTicks());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SimpleTurretBaseEntity(blockPos, blockState);
    }
}
