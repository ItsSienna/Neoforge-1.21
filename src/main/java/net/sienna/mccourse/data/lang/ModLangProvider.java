package net.sienna.mccourse.data.lang;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.block.ModBlocks;
import net.sienna.mccourse.ModCreativeModeTabs;
import net.sienna.mccourse.entity.ModEntities;
import net.sienna.mccourse.fluid.ModFluids;
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
        addItem(ModItems.ALEXANDRITE_SWORD, "Alexandrite Sword");
        addItem(ModItems.ALEXANDRITE_PICKAXE, "Alexandrite Pickaxe");
        addItem(ModItems.ALEXANDRITE_AXE, "Alexandrite Axe");
        addItem(ModItems.ALEXANDRITE_SHOVEL, "Alexandrite Shovel");
        addItem(ModItems.ALEXANDRITE_HOE, "Alexandrite Hoe");
        addItem(ModItems.ALEXANDRITE_PAXEL, "Alexandrite Paxel");
        addItem(ModItems.ALEXANDRITE_HAMMER, "Alexandrite Hammer");
        addItem(ModItems.SLOWING_SWORD, "Slowing Sword");
        addItem(ModItems.ALEXANDRITE_HELMET, "Alexandrite Helmet");
        addItem(ModItems.ALEXANDRITE_CHESTPLATE, "Alexandrite Chestplate");
        addItem(ModItems.ALEXANDRITE_LEGGINGS, "Alexandrite Leggings");
        addItem(ModItems.ALEXANDRITE_BOOTS, "Alexandrite Boots");
        addItem(ModItems.ALEXANDRITE_HORSE_ARMOR, "Alexandrite Horse Armour");
        addItem(ModItems.KOHLRABI_SEEDS, "Kohlrabi Seeds");
        addItem(ModItems.BAR_BRAWL_RECORD, "Bar Brawl");
        addItem(ModItems.RADIATION_STAFF, "Radiation Staff");
        addItem(ModItems.ALEXANDRITE_BOW, "Alexandrite Bow");
        addItem(ModItems.ALEXANDRITE_SHIELD, "Alexandrite Shield");
        addItem(ModFluids.SOAP_WATER_BUCKET, "Soap Water Bucket");
        addItem(ModItems.RHINO_SPAWN_EGG, "Rhino Spawn Egg");
        addItem(ModItems.WALNUT_HANGING_SIGN, "Walnut Hanging Sign");
        addItem(ModItems.WALNUT_SIGN, "Walnut Sign");
        addItem(ModItems.DICE, "Dice");

        //Blocks
        addBlock(ModBlocks.ALEXANDRITE_BLOCK, "Alexandrite Block");
        addBlock(ModBlocks.RAW_ALEXANDRITE_BLOCK, "Raw Alexandrite Block");
        addBlock(ModBlocks.ALEXANDRITE_ORE, "Alexandrite Ore");
        addBlock(ModBlocks.DEEPSLATE_ALEXANDRITE_ORE, "Deepslate Alexandrite Ore");
        addBlock(ModBlocks.NETHER_ALEXANDRITE_ORE, "Nether Alexandrite Ore");
        addBlock(ModBlocks.END_STONE_ALEXANDRITE_ORE, "End Stone Alexandrite Ore");
        addBlock(ModBlocks.SOUND_BLOCK, "Sound Block");
        addBlock(ModBlocks.ALEXANDRITE_STAIRS, "Alexandrite Stairs");
        addBlock(ModBlocks.ALEXANDRITE_SLAB, "Alexandrite Slab");
        addBlock(ModBlocks.ALEXANDRITE_BUTTON, "Alexandrite Button");
        addBlock(ModBlocks.ALEXANDRITE_PRESSURE_PLATE, "Alexandrite Pressure Plate");
        addBlock(ModBlocks.ALEXANDRITE_FENCE, "Alexandrite Fence");
        addBlock(ModBlocks.ALEXANDRITE_FENCE_GATE, "Alexandrite Fence Gate");
        addBlock(ModBlocks.ALEXANDRITE_WALL, "Alexandrite Wall");
        addBlock(ModBlocks.ALEXANDRITE_DOOR, "Alexandrite Door");
        addBlock(ModBlocks.ALEXANDRITE_TRAPDOOR, "Alexandrite Trapdoor");
        addBlock(ModBlocks.ALEXANDRITE_LAMP, "Alexandrite Lamp");
        addBlock(ModBlocks.SNAPDRAGON, "Snapdragon");
        addBlock(ModBlocks.GEM_EMPOWERING_STATION, "Gem Empowering Station");
        addBlock(ModFluids.SOAP_WATER_BLOCK, "Soap Water");
        addBlock(ModBlocks.WALNUT_LEAVES, "Walnut Leaves");
        addBlock(ModBlocks.WALNUT_PLANKS, "Walnut Planks");
        addBlock(ModBlocks.WALNUT_LOG, "Walnut Log");
        addBlock(ModBlocks.WALNUT_WOOD, "Walnut Wood");
        addBlock(ModBlocks.STRIPPED_WALNUT_WOOD, "Stripped Walnut Wood");
        addBlock(ModBlocks.STRIPPED_WALNUT_LOG, "Stripped Walnut Log");
        addBlock(ModBlocks.WALNUT_SAPLING, "Walnut Sapling");
        addEntityType(ModEntities.RHINO, "Rhino");



        //Creative mode menu
        add(ModCreativeModeTabs.modcourse_creative_mode_blocks, "Mod Course Blocks");
        add(ModCreativeModeTabs.modcourse_creative_mode_items, "Mod Course Items");

        add("item.mccourse.metaldetector.no.valuables", "No valuables detected.");

    }
}
