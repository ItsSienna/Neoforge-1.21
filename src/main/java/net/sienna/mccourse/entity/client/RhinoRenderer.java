package net.sienna.mccourse.entity.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.entity.custom.RhinoEntity;
import net.sienna.mccourse.entity.layers.ModModelLayers;
import net.sienna.mccourse.entity.variant.RhinoVariant;

import java.util.Map;

public class RhinoRenderer extends MobRenderer<RhinoEntity, RhinoModel<RhinoEntity>> {
    //Defining the resourcelocations for each variant of rhino
    private static final Map<RhinoVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(RhinoVariant.class), map -> {
                map.put(RhinoVariant.DEFAULT, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "textures/entity/rhino.png"));
                map.put(RhinoVariant.WHITE, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "textures/entity/white_rhino.png"));

            });

    public RhinoRenderer(EntityRendererProvider.Context context) {
        super(context, new RhinoModel<>(context.bakeLayer(ModModelLayers.RHINO_LAYER)), 2f);
    }

    @Override
    public ResourceLocation getTextureLocation(RhinoEntity rhinoEntity) {
        return LOCATION_BY_VARIANT.get(rhinoEntity.getVariant());
    }
    //baaaaaaaaby
    @Override
    public void render(RhinoEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        if (entity.isBaby()){
            poseStack.scale(0.45f, 0.45f, 0.45f);
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
