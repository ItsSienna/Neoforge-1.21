package net.sienna.mccourse.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.data.lang.ModLangProvider;
import net.sienna.mccourse.data.loot.ModLootTables;
import net.sienna.mccourse.data.recipe.ModRecipeProvider;
import net.sienna.mccourse.data.tag.ModBlockTagProvider;
import net.sienna.mccourse.data.texture.ModBlockStateProvider;
import net.sienna.mccourse.data.texture.ModItemStateProvider;

public class DataGenerators {

    public static void gatherData(GatherDataEvent event) {
        try {
            DataGenerator generator = event.getGenerator();
            PackOutput output = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();


            //Adds all our generators to the DataGenerators
            generator.addProvider(true, new ModLangProvider(output));
            generator.addProvider(true, new ModItemStateProvider(output, existingFileHelper));
            generator.addProvider(true, new ModBlockStateProvider(output, existingFileHelper));
            generator.addProvider(true, new ModBlockTagProvider(output, event.getLookupProvider(), existingFileHelper));
            generator.addProvider(true, new ModLootTables(output, event.getLookupProvider()));
            generator.addProvider(event.includeServer(), new ModRecipeProvider(output, event.getLookupProvider()));

        }catch (Exception e) {
            MCCourseMod.LOGGER.error("Failed to gather data");
        }
    }
}
