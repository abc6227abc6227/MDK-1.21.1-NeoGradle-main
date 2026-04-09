package net.mcreator.mofusbetterend.procedures;

import net.minecraft.world.entity.Entity;

public class NautilusThrustOnKeyReleasedProcedure {
    public static void execute(Entity entity) {
        if (entity != null && entity.getVehicle() != null) {
            entity.getVehicle().getPersistentData().putDouble("NautilusThrust", 0.0);
        }
    }
}