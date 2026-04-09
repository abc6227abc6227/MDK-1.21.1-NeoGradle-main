package net.mcreator.mofusbetterend.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class MofusBetterEndModTabs {
    public static final DeferredRegister<CreativeModeTab> REGISTRY =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "mofus_better_end_");

    public static final Supplier<CreativeModeTab> ASTEROID_PATH_BIOME =
            REGISTRY.register("asteroid_path_biome", () -> CreativeModeTab.builder()
                    .title(Component.translatable("item_group.mofus_better_end_.asteroid_path_biome"))
                    .icon(() -> new ItemStack(MofusBetterEndModBlocks.MECH_CREATION_STATION.get()))
                    .displayItems((parameters, output) -> {
                        // 鹦鹉螺号相关
                        output.accept(MofusBetterEndModItems.NAUTILUS_SUB_BLUEPRINT.get());
                        output.accept(MofusBetterEndModItems.NAUTILUS_THRUSTER.get());
                        output.accept(MofusBetterEndModItems.TITANIUM_INGOT.get());

                        // 制造站
                        output.accept(MofusBetterEndModBlocks.MECH_CREATION_STATION.get());
                    })
                    .build());
}