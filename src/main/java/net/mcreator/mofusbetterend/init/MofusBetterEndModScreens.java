package net.mcreator.mofusbetterend.init;

import net.mcreator.mofusbetterend.client.gui.MechCreationStationGUIScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = "mofus_better_end_", value = Dist.CLIENT)
public class MofusBetterEndModScreens {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(MofusBetterEndModMenus.MECH_CREATION_STATION_GUI.get(), MechCreationStationGUIScreen::new);
    }
}