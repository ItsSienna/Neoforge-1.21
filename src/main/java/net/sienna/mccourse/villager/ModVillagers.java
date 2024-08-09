package net.sienna.mccourse.villager;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.block.ModBlocks;

//Custom villagers!! I don't know how these work in 1.21 :(
//The Point of Interest type is the block that the villager needs to go to in order to get their profession.
public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, MCCourseMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, MCCourseMod.MOD_ID);

    public static void register(IEventBus eventBus){
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }

    //This sets the point of interest (composters, cartography tables, lecterns etc)
    public static final Holder<PoiType> SOUND_POI = POI_TYPES.register("sound_poi",
            //This immutableset basically says that whatever block state this block is in, it will be allowed.
            //EG, if we used the Alexandrite Lamp, it would be a poi type both if it were on or off.
            //You can also set it so it requires a lamp to be on or off.
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.SOUND_BLOCK.get().getStateDefinition().getPossibleStates()), 1, 1));

    //This sets the villager itself
    public static final Holder<VillagerProfession> SOUNDMASTER = VILLAGER_PROFESSIONS.register("soundmaster",
            () -> new VillagerProfession("soundmaster", holder -> holder.value() == SOUND_POI.value(), holder -> holder.value() == SOUND_POI.value(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_ARMORER));

}
