package net.mcreator.Lwlwlwlmech.entity;

import net.mcreator.Lwlwlwlmech.init.LwlwlwlmechEntities;
import net.mcreator.Lwlwlwlmech.init.LwlwlwlmechModKeyMappings;
import net.mcreator.Lwlwlwlmech.network.NautilusUpMessage;
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

import net.mcreator.Lwlwlwlmech.network.NautilusUpMessage;
import net.mcreator.Lwlwlwlmech.network.NautilusDownMessage;
import net.mcreator.Lwlwlwlmech.network.NautilusForwardMessage;
import net.mcreator.Lwlwlwlmech.network.NautilusThrustMessage;

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

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public String animationprocedure = "empty";
    private String prevAnim = "empty";

    // 按键状态（服务端用）
    private boolean forwardPressed = false;
    private boolean thrustPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    public void setForwardPressed(boolean pressed) { this.forwardPressed = pressed; }
    public void setThrustPressed(boolean pressed) { this.thrustPressed = pressed; }
    public void setUpPressed(boolean pressed) { this.upPressed = pressed; }
    public void setDownPressed(boolean pressed) { this.downPressed = pressed; }

    public NautilusSubEntity(EntityType<? extends NautilusSubEntity> type, Level world) {
        super(type, world);
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.setNoGravity(true);
    }

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
                System.out.println("[实体] 玩家开始骑乘");
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void positionRider(Entity passenger, Entity.MoveFunction callback) {
        callback.accept(passenger, this.getX(), this.getY() + 0.4, this.getZ());
    }

    @Override
    public void tick() {
        super.tick();

        Entity passenger = null;
        if (!this.getPassengers().isEmpty()) {
            passenger = this.getPassengers().get(0);
        }

        if (passenger == null) {
            return;
        }

        // 设置朝向
        this.setYRot(passenger.getYRot());

        // ========== 客户端：读取按键，发送网络消息 ==========
        if (this.level().isClientSide) {
            boolean up = LwlwlwlmechModKeyMappings.nautilusUp.isDown();
            boolean down = LwlwlwlmechModKeyMappings.nautilusDown.isDown();
            boolean forward = LwlwlwlmechModKeyMappings.nautilusForward.isDown();
            boolean thrust = LwlwlwlmechModKeyMappings.nautilusThrust.isDown();

            // 设置动画（同时保存到实体数据）
            String newAnim = thrust ? "thrust" : (forward ? "walk" : "idle");
            this.animationprocedure = newAnim;
            this.entityData.set(ANIMATION, newAnim);  // ← 同步到服务端

            // 同步到服务端（通过实体数据）
            this.getEntityData().set(ANIMATION, newAnim);


            // 根据按键设置动画
            if (thrust) {
                this.animationprocedure = "thrust";
            } else if (forward) {
                this.animationprocedure = "walk";
            } else if (up) {
                this.animationprocedure = "up";
            } else if (down) {
                this.animationprocedure = "down";
            } else {
                this.animationprocedure = "idle";
            }

            // 发送网络消息

            if (down != this.downPressed) {
                NautilusDownMessage.send(down ? 0 : 1);
            }
            if (forward != this.forwardPressed) {
                NautilusForwardMessage.send(forward ? 0 : 1);
            }
            if (thrust != this.thrustPressed) {
                NautilusThrustMessage.send(thrust ? 0 : 1);
            }
            if (up != this.upPressed) {
                NautilusUpMessage.send(up ? 0 : 1);
            }
        }

        // ========== 服务端：执行移动 ==========
        if (!this.level().isClientSide) {
            // 从实体数据读取动画（自动同步给所有客户端）
            this.animationprocedure = this.entityData.get(ANIMATION);
            double speed = thrustPressed ? 0.5 : (forwardPressed ? 0.2 : 0);
            double moveX = 0, moveY = 0, moveZ = 0;

            if (speed > 0) {
                double rad = Math.toRadians(this.getYRot());
                moveX = -Math.sin(rad) * speed;
                moveZ = Math.cos(rad) * speed;
            }
            if (upPressed) moveY = 0.3;
            if (downPressed) moveY = -0.3;

            if (speed > 0 || upPressed || downPressed) {
                this.setPos(this.getX() + moveX, this.getY() + moveY, this.getZ() + moveZ);
            }
            System.out.println("[移动] 上=" + upPressed + " 下=" + downPressed + " Y偏移=" + moveY);
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