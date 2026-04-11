package net.mcreator.Lwlwlwlmech.procedures;

import net.mcreator.Lwlwlwlmech.LwlwlwlmechMod;
import net.mcreator.Lwlwlwlmech.block.MechCreationStationBlock;
import net.mcreator.Lwlwlwlmech.init.LwlwlwlmechEntities;
import net.mcreator.Lwlwlwlmech.init.LwlwlwlmechModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MechCreationStationOnBlockRightClickedProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) return;

        // 检查是否正在制造
        BlockEntity blockEntity = world.getBlockEntity(BlockPos.containing(x, y, z));
        if (blockEntity != null && blockEntity.getPersistentData().getDouble("stop") == 1.0) {
            System.out.println("[制造站] 正在制造中，请稍后...");
            return;
        }

        // ========== 获取所有槽位物品 ==========
        ItemStack slot0 = getSlot(entity, 0);
        ItemStack slot2 = getSlot(entity, 2);
        ItemStack slot3 = getSlot(entity, 3);
        ItemStack slot5 = getSlot(entity, 5);
        ItemStack slot6 = getSlot(entity, 6);
        ItemStack slot7 = getSlot(entity, 7);
        ItemStack slot9 = getSlot(entity, 9);
        ItemStack slot10 = getSlot(entity, 10);
        ItemStack slot11 = getSlot(entity, 11);
        ItemStack slot12 = getSlot(entity, 12);
        ItemStack slot13 = getSlot(entity, 13);
        ItemStack slot14 = getSlot(entity, 14);
        ItemStack slot15 = getSlot(entity, 15);
        ItemStack slot16 = getSlot(entity, 16);

        // ========== 鹦鹉螺号配方验证 ==========
        boolean nautilusValid =
                slot0.getItem() == LwlwlwlmechModItems.NAUTILUS_SUB_BLUEPRINT.get() &&
                        slot2.getItem() == Blocks.GLASS.asItem() &&
                        slot3.getItem() == Blocks.GLASS.asItem() &&
                        slot5.getItem() == LwlwlwlmechModItems.TITANIUM_INGOT.get() &&
                        slot6.getItem() == Blocks.GLASS.asItem() &&
                        slot7.getItem() == Blocks.GLASS.asItem() &&
                        slot9.getItem() == LwlwlwlmechModItems.TITANIUM_INGOT.get() &&
                        slot10.getItem() == LwlwlwlmechModItems.TITANIUM_INGOT.get() &&
                        slot11.getItem() == LwlwlwlmechModItems.TITANIUM_INGOT.get() &&
                        slot12.getItem() == LwlwlwlmechModItems.TITANIUM_INGOT.get() &&
                        slot13.getItem() == LwlwlwlmechModItems.NAUTILUS_THRUSTER.get() &&
                        slot14.getItem() == LwlwlwlmechModItems.NAUTILUS_THRUSTER.get() &&
                        slot15.getItem() == LwlwlwlmechModItems.NAUTILUS_THRUSTER.get() &&
                        slot16.getItem() == LwlwlwlmechModItems.NAUTILUS_THRUSTER.get();

        if (nautilusValid) {
            System.out.println("[制造站] 配方正确！开始制造鹦鹉螺号...");
            startNautilusCrafting(world, x, y, z, entity);
        } else {
            System.out.println("[制造站] 配方错误！请检查物品摆放");
        }
    }

    private static void startNautilusCrafting(LevelAccessor world, double x, double y, double z, Entity entity) {
        // ========== 调试：打印坐标 ==========
        BlockPos pos = BlockPos.containing(x, y, z);
        BlockState state = world.getBlockState(pos);
        System.out.println("[制造站] 制造坐标: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
        System.out.println("[制造站] 该位置方块: " + state.getBlock());

        // 验证是否是制造站
        if (!(state.getBlock() instanceof MechCreationStationBlock)) {
            System.out.println("[制造站] 错误：坐标指向的不是制造站，而是 " + state.getBlock());
            return;
        }
        // ====================================

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            blockEntity.getPersistentData().putDouble("stop", 1.0);
        }

        // 消耗材料
        consumeSlot(entity, 0, 1);
        consumeSlot(entity, 2, 1);
        consumeSlot(entity, 3, 1);
        consumeSlot(entity, 5, 1);
        consumeSlot(entity, 6, 1);
        consumeSlot(entity, 7, 1);
        consumeSlot(entity, 9, 1);
        consumeSlot(entity, 10, 1);
        consumeSlot(entity, 11, 1);
        consumeSlot(entity, 12, 1);
        consumeSlot(entity, 13, 1);
        consumeSlot(entity, 14, 1);
        consumeSlot(entity, 15, 1);
        consumeSlot(entity, 16, 1);

        // 关闭GUI
        if (entity instanceof Player player) {
            player.closeContainer();
        }

        // 设置动画
        world.setBlock(pos, state.setValue(MechCreationStationBlock.ANIMATION, 2), 3);

        // 400 ticks (20秒) 后重置动画
        LwlwlwlmechMod.queueServerWork(400, () -> {
            BlockState currentState = world.getBlockState(pos);
            world.setBlock(pos, currentState.setValue(MechCreationStationBlock.ANIMATION, 0), 3);
        });

        // 380 ticks (19秒) 后生成实体
        LwlwlwlmechMod.queueServerWork(380, () -> {
            if (world instanceof ServerLevel serverLevel) {
                LwlwlwlmechEntities.NAUTILUS_SUB.get().spawn(
                        serverLevel,
                        pos.above(2),
                        MobSpawnType.MOB_SUMMONED
                );
                System.out.println("[制造站] 鹦鹉螺号生成成功！");
            }
            if (blockEntity != null) {
                blockEntity.getPersistentData().putDouble("stop", 0.0);
            }
        });
    }

    private static ItemStack getSlot(Entity entity, int slot) {
        if (entity instanceof Player player) {
            AbstractContainerMenu menu = player.containerMenu;
            if (menu != null && menu.slots.size() > slot && menu.getSlot(slot).hasItem()) {
                return menu.getSlot(slot).getItem();
            }
        }
        return ItemStack.EMPTY;
    }

    private static void consumeSlot(Entity entity, int slot, int count) {
        if (entity instanceof Player player) {
            AbstractContainerMenu menu = player.containerMenu;
            if (menu != null && menu.slots.size() > slot && menu.getSlot(slot).hasItem()) {
                menu.getSlot(slot).remove(count);
            }
        }
    }
}