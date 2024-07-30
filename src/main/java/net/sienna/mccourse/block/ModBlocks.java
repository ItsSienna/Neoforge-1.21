package net.sienna.mccourse.block;

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
import net.sienna.mccourse.block.custom.SoundBlock;
import net.sienna.mccourse.item.ModItems;

import java.util.function.Supplier;

import static net.sienna.mccourse.MCCourseMod.MOD_ID;
/*
TO CREATE A NEW BLOCK
1. Create a DeferredBlock<Block> below. Use registerBlock with new Block for a basic block, and new CustomBlock for a custom one
2. Add it to the creative mode tab (I have it in the item folder, it should really be in its own package
3. Set a language translation in ModLangProvider
4. Set its drop in ModBlockLootTables (if it drops itself or something else, like an ore)
5. Set its recipe in ModRecipeProvider
6. Set any tags it may have (valuable tag, ore tag, etc)
7. Create a texture and put it in textures/block in resources
8. Use ModBlockStateProvider to point the block to its texture (and saying whether its a normal cube or something more complicated like stairs
 */
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
    public static final DeferredBlock<Block> SOUND_BLOCK = registerBlock("sound_block",() -> new SoundBlock(Block.Properties.ofFullCopy(Blocks.WHITE_WOOL)));
}
