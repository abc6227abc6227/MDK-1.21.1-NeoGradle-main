package net.mcreator.mofusbetterend.procedures;

import net.mcreator.mofusbetterend.init.MofusBetterEndModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MechCreationStationGUIWhileThisGUIIsOpenTickProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) return;

        ItemStack slot7 = getSlot(entity, 7);
        if (slot7.getItem() == MofusBetterEndModItems.NAUTILUS_SUB_BLUEPRINT.get()) {
            ItemStack slot6 = getSlot(entity, 6);
            if (slot6.getItem() == MofusBetterEndModItems.NAUTILUS_SUB_BLUEPRINT.get() && !world.isClientSide()) {
                BlockEntity be = world.getBlockEntity(BlockPos.containing(x, y, z));
                if (be != null) {
                    be.getPersistentData().putDouble("nautilusbuild", 1.0);
                }
            }
        }
    }

    private static ItemStack getSlot(Entity entity, int slot) {
        if (entity instanceof Player player && player.containerMenu instanceof net.minecraft.world.inventory.AbstractContainerMenu menu) {
            if (menu.getSlot(slot).hasItem()) return menu.getSlot(slot).getItem();
        }
        return ItemStack.EMPTY;
    }
}