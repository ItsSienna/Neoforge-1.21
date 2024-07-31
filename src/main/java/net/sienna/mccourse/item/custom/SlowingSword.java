package net.sienna.mccourse.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

//We can create a sword (or anything really) that applies an effect when we hit something with it.
public class SlowingSword extends SwordItem {
    public SlowingSword(Tier tier, Properties properties) {
        super(tier, properties);
    }

    //Like the metal detector's useOn method, we can override the onLeftClickEntity method, which gives us what entity is being hit, and therefore, we can apply effects to it.
    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if(entity instanceof LivingEntity livingEntity) {
            //Make sure to check whether the entity you're hitting is living or not (eg painting/boat vs zombie/skeleton/player
            //Then you can do livingEntity.addEffect to add an effect
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 3));
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
}
