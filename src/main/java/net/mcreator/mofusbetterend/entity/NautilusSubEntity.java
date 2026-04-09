package net.mcreator.mofusbetterend.entity;

import net.mcreator.mofusbetterend.init.MofusBetterEndModEntities;
import net.mcreator.mofusbetterend.init.MofusBetterEndModKeyMappings;
import net.mcreator.mofusbetterend.procedures.NautilusSubEntityDiesProcedure;
import net.mcreator.mofusbetterend.procedures.NautilusSubEntityIsHurtProcedure;
import net.mcreator.mofusbetterend.procedures.NautilusSubOnEntityTickUpdateProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.MoverType;

import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class NautilusSubEntity extends PathfinderMob implements GeoEntity {
    private static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(NautilusSubEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(NautilusSubEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(NautilusSubEntity.class, EntityDataSerializers.STRING);

    private float currentYaw = 0;
    private Vec3 targetPosition = Vec3.ZERO;

    private int syncCounter = 0;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public String animationprocedure = "empty";
    private String prevAnim = "empty";


    // 构造函数1：被 PlayMessages 构造函数调用
    public NautilusSubEntity(EntityType<? extends NautilusSubEntity> type, Level world) {
        super(type, world);
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.setNoGravity(true);
        this.currentYaw = this.getYRot();
        this.targetYaw = this.getYRot();
    }

    private boolean forwardPressed = false;
    private boolean thrustPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    public void setForwardPressed(boolean pressed) { this.forwardPressed = pressed; }
    public void setThrustPressed(boolean pressed) { this.thrustPressed = pressed; }
    public void setUpPressed(boolean pressed) { this.upPressed = pressed; }
    public void setDownPressed(boolean pressed) { this.downPressed = pressed; }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SHOOT, false);
        builder.define(ANIMATION, "undefined");
        builder.define(TEXTURE, "nautilussub");





    }

    public void setTexture(String texture) {
        this.entityData.set(TEXTURE, texture);
    }

    public String getTexture() {
        return this.entityData.get(TEXTURE);
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new FlyingPathNavigation(this, world);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.GENERIC_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.GENERIC_DEATH;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        NautilusSubEntityIsHurtProcedure.execute(this.level(), this, source);
        return super.hurt(source, amount);
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        NautilusSubEntityDiesProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture")) {
            this.setTexture(compound.getString("Texture"));
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level().isClientSide) {
            if (!player.isPassenger()) {
                player.startRiding(this);
                System.out.println("[实体] 玩家开始骑乘: " + player.getName().getString());
            }
        }
        return InteractionResult.SUCCESS;
    }
/*
    @Override
    public void rideTick() {
        super.rideTick();

        // 获取所有乘客
        if (!this.getPassengers().isEmpty()) {
            Entity passenger = this.getPassengers().get(0);
            // 强制让乘客的位置跟随鹦鹉螺号
            passenger.setPos(this.getX(), this.getY() + 0.4, this.getZ());
        }
    }

*/

    @Override
    protected void positionRider(Entity passenger, Entity.MoveFunction callback) {
        // 直接设置乘客位置到鹦鹉螺号上方，忽略默认计算
        double offsetY = 0.4;  // 乘客坐高
        callback.accept(passenger, this.getX(), this.getY() + offsetY, this.getZ());
    }
/*
    @Override
    public void onPassengerTurned(Entity passenger) {
        super.onPassengerTurned(passenger);
        passenger.setPos(passenger.getX(), this.getY() + 0.4, passenger.getZ());
    }
*/
    private float smoothYaw = 0;
    private float targetYaw = 0;


    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide && this.getControllingPassenger() instanceof Player player) {
            // 方法1：使用移动输入
            float forward = player.zza;

            // 方法2：使用冲刺键测试
            boolean sprinting = player.isSprinting();

            if (sprinting) {
                double rad = Math.toRadians(player.getYRot());
                this.setPos(this.getX() - Math.sin(rad) * 0.2, this.getY(), this.getZ() + Math.cos(rad) * 0.2);
                System.out.println("[移动] 冲刺中");
            }
        }
    }


    @Override
    public void travel(Vec3 travelVector) {
        if (this.isVehicle() && this.getControllingPassenger() instanceof LivingEntity passenger) {
        /*    // 让鹦鹉螺号朝向乘客的方向
            this.setYRot(passenger.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(passenger.getXRot() * 0.5f);
            this.setRot(this.getYRot(), this.getXRot());
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.getYRot();
*/
            // 打印当前朝向
            System.out.println("[实体] 当前朝向: " + this.getYRot());

            super.travel(new Vec3(passenger.xxa, 0, passenger.zza));
            return;
        }
        super.travel(travelVector);
    }



    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.MAX_HEALTH, 100)
                .add(Attributes.FOLLOW_RANGE, 16);
    }

    private PlayState movementPredicate(AnimationState<NautilusSubEntity> event) {
        if (this.animationprocedure.equals("empty")) {
            boolean isMoving = event.isMoving();
            return event.setAndContinue(RawAnimation.begin().thenLoop(isMoving ? "walk" : "idle"));
        }
        return PlayState.STOP;
    }

    private PlayState procedurePredicate(AnimationState<NautilusSubEntity> event) {
        if (!this.animationprocedure.equals("empty")) {
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED || !this.animationprocedure.equals(this.prevAnim)) {
                if (!this.animationprocedure.equals(this.prevAnim)) {
                    event.getController().forceAnimationReset();
                }
                event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
                if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                    this.animationprocedure = "empty";
                    event.getController().forceAnimationReset();
                }
            }
            this.prevAnim = this.animationprocedure;
            return PlayState.CONTINUE;
        } else {
            this.prevAnim = "empty";
            return PlayState.STOP;
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
        controllers.add(new AnimationController<>(this, "procedure", 4, this::procedurePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}