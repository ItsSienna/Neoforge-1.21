package net.sienna.mccourse.data.texture;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.block.ModBlocks;

import javax.annotation.Nullable;

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

        stairsBlock((StairBlock) ModBlocks.ALEXANDRITE_STAIRS.get(), blockTexture(ModBlocks.ALEXANDRITE_BLOCK.get()));
        //Note - slabs need the texture listed twice - once for the slab, and once for a double slab
        slabBlock((SlabBlock) ModBlocks.ALEXANDRITE_SLAB.get(), blockTexture(ModBlocks.ALEXANDRITE_BLOCK.get()), blockTexture(ModBlocks.ALEXANDRITE_BLOCK.get()));
        pressurePlateBlock((PressurePlateBlock) ModBlocks.ALEXANDRITE_PRESSURE_PLATE.get(), blockTexture(ModBlocks.ALEXANDRITE_BLOCK.get()));
        fenceGateBlock((FenceGateBlock) ModBlocks.ALEXANDRITE_FENCE_GATE.get(), blockTexture(ModBlocks.ALEXANDRITE_BLOCK.get()));

        //Doors and trapdoors are also weird! They get their own render type, orientable tag and a door has a top and a bottom.
        doorBlockWithRenderType((DoorBlock) ModBlocks.ALEXANDRITE_DOOR.get(), modLoc("block/alexandrite_door_bottom"), modLoc("block/alexandrite_door_top"), "cutout");
        trapdoorBlockWithRenderType((TrapDoorBlock) ModBlocks.ALEXANDRITE_TRAPDOOR.get(), modLoc("block/alexandrite_trapdoor"), true, "cutout");


        blockItem(ModBlocks.ALEXANDRITE_SLAB.get());
        blockItem(ModBlocks.ALEXANDRITE_STAIRS.get());
        blockItem(ModBlocks.ALEXANDRITE_PRESSURE_PLATE.get());
        blockItem(ModBlocks.ALEXANDRITE_FENCE_GATE.get());

        //Trapdoors! Weird. Yes, I know. It's all to do with how they render in the inventory. At least I didn't have to go into the ModItems provider this time...
        //...looking at you, buttons
        blockItem(ModBlocks.ALEXANDRITE_TRAPDOOR.get(), "_bottom");

        //Buttons, fences and walls are really weird. They exist in your inventory differently to other blocks - see ModItemStateProvider
        buttonBlock((ButtonBlock) ModBlocks.ALEXANDRITE_BUTTON.get(), blockTexture(ModBlocks.ALEXANDRITE_BLOCK.get()));
        fenceBlock((FenceBlock) ModBlocks.ALEXANDRITE_FENCE.get(), blockTexture(ModBlocks.ALEXANDRITE_BLOCK.get()));
        wallBlock((WallBlock) ModBlocks.ALEXANDRITE_WALL.get(), blockTexture(ModBlocks.ALEXANDRITE_BLOCK.get()));

    }

    //This is for specifically cube blocks
    private void normalBlock (Block block) { //Tells the game that a "normal" block is a block that uses the same texture on all six sides
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();
        simpleBlock(block, models().cubeAll(path, modLoc("block/" + path)));
        simpleBlockItem(block, models().getExistingFile(modLoc("block/" + path)));
    }

    //This is for everything else - the benefit of splitting these two out is that for cubes (the vast majority) you only need to write one line of code
    //vs the two you need for stairs and slabs (one for their in-game model, one for their item)
    private void blockItem (Block block) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();
        simpleBlockItem(block, models().getExistingFile(modLoc("block/" + path)));
    }
    private void blockItem (Block block, String appendix) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();
        simpleBlockItem(block, models().getExistingFile(modLoc("block/" + path + appendix)));
    }
}
