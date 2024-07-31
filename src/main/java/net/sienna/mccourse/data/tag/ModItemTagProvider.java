package net.sienna.mccourse.data.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.sienna.mccourse.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {


    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        //I can't believe armour is the first time I get to use this class
        tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.ALEXANDRITE_HELMET.get())
                .add(ModItems.ALEXANDRITE_CHESTPLATE.get())
                .add(ModItems.ALEXANDRITE_LEGGINGS.get())
                .add(ModItems.ALEXANDRITE_BOOTS.get());

    }
}
