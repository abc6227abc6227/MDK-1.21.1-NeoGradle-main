package net.mcreator.Lwlwlwlmech.procedures;

import net.minecraft.world.entity.Entity;

public class NautilusDownOnKeyReleasedProcedure {
    public static void execute(Entity entity) {
        if (entity != null && entity.getVehicle() != null) {
            entity.getVehicle().getPersistentData().putDouble("nautilusdown", 0.0);
        }
    }
}