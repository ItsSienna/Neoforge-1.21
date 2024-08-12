package net.sienna.mccourse.entity;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sienna.mccourse.MCCourseMod;
import net.sienna.mccourse.entity.custom.DiceProjectileEntity;
import net.sienna.mccourse.entity.custom.MagicProjectileEntity;
import net.sienna.mccourse.entity.custom.RhinoEntity;

import java.util.function.Supplier;

/*
ENTITIES!
For an entity, you need a blockbench model, an animations json and a texture png.
In custom/mobentities, you can set its goals, attributes, and how the animations behave.
Next, register the entity in this file.
Next, create the client classes. The MobModel is the export from BlockBench, and the MobRenderer is a new creation. They are in the client package.
Setup the animations within the model file (this.animateIdle or this.animate etc) - see other mobs to get what they do
Next, set up the mod model layer location for the mob in the ModModelLayers class. This is in the layers package, but this is for ALL mobs.
That model layer will be combined with the createBody in the model in an event.
This event is in event/ModEventBusEvents. Just need to basically apply the model to the model layer.
Now, create the mob renderer MobRenderer in the client package. give it its resourcelocation. REMEMBER TO ADD THE TEXTURE!
FINALLY, go to the main class, and in the clientsetup event, register the EntityRenderer with the entity!
FINALLY FINALLY... add a spawn egg :D In ModItems, then a lang file in ModLangProvider, then a ModItemModelProvider entry as usual
 */
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MCCourseMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

    //Now we're gonna register the entity.
    public static final Supplier<EntityType<RhinoEntity>> RHINO =
            ENTITY_TYPES.register("rhino", () -> EntityType.Builder.of(RhinoEntity::new, MobCategory.CREATURE)
                    //This is the size of the hitbox.
                    .sized(2.5f, 2.5f).build("rhino"));

    public static final Supplier<EntityType<DiceProjectileEntity>> DICE_PROJECTIlE = //v I hate this
            ENTITY_TYPES.register("dice_projectile", () -> EntityType.Builder.<DiceProjectileEntity>of(DiceProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("dice_projectile")
            );

    public static final Supplier<EntityType<MagicProjectileEntity>> MAGIC_PROJECTILE =
            ENTITY_TYPES.register("magic_projectile", () -> EntityType.Builder.<MagicProjectileEntity>of(MagicProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("magic_projectile")
            );

}
