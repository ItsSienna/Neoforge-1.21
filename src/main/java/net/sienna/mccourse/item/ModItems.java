package net.sienna.mccourse.item;

import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sienna.mccourse.block.ModBlocks;
import net.sienna.mccourse.entity.ModEntities;
import net.sienna.mccourse.item.custom.*;
import net.sienna.mccourse.sound.ModJukeboxSongs;

import static net.sienna.mccourse.MCCourseMod.MOD_ID;
/*
TO CREATE A NEW ITEM
1. Create a DeferredItem<Item> below. Use registerSimpleItem with new Item for a basic item, or just register for a custom item.
2. Add it to the creative mode tab (I have it in the item folder, it should really be in its own package) <- DO NOT FORGET I KEEP FORGETTING THIS STEP
3. Set a language translation in ModLangProvider
5. Set its recipe in ModRecipeProvider
6. Set any tags it may have (valuable tag, ore tag, etc)
7. Create a texture and put it in textures/item in resources
8. Use ModItemStateProvider to point the item to its texture (and saying whether its generated like regular items, or a tool like swords, or a custom model
 */

/*
Difference between an ITEM and an ITEMSTACK is similar to Blocks vs BlockStates.
If you and a friend have an iron sword, they are the same ITEM, but they are different ITEMSTACKS, because they're different iterations of the same item.
That's why when one takes damage, the other does not.
 */
