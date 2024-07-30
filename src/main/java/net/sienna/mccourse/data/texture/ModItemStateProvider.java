package net.sienna.mccourse.data.texture;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.item.ModItems;

public class ModItemStateProvider extends ItemModelProvider {

    public ModItemStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MCCourseMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        item(ModItems.ALEXANDRITE.get());
        item(ModItems.RAW_ALEXANDRITE.get());
        item(ModItems.METAL_DETECTOR.get());
        item(ModItems.KOHLRABI.get());
        item(ModItems.PEAT_BRICK.get());
    }

    private void item(Item item) { //Tells the game where to find the item texture, and also what kind of item they are (item/generated is just regular item, not like a sword)
        String name = getItemName(item);
        getBuilder(name)
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", "item/" + name);
    }

    private String getItemName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString().replace(MCCourseMod.MOD_ID + ":","");
    }
}
