package com.tomasborsje.omtreloaded.blockentities;

import com.tomasborsje.omtreloaded.Config;
import com.tomasborsje.omtreloaded.OMTReloaded;
import com.tomasborsje.omtreloaded.core.AbstractTurretBlockEntity;
import com.tomasborsje.omtreloaded.core.TurretBaseStats;
import com.tomasborsje.omtreloaded.network.TurretAcquireTargetPacket;
import com.tomasborsje.omtreloaded.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BasicTurretBlockEntity extends AbstractTurretBlockEntity {
    public BasicTurretBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.BASIC_TURRET_BLOCK_ENTITY.get(), pos, blockState, new TurretBaseStats(Config.BASIC_TURRET_ATTACK_COOLDOWN.getAsInt(), 5));
    }


    @Override
    protected boolean tryAttackTarget(@NotNull Entity target) {
        // Apply damage
        var dmg = new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.GENERIC),
                null,
                null,
                null);
        target.hurtServer((ServerLevel) level, dmg, 1);
        return true;
    }
}
