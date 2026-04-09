package net.mcreator.mofusbetterend.client.renderer;

import net.mcreator.mofusbetterend.entity.NautilusSubEntity;
import net.mcreator.mofusbetterend.entity.layer.NautilusSubLayer;
import net.mcreator.mofusbetterend.entity.model.NautilusSubModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class NautilusSubRenderer extends GeoEntityRenderer<NautilusSubEntity> {

    public NautilusSubRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new NautilusSubModel());
        this.shadowRadius = 0.0f;
        this.addRenderLayer(new NautilusSubLayer(this));
    }
}