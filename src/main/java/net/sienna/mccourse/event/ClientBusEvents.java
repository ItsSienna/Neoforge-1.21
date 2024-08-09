package net.sienna.mccourse.event;

import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.block.entity.ModBlockEntities;
import net.sienna.mccourse.particle.ModParticles;
import net.sienna.mccourse.particle.custom.AlexandriteParticle;

//This event bus is SPECIFICALLY FOR THE CLIENT. NOTHING ELSE.
//Remember, the client deals with rendering and such! Particles, FOV changes, etc
@EventBusSubscriber(modid = MCCourseMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientBusEvents {

    //Now, the particles we REGISTERED (ModParticles.ALEXANDRITE_PARTICLE) is attached to the new particle class (AlexandriteParticle) we created.
    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.ALEXANDRITE_PARTICLE.get(), AlexandriteParticle.Provider::new);
    }

    //BER stands for Block Entity Renderer! Jesus christ block entities are so confusing
    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.MOD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);

    }

}
