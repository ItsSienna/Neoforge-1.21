package net.sienna.mccourse.data.lang;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.block.ModBlocks;
import net.sienna.mccourse.item.ModCreativeModeTabs;
import net.sienna.mccourse.item.ModItems;

public class ModLangProvider extends LanguageProvider {

    public ModLangProvider(PackOutput output) {
        super(output, MCCourseMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //Items
        addItem(ModItems.ALEXANDRITE, "Alexandrite"); //Sets the name of the item in the en_us translation
        addItem(ModItems.RAW_ALEXANDRITE, "Raw Alexandrite");
        addItem(ModItems.METAL_DETECTOR, "Metal Detector");
        addItem(ModItems.PEAT_BRICK, "Peat Brick");
        addItem(ModItems.KOHLRABI, "Kohlrabi");

        //Blocks
        addBlock(ModBlocks.ALEXANDRITE_BLOCK, "Alexandrite Block");
        addBlock(ModBlocks.RAW_ALEXANDRITE_BLOCK, "Raw Alexandrite Block");
        addBlock(ModBlocks.ALEXANDRITE_ORE, "Alexandrite Ore");
        addBlock(ModBlocks.DEEPSLATE_ALEXANDRITE_ORE, "Deepslate Alexandrite Ore");
        addBlock(ModBlocks.NETHER_ALEXANDRITE_ORE, "Nether Alexandrite Ore");
        addBlock(ModBlocks.END_STONE_ALEXANDRITE_ORE, "End Stone Alexandrite Ore");
        addBlock(ModBlocks.SOUND_BLOCK, "Sound Block");

        //Creative mode menu
        add(ModCreativeModeTabs.modcourse_creative_mode_blocks, "Mod Course Blocks");
        add(ModCreativeModeTabs.modcourse_creative_mode_items, "Mod Course Items");

        add("item.mccourse.metaldetector.no.valuables", "No valuables detected.");
    }
}
