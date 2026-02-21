package com.tomasborsje.omtreloaded.core;

import com.tomasborsje.omtreloaded.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.BiFunction;

/**
 * The base class for all turret blocks.
 */
// TODO: Create block item model and set renderer:
// https://wiki.geckolib.com/docs/geckolib5/items/intro
// https://github.com/bernie-g/geckolib-examples/blob/Multiloader-1.21.11/common/src/main/java/com/example/examplemod/item/GeckoHabitatItem.java
public abstract class AbstractTurretBlock extends Block implements EntityBlock {
    private final BiFunction<BlockPos, BlockState, BlockEntity> blockEntitySupplier;

    public AbstractTurretBlock(BlockBehaviour.Properties properties, BiFunction<BlockPos, BlockState, BlockEntity> blockEntitySupplier) {
        super(properties.noOcclusion());
        this.blockEntitySupplier = blockEntitySupplier;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return blockEntitySupplier.apply(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NonNull Level level, @NonNull BlockState state, @NonNull BlockEntityType<T> blockEntityType) {
        // Tick server and clientside respectively
        if(blockEntityType.getValidBlocks().stream().noneMatch(block -> block instanceof AbstractTurretBlock)) { return null; }
        return level instanceof ServerLevel ? TurretBlockEntityTicker::tickServer : TurretBlockEntityTicker::tickClient;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(3, 0, 3, 13, 16, 13);
    }
}
