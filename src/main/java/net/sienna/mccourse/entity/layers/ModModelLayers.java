package net.sienna.mccourse.entity.layers;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.sienna.mccourse.MCCourseMod;

public class ModModelLayers {
    public static final ModelLayerLocation RHINO_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "rhino_layer"), "rhino_layer");

    public static final ModelLayerLocation MAGIC_PROJECTILE_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "magic_projectile"), "magic_projectile_layer");
}
