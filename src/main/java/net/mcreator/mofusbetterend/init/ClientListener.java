package net.mcreator.mofusbetterend.init;

import net.mcreator.mofusbetterend.MofusBetterEndMod;
import net.mcreator.mofusbetterend.block.renderer.MechCreationStationTileRenderer;
import net.mcreator.mofusbetterend.client.renderer.NautilusSubRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = MofusBetterEndMod.MODID, value = Dist.CLIENT)
public class ClientListener {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                MofusBetterEndModBlockEntities.MECH_CREATION_STATION.get(),
                context -> new MechCreationStationTileRenderer()
        );
        event.registerEntityRenderer(
                MofusBetterEndModEntities.NAUTILUS_SUB.get(),
                NautilusSubRenderer::new
        );
    }
}