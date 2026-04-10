package net.mcreator.Lwlwlwlmech.block.renderer;

import net.mcreator.Lwlwlwlmech.block.display.MechCreationStationDisplayItem;
import net.mcreator.Lwlwlwlmech.block.model.MechCreationStationDisplayModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class MechCreationStationDisplayItemRenderer extends GeoItemRenderer<MechCreationStationDisplayItem> {
    public MechCreationStationDisplayItemRenderer() {
        super(new MechCreationStationDisplayModel());
    }

    @Override
    public RenderType getRenderType(MechCreationStationDisplayItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }
}