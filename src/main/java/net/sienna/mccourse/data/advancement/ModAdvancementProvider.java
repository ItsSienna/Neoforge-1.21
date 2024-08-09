package net.sienna.mccourse.data.advancement;

import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.item.ModItems;

import java.util.function.Consumer;

public class ModAdvancementProvider implements AdvancementProvider.AdvancementGenerator {

    //This is where advancements are made! They are purely made through datageneration.
    //I actually know how to make custom advancements already - however, I know how to write their json files. This will be way better because datagen :D
    @Override
    public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {

        //So, the root advancement is an AdvancementHolder...
        AdvancementHolder rootAdvancement = Advancement.Builder.advancement()
                .display(new ItemStack(ModItems.ALEXANDRITE.get()),
                        Component.literal("MC Course"),
                        Component.literal("Power of alexandrite"),
                        ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "textures/block/alexandrite_ore.png"),
                        AdvancementType.TASK,
                        true, true, false)
                //Check out all the triggers to see what we can detect with our advancement
                .addCriterion("has_alexandrite", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ALEXANDRITE.get()))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "mccourse"), existingFileHelper);

        //while the actual advancements are just Advancements.
        Advancement metalDetector = Advancement.Builder.advancement()
                .parent(rootAdvancement)
                .display(new ItemStack(ModItems.METAL_DETECTOR.get()),
                        Component.literal("Metal Detector"),
                        Component.literal("Batteries not included"),
                        null,
                        AdvancementType.TASK,
                        true, true, false)
                //Check out all the triggers to see what we can detect with our advancement
                .addCriterion("has_metal_detector", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.METAL_DETECTOR.get()))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "metal_detector"), existingFileHelper).value();

    }
}
