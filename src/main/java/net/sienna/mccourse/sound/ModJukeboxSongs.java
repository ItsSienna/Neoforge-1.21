package net.sienna.mccourse.sound;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.sienna.mccourse.MCCourseMod;

public class ModJukeboxSongs {
    public static final ResourceKey<JukeboxSong> BAR_BRAWL = create("bar_brawl");

    public static void bootstrap(BootstrapContext<JukeboxSong> context) {
        register(context, BAR_BRAWL, ModSounds.BAR_BRAWL, 121, 1);
    }



    private static ResourceKey<JukeboxSong> create(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, name));
    }

    private static void register(
            BootstrapContext<JukeboxSong> context, ResourceKey<JukeboxSong> key, Holder<SoundEvent> soundEvent, int lengthInSeconds, int comparatorOutput
    ) {
        context.register(
                key,
                new JukeboxSong(soundEvent, Component.translatable(Util.makeDescriptionId("jukebox_song", key.location())), (float)lengthInSeconds, comparatorOutput)
        );
    }
}
