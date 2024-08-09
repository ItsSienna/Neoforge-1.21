package net.sienna.mccourse.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sienna.mccourse.MCCourseMod;

import java.util.function.Supplier;

//This is where we register (yes, they need registering!) particles.
//Register a particle here, create a class for them
//Then, write the (very simple!) json file in mccourse/particles (WITH AN S)
//Then add their texture to mccourse/textures/particle (WITHOUT AN S)
public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MCCourseMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }

    //Funnily enough, we don't actually call the class here - we're using SimpleParticleType. We do still need to connect them. This is done in an event.
    public static final Supplier<SimpleParticleType> ALEXANDRITE_PARTICLE = PARTICLE_TYPES.register("alexandrite_particle",
            () -> new SimpleParticleType(true));

}
