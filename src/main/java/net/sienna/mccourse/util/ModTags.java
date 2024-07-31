package net.sienna.mccourse.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.sienna.mccourse.MCCourseMod;

public class ModTags {
    public static class Items {

        private static TagKey<Item> modTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, name));
            //I'm pretty sure this is right? Middle-clicking on "ResourceLocation" takes me to its class, and the method "fromNamespaceAndPath" returns what I want!
        }

        private static TagKey<Item> neoforgeTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("neoforge", name));
            //This allows us to return a neoforge tag specifically (which is widely used)
        }
    }

    public static class Blocks {

        //This is a custom tag - we can assign blocks to have this tag, then change our metal detector to detect blocks with those tags.
        //Now that we've made the "metal_detector_valuables" tag, we can go into our "tags" folder and make a json file with a list of blocks.
        //This is way better than the fucking massive boolean statement that used to be in our metal detector class!
        public static final TagKey<Block> METAL_DETECTOR_VALUABLES = modBlockTag("metal_detector_valuables");
        public static final TagKey<Block> NEEDS_ALEXANDRITE_TOOL = modBlockTag("needs_alexandrite_tool");
        public static final TagKey<Block> PAXEL_MINEABLE = modBlockTag("mineable/paxel");

        private static TagKey<Block> modBlockTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, name));
        }

        private static TagKey<Item> modItemTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, name));
        }

        private static TagKey<Block> neoforgeBlockTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath("neoforge", name));
        }

        private static TagKey<Item> neoforgeItemTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("neoforge", name));
        }
    }
}
