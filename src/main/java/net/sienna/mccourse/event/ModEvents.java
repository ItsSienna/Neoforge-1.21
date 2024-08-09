package net.sienna.mccourse.event;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.block.ModBlocks;
import net.sienna.mccourse.command.ReturnHomeCommand;
import net.sienna.mccourse.command.SetHomeCommand;
import net.sienna.mccourse.item.ModItems;
import net.sienna.mccourse.item.custom.HammerItem;
import net.sienna.mccourse.villager.ModVillagers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Events require this tag at the top @EventBusSubscriber (the lectures tell you to use Mod.EventBusSubscriber, this is not necessary as we import it from NeoForge above!
//Definitely should go into the events and read all of them. It's crazy useful and cool!
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
    //Very important to call all new commands in the RegisterCommandsEvent context.
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        //This is where we register our commands!
        new SetHomeCommand(event.getDispatcher());
        new ReturnHomeCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    //Really interesting - a PlayerEvent.Clone is called when a player RESPAWNS.
    //Data isn't automatically saved across "clones", we need to do that ourselves on a clone event.
    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        //We're using the key from before to transfer the home position from one "clone" to the other.
        event.getEntity().getPersistentData().putIntArray("mccourse.homepos",
                event.getOriginal().getPersistentData().getIntArray("mccourse.homepos"));
    }

    //Custom trades!
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        //These are existing villagers...
        if(event.getType() == VillagerProfession.FARMER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades(); //This gets a list of trades that the villager can do.
            int villagerLevel = 1; //This will tell the game what level Villager is required for the trade we will add.
            trades.get(villagerLevel).add((pTrader, pRandom) -> new MerchantOffer( //Here we use the villager level to add the trade below to the villager.
                    new ItemCost(Items.EMERALD, 2), new ItemStack(ModItems.KOHLRABI.get(), 6), 10, 2, 0.02f
            ));
        }
        if(event.getType() == VillagerProfession.TOOLSMITH) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            int villagerLevel = 3;
            trades.get(villagerLevel).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 12), new ItemStack(ModItems.ALEXANDRITE_PAXEL.get(), 1), 10, 5, 0.08f
            ));
        }
        //And this is our custom villager!
        if(event.getType() == ModVillagers.SOUNDMASTER.value()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            int villagerLevel = 1;
            trades.get(villagerLevel).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 12), new ItemStack(ModBlocks.SOUND_BLOCK.get(), 1), 10, 5, 0.08f
            ));
        }
    }
    //Custom wandering trader trades!
    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades(); //For the wandering trader, the Int2ObjectMap just becomes a list
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades(); //And he also has generic and rare trades.
        //Also, no need for a villager level as the wandering trader has no level.
        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 12), new ItemStack(ModItems.ALEXANDRITE_PAXEL.get(), 1), 10, 5, 0.08f
        ));
    }

}
