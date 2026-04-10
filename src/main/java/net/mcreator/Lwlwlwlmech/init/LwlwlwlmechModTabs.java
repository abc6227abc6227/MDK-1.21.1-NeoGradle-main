package net.mcreator.Lwlwlwlmech.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class LwlwlwlmechModTabs {
    public static final DeferredRegister<CreativeModeTab> REGISTRY =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "lwlwlwl_mech_");

    public static final Supplier<CreativeModeTab> ASTEROID_PATH_BIOME =
            REGISTRY.register("asteroid_path_biome", () -> CreativeModeTab.builder()
                    .title(Component.translatable("item_group.lwlwlwl_mech_.asteroid_path_biome"))
                    .icon(() -> new ItemStack(LwlwlwlmechModBlocks.MECH_CREATION_STATION.get()))
                    .displayItems((parameters, output) -> {
                        // 鹦鹉螺号相关
                        output.accept(LwlwlwlmechModItems.NAUTILUS_SUB_BLUEPRINT.get());
                        output.accept(LwlwlwlmechModItems.NAUTILUS_THRUSTER.get());
                        output.accept(LwlwlwlmechModItems.TITANIUM_INGOT.get());

                        // 制造站
                        output.accept(LwlwlwlmechModBlocks.MECH_CREATION_STATION.get());
                    })
                    .build());
}