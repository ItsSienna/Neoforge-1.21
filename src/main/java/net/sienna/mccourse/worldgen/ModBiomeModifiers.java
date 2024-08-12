package net.sienna.mccourse.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.sienna.mccourse.MCCourseMod;

public class ModBiomeModifiers {
    //Third verse same as the second and the first
    //Here, we're registering the method to add a walnut tree to generation.
    public static final ResourceKey<BiomeModifier> ADD_TREE_WALNUT = registerKey("add_tree_walnut");

    public static final ResourceKey<BiomeModifier> ADD_ALEXANDRITE_ORE = registerKey("add_alexandrite_ore");
    public static final ResourceKey<BiomeModifier> ADD_NETHER_ALEXANDRITE_ORE = registerKey("add_nether_alexandrite_ore");
    public static final ResourceKey<BiomeModifier> ADD_END_ALEXANDRITE_ORE = registerKey("add_end_alexandrite_ore");

    public static final ResourceKey<BiomeModifier> ADD_SNAPDRAGON = registerKey("add_snapdragon");

    public static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, name));
    }

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        //We need to be able to refer to our biomes and placed features
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        //Now we get to define WHERE the tree spawns. We can use tags to enable this.
        context.register(ADD_TREE_WALNUT, new BiomeModifiers.AddFeaturesBiomeModifier(
                //If we make a new biome, we can give it a tag and say that our tree only spawns there.
                //We can use things like "BiomeTags.IS_OVERWORLD" or "Tags.Biomes.IS_PLAINS".
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                //We're grabbing which feature we're actually throwing in - in this case, the tree from placed features.
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.WALNUT_PLACED_TREE)),
                //What gen step? The tree one!
                GenerationStep.Decoration.VEGETAL_DECORATION));

        //Same with our ores below.
        context.register(ADD_ALEXANDRITE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ALEXANDRITE_PLACED_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_NETHER_ALEXANDRITE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.NETHER_ALEXANDRITE_PLACED_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_END_ALEXANDRITE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.END_ALEXANDRITE_PLACED_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        //And flowers!
        context.register(ADD_SNAPDRAGON, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SNAPDRAGON_PLACED)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

    }
}
