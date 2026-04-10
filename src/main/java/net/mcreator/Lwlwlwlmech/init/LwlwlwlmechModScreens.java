package net.mcreator.Lwlwlwlmech.init;

import net.mcreator.Lwlwlwlmech.client.gui.MechCreationStationGUIScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = "lwlwlwl_mech_", value = Dist.CLIENT)
public class LwlwlwlmechModScreens {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(LwlwlwlmechModMenus.MECH_CREATION_STATION_GUI.get(), MechCreationStationGUIScreen::new);
    }
}