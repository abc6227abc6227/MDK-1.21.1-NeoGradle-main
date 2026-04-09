package net.mcreator.mofusbetterend.block.model;

import net.mcreator.mofusbetterend.MofusBetterEndMod;
import net.mcreator.mofusbetterend.block.entity.MechCreationStationTileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MechCreationStationBlockModel extends GeoModel<MechCreationStationTileEntity> {

    @Override
    public ResourceLocation getAnimationResource(MechCreationStationTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MofusBetterEndMod.MODID, "animations/mechcreationstation.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(MechCreationStationTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MofusBetterEndMod.MODID, "geo/mechcreationstation.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MechCreationStationTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(MofusBetterEndMod.MODID, "textures/block/mechcreationstation.png");
    }
}