package net.mcreator.Lwlwlwlmech.block.entity;

import net.mcreator.Lwlwlwlmech.block.MechCreationStationBlock;
import net.mcreator.Lwlwlwlmech.init.LwlwlwlmechModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;





public class MechCreationStationTileEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public final ItemStackHandler inventory = new ItemStackHandler(17);
    private String prevAnim = "0";

    public MechCreationStationTileEntity(BlockPos pos, BlockState state) {
        super(LwlwlwlmechModBlockEntities.MECH_CREATION_STATION.get(), pos, state);
    }

    private PlayState predicate(AnimationState<MechCreationStationTileEntity> event) {
        String anim = "" + getBlockState().getValue(MechCreationStationBlock.ANIMATION);

        if (anim.equals("0")) {
            // 空闲状态：循环播放空闲动画
            event.getController().setAnimation(RawAnimation.begin().thenLoop("0"));
            return PlayState.CONTINUE;
        }

        // 制造动画：播放一次
        event.getController().setAnimation(RawAnimation.begin().thenPlay(anim));
        return PlayState.CONTINUE;
    }

    private PlayState procedurePredicate(AnimationState<MechCreationStationTileEntity> event) {
        String anim = "" + getBlockState().getValue(MechCreationStationBlock.ANIMATION);

        if (!anim.equals("0")) {
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED || !anim.equals(prevAnim)) {
                if (!anim.equals(prevAnim)) {
                    event.getController().forceAnimationReset();
                }
                event.getController().setAnimation(RawAnimation.begin().thenPlay(anim));
                if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                    if (level != null) {
                        level.setBlockAndUpdate(worldPosition, getBlockState().setValue(MechCreationStationBlock.ANIMATION, 0));
                    }
                    event.getController().forceAnimationReset();
                }
            }
            prevAnim = anim;
            return PlayState.CONTINUE;
        } else {
            prevAnim = "0";
            // 空闲时循环播放空闲动画
            event.getController().setAnimation(RawAnimation.begin().thenLoop("0"));
            return PlayState.CONTINUE;
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllers.add(new AnimationController<>(this, "procedurecontroller", 0, this::procedurePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        CompoundTag invTag = inventory.serializeNBT(registries);
        tag.put("Inventory", invTag);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(registries, tag.getCompound("Inventory"));
        }
    }

    public void drops() {
        SimpleContainer container = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++) {
            container.setItem(i, inventory.getStackInSlot(i));
        }
        Containers.dropContents(level, worldPosition, container);
    }
}







/*
public class MechCreationStationTileEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public final ItemStackHandler inventory = new ItemStackHandler(17);
    private String prevAnim = "0";

    public MechCreationStationTileEntity(BlockPos pos, BlockState state) {
        super(LwlwlwlmechModBlockEntities.MECH_CREATION_STATION.get(), pos, state);
    }

    private PlayState predicate(AnimationState<MechCreationStationTileEntity> event) {
        String anim = "" + getBlockState().getValue(MechCreationStationBlock.ANIMATION);
        if (anim.equals("0")) {
            return PlayState.STOP;
        }
        event.getController().setAnimation(RawAnimation.begin().thenLoop(anim));
        return PlayState.CONTINUE;
    }

    private PlayState procedurePredicate(AnimationState<MechCreationStationTileEntity> event) {
        String anim = "" + getBlockState().getValue(MechCreationStationBlock.ANIMATION);
/*123
        if (anim.equals("0")) {
            // 播放空闲动画（动画 "0" 应该定义机械臂的收起状态）
            event.getController().setAnimation(RawAnimation.begin().thenLoop("0"));
            return PlayState.CONTINUE;
        }

//123
        if (!anim.equals("0")) {

            // 其他动画（1, 2, 3）播放一次
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED || !anim.equals(prevAnim)) {
                if (!anim.equals(prevAnim)) {
                    event.getController().forceAnimationReset();
                }
                event.getController().setAnimation(RawAnimation.begin().thenPlay(anim));
                if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                    if (level != null) {
                        level.setBlockAndUpdate(worldPosition, getBlockState().setValue(MechCreationStationBlock.ANIMATION, 0));
                    }
                    event.getController().forceAnimationReset();
                }
            }
            prevAnim = anim;
            return PlayState.CONTINUE;
        } else {
            prevAnim = "0";
            return PlayState.STOP;
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
        controllers.add(new AnimationController<>(this, "procedurecontroller", 0, this::procedurePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        CompoundTag invTag = inventory.serializeNBT(registries);
        tag.put("Inventory", invTag);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("Inventory")) {
            // 注意：第一个参数是 registries，第二个参数是 CompoundTag
            inventory.deserializeNBT(registries, tag.getCompound("Inventory"));
        }
    }

    public void drops() {
        SimpleContainer container = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++) {
            container.setItem(i, inventory.getStackInSlot(i));
        }
        Containers.dropContents(level, worldPosition, container);
    }
}
*/