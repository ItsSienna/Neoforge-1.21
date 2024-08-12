package net.sienna.mccourse.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.sienna.mccourse.block.ModBlocks;
import net.sienna.mccourse.block.custom.DiceBlock;
import net.sienna.mccourse.entity.ModEntities;
import net.sienna.mccourse.item.ModItems;

//Throwable projectile!
public class DiceProjectileEntity extends ThrowableItemProjectile {
    public DiceProjectileEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public DiceProjectileEntity(Level level) {
        this(ModEntities.DICE_PROJECTIlE.get(), level);
    }

    public DiceProjectileEntity(Level level, LivingEntity livingEntity) {
        super(ModEntities.DICE_PROJECTIlE.get(), livingEntity, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.DICE.get();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if(!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.level().setBlock(blockPosition(), ((DiceBlock) ModBlocks.DICE_BLOCK.get()).getRandomBlockState(), 3);
        }

        this.discard();
        super.onHitBlock(result);
    }
}
