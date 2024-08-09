package net.sienna.mccourse.potion;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.effect.ModEffects;

@EventBusSubscriber
//Potion! I did this myself B)
public class ModPotions {

    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(BuiltInRegistries.POTION, MCCourseMod.MOD_ID);

    public static void register(IEventBus eventBus){
        POTIONS.register(eventBus);
    }

    public static final Holder<Potion> SLIMY_POTION = POTIONS.register("slimy_potion", () -> new Potion(new MobEffectInstance(ModEffects.SLIMY_EFFECT, 3600)));

    @SubscribeEvent
    public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();
        builder.addMix(Potions.AWKWARD, Items.SLIME_BALL, SLIMY_POTION);
    }
}
