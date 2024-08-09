package net.sienna.mccourse.fluid;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.block.ModBlocks;
import net.sienna.mccourse.item.ModItems;

import java.util.function.Supplier;

//Per fluid, there is a LOT that goes into it.
//Maybe it's worth separating each fluid out into its own class? I'm not sure how I could abstract the processes - there probably is a way!
/* To make a fluid:
Register its fluid TYPE in ModFluidTypes
Create the BaseFlowingFluid.Properties for the fluid
Register the fluid itself in this class - requires registering both source and flowing fluids.
Create the source block and the bucket item (probably in here too to keep things together) - PUT THE BUCKET IN THE CREATIVE MODE TAB!
Set its render type in the main class, under ClientSetup (because they're a render type, they go in the client section!)
Register it under client extensions, again in the main class!
Add the water/lava tags to the fluid (depending on what physics you want) in the ModFluidTagProvider class
Cry
*/
public class ModFluids {

    //HERE I GO REGISTERING AGAIN... AGAIN! Before was fluid TYPES, now we're doing actual fluids
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, MCCourseMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }

    //And here's our fluid! MAKE SURE THAT ONE IS .Source, AND THE OTHER IS .Flowing!!! AND ADD "FLOWING" TO THE NAME!
    public static final Supplier<FlowingFluid> SOURCE_SOAP_WATER = FLUIDS.register("source_soap_water",
            () -> new BaseFlowingFluid.Source(ModFluids.SOAP_WATER_FLUID_PROPERTIES));
    public static final Supplier<FlowingFluid> FLOWING_SOAP_WATER = FLUIDS.register("flowing_soap_water_fluid",
            () -> new BaseFlowingFluid.Flowing(ModFluids.SOAP_WATER_FLUID_PROPERTIES));

    //Custom fluid block! Call .noLootTables otherwise "issues", apparently...
    public static final DeferredBlock<LiquidBlock> SOAP_WATER_BLOCK = ModBlocks.BLOCKS.register("soap_water_block",
            () -> new LiquidBlock(ModFluids.SOURCE_SOAP_WATER.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));
    //Custom bucket! Craft remainder leaves the bucket behind, and it only stacks to 1.
    public static final DeferredItem<Item> SOAP_WATER_BUCKET = ModItems.ITEMS.register("soap_water_bucket",
            () -> new BucketItem(ModFluids.SOURCE_SOAP_WATER.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    //Here we go, making the properties for soap water.
    //Have to add both the block and the bucket for this!
    //There's also buttloads of properties to have a look at :)
    public static final BaseFlowingFluid.Properties SOAP_WATER_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
            ModFluidTypes.SOAP_WATER_FLUID_TYPE, SOURCE_SOAP_WATER , FLOWING_SOAP_WATER)
                    .slopeFindDistance(2).levelDecreasePerBlock(1).block(SOAP_WATER_BLOCK).bucket(SOAP_WATER_BUCKET);

    //ALL of the above (register method downwards) is used just for one fluid.
}
