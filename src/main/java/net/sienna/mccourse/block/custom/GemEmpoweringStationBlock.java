package net.sienna.mccourse.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

//This is for a custom block that has a facing value (see our custom model). It is NOT symmetrical.
//This also includes how to change the hitbox - go into BlockBench and approximate the size of the hitbox (DO NOT MAKE IT 1:1, IT SLOWS THE GAME DOWN!)
//weirdly, I had to add a "particle": section in the blockbench-generation .json file to get its break particles working correctly. odd, but ok
public class GemEmpoweringStationBlock extends HorizontalDirectionalBlock {
    public GemEmpoweringStationBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return null;
    }

    //This ensures the block faces us when we place it down (ie opposite to where we're looking)
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    //This just adds the fact that it is indeed a facing block.
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    //This is where we define the hitbox!
    //In this case, I know it's 16,12,16 because I went into blockbench and measured out how high (the y value) the model goes.
    public static final VoxelShape SHAPE = Block.box(0,0,0,16,12,16);
    //Now, we override the getShape method to tell the game what hitbox our block has.
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
