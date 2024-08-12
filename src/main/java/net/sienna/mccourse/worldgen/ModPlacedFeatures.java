package net.sienna.mccourse.worldgen;

import com.ibm.icu.text.Replaceable;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.block.ModBlocks;

import java.util.List;
/*
TO ADD NEW FEATURES LIKE TREES, ORES OR STRUCTURES:
Create a configured feature key for anything that needs configuring
Register that feature and apply the configurations in the method
Create a PLACED feature key below
Register that placed feature key, defining how its placed in the world
Create a biome modifier key
Register that biome modifier key, telling the game WHERE it will spawn in the world

Remember:
Configured Feature is for changing the feature itself.
Placed Feature is for changing how the feature spawns.
Biome modifier is for changing WHERE the feature spawns.

 */

public class ModPlacedFeatures {
    //All is same as in configuredfeatures and in enchantments.

    public static final ResourceKey<PlacedFeature> WALNUT_PLACED_TREE = registerKey("walnut_placed_tree");

    public static final ResourceKey<PlacedFeature> ALEXANDRITE_PLACED_ORE = registerKey("alexandrite_placed_ore");
    public static final ResourceKey<PlacedFeature> NETHER_ALEXANDRITE_PLACED_ORE = registerKey("nether_alexandrite_placed_ore");
    public static final ResourceKey<PlacedFeature> END_ALEXANDRITE_PLACED_ORE = registerKey("end_alexandrite_placed_ore");

    public static final ResourceKey<PlacedFeature> SNAPDRAGON_PLACED = registerKey("snapdragon_placed");



    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<? ,?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<? ,?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, WALNUT_PLACED_TREE, configuredFeatures.getOrThrow(ModConfiguredFeatures.WALNUT_TREE),
                //Count is how many are in one chunk, 10% chance of two more being added.
                //FOR THE CHANCE: 1/CHANCE MUST BE AN INTEGER. NO 1/0.3.
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2),
                        //We can only spawn trees where a sapling can grow!
                        ModBlocks.WALNUT_SAPLING.get()));

        register(context, ALEXANDRITE_PLACED_ORE, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_ALEXANDRITE_ORE),
                //How many veins per chunk, then what Y value to find the ore in. Here, I've put -64 to 80.
                //Uniform means its uniform, triangle means it'll spawn the most amount of ore ~ the middle of the two values
                ModOrePlacements.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, NETHER_ALEXANDRITE_PLACED_ORE, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_ALEXANDRITE_ORE),
                ModOrePlacements.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
        register(context, END_ALEXANDRITE_PLACED_ORE, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_ALEXANDRITE_ORE),
                ModOrePlacements.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, SNAPDRAGON_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.SNAPDRAGON),
                //This is the rarity of the patches! pumpkins are 300, sunflowers are 3 (so patches are close together).
                //Heightmap gives the worlds heightmap.
                List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    }
}
