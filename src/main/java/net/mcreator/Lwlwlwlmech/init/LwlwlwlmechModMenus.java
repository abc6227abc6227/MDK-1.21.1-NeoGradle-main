package net.mcreator.Lwlwlwlmech.init;

import net.mcreator.Lwlwlwlmech.LwlwlwlmechMod;
import net.mcreator.Lwlwlwlmech.world.inventory.MechCreationStationGUIMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class LwlwlwlmechModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, LwlwlwlmechMod.MODID);

    public static final Supplier<MenuType<MechCreationStationGUIMenu>> MECH_CREATION_STATION_GUI =
            MENUS.register("mech_creation_station_gui", () -> IMenuTypeExtension.create(
                    (containerId, inv, buf) -> {
                        // buf 在客户端打开菜单时可能为 null，需要处理
                        //BlockPos pos = buf != null ? buf.readBlockPos() : BlockPos.ZERO;
                        return new MechCreationStationGUIMenu(containerId, inv, BlockPos.ZERO);
                    }
            ));
}