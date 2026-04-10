package net.mcreator.Lwlwlwlmech.block.model;

import net.mcreator.Lwlwlwlmech.LwlwlwlmechMod;
import net.mcreator.Lwlwlwlmech.block.display.MechCreationStationDisplayItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MechCreationStationDisplayModel extends GeoModel<MechCreationStationDisplayItem> {

    @Override
    public ResourceLocation getAnimationResource(MechCreationStationDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(LwlwlwlmechMod.MODID, "animations/mechcreationstation.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(MechCreationStationDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(LwlwlwlmechMod.MODID, "geo/mechcreationstation.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MechCreationStationDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(LwlwlwlmechMod.MODID, "textures/block/mechcreationstation.png");
    }
}