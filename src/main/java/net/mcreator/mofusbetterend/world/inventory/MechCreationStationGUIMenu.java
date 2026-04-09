package net.mcreator.mofusbetterend.world.inventory;

import net.mcreator.mofusbetterend.init.MofusBetterEndModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class MechCreationStationGUIMenu extends AbstractContainerMenu {
    // 静态变量，用于在两次构造函数调用之间传递坐标
    private static BlockPos savedPos = BlockPos.ZERO;

    public final Player player;
    private final BlockPos pos;
    private final ItemStackHandler inventory = new ItemStackHandler(17);

    // 构造函数1：服务器端创建（坐标正确）
    public MechCreationStationGUIMenu(int id, Inventory inv, BlockPos pos) {
        super(MofusBetterEndModMenus.MECH_CREATION_STATION_GUI.get(), id);
        this.player = inv.player;

        // 保存坐标供第二次调用使用
        savedPos = pos;
        this.pos = pos;

        System.out.println("[GUI菜单] 构造函数1接收坐标: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());

        initSlots(inv);
    }

    // 构造函数2：客户端从网络包创建（buf 为 null，坐标错误）
    public MechCreationStationGUIMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        super(MofusBetterEndModMenus.MECH_CREATION_STATION_GUI.get(), id);
        this.player = inv.player;

        // 使用保存的坐标
        this.pos = savedPos;

        System.out.println("[GUI菜单] 构造函数2接收buf，使用保存坐标: " + this.pos.getX() + ", " + this.pos.getY() + ", " + this.pos.getZ());

        initSlots(inv);
    }

    private void initSlots(Inventory inv) {
        // 17个槽位
        int[][] slots = {
                {21,28}, {63,28}, {63,46}, {63,64}, {63,82},
                {81,28}, {81,46}, {81,64}, {81,82},
                {99,28}, {99,46}, {99,64}, {99,82},
                {117,28}, {117,46}, {117,64}, {117,82}
        };
        for (int i = 0; i < 17; i++) {
            this.addSlot(new SlotItemHandler(inventory, i, slots[i][0], slots[i][1]));
        }

        // 玩家背包
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inv, col + row * 9 + 9, 45 + col * 18, 131 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inv, col, 45 + col * 18, 189));
        }
    }

    public BlockPos getPos() {
        return this.pos;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            stack = slotStack.copy();
            if (index < 17) {
                if (!this.moveItemStackTo(slotStack, 17, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotStack, 0, 17, false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return stack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}