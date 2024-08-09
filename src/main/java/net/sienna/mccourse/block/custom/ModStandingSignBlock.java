package net.sienna.mccourse.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.sienna.mccourse.block.entity.ModSignBlockEntity;

public class ModStandingSignBlock extends StandingSignBlock {
    public ModStandingSignBlock(WoodType type, Properties properties) {
        super(type, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ModSignBlockEntity(pos, state);
    }
}