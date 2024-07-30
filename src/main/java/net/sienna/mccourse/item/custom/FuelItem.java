package net.sienna.mccourse.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;

public class FuelItem extends Item {

    private int burnTime;
    public FuelItem(Properties properties, int burnTime) {
        super(properties);
        this.burnTime = burnTime;
    }
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.burnTime;
    }
}
