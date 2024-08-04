package net.sienna.mccourse.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.commons.compress.archivers.sevenz.CLI;


public class AlexandriteLampBlock extends Block {

    //Our BooleanProperty (NOT BOOLEAN!) - it is going to be unique for every blockstate of this block.
    //There are also integerproperties (like a redstone repeater) and enumproperties (for direction, like glazed terracotta).
    //You can also make custom properties!
    //I could 100% make this a LampBlock if I wanted to make multiple lamps, so I don't have to use code over and over.
    public static final BooleanProperty CLICKED = BooleanProperty.create("clicked");

    public AlexandriteLampBlock(Properties properties) {
        super(properties);
        //We need to define a default state here. In this case, it's false (because it's turned off normally).
        this.registerDefaultState(this.defaultBlockState().setValue(CLICKED, false));
    }

    //With a new property, we always need to override a blockstatedefinition creator, and add our new property.
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CLICKED);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        //We're changing something in the world, so we have to be on the server!
        if(!level.isClientSide()) {
            //First, get the current boolean of the booleanproperty
            boolean currentState = state.getValue(CLICKED);
            //Then, set the booleanproperty of the OPPOSITE of what it currently is (genuinely don't know what the flags:3 means)
            level.setBlock(pos, state.setValue(CLICKED, !currentState), 3);

        }
        return InteractionResult.SUCCESS;
    }
}
