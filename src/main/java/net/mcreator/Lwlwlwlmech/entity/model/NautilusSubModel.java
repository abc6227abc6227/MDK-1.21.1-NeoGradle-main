package net.mcreator.Lwlwlwlmech.entity.model;

import net.mcreator.Lwlwlwlmech.LwlwlwlmechMod;
import net.mcreator.Lwlwlwlmech.entity.NautilusSubEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class NautilusSubModel extends GeoModel<NautilusSubEntity> {

    @Override
    public ResourceLocation getAnimationResource(NautilusSubEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(LwlwlwlmechMod.MODID, "animations/nautilussubmarine.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(NautilusSubEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(LwlwlwlmechMod.MODID, "geo/nautilussubmarine.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NautilusSubEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(LwlwlwlmechMod.MODID, "textures/entities/" + entity.getTexture() + ".png");
    }
}