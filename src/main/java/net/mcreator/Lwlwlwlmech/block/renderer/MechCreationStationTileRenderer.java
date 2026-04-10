package net.mcreator.Lwlwlwlmech.block.renderer;

import net.mcreator.Lwlwlwlmech.block.entity.MechCreationStationTileEntity;
import net.mcreator.Lwlwlwlmech.block.model.MechCreationStationBlockModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class MechCreationStationTileRenderer extends GeoBlockRenderer<MechCreationStationTileEntity> {

    public MechCreationStationTileRenderer() {
        super(new MechCreationStationBlockModel());
    }

    @Override
    public RenderType getRenderType(MechCreationStationTileEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }
}