package net.sienna.mccourse.fluid;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.sienna.mccourse.MCCourseMod;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class ModFluidTypes {

    //We're just grabbing the water textures and putting them in variables for use down below. Don't have to do this, we can just create our own!
    public static final ResourceLocation WATER_STILL_RL = ResourceLocation.parse("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = ResourceLocation.parse("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = ResourceLocation.parse("block/water_overlay");

    //Oh boy, here I go registering again
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, MCCourseMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }

    //Helper method to make fluid types.
    private static Supplier<FluidType> registerFluidType(String name, FluidType fluidType) {
        return FLUID_TYPES.register(name, () -> fluidType);
    }

    //Fluids!
    public static final Supplier<FluidType> SOAP_WATER_FLUID_TYPE = registerFluidType("soap_water_fluid",
            //We're doing ints in hexadecimal. The first bit (A1) is the alpha value, and then E038D0 is the actual colour code. That's the tint colour!
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0xA100ff7f,
                    //Don't panic, you're smart enough to know what's happening here!
                    //We're using a Vector3 from 0 to 1, and converting it into a 0-255 scale for colours. ezpz!
                    //This is for the fog colour!
                    new Vector3f(224f/255f, 56f/255f, 208f/255f),
                    //This is where we MAKE OUR FLUID! There are SO MANY builder methods we can call here, look through them all :D
                    //Don't think viscosity and density actually change anything physically, but if a mod out there looks for those, it will see them!
                    FluidType.Properties.create()));

}
