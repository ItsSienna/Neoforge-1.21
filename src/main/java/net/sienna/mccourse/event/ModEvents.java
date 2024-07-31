package net.sienna.mccourse.event;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.item.custom.HammerItem;

import java.util.HashSet;
import java.util.Set;

//Events require this tag at the top @EventBusSubscriber (the lectures tell you to use Mod.EventBusSubscriber, this is not necessary as we import it from NeoForge above!
@EventBusSubscriber(modid = MCCourseMod.MOD_ID)
public class ModEvents {

    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();
    //Event! This particular event uses BlockEvent.BreakEvent, which is the event that is called whenever a block is broken.
    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = event.getPlayer().getMainHandItem();
        //This is checking to make sure that the event in question did in fact happen due to a hammer, and that we're on the server.
        if (mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            LogUtils.getLogger().info("HAMMER TIME BUT AGAIN!");

            //This is necessary because a new BreakEvent is called every time the hammer breaks something. That's 9 times per hammer swing.
            //This way, if a block is broken due to a hammer, it DOES NOT trigger a new hammer event, and we dont get the world infinitely mining itself.
            if (HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            for (BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if (pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
                }

                HARVESTED_BLOCKS.add(pos);
                serverPlayer.gameMode.destroyBlock(pos);
                HARVESTED_BLOCKS.remove(pos);
            }
        }
    }

}
