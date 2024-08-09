package net.sienna.mccourse.util;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.sienna.mccourse.MCCourseMod;
import org.intellij.lang.annotations.Identifier;

public class ModWoodTypes {
    //Addded a wood type here and in ModClientEvents
    public static final WoodType WALNUT = WoodType.register(new WoodType(MCCourseMod.MOD_ID + ":walnut", BlockSetType.OAK));
}
