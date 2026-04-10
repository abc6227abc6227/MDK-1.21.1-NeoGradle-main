package net.mcreator.Lwlwlwlmech.block.model;

import net.mcreator.Lwlwlwlmech.LwlwlwlmechMod;
import net.mcreator.Lwlwlwlmech.block.entity.MechCreationStationTileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MechCreationStationBlockModel extends GeoModel<MechCreationStationTileEntity> {

    @Override
    public ResourceLocation getAnimationResource(MechCreationStationTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(LwlwlwlmechMod.MODID, "animations/mechcreationstation.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(MechCreationStationTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(LwlwlwlmechMod.MODID, "geo/mechcreationstation.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MechCreationStationTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(LwlwlwlmechMod.MODID, "textures/block/mechcreationstation.png");
    }
}