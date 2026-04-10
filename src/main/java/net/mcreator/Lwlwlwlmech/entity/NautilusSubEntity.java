package net.mcreator.Lwlwlwlmech.entity;

import net.mcreator.Lwlwlwlmech.init.LwlwlwlmechModEntities;
import net.mcreator.Lwlwlwlmech.init.LwlwlwlmechModKeyMappings;
import net.mcreator.Lwlwlwlmech.procedures.NautilusSubEntityDiesProcedure;
import net.mcreator.Lwlwlwlmech.procedures.NautilusSubEntityIsHurtProcedure;
import net.mcreator.Lwlwlwlmech.procedures.NautilusSubOnEntityTickUpdateProcedure;
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
        super.tick();  // ← 必须加上

        Entity passenger = null;
        if (!this.getPassengers().isEmpty()) {
            passenger = this.getPassengers().get(0);
        }

        if (passenger == null) {
            return;
        }

        // 设置朝向（服务端和客户端都执行）
        this.setYRot(passenger.getYRot());

        // 只在服务端执行移动
        if (!this.level().isClientSide) {
            CompoundTag data = this.getPersistentData();
            boolean forward = data.getDouble("nautilusF") == 1;
            boolean thrust = data.getDouble("NautilusThrust") == 1;
            boolean up = data.getDouble("nautilusup") == 1;
            boolean down = data.getDouble("nautilusdown") == 1;

            double speed = thrust ? 0.5 : (forward ? 0.2 : 0);
            double moveX = 0, moveY = 0, moveZ = 0;

            if (speed > 0) {
                double rad = Math.toRadians(this.getYRot());
                moveX = -Math.sin(rad) * speed;
                moveZ = Math.cos(rad) * speed;
            }
            if (up) moveY = 0.3;
            if (down) moveY = -0.3;

            if (speed > 0 || up || down) {
                this.setPos(this.getX() + moveX, this.getY() + moveY, this.getZ() + moveZ);
                passenger.setPos(this.getX(), this.getY() + 1.2, this.getZ());
            }
        }

        // 客户端只负责读取按键
        if (this.level().isClientSide) {
            boolean up = LwlwlwlmechModKeyMappings.nautilusUp.isDown();
            boolean down = LwlwlwlmechModKeyMappings.nautilusDown.isDown();
            boolean forward = LwlwlwlmechModKeyMappings.nautilusForward.isDown();
            boolean thrust = LwlwlwlmechModKeyMappings.nautilusThrust.isDown();

            this.getPersistentData().putDouble("nautilusup", up ? 1 : 0);
            this.getPersistentData().putDouble("nautilusdown", down ? 1 : 0);
            this.getPersistentData().putDouble("nautilusF", forward ? 1 : 0);
            this.getPersistentData().putDouble("NautilusThrust", thrust ? 1 : 0);
        }
    }


    @Override
    public void travel(Vec3 travelVector) {
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