public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    // Deferred register is a class that is a LIST OF ITEMS that are going to be registered at a certain moment in time when the mod is ready for them.

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
        //This actually registers the items and has to be called in the main class
    }
    //Simple items can be written like this
    public static final DeferredItem<Item> ALEXANDRITE = ITEMS.registerSimpleItem("alexandrite");
    public static final DeferredItem<Item> RAW_ALEXANDRITE = ITEMS.registerSimpleItem("raw_alexandrite");
    public static final DeferredItem<Item> KOHLRABI = ITEMS.registerSimpleItem("kohlrabi", new Item.Properties().food(ModFoodProperties.KOHLRABI));

    //Complex items and fuels should be written like this
    public static final DeferredItem<Item> METAL_DETECTOR = ITEMS.register("metal_detector", () -> new MetalDetectorItem(new Item.Properties()));
    public static final DeferredItem<Item> PEAT_BRICK = ITEMS.register("peat_brick", () -> new FuelItem(new Item.Properties(), 200));

    //Tools! They use a .attribute(createAttributes()) method to store their respective stats (damage, speed, same for all shovels / swords etc), then the tier adds bonuses depending on what it is. See ModToolTiers
    public static final DeferredItem<Item> ALEXANDRITE_SWORD = ITEMS.register("alexandrite_sword", () -> new SwordItem(ModToolTiers.ALEXANDRITE_TIER, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.ALEXANDRITE_TIER,3, -2.4f))));
    public static final DeferredItem<Item> ALEXANDRITE_PICKAXE = ITEMS.register("alexandrite_pickaxe", () -> new PickaxeItem(ModToolTiers.ALEXANDRITE_TIER, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.ALEXANDRITE_TIER,1, -2.8f))));
    public static final DeferredItem<Item> ALEXANDRITE_AXE = ITEMS.register("alexandrite_axe", () -> new AxeItem(ModToolTiers.ALEXANDRITE_TIER, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.ALEXANDRITE_TIER,5, -3f))));
    public static final DeferredItem<Item> ALEXANDRITE_SHOVEL = ITEMS.register("alexandrite_shovel", () -> new ShovelItem(ModToolTiers.ALEXANDRITE_TIER, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.ALEXANDRITE_TIER,1.5f, -3f))));
    public static final DeferredItem<Item> ALEXANDRITE_HOE = ITEMS.register("alexandrite_hoe", () -> new HoeItem(ModToolTiers.ALEXANDRITE_TIER, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.ALEXANDRITE_TIER,-4.0f, 0f))));

    //Paxel! Created a custom PaxelItem that simply extends the DiggerItem, except it has a specific tag, which is later used to combine all mineable tags together.
    public static final DeferredItem<Item> ALEXANDRITE_PAXEL = ITEMS.register("alexandrite_paxel", () -> new PaxelItem(ModToolTiers.ALEXANDRITE_TIER, new Item.Properties().attributes(DiggerItem.createAttributes(ModToolTiers.ALEXANDRITE_TIER,3, -2.4f))));

    //Hammer! Same as above!
    public static final DeferredItem<Item> ALEXANDRITE_HAMMER = ITEMS.register("alexandrite_hammer", () -> new HammerItem(ModToolTiers.ALEXANDRITE_TIER, new Item.Properties().attributes(DiggerItem.createAttributes(ModToolTiers.ALEXANDRITE_TIER, 1, -2.8f))));

    //Slowing sword! Custom SlowingSword class (I've used the same texture because lazy
    public static final DeferredItem<Item> SLOWING_SWORD = ITEMS.register("slowing_sword", () -> new SlowingSword(ModToolTiers.ALEXANDRITE_TIER, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.ALEXANDRITE_TIER,3, -2.4f))));

    //Armour! They make use of the ModArmorMaterials class made for a specific armor tier. Note, while ToolTiers didn't need to be registered, ArmorMaterials DO.
    public static final DeferredItem<Item> ALEXANDRITE_HELMET = ITEMS.register("alexandrite_helmet", () -> new ModArmorItem(ModArmorMaterials.ALEXANDRITE, ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ALEXANDRITE_CHESTPLATE = ITEMS.register("alexandrite_chestplate", () -> new ModArmorItem(ModArmorMaterials.ALEXANDRITE, ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ALEXANDRITE_LEGGINGS = ITEMS.register("alexandrite_leggings", () -> new ModArmorItem(ModArmorMaterials.ALEXANDRITE, ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ALEXANDRITE_BOOTS = ITEMS.register("alexandrite_boots", () -> new ModArmorItem(ModArmorMaterials.ALEXANDRITE, ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1)));

    //This is so weird. So, I originally thought that I would have to define a model for the horse armor and point it to the right texture.
    //Turns out, the BodyType.EQUESTRIAN hard-codes the model pointer into it (it points to textures/entity/horse/armor), and looks for a texture called "horse_armor_MATERIAL.png" on its own. Neat!
    public static final DeferredItem<Item> ALEXANDRITE_HORSE_ARMOR = ITEMS.register("alexandrite_horse_armor", () -> new AnimalArmorItem(ModArmorMaterials.ALEXANDRITE, AnimalArmorItem.BodyType.EQUESTRIAN, false, new Item.Properties()));

    //"ItemNameBlockItem" is a blockitem (ie seeds) with a specific item name (ie kohlrabi seeds)
    public static final DeferredItem<Item> KOHLRABI_SEEDS = ITEMS.register("kohlrabi_seeds", () -> new ItemNameBlockItem(ModBlocks.KOHLRABI_CROP.get(), new Item.Properties()));

    //This is a jukebox item. It is a simple item, but uses the .jukeboxPlayable tag under its item properties.
    public static final DeferredItem<Item> BAR_BRAWL_RECORD = ITEMS.registerSimpleItem("bar_brawl_record", new Item.Properties().jukeboxPlayable(ModJukeboxSongs.BAR_BRAWL));

    //This is the 3D model - its a super simple setup, the literal only thing that changes is the json file comes from BlockBench.
    //Obviously it doesn't do anything right now - we'd need a custom class for that! Once I can figure out particles, projectiles or hitboxes, I can make a laser maybe??
    public static final DeferredItem<Item> RADIATION_STAFF = ITEMS.register("radiation_staff",() -> new RadiationStaffItem(new Item.Properties().durability(1000)));

    //Bow!
    public static final DeferredItem<Item> ALEXANDRITE_BOW = ITEMS.register("alexandrite_bow", () -> new BowItem(new Item.Properties().stacksTo(1).durability(1024)));
    //Shield! Uses a 3D model!
    public static final DeferredItem<Item> ALEXANDRITE_SHIELD = ITEMS.register("alexandrite_shield", () -> new ShieldItem(new Item.Properties().stacksTo(1).durability(1024)));

    //SIGN ITEMS AAAAA
    public static final DeferredItem<Item> WALNUT_SIGN = ITEMS.register("walnut_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.WALNUT_SIGN.get(), ModBlocks.WALNUT_WALL_SIGN.get()));
    public static final DeferredItem<Item> WALNUT_HANGING_SIGN = ITEMS.register("walnut_hanging_sign", () -> new HangingSignItem(ModBlocks.WALNUT_HANGING_SIGN.get(), ModBlocks.WALNUT_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    //SpawnEgg
    public static final DeferredItem<Item> RHINO_SPAWN_EGG = ITEMS.register("rhino_spawn_egg", () -> new SpawnEggItem(ModEntities.RHINO.get(), 0x7e9680, 0xc5d1c5, new Item.Properties().stacksTo(16)));
    //Throwable item
    public static final DeferredItem<Item> DICE = ITEMS.register("dice", () -> new DiceItem(new Item.Properties()));



}
