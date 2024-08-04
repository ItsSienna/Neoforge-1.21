package net.sienna.mccourse.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.data.lang.ModLangProvider;
import net.sienna.mccourse.data.loot.ModLootTables;
import net.sienna.mccourse.data.recipe.ModRecipeProvider;
import net.sienna.mccourse.data.tag.ModBlockTagProvider;
import net.sienna.mccourse.data.tag.ModItemTagProvider;
import net.sienna.mccourse.data.texture.ModBlockStateProvider;
import net.sienna.mccourse.data.texture.ModItemStateProvider;
import net.sienna.mccourse.enchantment.ModEnchantments;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DataGenerators {

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
            PackOutput output = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();


            //Adds all our generators to the DataGenerators
            //Language generator
            generator.addProvider(true, new ModLangProvider(output));

            //Item/Block state generator - for how items/blocks appear when held
            generator.addProvider(true, new ModItemStateProvider(output, existingFileHelper));
            generator.addProvider(true, new ModBlockStateProvider(output, existingFileHelper));

            //Item/Block tag provider - for registering tags to blocks and items
            BlockTagsProvider blockTagsProvider = new ModBlockTagProvider(output, event.getLookupProvider(), existingFileHelper);
            generator.addProvider(true, blockTagsProvider);
            generator.addProvider(true, new ModItemTagProvider(output, event.getLookupProvider(), blockTagsProvider.contentsGetter()));
            //Loot tables generator
            generator.addProvider(true, new ModLootTables(output, event.getLookupProvider()));

            //Recipes generator
            generator.addProvider(true, new ModRecipeProvider(output, event.getLookupProvider()));

    }
}
