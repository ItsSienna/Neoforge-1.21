package net.sienna.mccourse.data.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.block.ModBlocks;
import net.sienna.mccourse.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MCCourseMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) { //Adds tags to the blocks (eg mineable via diamond pickaxe)
        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.ALEXANDRITE_BLOCK.get())
                .add(ModBlocks.ALEXANDRITE_ORE.get())
                .add(ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get())
                .add(ModBlocks.NETHER_ALEXANDRITE_ORE.get())
                .add(ModBlocks.END_STONE_ALEXANDRITE_ORE.get())
                .add(ModBlocks.RAW_ALEXANDRITE_BLOCK.get())
                .add(ModBlocks.ALEXANDRITE_STAIRS.get())
                .add(ModBlocks.ALEXANDRITE_SLAB.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ALEXANDRITE_BLOCK.get())
                .add(ModBlocks.ALEXANDRITE_ORE.get())
                .add(ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get())
                .add(ModBlocks.NETHER_ALEXANDRITE_ORE.get())
                .add(ModBlocks.END_STONE_ALEXANDRITE_ORE.get())
                .add(ModBlocks.RAW_ALEXANDRITE_BLOCK.get())
                .add(ModBlocks.ALEXANDRITE_STAIRS.get())
                .add(ModBlocks.ALEXANDRITE_SLAB.get())
                .add(ModBlocks.ALEXANDRITE_BUTTON.get())
                .add(ModBlocks.ALEXANDRITE_PRESSURE_PLATE.get())
                .add(ModBlocks.ALEXANDRITE_FENCE_GATE.get())
                .add(ModBlocks.ALEXANDRITE_FENCE.get())
                .add(ModBlocks.ALEXANDRITE_WALL.get())
                .add(ModBlocks.ALEXANDRITE_DOOR.get())
                .add(ModBlocks.ALEXANDRITE_TRAPDOOR.get());

        tag(ModTags.Blocks.METAL_DETECTOR_VALUABLES)
                .add(Blocks.DIAMOND_ORE)
                .add(Blocks.REDSTONE_ORE)
                .add(Blocks.IRON_ORE)
                .add(Blocks.GOLD_ORE)
                .add(Blocks.LAPIS_ORE)
                .add(Blocks.EMERALD_ORE)
                .add(Blocks.COAL_ORE)
                .add(Blocks.COPPER_ORE)
                .add(ModBlocks.ALEXANDRITE_ORE.get())
                .add(Blocks.DEEPSLATE_DIAMOND_ORE)
                .add(Blocks.DEEPSLATE_REDSTONE_ORE)
                .add(Blocks.DEEPSLATE_IRON_ORE)
                .add(Blocks.DEEPSLATE_GOLD_ORE)
                .add(Blocks.DEEPSLATE_LAPIS_ORE)
                .add(Blocks.DEEPSLATE_EMERALD_ORE)
                .add(Blocks.DEEPSLATE_COAL_ORE)
                .add(Blocks.DEEPSLATE_COPPER_ORE)
                .add(ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get());

        tag(ModTags.Blocks.PAXEL_MINEABLE)
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(BlockTags.MINEABLE_WITH_SHOVEL)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .addTag(BlockTags.MINEABLE_WITH_HOE);

        //These tags help the fences and walls connect to each other properly! I didn't need to do this for buttons (even though there is a tag for it), but I definitely can (and should!)!
        tag(BlockTags.FENCES)
                .add(ModBlocks.ALEXANDRITE_FENCE.get());
        tag(BlockTags.WALLS)
                .add(ModBlocks.ALEXANDRITE_WALL.get());
        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.ALEXANDRITE_FENCE_GATE.get());

        tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.WALNUT_LOG.get())
                .add(ModBlocks.WALNUT_WOOD.get())
                .add(ModBlocks.STRIPPED_WALNUT_WOOD.get())
                .add(ModBlocks.STRIPPED_WALNUT_LOG.get());

        tag(BlockTags.PLANKS)
                .add(ModBlocks.WALNUT_PLANKS.get());


    }
}
