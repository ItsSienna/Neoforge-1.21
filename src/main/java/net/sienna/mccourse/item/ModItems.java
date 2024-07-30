package net.sienna.mccourse.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.sienna.mccourse.item.custom.FuelItem;
import net.sienna.mccourse.item.custom.MetalDetectorItem;

import static net.sienna.mccourse.MCCourseMod.MOD_ID;
/*
TO CREATE A NEW ITEM
1. Create a DeferredItem<Item> below. Use registerSimpleItem with new Item for a basic item, or just register for a custom item.
2. Add it to the creative mode tab (I have it in the item folder, it should really be in its own package
3. Set a language translation in ModLangProvider
5. Set its recipe in ModRecipeProvider
6. Set any tags it may have (valuable tag, ore tag, etc)
7. Create a texture and put it in textures/item in resources
8. Use ModItemStateProvider to point the item to its texture (and saying whether its generated like regular items, or a tool like swords, or a custom model
 */
public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    // Deferred register is a class that is a LIST OF ITEMS that are going to be registered at a certain moment in time when the mod is ready for them.

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
        //This actually registers the items and has to be called in the main class
    }
    //Simple items can be written like this
    public static final DeferredItem<Item> ALEXANDRITE = ITEMS.registerSimpleItem("alexandrite", new Item.Properties());
    public static final DeferredItem<Item> RAW_ALEXANDRITE = ITEMS.registerSimpleItem("raw_alexandrite", new Item.Properties());
    public static final DeferredItem<Item> KOHLRABI = ITEMS.registerSimpleItem("kohlrabi", new Item.Properties().food(ModFoodProperties.KOHLRABI));

    //Complex items and fuels should be written like this
    public static final DeferredItem<Item> METAL_DETECTOR = ITEMS.register("metal_detector", () -> new MetalDetectorItem(new Item.Properties()));
    public static final DeferredItem<Item> PEAT_BRICK = ITEMS.register("peat_brick", () -> new FuelItem(new Item.Properties(), 200));
}
