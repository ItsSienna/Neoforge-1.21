package net.sienna.mccourse.enchantment.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.sienna.mccourse.enchantment.ModEnchantments;

@EventBusSubscriber
public class EnchantmentHandler {

    //This class is basically where the enchantments learn how to work.
    //onWalkingOnGrass takes an entity tick event and applies a speedboost when they are (you guessed it) walking on grass
    //onTakeDamage tests for when the player has damage taken, then STRIKES LIGHTNING ON THE ATTACKER!
    @SubscribeEvent
    public static void onWalkingOnGrass(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Level level = event.getEntity().level();
            BlockState standingOn = entity.getBlockStateOn();
            ItemStack input = ((Player) entity).getInventory().getArmor(0);
            int flowerWalkerLevel = input.getEnchantmentLevel(ModEnchantments.unwrap(level, ModEnchantments.FLOWER_WALKER));

            if (!level.isClientSide() && standingOn == Blocks.GRASS_BLOCK.defaultBlockState()) {
                switch(flowerWalkerLevel){
                    case 1:
                        ((Player) entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 0, true, false));
                        break;
                    case 2:
                        ((Player) entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 1, true, false));
                        break;
                }
            }
        }

    }
    @SubscribeEvent
    public static void onTakeDamageEffect(LivingDamageEvent.Post event) {
        Entity entity = event.getEntity();
        Level level = event.getEntity().level();
        if(!level.isClientSide() && entity instanceof Player)
        {
            Entity attacker = event.getSource().getEntity();
            if (attacker != null) {
                BlockPos attackerPosition = attacker.getOnPos();
                ItemStack input = ((Player) entity).getInventory().getArmor(2);
                int lightningStrikerLevel = input.getEnchantmentLevel(ModEnchantments.unwrap(level, ModEnchantments.LIGHTNING_STRIKER));
                switch (lightningStrikerLevel) {
                    case 1:
                        EntityType.LIGHTNING_BOLT.spawn((ServerLevel) level, null, null, attackerPosition, MobSpawnType.TRIGGERED, true, true);
                        break;
                    case 2:
                        EntityType.LIGHTNING_BOLT.spawn((ServerLevel) level, null, null, attackerPosition, MobSpawnType.TRIGGERED, true, true);
                        EntityType.LIGHTNING_BOLT.spawn((ServerLevel) level, null, null, attackerPosition, MobSpawnType.TRIGGERED, true, true);
                        break;
                }
            }
        }
    }
}
