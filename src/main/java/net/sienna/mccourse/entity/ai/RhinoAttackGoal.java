package net.sienna.mccourse.entity.ai;

import com.mojang.logging.LogUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.neoforged.api.distmarker.Dist;
import net.sienna.mccourse.entity.custom.RhinoEntity;


//This is the rhino attack goal! It extends melee attack goal, but we can do some fun stuff with it.
//It's ridiculously long, but it's honestly understandable. There is just a LOT of logic behind it.
public class RhinoAttackGoal extends MeleeAttackGoal {
    public RhinoAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
        entity = ((RhinoEntity) mob);
    }

    private final RhinoEntity entity;
    private int attackDelay = 40; //Half of the time the attack animation is IF the attack itself actually takes place in the middle of the animation.
    //In our case, the attack animation happens at about 2 seconds in, compared to the 4 seconds that the animation plays for.
    //This is when we want the actual hitbox and damage to come through.
    private int ticksUntilNextAttack = 40;
    private boolean shouldCountTilNextAttack = false;

    //This is the important one! The rest are helpers.
    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        if(canPerformAttack(target)) {
            shouldCountTilNextAttack = true;

            if(isTimeToStartAttackAnimation()) {
                entity.setAttacking(true); //Animation starts
            }

            if(isTimeToAttack()) {
                this.mob.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
                performAttack(target);
            }
        } else {
            resetAttackCooldown();
            shouldCountTilNextAttack = false;
            entity.setAttacking(false);
            entity.attackAnimationTimeout = 0;
        }

    }

    //Overriding some MeleeAttackGoal methods so that animations and attacks are done correctly!


    @Override
    protected boolean canPerformAttack(LivingEntity entity) {
        return this.mob.isWithinMeleeAttackRange(entity) && this.mob.getSensing().hasLineOfSight(entity);
    }

    private boolean isTimeToStartAttackAnimation() {
        return this.ticksUntilNextAttack <= attackDelay;
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(attackDelay*2);
    }

    protected boolean isTimeToAttack(){
        return this.ticksUntilNextAttack <=0;
    }

    @Override
    public int getTicksUntilNextAttack() {
        return ticksUntilNextAttack;
    }

    protected void performAttack(LivingEntity enemy) {
        this.resetAttackCooldown();
        this.mob.swing(InteractionHand.MAIN_HAND);
        this.mob.doHurtTarget(enemy);
    }

    @Override
    public void start() {
        super.start();
        //Resets our timer
        this.attackDelay = 40;
        this.ticksUntilNextAttack = 40;
    }

    @Override
    public void tick() {
        super.tick();
        if(shouldCountTilNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack-1, 0); //Counting down until we hit zero.
        }
    }

    @Override
    public void stop() {
        entity.setAttacking(false); //No longer attacking when we hit stop!
        super.stop();
    }
}
