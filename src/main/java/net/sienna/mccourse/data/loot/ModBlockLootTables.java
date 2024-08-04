package net.sienna.mccourse.data.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.block.ModBlocks;
import net.sienna.mccourse.block.custom.KohlrabiCropBlock;
import net.sienna.mccourse.item.ModItems;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLootTables extends BlockLootSubProvider {
    protected ModBlockLootTables(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.ALEXANDRITE_BLOCK.get());
        dropSelf(ModBlocks.SOUND_BLOCK.get());
        dropSelf(ModBlocks.RAW_ALEXANDRITE_BLOCK.get());
        dropSelf(ModBlocks.ALEXANDRITE_STAIRS.get());
        dropSelf(ModBlocks.ALEXANDRITE_BUTTON.get());
        dropSelf(ModBlocks.ALEXANDRITE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.ALEXANDRITE_FENCE_GATE.get());
        dropSelf(ModBlocks.ALEXANDRITE_FENCE.get());
        dropSelf(ModBlocks.ALEXANDRITE_WALL.get());
        dropSelf(ModBlocks.ALEXANDRITE_TRAPDOOR.get());
        dropSelf(ModBlocks.ALEXANDRITE_LAMP.get());

        //Ores
        add(ModBlocks.ALEXANDRITE_ORE.get(), createOreDrop(ModBlocks.ALEXANDRITE_ORE.get(), ModItems.RAW_ALEXANDRITE.get()));
        add(ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get(), createOreDrop(ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get(), ModItems.RAW_ALEXANDRITE.get()));
        add(ModBlocks.NETHER_ALEXANDRITE_ORE.get(), createOreDrop(ModBlocks.NETHER_ALEXANDRITE_ORE.get(), ModItems.RAW_ALEXANDRITE.get()));
        add(ModBlocks.END_STONE_ALEXANDRITE_ORE.get(), createOreDrop(ModBlocks.END_STONE_ALEXANDRITE_ORE.get(), ModItems.RAW_ALEXANDRITE.get()));

        //Door
        add(ModBlocks.ALEXANDRITE_DOOR.get(), createDoorTable(ModBlocks.ALEXANDRITE_DOOR.get()));

        //Slabs get custom behaviour, because of course they do
        add(ModBlocks.ALEXANDRITE_SLAB.get(), createSlabItemTable(ModBlocks.ALEXANDRITE_SLAB.get()));

        //Crops - just copy pasted from wheat lol
        LootItemCondition.Builder kohlrabiseedsbuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.KOHLRABI_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(KohlrabiCropBlock.AGE, 6));
        this.add(ModBlocks.KOHLRABI_CROP.get(), this.createCropDrops(ModBlocks.KOHLRABI_CROP.get(), ModItems.KOHLRABI.get(), ModItems.KOHLRABI_SEEDS.get(), kohlrabiseedsbuilder));
    }

    @Override //This allows us to add all the mod-specific blocks to the block set used in the super
    protected Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.stream()
                .filter(block -> Optional.of(BuiltInRegistries.BLOCK.getKey(block))
                        .filter(key -> key.getNamespace().equals(MCCourseMod.MOD_ID))
                        .isPresent())
                .collect(Collectors.toSet());
    }
}
