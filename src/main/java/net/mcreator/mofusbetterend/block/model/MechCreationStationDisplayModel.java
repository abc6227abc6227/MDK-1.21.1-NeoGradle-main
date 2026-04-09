package net.mcreator.mofusbetterend.block.model;

import net.mcreator.mofusbetterend.MofusBetterEndMod;
import net.mcreator.mofusbetterend.block.display.MechCreationStationDisplayItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MechCreationStationDisplayModel extends GeoModel<MechCreationStationDisplayItem> {

    @Override
    public ResourceLocation getAnimationResource(MechCreationStationDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(MofusBetterEndMod.MODID, "animations/mechcreationstation.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(MechCreationStationDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(MofusBetterEndMod.MODID, "geo/mechcreationstation.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MechCreationStationDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(MofusBetterEndMod.MODID, "textures/block/mechcreationstation.png");
    }
}