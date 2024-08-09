package net.sienna.mccourse.data.texture;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.block.ModBlocks;
import net.sienna.mccourse.block.custom.AlexandriteLampBlock;
import net.sienna.mccourse.block.custom.KohlrabiCropBlock;

import java.util.function.Function;

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

        //LAMP
        //only one because I only have one lamp :)
        customLamp();

        //Flower! Basically just tells the game to create a cross of two textures (they're both the same) and render them in a cutout.
        simpleBlockWithItem(ModBlocks.SNAPDRAGON.get(),
                models().cross(blockTexture(ModBlocks.SNAPDRAGON.get()).getPath(), blockTexture(ModBlocks.SNAPDRAGON.get())).renderType("cutout"));

        makeCrop((KohlrabiCropBlock) ModBlocks.KOHLRABI_CROP.get(), "kohlrabi_stage", "kohlrabi_stage");

        //Use horizontalBlock for a facing block (not like a dispenser, more like a crafting station)
        horizontalBlock(ModBlocks.GEM_EMPOWERING_STATION.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/gem_empowering_station")));

        //Main log is a log block
        logBlock((RotatedPillarBlock) ModBlocks.WALNUT_LOG.get());
        //Wood is an axisblock
        axisBlock((RotatedPillarBlock) ModBlocks.WALNUT_WOOD.get(), blockTexture(ModBlocks.WALNUT_LOG.get()), blockTexture(ModBlocks.WALNUT_LOG.get()));
        //Stripped log, for some reason, is also an axis block. It takes the parameter for walnut log and the log top
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_WALNUT_LOG.get(), ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "block/stripped_walnut_log"),
                ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "block/stripped_walnut_log_top"));
        //Stripped wood is also just as aids??? Even though its a NORMAL BLOCK?????
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_WALNUT_WOOD.get(), ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "block/stripped_walnut_log"),
                ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "block/stripped_walnut_log"));
        blockItem(ModBlocks.WALNUT_LOG.get());
        blockItem(ModBlocks.WALNUT_WOOD.get());
        blockItem(ModBlocks.STRIPPED_WALNUT_WOOD.get());
        blockItem(ModBlocks.STRIPPED_WALNUT_LOG.get());
        //Luckily, planks are normal
        normalBlock(ModBlocks.WALNUT_PLANKS.get());
        //Leaves are not normal but not terrible
        leavesBlock(ModBlocks.WALNUT_LEAVES);
        //Saplings are a little weird but not very weird
        saplingBlock(ModBlocks.WALNUT_SAPLING);
        //Signs
        //THESE TWO LINES ARE WHAT IS NOT WORKING

        signBlock((StandingSignBlock) ModBlocks.WALNUT_SIGN.get(), (WallSignBlock) ModBlocks.WALNUT_WALL_SIGN.get(), blockTexture(ModBlocks.WALNUT_PLANKS.get()));
        hangingSignBlock(ModBlocks.WALNUT_HANGING_SIGN.get(), ModBlocks.WALNUT_WALL_HANGING_SIGN.get(), blockTexture(ModBlocks.WALNUT_PLANKS.get()));
    }
    //SIGN
    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }
    //SIGN
    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }
    //SIGN
    private String name(Block block) {
        return key(block).getPath();
    }
    //SIGN AGAIN JESUS CHRIST
    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
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

    //This is for saplings
    private void saplingBlock(DeferredBlock<Block> block) {
        simpleBlock(block.get(),
                models().cross(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), blockTexture(block.get())).renderType("cutout"));
    }
    //This is for leaves - technically they also have a cutout (transparency)
    private void leavesBlock(DeferredBlock<Block> block) {
        simpleBlock(block.get(),
                models().cubeAll(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), blockTexture(block.get())).renderType("cutout"));
        simpleBlockItem(block.get(), models().getExistingFile(modLoc("block/" + block.getId().getPath())));
    }

    //This is for lamp blocks. It basically points to two different places depending on the BlockState's booleanproperty.
    //It's not generalised, but I could 100% generalise it for multiple lamps by making a LampBlock class with a CLICKED property.
    //Right now, I only have one lamp, but I may try my hand at making multiple and generalising this method at some point. Shouldn't be too hard~
    //TLDR this is how you change the texture based on its data.
    private void customLamp() {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(ModBlocks.ALEXANDRITE_LAMP.get());
        String path = blockKey.getPath();
        getVariantBuilder(ModBlocks.ALEXANDRITE_LAMP.get()).forAllStates(blockState -> {
            if(blockState.getValue(AlexandriteLampBlock.CLICKED)) {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(path, modLoc("block/alexandrite_lamp_on")))};
            } else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(path, modLoc("block/alexandrite_lamp_off")))};
            }
        });

        simpleBlockItem(ModBlocks.ALEXANDRITE_LAMP.get(), models().cubeAll(path, modLoc("block/alexandrite_lamp_on")));

    }

    public void makeCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((KohlrabiCropBlock) block).getAgeProperty()),
                modLoc("block/kohlrabi_stage" + state.getValue(((KohlrabiCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }



}
