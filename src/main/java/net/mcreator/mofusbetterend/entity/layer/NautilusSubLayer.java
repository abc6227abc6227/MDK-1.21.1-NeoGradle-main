package net.mcreator.mofusbetterend.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mcreator.mofusbetterend.MofusBetterEndMod;
import net.mcreator.mofusbetterend.entity.NautilusSubEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class NautilusSubLayer extends GeoRenderLayer<NautilusSubEntity> {
    private static final ResourceLocation LAYER = ResourceLocation.fromNamespaceAndPath(
            MofusBetterEndMod.MODID, "textures/entities/nautilussubglow.png");

    public NautilusSubLayer(GeoRenderer<NautilusSubEntity> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(PoseStack poseStack, NautilusSubEntity animatable, BakedGeoModel bakedModel,
                       RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
                       float partialTick, int packedLight, int packedOverlay) {
        RenderType glowRenderType = RenderType.eyes(LAYER);
        this.getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, glowRenderType,
                bufferSource.getBuffer(glowRenderType), partialTick, packedLight, packedLight, OverlayTexture.NO_OVERLAY);
    }
}