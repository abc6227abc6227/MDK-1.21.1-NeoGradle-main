package net.mcreator.mofusbetterend.procedures;

import net.minecraft.world.entity.Entity;

public class NautilusDownOnKeyPressedProcedure {
    public static void execute(Entity entity) {
        if (entity != null && entity.getVehicle() != null) {
            entity.getVehicle().getPersistentData().putDouble("nautilusdown", 1.0);
        }
    }
}