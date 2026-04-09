package net.mcreator.mofusbetterend.procedures;

import net.minecraft.world.entity.Entity;

public class NautilusForwardOnKeyReleasedProcedure {
    public static void execute(Entity entity) {
        if (entity != null && entity.getVehicle() != null) {
            entity.getVehicle().getPersistentData().putDouble("nautilusF", 0.0);
        }
    }
}