package net.sienna.mccourse.entity.custom;

import jdk.jfr.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.sienna.mccourse.entity.ModEntities;
import net.sienna.mccourse.particle.ModParticles;
import net.sienna.mccourse.sound.ModSounds;

//Custom projectile!
public class MagicProjectileEntity extends Projectile {
    public MagicProjectileEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }


    public MagicProjectileEntity(Level level, Player player) {
        super(ModEntities.MAGIC_PROJECTILE.get(), level);
        setOwner(player);
        BlockPos blockPos = player.blockPosition(); //This handles where the entity spawns.
        double d0 = (double)blockPos.getX()+0.5;
        double d1 = (double)blockPos.getY()+1.75;
        double d2 = (double)blockPos.getZ()+0.5;
        this.moveTo(d0, d1, d2, this.getYRot(), this.getXRot());
    }

    private static final EntityDataAccessor<Boolean> HIT =
            SynchedEntityData.defineId(MagicProjectileEntity.class, EntityDataSerializers.BOOLEAN);
    private int counter = 0;

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(HIT, false);
    }

    @Override //This is where we define the movement.
    public void tick() {
        super.tick();
        //If this particle does hit something, then it will be discarded
        if(this.entityData.get(HIT)) {
            if(this.tickCount >= counter) {
                this.discard();
            }
        }

        if (this.tickCount >= 300) {
            this.remove(RemovalReason.DISCARDED);
        }
        //Movement vector in which this projectile will move
        Vec3 vec3 = this.getDeltaMovement();
        //Hit result
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        //This gets called if it hits a block or an entity
        if (hitresult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact(this, hitresult))
            this.onHit(hitresult);
        //If no hit, then the delta movement is added onto the position
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();

        double d5 = vec3.x;
        double d6 = vec3.y;
        double d7 = vec3.z;

        //Particles to make it look cool :D
        for(int i = 1; i < 5; ++i) {
            this.level().addParticle(ModParticles.ALEXANDRITE_PARTICLE.get(), d0-(d5*2), d1-(d6*2), d2-(d7*2),
                    -d5, -d6 - 0.1D, -d7);
        }

        //If we're not in air, discard the projectile
        if (this.level().getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
            this.discard();
        } else if (this.isInWaterOrBubble()) {
            this.discard();
        } else {
            this.setDeltaMovement(vec3.scale(0.99F));
            this.setPos(d0, d1, d2);
        }

    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        Entity hitEntity = hitResult.getEntity();
        Entity owner = this.getOwner();
        //Don't hit the owner!
        if(hitEntity == owner && this.level().isClientSide()) {
            return;
        }

        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.METAL_DETECTOR_FOUND_ORE.get(), SoundSource.NEUTRAL,
                2F, 1F);
        //Deals damage when it hits a thing
        LivingEntity livingentity = owner instanceof LivingEntity ? (LivingEntity)owner : null;
        float damage = 2f;
        boolean hurt = hitEntity.hurt(this.damageSources().mobProjectile(this, livingentity), damage);
        if (hurt) {
            if(hitEntity instanceof LivingEntity livingHitEntity) {
                livingHitEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 1), owner);
            }
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        //Spawns particles when it hits something (including a block)
        for(int x = 0; x < 18; ++x) {
            for(int y = 0; y < 18; ++y) {
                this.level().addParticle(ModParticles.ALEXANDRITE_PARTICLE.get(), this.getX(), this.getY(), this.getZ(),
                        Math.cos(x*20) * 0.15d, Math.cos(y*20) * 0.15d, Math.sin(x*20) * 0.15d);
            }
        }

        if(this.level().isClientSide()) {
            return;
        }
        //Setting HIT to true when it hits something
        if(hitResult.getType() == HitResult.Type.ENTITY && hitResult instanceof EntityHitResult entityHitResult) {
            Entity hit = entityHitResult.getEntity();
            Entity owner = this.getOwner();
            if(owner != hit) {
                this.entityData.set(HIT, true);
                counter = this.tickCount + 5;
            }
        } else {
            this.entityData.set(HIT, true);
            counter = this.tickCount + 5;
        }
    }
}
