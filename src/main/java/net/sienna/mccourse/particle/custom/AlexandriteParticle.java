package net.sienna.mccourse.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

//PARTICLES!! This is what I'm excited for! This and entities - I'll get there eventually...
public class AlexandriteParticle extends TextureSheetParticle {
    //There are two constructors here. We want the one WITH speed because it gives more customisability.
    protected AlexandriteParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        this.friction = 0.8f; //Friction!
        this.xd = xSpeed; //xd, yd and zd are all speed parameters!
        this.yd = ySpeed;
        this.zd = zSpeed;

        this.quadSize *= 0.75f; //Size of the particle!
        this.lifetime = 20; //Lifetime in ticks of the particle!
        this.setSpriteFromAge(spriteSet); //Changes sprite depending on age.

        this.rCol = 1f; //Colours of the particle! 1,1,1 is the normal colour, but each colour goes from 0 to 1.
        this.gCol = 1f;
        this.bCol = 1f;
        //There is a RIDICULOUS amount of stuff you can do with particles. Check out the Particle class in Minecraft!
        //Also, if you press Ctrl+H (in IntelliJ!) on "TextureSheetParticle" you can see all the other particles that use this class.
        //Examples are dust, spells, drips, explosions, glow, noteblocks... The list goes on
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT; //For translucent particles!
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;


        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new AlexandriteParticle(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
        }

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }
    }
}
