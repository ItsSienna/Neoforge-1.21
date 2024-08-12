package net.sienna.mccourse.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.block.ModBlocks;

import java.util.List;

//Configured features! So stuff like trees, which randomly grow. Anything that is random and requires configuring goes here.
public class ModConfiguredFeatures {
    //The list of resource keys. Right at the top, like the enchantments.
    public static final ResourceKey<ConfiguredFeature<?, ?>> WALNUT_TREE = registerKey("walnut_tree");

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_ALEXANDRITE_ORE = registerKey("overworld_alexandrite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_ALEXANDRITE_ORE = registerKey("nether_alexandrite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_ALEXANDRITE_ORE = registerKey("end_alexandrite_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SNAPDRAGON = registerKey("snapdragon");

    //Two helper methods (one is a register, the other is fuck me)
    public static ResourceKey<ConfiguredFeature<? ,?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, name));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<? ,?>> context, ResourceKey<ConfiguredFeature<?,?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    //The actual features being registered. Like enchantments, it all happens inside a bootstrap method.
    //It is taking the feature and configuration and the key and making shit happen!
    public static void bootstrap(BootstrapContext<ConfiguredFeature<? , ?>> context) {
        //Which blocks can we replace in the world with the ores we wanna place in?
        //In the top two cases, there are specific tags for the ores.
        //In the nether and end, we're simply looking for netherrack and end stone to relplace.
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> overworldAlexandriteOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.ALEXANDRITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get().defaultBlockState()));

        register(context, WALNUT_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                //We're grabbing the block state for the trunk - This is gonna be the log!
                //Eventually, we'll be able to make custom ones, but not yet. I'm cool with regular trees...
                BlockStateProvider.simple(ModBlocks.WALNUT_LOG.get()),
                //Here, we're defining the size of the trunk. Base height, heightRandA, heightRandB.
                new StraightTrunkPlacer(5, 4, 3),
                //Leaves!
                BlockStateProvider.simple(ModBlocks.WALNUT_LEAVES.get()),
                //How the blob of leaves should look. It'll be radius, then offset, then height.
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 3),
                //How big this thang gon' be! Limit, lowersize, uppersize.
                new TwoLayersFeatureSize(1, 0, 2)).build());
        //Here, we pass in size! Eg coal is a size of 17, copper ore is 20, iron ore has 9
        register(context, OVERWORLD_ALEXANDRITE_ORE, Feature.ORE, new OreConfiguration(overworldAlexandriteOres, 9));
        register(context, NETHER_ALEXANDRITE_ORE, Feature.ORE, new OreConfiguration(netherrackReplaceables, ModBlocks.NETHER_ALEXANDRITE_ORE.get().defaultBlockState(), 9));
        register(context, END_ALEXANDRITE_ORE, Feature.ORE, new OreConfiguration(endReplaceables, ModBlocks.END_STONE_ALEXANDRITE_ORE.get().defaultBlockState(), 9));
        //Flowers use a random patch configuration. Melons take 10 tries, grass takes 54, flowers in a flower forest are at 96...
        //The rest tells us what we're spawning - in this case it's just the snapdragon.
        register(context, SNAPDRAGON, Feature.FLOWER, new RandomPatchConfiguration(32, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.SNAPDRAGON.get())))));
    }

}
