package net.sienna.mccourse.data.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.block.ModBlocks;
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
        add(ModBlocks.ALEXANDRITE_ORE.get(), createOreDrop(ModBlocks.ALEXANDRITE_ORE.get(), ModItems.RAW_ALEXANDRITE.get()));
        add(ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get(), createOreDrop(ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get(), ModItems.RAW_ALEXANDRITE.get()));
        add(ModBlocks.NETHER_ALEXANDRITE_ORE.get(), createOreDrop(ModBlocks.NETHER_ALEXANDRITE_ORE.get(), ModItems.RAW_ALEXANDRITE.get()));
        add(ModBlocks.END_STONE_ALEXANDRITE_ORE.get(), createOreDrop(ModBlocks.END_STONE_ALEXANDRITE_ORE.get(), ModItems.RAW_ALEXANDRITE.get()));
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
