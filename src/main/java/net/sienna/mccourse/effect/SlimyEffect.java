package net.sienna.mccourse.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

//Custom effects!
public class SlimyEffect extends MobEffect {
    protected SlimyEffect(MobEffectCategory category, int color) {
        super(category, color);
    }


    //Decrease movement speed by 25% and gives a spider climb effect
    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if(livingEntity.horizontalCollision) {
            Vec3 initialVec = livingEntity.getDeltaMovement();
            Vec3 climbVec = new Vec3(initialVec.x, 0.2, initialVec.z);
            //obviously, can change the multipliers just to change the speed
            livingEntity.setDeltaMovement(climbVec.x * 0.91, climbVec.y * 0.98, climbVec.z * 0.91);
        }
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
