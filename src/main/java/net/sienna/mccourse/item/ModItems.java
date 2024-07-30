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

//TO CREATE A NEW ITEM:
//First, add the RegistryObject<Item> in the ModItems class (with all the item properties you may want)
//Then, add it to a creative tab in the main class
//Then, set its language translation in lang/en_us.json (eg mccourse.item.alexandrite becomes "Alexandrite"
//Then, create its model/item file with appropriate parents and a link to its texture file
//Then, put its .png file into textures/item (and make sure it is named the same as the model/item entry

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
