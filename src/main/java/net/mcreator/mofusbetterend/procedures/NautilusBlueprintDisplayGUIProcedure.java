package net.mcreator.mofusbetterend.procedures;

import net.mcreator.mofusbetterend.init.MofusBetterEndModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class NautilusBlueprintDisplayGUIProcedure {
    public static boolean execute(Entity entity) {
        if (entity instanceof Player player) {
            // 检查玩家背包是否有鹦鹉螺蓝图
            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (stack.getItem() == MofusBetterEndModItems.NAUTILUS_SUB_BLUEPRINT.get()) {
                    return true;
                }
            }
        }
        return false;
    }
}