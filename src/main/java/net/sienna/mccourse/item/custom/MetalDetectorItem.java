package net.sienna.mccourse.item.custom;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.sienna.mccourse.particle.ModParticles;
import net.sienna.mccourse.sound.ModSounds;
import net.sienna.mccourse.util.ModTags;

public class MetalDetectorItem extends Item {
    public MetalDetectorItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        //First, make sure we're on the server. Two sides: server and client. The client has control over visuals, rendering etc
        //but the server has control over health, the world, damage
        if (!pContext.getLevel().isClientSide) { //check to see if NOT client
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            boolean foundBlock = false;

            for (int i = 0; i <= positionClicked.getY() + 64; i++) { //This checks every block between the position clicked and y=-64 (bedrock)
                BlockState blockState = pContext.getLevel().getBlockState(positionClicked.below(i)); //This is what defines how far down you're checking. positionClicked.below(i) is i blocks below the click
                //i = 0 corresponds to the block clicked. blockstate corresponds to the block itself in the world.

                if (isValuableBlock(blockState)) { //a new class dedicated to checking if a block is a metal
                    outputValuableCoordinates(positionClicked.below(i), player, blockState.getBlock()); //new class dedicated to sending the correct coordinates
                    foundBlock = true;

                    //Adding a sound! We're on the server here, so in the "player" part, PUT NULL, BECAUSE WHEN YOU PUT IN A PLAYER
                    //THAT PLAYER GETS IGNORED WHEN ON THE SERVER.
                    pContext.getLevel().playSeededSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.METAL_DETECTOR_FOUND_ORE.get(), SoundSource.BLOCKS, 1f, 1f, 0);

                    spawnFoundParticles(pContext, positionClicked, blockState);
                    break;
                }
            }

            if (!foundBlock) {
                outputNoValuableFound(player);
            }
        }
        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(), LivingEntity.getSlotForHand(pContext.getHand()));
        return InteractionResult.SUCCESS; //just gives the arm an animation
    }

    private void spawnFoundParticles(UseOnContext pContext, BlockPos positionClicked, BlockState blockState) {
        for (int i = 0; i < 20; i++) {
            ServerLevel level = (ServerLevel) pContext.getLevel(); //we're fine because we're only on the server!
            //THIS IS HOW YOU SPAWN PARTICLES. WITH level.sendParticles - this sends it to everyone in the vicinity.
            //This is really fucking cool. See the cos and sin? YES SIR WE'RE MAKING A CIRCLE
            level.sendParticles(ModParticles.ALEXANDRITE_PARTICLE.get(),
                    positionClicked.getX() + 0.5d, positionClicked.getY() + 1, positionClicked.getZ() + 0.5d, 1,
                    Math.cos(i * 18) + 0.5d, 1, Math.sin(i*18) +0.5d, 0.1);
        }
    }

    private void outputNoValuableFound(Player player) {
        player.sendSystemMessage(Component.translatable("item.mccourse.metaldetector.no.valuables"));
    }

    private void outputValuableCoordinates(BlockPos below, Player player, Block block) { //vvv THIS TRANSLATES THE BLOCK INTO THE LANGUAGE
        player.sendSystemMessage(Component.literal("Valuable found: " + I18n.get(block.getDescriptionId()) + " at (" + below.getX() + "," + below.getY() + "," +  below.getZ() + ")"));
    }

    //This used to be a massive boolean (return if blockstate is iron or gold or diamond etc etc, but now it references a tag.
    private boolean isValuableBlock(BlockState blockState) {
        return blockState.is(ModTags.Blocks.METAL_DETECTOR_VALUABLES); //A tag! See the util/ModTags class
        //return blockState.is(Blocks.IRON_ORE) || blockState.is(Blocks.DEEPSLATE_GOLD_ORE) || blockState.is(Blocks.DEEPSLATE_DIAMOND_ORE) || blockState.is(Blocks.EMERALD_ORE);
    }
}
