package com.tomasborsje.omtreloaded.blockentities;

import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.network.DummyPacket;
import com.tomasborsje.omtreloaded.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jspecify.annotations.NonNull;

public class TurretBaseBlockEntity extends BlockEntity {
    public TurretBaseBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.TURRET_BASE_BLOCK_ENTITY.get(), pos, blockState);
    }

    public static <T extends BlockEntity> void tickServer(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (!(blockEntity instanceof TurretBaseBlockEntity turretBaseBlockEntity)) { return; }

        OMTReloaded.LOGGER.info("Ticking server!");
        PacketDistributor.sendToPlayersTrackingChunk((ServerLevel)level, new ChunkPos(blockPos), new DummyPacket("Hey, client, you can see me!"));
    }

    public static <T extends BlockEntity> void tickClient(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (!(blockEntity instanceof TurretBaseBlockEntity turretBaseBlockEntity)) { return; }

        OMTReloaded.LOGGER.info("Ticking client!");
    }

    // Saving and loading
    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);
    }

    @Override
    public void preRemoveSideEffects(@NonNull BlockPos pos, @NonNull BlockState state) {
        super.preRemoveSideEffects(pos, state);
        // TODO: Drop container items, etc.
    }
}
