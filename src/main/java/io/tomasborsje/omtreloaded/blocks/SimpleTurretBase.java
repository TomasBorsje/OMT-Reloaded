package io.tomasborsje.omtreloaded.blocks;

import io.tomasborsje.omtreloaded.core.TurretBase;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class SimpleTurretBase extends Block implements TurretBase {
    public SimpleTurretBase() {
        super(Properties.of()
                .strength(3.5F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL)
                .randomTicks());
    }
}
