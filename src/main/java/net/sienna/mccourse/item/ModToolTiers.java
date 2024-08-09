package net.sienna.mccourse.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

import static net.sienna.mccourse.util.ModTags.Blocks.NEEDS_ALEXANDRITE_TOOL;

//I DID THIS MYSELF!! The lectures tell you about ForgeRegistries, but again, the tags in 1.21 are totally different.
public class ModToolTiers {
    public static final Tier ALEXANDRITE_TIER = new SimpleTier(NEEDS_ALEXANDRITE_TOOL, 3000, 12.0f, 5.0f, 22,
            () -> Ingredient.of(ModItems.ALEXANDRITE.get()));
}
