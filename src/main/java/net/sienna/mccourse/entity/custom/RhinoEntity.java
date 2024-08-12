package net.sienna.mccourse.entity.custom;

import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.sienna.mccourse.entity.ModEntities;
import net.sienna.mccourse.entity.ai.RhinoAttackGoal;
import net.sienna.mccourse.entity.variant.RhinoVariant;
import org.jetbrains.annotations.Nullable;


//When making a mob spawn in the world, add it to the Mod Biome Modifiers, AND the ModEventBusEvents!
public class RhinoEntity extends TamableAnimal implements PlayerRideable {
    public RhinoEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.RHINO.get().create(serverLevel);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    //For an attack, we need a new animation state
    public final AnimationState attackAnimationState = new AnimationState();
    //New sitting animation state
    public final AnimationState sitAnimationState = new AnimationState();

    public int attackAnimationTimeout = 0;
    private int idleAnimationTimeout = 0;

    //Entity data accessor is basically data that should be saved in client and in server.
    //Kind of similar to blockstates, this is for EACH rhino.
    //This is things like attacking state, or variant state
    //Remember, whenever you add one of these, ADD IT TO THE DEFINESYNCHEDDATA METHOD!
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(RhinoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT =
            SynchedEntityData.defineId(RhinoEntity.class, EntityDataSerializers.INT);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACKING, false);
        builder.define(DATA_ID_TYPE_VARIANT, 0);
    }

    //This is basically the custom AI for the entity. Won't go too crazy, but for example, if I were to make a boss...
    @Override
    protected void registerGoals() {
        //The lower the number, the higher the priority
        //This is to stop the dumb thing drowning :D
        this.goalSelector.addGoal(0, new FloatGoal(this));
        //This gives it a small speed boost when following a parent
        this.goalSelector.addGoal(1, new FollowParentGoal(this, 1.1d));
        //Makes it avoid water
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.8));
        //Makes it look at the player (when within 4 blocks)
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 4f));
        //Make it randomly look around.
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        //Gives it a target to attack - in this case, it'll TARGET something that hits it
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        //This is where the rhino actually attacks! We need to make the logic for this. This will be stored in the ai package.
        this.goalSelector.addGoal(1, new RhinoAttackGoal(this, 2, true));
        //This is for taming!
        this.goalSelector.addGoal(4, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.25d, 18f, 7f));
        //This is for breeding
        this.goalSelector.addGoal(2, new BreedGoal(this, 1f));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1f, Ingredient.of(Items.COOKED_BEEF), true));
    }

    //This gives our rhino some attributes, like its health, move speed etc
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 35)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.ARMOR_TOUGHNESS, 0.1f)
                .add(Attributes.ATTACK_DAMAGE, 2f)
                //Apparently you have to add this too when using createLivingAttributes
                .add(Attributes.FOLLOW_RANGE, 160)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.STEP_HEIGHT, 1f);


    }

    //Three methods that are basically always the same for this kind of mob. See the camel class
    private void setupAnimationStates() {
        //This is basically for the keyframe animation system. Here for the idle animation
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            this.idleAnimationTimeout--;
        }

        //We want to set it to attacking only if the rhino is actually attacking!
        if (this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = 80; //We set this to 4 seconds (80 ticks) because the attack animation is 4 seconds long.)
            attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAnimationTimeout;
        }

        if(!this.isAttacking()) {
            attackAnimationState.stop();
        }
        //For sitting
        if(this.isInSittingPose()) {
            sitAnimationState.startIfStopped(this.tickCount);
        } else {
            sitAnimationState.stop();
        }
    }
    //And here for the walk animation
    protected void updateWalkAnimation(float v) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(v * 6.0f, 1.0f);
        } else {
            f = 0.0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }

    //Executed every tick
    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    public void setAttacking(Boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    //FOR THE VARIANTS: AGAIN COPIED FROM HORSE.
    public RhinoVariant getVariant() {
        return RhinoVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(RhinoVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    //This is where we set what variants spawn.
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        RhinoVariant variant = Util.getRandom(RhinoVariant.values(), this.random);
        this.setVariant(variant);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }
    //We need to SAVE the rhino's data so it stays when we leave and rejoin (like the lamp!)
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(DATA_ID_TYPE_VARIANT, compound.getInt("Variant"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getTypeVariant());
    }

    //Sounds! Can also do custom sounds!
    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.HOGLIN_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.RAVAGER_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }

    //TAMEABLE! To make it tameable, now the class will extend "TameableAnimal" and not just "Animal".
    //Can look at how tameable entities react.
    //This is the method that is called when you right-click a mob.
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();

        Item itemForTaming = Items.APPLE;

        if(item == itemForTaming && !isTame()) {
            if(this.level().isClientSide) {
                return InteractionResult.CONSUME;
            } else {
                if(!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                if (!EventHooks.onAnimalTame(this, player)) {
                    super.tame(player);
                    this.navigation.recomputePath();
                    this.setTarget(null);
                    this.level().broadcastEntityEvent(this, (byte)7);
                    setOrderedToSit(true);
                    this.setInSittingPose(true);
                }
                return InteractionResult.SUCCESS;
            }
        }
        //Toggles sitting for the entity when they are tamed.
        if(isTame() && hand == InteractionHand.MAIN_HAND && !isFood(itemstack)) {

            if(!player.isCrouching()) {
                setRiding(player);
            } else {
                setOrderedToSit(!isOrderedToSit());
                setInSittingPose(!isOrderedToSit());
            }
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }
    //Rideable stuff!
    //Copied and pasted from pig.

    private void setRiding(Player player) {
        this.setInSittingPose(false);

        player.setYRot(this.getYRot());
        player.setXRot(this.getXRot());
        player.startRiding(this);
    }


    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        Direction direction = this.getMotionDirection();
        if (direction.getAxis() == Direction.Axis.Y) {
            return super.getDismountLocationForPassenger(passenger);
        } else {
            int[][] aint = DismountHelper.offsetsForDirection(direction);
            BlockPos blockpos = this.blockPosition();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
            UnmodifiableIterator var6 = passenger.getDismountPoses().iterator();

            while (var6.hasNext()) {
                Pose pose = (Pose) var6.next();
                AABB aabb = passenger.getLocalBoundsForPose(pose);
                int[][] var9 = aint;
                int var10 = aint.length;

                for (int var11 = 0; var11 < var10; ++var11) {
                    int[] aint1 = var9[var11];
                    blockpos$mutableblockpos.set(blockpos.getX() + aint1[0], blockpos.getY(), blockpos.getZ() + aint1[1]);
                    double d0 = this.level().getBlockFloorHeight(blockpos$mutableblockpos);
                    if (DismountHelper.isBlockFloorValid(d0)) {
                        Vec3 vec3 = Vec3.upFromBottomCenterOf(blockpos$mutableblockpos, d0);
                        if (DismountHelper.canDismountTo(this.level(), passenger, aabb.move(vec3))) {
                            passenger.setPose(pose);
                            return vec3;
                        }
                    }
                }
            }

            return super.getDismountLocationForPassenger(passenger);
        }
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return (LivingEntity) this.getFirstPassenger();
    }

    @Override
    public void travel(Vec3 travelVector) {
        if(this.isVehicle() && getControllingPassenger() instanceof Player) {
            LivingEntity passenger = this.getControllingPassenger();
            this.setYRot(passenger.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(passenger.getXRot()*0.5f);
            this.setRot(this.getYRot(), this.getXRot());
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.yBodyRot;
            float f = passenger.xxa * 0.5f;
            float f1 = passenger.zza;

            if(isControlledByLocalInstance()) {
                float newSpeed = (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED);

                if(Minecraft.getInstance().options.keySprint.isDown()) {
                    newSpeed *= 2f;
                }

                this.setSpeed(newSpeed);
                super.travel(new Vec3(f, travelVector.y, f1));
            }
        } else {
            super.travel(travelVector);
        }
    }

    //Breeding
    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Items.COOKED_BEEF);
    }

    //BOSS BAR THINGS - I LIKE THIS. A boss is always a boss EVENT. We want to know who is in the event.
//    private final ServerBossEvent bossEvent =
//            new ServerBossEvent(Component.literal("our cool rhino"), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.NOTCHED_10);
//
//    @Override
//    public void startSeenByPlayer(ServerPlayer serverPlayer) {
//        super.startSeenByPlayer(serverPlayer);
//        this.bossEvent.addPlayer(serverPlayer);
//    }
//
//    @Override
//    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
//        super.stopSeenByPlayer(serverPlayer);
//        this.bossEvent.removePlayer(serverPlayer);
//    }
//
//    @Override
//    public void aiStep() {
//        super.aiStep();
//        this.bossEvent.setProgress(this.getHealth()/this.getMaxHealth()); //Updates the bossbar.
//    }
}
