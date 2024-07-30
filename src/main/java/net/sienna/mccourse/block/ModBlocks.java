package net.sienna.mccourse.block;
//TO CREATE A NEW ITEM:
//First, add the RegistryObject<Block> in the ModBlocks class (with all the item properties you may want) - usually, you can use .ofFullCopy to copy an existing block, or click through
//to the blocks and make one yourself
//Then, add it to a creative tab in the main class
//Then, set its language translation in lang/en_us.json (eg mccourse.item.alexandrite becomes "Alexandrite"
//Then, create its blockstates json file in blockstates. This refers to the MODEL and displays a different model depending on its block state (think like furnaces lighting up)
//Then, create its model/block file with appropriate parents and a link to its texture file. eg, "cube_all" means this texture is on all sides of the cube
//Then, put its BLOCK .png file into textures/block (and make sure it is named the same as the model/block entry
//Then, go back into the item model folder and add the blockitem, by adding its .json files in model/item. It just needs to have a parent referring back to the block model json file

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sienna.mccourse.item.ModItems;

import java.util.function.Supplier;

import static net.sienna.mccourse.MCCourseMod.MOD_ID;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    //We need two helper methods for creating a block, as they help us create a BLOCK and an ITEM for that block.
    //<T extends Block> is a generic: Anything that we put into the T has to be a Minecraft block.
    public static DeferredBlock<Block> registerBlock(String name, Supplier<Block> block) {
        DeferredBlock<Block> blockRegistry = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(blockRegistry.get(), new Item.Properties()));
        return blockRegistry;
    }

    public static final DeferredBlock<Block> ALEXANDRITE_BLOCK = registerBlock("alexandrite_block",() -> new Block(Block.Properties.ofFullCopy(Blocks.IRON_BLOCK).lightLevel(lightemission -> 15)));
    public static final DeferredBlock<Block> ALEXANDRITE_ORE = registerBlock("alexandrite_ore",() -> new DropExperienceBlock(UniformInt.of(2, 5), BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> DEEPSLATE_ALEXANDRITE_ORE = registerBlock("deepslate_alexandrite_ore",() -> new DropExperienceBlock(UniformInt.of(2, 5), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> NETHER_ALEXANDRITE_ORE = registerBlock("nether_alexandrite_ore",() -> new DropExperienceBlock(UniformInt.of(2, 5), BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_GOLD_ORE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> END_STONE_ALEXANDRITE_ORE = registerBlock("end_stone_alexandrite_ore",() -> new DropExperienceBlock(UniformInt.of(2, 5), BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> RAW_ALEXANDRITE_BLOCK = registerBlock("raw_alexandrite_block",() -> new Block(Block.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK)));
    public static final DeferredBlock<Block> SOUND_BLOCK = registerBlock("sound_block",() -> new Block(Block.Properties.ofFullCopy(Blocks.WHITE_WOOL)));
}
