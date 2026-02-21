package com.tomasborsje.omtreloaded.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class TurretUtil {
    public static boolean WithinSquareDistance(BlockPos one, Vec3 two, int distance) {
        return Math.abs(one.getX() - two.x()) < distance && Math.abs(one.getY() - two.y()) < distance && Math.abs(one.getZ() - two.z()) < distance;
    }
}
