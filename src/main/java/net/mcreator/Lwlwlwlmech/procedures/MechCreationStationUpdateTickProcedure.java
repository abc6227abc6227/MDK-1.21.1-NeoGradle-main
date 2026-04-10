package net.mcreator.Lwlwlwlmech.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MechCreationStationUpdateTickProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        if (!world.isClientSide()) {
            BlockEntity be = world.getBlockEntity(BlockPos.containing(x, y, z));
            if (be != null) {
                double current = be.getPersistentData().getDouble("buildblock");
                be.getPersistentData().putDouble("buildblock", current + 0.0);
            }
        }
    }
}