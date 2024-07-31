package net.sienna.mccourse.item;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

import static net.sienna.mccourse.MCCourseMod.MOD_ID;

public class ModArmorMaterials {

    //ArmorMaterials require registering. That's what we're doing below. Remember to add the register event in the main class.
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, MOD_ID);
    public static void register(IEventBus eventBus) {
        ARMOR_MATERIALS.register(eventBus);
    }

    //Unlike ToolTiers, armor materials actually need to be registered. We've used Minecraft's armour register (BuiltInRegistries) above, then used it to create and register the Alexandrite armour below.
    public static final Holder<ArmorMaterial> ALEXANDRITE =
            ARMOR_MATERIALS.register("alexandrite", () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 3);
                        map.put(ArmorItem.Type.LEGGINGS, 6);
                        map.put(ArmorItem.Type.HELMET, 3);
                        map.put(ArmorItem.Type.CHESTPLATE, 8);
                        map.put(ArmorItem.Type.BODY, 4);
                    }), 20, SoundEvents.ARMOR_EQUIP_DIAMOND, () -> Ingredient.of(ModItems.ALEXANDRITE),
                    List.of(
                            new ArmorMaterial.Layer(
                                    ResourceLocation.fromNamespaceAndPath(MOD_ID, "alexandrite"
                                    )),
                            //This sets the first layer of armour
                            new ArmorMaterial.Layer(
                                    ResourceLocation.fromNamespaceAndPath(MOD_ID, "alexandrite"), "", true)),
                            4, 1));
                            //This sets a second layer. Usually, the suffix above is something, but in this case I've left it blank so I don't need two textures.
                            //To make armour trimmable, just set "dyeable" above to true in this class, then add the "TRIMMABLE ARMOUR" tag in ModItemTagProvider.
                            //I'd still recommend going through the lecture, but it's largely irrelevant for armour.

    //Fun fact, only diamond and netherite armour has a nonzero toughness (2 and 3 respectively)
    //and only netherite has a nonzero knockback resistance!

    //Now we can refer to this during the actual item registration in ModItems.
}
