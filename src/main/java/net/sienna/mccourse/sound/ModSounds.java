package net.sienna.mccourse.sound;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sienna.mccourse.MCCourseMod;

import java.util.function.Supplier;

public class ModSounds {
    //Sounds! Sounds need to be registered.
    //To add a sound, create a DeferredHolder with the name (or a SoundType if its a sound type)
    //Add the name (and its sounds list) to sounds.json
    // //If it's a music desk, set "stream" to true, because it's being played over a longer period of time.
    //If applicable, add translations to en_us.
    //Sounds need to be MONO and .ogg files.
    //Then, go into whatever class it is you've made a sound for and call it!
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MCCourseMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvents(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    //This is where we add sounds - they all go here
    public static final Supplier<SoundEvent> METAL_DETECTOR_FOUND_ORE = registerSoundEvents("metal_detector_found_ore");
    //This is all for the alexandrite lamp
    public static final Supplier<SoundEvent> ALEXANDRITE_LAMP_BREAK = registerSoundEvents("alexandrite_lamp_break");
    public static final Supplier<SoundEvent> ALEXANDRITE_LAMP_STEP = registerSoundEvents("alexandrite_lamp_step");
    public static final Supplier<SoundEvent> ALEXANDRITE_LAMP_FALL = registerSoundEvents("alexandrite_lamp_fall");
    public static final Supplier<SoundEvent> ALEXANDRITE_LAMP_PLACE = registerSoundEvents("alexandrite_lamp_place");
    public static final Supplier<SoundEvent> ALEXANDRITE_LAMP_HIT = registerSoundEvents("alexandrite_lamp_hit");

    public static final Holder<SoundEvent> BAR_BRAWL = registerSoundEvents("bar_brawl");


    //This is for sound groups - sounds are registered above, but sound GROUPS are registered here. BREAK, STEP, PLACE, HIT, FALL
    public static final DeferredSoundType ALEXANDRITE_LAMP_SOUNDS = new DeferredSoundType(1f, 1f,
            ModSounds.ALEXANDRITE_LAMP_BREAK, ModSounds.ALEXANDRITE_LAMP_STEP,
            ModSounds.ALEXANDRITE_LAMP_PLACE, ModSounds.ALEXANDRITE_LAMP_HIT,
            ModSounds.ALEXANDRITE_LAMP_FALL);

    private static ResourceKey<JukeboxSong> createSong(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, name));
    }

}
