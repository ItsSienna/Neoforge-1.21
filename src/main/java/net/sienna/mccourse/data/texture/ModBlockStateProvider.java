package net.sienna.mccourse.data.texture;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MCCourseMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() { //Registers all these as "normal" blocks
        normalBlock(ModBlocks.ALEXANDRITE_BLOCK.get());
        normalBlock(ModBlocks.RAW_ALEXANDRITE_BLOCK.get());
        normalBlock(ModBlocks.ALEXANDRITE_ORE.get());
        normalBlock(ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get());
        normalBlock(ModBlocks.NETHER_ALEXANDRITE_ORE.get());
        normalBlock(ModBlocks.END_STONE_ALEXANDRITE_ORE.get());
        normalBlock(ModBlocks.SOUND_BLOCK.get());
    }

    private void normalBlock (Block block) { //Tells the game that a "normal" block is a block that uses the same texture on all six sides
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();
        simpleBlock(block, models().cubeAll(path, modLoc("block/" + path)));
        simpleBlockItem(block, models().getExistingFile(modLoc("block/" + path)));
    }
}
