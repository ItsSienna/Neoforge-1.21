package net.sienna.mccourse.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sienna.mccourse.block.custom.*;
import net.sienna.mccourse.item.ModItems;
import net.sienna.mccourse.sound.ModSounds;
import net.sienna.mccourse.util.ModWoodTypes;
import net.sienna.mccourse.worldgen.ModConfiguredFeatures;

import java.util.Optional;
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
9. IF required, use ModItemStateProvider to add inventory models for blocks - eg, buttons, fences, walls
 */

/*
What's the difference between a block and a blockstate?
Blocks are the general idea of a block - eg, our alexandrite block is considered a block.
A blockstate is the moment a block is placed in the world and "exists" - you can't get level/position information from a block, because they are just the idea of a block,
but you can get a position from a blockstate.
The best example of this is the redstone ore! It has two different states - powered and unpowered. When it's hit, it shifts state into its powered variant.
The redstone ore has a booleanproperty associated with it - whether it's lit or not. This is independent for each individual blockstate.
Note: BooleanProperty, NOT boolean. A boolean would be true for every copy of that class (eg every redstone ore would be lit).
TL;DR: Three of a block placed in the world are the same block, but different blockstates.
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
    //Regular blocks
    public static final DeferredBlock<Block> ALEXANDRITE_BLOCK = registerBlock("alexandrite_block",() -> new Block(Block.Properties.ofFullCopy(Blocks.COPPER_BLOCK).lightLevel(lightemission -> 15)));
    public static final DeferredBlock<Block> RAW_ALEXANDRITE_BLOCK = registerBlock("raw_alexandrite_block",() -> new Block(Block.Properties.ofFullCopy(Blocks.RAW_COPPER_BLOCK)));

    //Experience blocks (like ores!)
    public static final DeferredBlock<Block> ALEXANDRITE_ORE = registerBlock("alexandrite_ore",() -> new DropExperienceBlock(UniformInt.of(2, 5), BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> DEEPSLATE_ALEXANDRITE_ORE = registerBlock("deepslate_alexandrite_ore",() -> new DropExperienceBlock(UniformInt.of(2, 5), BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> NETHER_ALEXANDRITE_ORE = registerBlock("nether_alexandrite_ore",() -> new DropExperienceBlock(UniformInt.of(2, 5), BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_GOLD_ORE).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> END_STONE_ALEXANDRITE_ORE = registerBlock("end_stone_alexandrite_ore",() -> new DropExperienceBlock(UniformInt.of(2, 5), BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).requiresCorrectToolForDrops()));

    //Slabs and stairs use new StairBlock and new SlabBlock! So do buttons, pressure plates, fences, fence gates, walls, doors and trapdoors
    public static final DeferredBlock<Block> ALEXANDRITE_STAIRS = registerBlock("alexandrite_stairs", () -> new StairBlock(ModBlocks.ALEXANDRITE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_STAIRS)));
    public static final DeferredBlock<Block> ALEXANDRITE_SLAB = registerBlock("alexandrite_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_SLAB)));
    public static final DeferredBlock<Block> ALEXANDRITE_BUTTON = registerBlock("alexandrite_button", () -> new ButtonBlock(BlockSetType.COPPER, 20, BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_BLACKSTONE_BUTTON)));
    public static final DeferredBlock<Block> ALEXANDRITE_PRESSURE_PLATE = registerBlock("alexandrite_pressure_plate", () -> new PressurePlateBlock(BlockSetType.COPPER, BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_PRESSURE_PLATE)));
    public static final DeferredBlock<Block> ALEXANDRITE_FENCE = registerBlock("alexandrite_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_FENCE)));
    public static final DeferredBlock<Block> ALEXANDRITE_FENCE_GATE = registerBlock("alexandrite_fence_gate", () -> new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_FENCE_GATE)));
    public static final DeferredBlock<Block> ALEXANDRITE_WALL = registerBlock("alexandrite_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ANDESITE_WALL)));
    public static final DeferredBlock<Block> ALEXANDRITE_DOOR = registerBlock("alexandrite_door", () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_DOOR)));
    public static final DeferredBlock<Block> ALEXANDRITE_TRAPDOOR = registerBlock("alexandrite_trapdoor", () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_TRAPDOOR)));
    //Custom block!
    public static final DeferredBlock<Block> SOUND_BLOCK = registerBlock("sound_block",() -> new SoundBlock(Block.Properties.ofFullCopy(Blocks.WHITE_WOOL)));

    //Lamp! This has two blockstates. One lights up, one does not.
    //The datagen for this is cool - we need to account for two separate blockstates. It also includes our custom sound type for the lamp!
    public static final DeferredBlock<Block> ALEXANDRITE_LAMP = registerBlock("alexandrite_lamp",() -> new AlexandriteLampBlock(Block.Properties.ofFullCopy(Blocks.REDSTONE_LAMP).sound(ModSounds.ALEXANDRITE_LAMP_SOUNDS).lightLevel(
            state -> state.getValue(AlexandriteLampBlock.CLICKED) ? 15 : 0
            //A ternary!! Basically, if true, return 15, if not, return 0.
    )));
    //Crops! We don't do registerBlock, we use BLOCKS.register (above) because it doesn't have an item associated with it.
    public static final DeferredBlock<Block> KOHLRABI_CROP = BLOCKS.register("kohlrabi_crop", () -> new KohlrabiCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));

    //Flowers! The flower itself gets "registerBlock", but the potted variant gets a BLOCKS.register, for the same reason as above.
    public static final DeferredBlock<Block> SNAPDRAGON = registerBlock("snapdragon", () -> new FlowerBlock(MobEffects.BLINDNESS, 5, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY)));
    public static final DeferredBlock<Block> POTTED_SNAPDRAGON = BLOCKS.register("potted_snapdragon", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SNAPDRAGON, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));

    //Custom model block. No occlusion! Remember, registerBlock has a item associated with it, but BLOCKS.register doesn't. (I made this mistake when copying, and the game crashed!)
    public static final DeferredBlock<Block> GEM_EMPOWERING_STATION = registerBlock("gem_empowering_station", () -> new GemEmpoweringStationBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));

    //New wood type!
    //We need to remember to define the ability to strip a log
    //Logs are also rotateable! They are technically "rotatable flammable pillar blocks"
    public static final DeferredBlock<Block> WALNUT_LOG = registerBlock("walnut_log", () -> new ModWoodBlocks(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));
    public static final DeferredBlock<Block> WALNUT_WOOD = registerBlock("walnut_wood", () -> new ModWoodBlocks(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final DeferredBlock<Block> STRIPPED_WALNUT_LOG = registerBlock("stripped_walnut_log", () -> new ModWoodBlocks(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
    public static final DeferredBlock<Block> STRIPPED_WALNUT_WOOD = registerBlock("stripped_walnut_wood", () -> new ModWoodBlocks(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));
    //Wood planks! Making this anonymous, but to be honest, I don't really like it? I would probably abstract this because I imagine I'd like to add many wood types :D
    public static final DeferredBlock<Block> WALNUT_PLANKS = registerBlock("walnut_planks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)) {
        @Override
        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return true;
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 20; //This is just default for planks
        }

        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5; //This is just default for planks
        }
    });
    public static final DeferredBlock<Block> WALNUT_LEAVES = registerBlock("walnut_leaves", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60; //This is just default for leaves
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30; //This is just default for leaves
                }
    });
    //SAPLING - this is where world gen will be used!
    //It takes a treegrower which references a "ModConfiguredFeatures" with the key "WALNUT_TREE". This is our regular walnut tree.
    public static final DeferredBlock<Block> WALNUT_SAPLING = registerBlock("walnut_sapling", () -> new SaplingBlock(new TreeGrower("walnut_tree", Optional.empty(), Optional.of(ModConfiguredFeatures.WALNUT_TREE), Optional.empty()), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));

    //Signs? Apparently they need to all be custom classes, oh my god
    //THEYRE BLOCK ENTITIES. THEY HAVE ITEMS ASSOCIATED WITH THEM. I HATE SIGNS
    public static final DeferredBlock<Block> WALNUT_SIGN = BLOCKS.register("walnut_sign", () -> new ModStandingSignBlock(ModWoodTypes.WALNUT, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)));
    public static final DeferredBlock<Block> WALNUT_WALL_SIGN = BLOCKS.register("walnut_wall_sign", () -> new ModWallSignBlock(ModWoodTypes.WALNUT, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN)));
    public static final DeferredBlock<Block> WALNUT_HANGING_SIGN = BLOCKS.register("walnut_hanging_sign", () -> new ModHangingSignBlock(ModWoodTypes.WALNUT, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)));
    public static final DeferredBlock<Block> WALNUT_WALL_HANGING_SIGN = BLOCKS.register("walnut_wall_hanging_sign", () -> new ModWallHangingSignBlock(ModWoodTypes.WALNUT, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN)));


}
