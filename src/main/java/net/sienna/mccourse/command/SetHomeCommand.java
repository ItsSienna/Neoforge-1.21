package net.sienna.mccourse.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SetHomeCommand {

    //Commands! I actually know more or less how these work, as I've done plugin stuff before.
    //home set
    public SetHomeCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("home").then(Commands.literal("set").executes(this::execute)));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer serverPlayer = context.getSource().getPlayer();
        BlockPos position = serverPlayer.blockPosition();
        String positionString = "(" + position.getX() +", " + position.getY()+ ", " + position.getZ() + ")";

        //getPersistentData is a compound tag that gets applied to an entity. Here, we're saving the home coordinates into the entity.
        //We've set a key to tell the game what this dataset is, and we can refer to it elsewhere.
        serverPlayer.getPersistentData().putIntArray("mccourse.homepos",
                new int[] {position.getX(), position.getY(), position.getZ()});

        context.getSource().sendSuccess(() -> Component.literal("Set home at " + positionString), false);
        return 1;
    }
}
