package net.sienna.mccourse.item.custom;

import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.sienna.mccourse.entity.custom.MagicProjectileEntity;
import net.sienna.mccourse.sound.ModSounds;

public class RadiationStaffItem extends Item {
    public RadiationStaffItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), ModSounds.METAL_DETECTOR_FOUND_ORE.get(), SoundSource.NEUTRAL,
                1.5F, 1F);
        pPlayer.getCooldowns().addCooldown(this, 40);

        if(!pLevel.isClientSide()) {
            MagicProjectileEntity magicProjectile = new MagicProjectileEntity(pLevel, pPlayer);
            magicProjectile.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 0.25F);
            pLevel.addFreshEntity(magicProjectile);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.hurtAndBreak(1, pPlayer, EquipmentSlot.MAINHAND);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}
