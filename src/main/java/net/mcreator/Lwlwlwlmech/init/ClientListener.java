package net.mcreator.Lwlwlwlmech.init;

import net.mcreator.Lwlwlwlmech.LwlwlwlmechMod;
import net.mcreator.Lwlwlwlmech.block.renderer.MechCreationStationTileRenderer;
import net.mcreator.Lwlwlwlmech.client.renderer.NautilusSubRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = LwlwlwlmechMod.MODID, value = Dist.CLIENT)
public class ClientListener {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                LwlwlwlmechModBlockEntities.MECH_CREATION_STATION.get(),
                context -> new MechCreationStationTileRenderer()
        );
        event.registerEntityRenderer(
                LwlwlwlmechModEntities.NAUTILUS_SUB.get(),
                NautilusSubRenderer::new
        );
    }
}