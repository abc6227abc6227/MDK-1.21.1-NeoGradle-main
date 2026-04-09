package net.mcreator.mofusbetterend.entity.model;

import net.mcreator.mofusbetterend.MofusBetterEndMod;
import net.mcreator.mofusbetterend.entity.NautilusSubEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class NautilusSubModel extends GeoModel<NautilusSubEntity> {

    @Override
    public ResourceLocation getAnimationResource(NautilusSubEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(MofusBetterEndMod.MODID, "animations/nautilussubmarine.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(NautilusSubEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(MofusBetterEndMod.MODID, "geo/nautilussubmarine.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NautilusSubEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(MofusBetterEndMod.MODID, "textures/entities/" + entity.getTexture() + ".png");
    }
}