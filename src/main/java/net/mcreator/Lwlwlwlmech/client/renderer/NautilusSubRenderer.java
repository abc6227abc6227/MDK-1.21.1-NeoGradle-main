package net.mcreator.Lwlwlwlmech.client.renderer;

import net.mcreator.Lwlwlwlmech.entity.NautilusSubEntity;
import net.mcreator.Lwlwlwlmech.entity.layer.NautilusSubLayer;
import net.mcreator.Lwlwlwlmech.entity.model.NautilusSubModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class NautilusSubRenderer extends GeoEntityRenderer<NautilusSubEntity> {

    public NautilusSubRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new NautilusSubModel());
        this.shadowRadius = 0.0f;
        this.addRenderLayer(new NautilusSubLayer(this));
    }
}