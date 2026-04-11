package net.mcreator.Lwlwlwlmech.block.model;

import net.mcreator.Lwlwlwlmech.LwlwlwlmechMod;
import net.mcreator.Lwlwlwlmech.block.MechCreationStationBlock;
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
        // 根据动画状态选择不同的几何模型
        String anim = "" + animatable.getBlockState().getValue(MechCreationStationBlock.ANIMATION);

        if (anim.equals("0")) {
            // 空闲状态：使用收起状态的模型
            return ResourceLocation.fromNamespaceAndPath(LwlwlwlmechMod.MODID, "geo/mechcreationstation_idle.geo.json");
        } else {
            // 制造动画：使用可动状态的模型
            return ResourceLocation.fromNamespaceAndPath(LwlwlwlmechMod.MODID, "geo/mechcreationstation_animate.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(MechCreationStationTileEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(LwlwlwlmechMod.MODID, "textures/block/mechcreationstation.png");
    }
}