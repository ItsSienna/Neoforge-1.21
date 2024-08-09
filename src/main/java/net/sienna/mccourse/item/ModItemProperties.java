package net.sienna.mccourse.item;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

//This class is required for things like the bow. The bow has an animation for when its being used, so it requires custom item properties.
public class ModItemProperties {
    public static void addCustomItemProperties(){
        makeBow(ModItems.ALEXANDRITE_BOW.get());
        makeShield(ModItems.ALEXANDRITE_SHIELD.get());
    }

    //This was copy-pasted from Minecraft's code - the weird parameter names are a result of obfuscation, but as long as they remain the same,
    //we don't need to change anything.
    //The point is, the bow will change its texture to "pull" and "pulling" depending on its state.
    private static void makeBow(Item item) {
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pull"), (p_344163_, p_344164_, p_344165_, p_344166_) -> {
            if (p_344165_ == null) {
                return 0.0F;
            } else {
                return p_344165_.getUseItem() != p_344163_ ? 0.0F : (float)(p_344163_.getUseDuration(p_344165_) - p_344165_.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
            return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
        });
    }

    //Same as above - just copied from the ItemProperties tab, and changes between normal shield and blocking.
    private static void makeShield (Item item) {
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("blocking"), (p_174575_, p_174576_, p_174577_, p_174578_) -> {
            return p_174577_ != null && p_174577_.isUsingItem() && p_174577_.getUseItem() == p_174575_ ? 1.0F : 0.0F;
        });
    }


}
