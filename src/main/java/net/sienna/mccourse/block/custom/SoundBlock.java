package net.sienna.mccourse.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.sienna.mccourse.MCCourseMod;

public class SoundBlock extends Block {
    public SoundBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()){
            // MCCourseMod.LOGGER.info("Clientside"); in clientside, it uses the renderthread, and in serverside, its serverthread
            if(pPlayer.isCrouching()) {
                MCCourseMod.LOGGER.info("Crouching");
                pLevel.playSound(null, pPos, SoundEvents.WARDEN_ROAR, SoundSource.BLOCKS, 1f, 1f); //NULL, because if you specify a player, it IGNORES the player. Middle press on the playSound method to see more.
                return InteractionResult.SUCCESS; //Still allows for other things to take place (eg placing blocks)
            } else {
                MCCourseMod.LOGGER.info("Not crouching");
                pLevel.playSound(null, pPos, SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM, SoundSource.BLOCKS, 1f, 1f);
                return InteractionResult.CONSUME; //Completely consumes the rightclick
            }
        }
        return super.useWithoutItem(pState, pLevel, pPos, pPlayer, pHitResult);
    }

}
