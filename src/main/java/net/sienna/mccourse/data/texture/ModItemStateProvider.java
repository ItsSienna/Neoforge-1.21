package net.sienna.mccourse.data.texture;

import com.ibm.icu.impl.data.HolidayBundle_ja_JP;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.block.ModBlocks;
import net.sienna.mccourse.item.ModItems;

import java.lang.reflect.Field;
import java.util.List;

public class ModItemStateProvider extends ItemModelProvider {

    public ModItemStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MCCourseMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //Regular items
        item(ModItems.ALEXANDRITE.get());
        item(ModItems.RAW_ALEXANDRITE.get());
        item(ModItems.METAL_DETECTOR.get());
        item(ModItems.KOHLRABI.get());
        item(ModItems.PEAT_BRICK.get());
        item(ModItems.KOHLRABI_SEEDS.get());

        //There is no way to data-gen .json files for held trimmed armour, and I'm way too fucking lazy to edit 44 file names individually lol
        //Check the lectures if you wanna see the pain
        item(ModItems.ALEXANDRITE_HELMET.get());
        item(ModItems.ALEXANDRITE_CHESTPLATE.get());
        item(ModItems.ALEXANDRITE_LEGGINGS.get());
        item(ModItems.ALEXANDRITE_BOOTS.get());
        item(ModItems.ALEXANDRITE_HORSE_ARMOR.get());


        //Handheld items
        handheldItem(ModItems.ALEXANDRITE_SWORD.get());
        handheldItem(ModItems.ALEXANDRITE_PICKAXE.get());
        handheldItem(ModItems.ALEXANDRITE_SHOVEL.get());
        handheldItem(ModItems.ALEXANDRITE_AXE.get());
        handheldItem(ModItems.ALEXANDRITE_HOE.get());
        handheldItem(ModItems.ALEXANDRITE_PAXEL.get());
        handheldItem(ModItems.ALEXANDRITE_HAMMER.get());
        handheldItem(ModItems.SLOWING_SWORD.get());

        //Buttons, fences and walls! I've created custom methods for this - it looks different to Kaupenjoe's because 1.21 made ResourceLocation private, so you have to work around it.
        buttomItem(ModBlocks.ALEXANDRITE_BUTTON.get(), ModBlocks.ALEXANDRITE_BLOCK.get());
        fenceItem(ModBlocks.ALEXANDRITE_FENCE.get(), ModBlocks.ALEXANDRITE_BLOCK.get());
        wallItem(ModBlocks.ALEXANDRITE_WALL.get(), ModBlocks.ALEXANDRITE_BLOCK.get());

        //God damn doors need a special thing too. Except they're flat in the inventory, so they take the item/generated location
        blockItem(ModBlocks.ALEXANDRITE_DOOR.get());
    }

    private void trimmedArmour(Item item) {
        String itemName = getItemName(item);

    }

    private void item(Item item) { //Tells the game where to find the item texture, and also what kind of item they are (item/generated is just regular item, not like a sword)
        String name = getItemName(item);
        getBuilder(name)
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", "item/" + name);
    }

    private void handheldItem(Item item) { //Same as above, but for held items such as swords and tools
        String name = getItemName(item);
        getBuilder(name)
                .parent(getExistingFile(mcLoc("item/handheld")))
                .texture("layer0", "item/" + name);

    }
    private void blockItem(Block block) { //Tells the game where to find the item texture, and also what kind of item they are (item/generated is just regular item, not like a sword)
        String name = getItemName(block);
        getBuilder(name)
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", "item/" + name);
    }

    private String getItemName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString().replace(MCCourseMod.MOD_ID + ":","");
    }
    private String getItemName(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).toString().replace(MCCourseMod.MOD_ID + ":","");
    }

    //Here's the weird methods needed to make buttons, gates and walls work, owing to how different they look when placed down in different ways.
    public void buttomItem(Block block, Block baseBlock) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        ResourceLocation baseBlockKey = BuiltInRegistries.BLOCK.getKey(baseBlock);
        this.withExistingParent(blockKey.getPath(), mcLoc("block/button_inventory"))
                .texture("texture", "block/" + baseBlockKey.getPath());
    }
    public void fenceItem(Block block, Block baseBlock) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        ResourceLocation baseBlockKey = BuiltInRegistries.BLOCK.getKey(baseBlock);
        this.withExistingParent(blockKey.getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", "block/" + baseBlockKey.getPath());
    }
    public void wallItem(Block block, Block baseBlock) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        ResourceLocation baseBlockKey = BuiltInRegistries.BLOCK.getKey(baseBlock);
        this.withExistingParent(blockKey.getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", "block/" + baseBlockKey.getPath());
    }
}
