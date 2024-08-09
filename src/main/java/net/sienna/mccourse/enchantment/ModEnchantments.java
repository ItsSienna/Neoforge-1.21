package net.sienna.mccourse.enchantment;


import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.sienna.mccourse.MCCourseMod.MOD_ID;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> FLOWER_WALKER = key("flower_walker");
    public static final ResourceKey<Enchantment> LIGHTNING_STRIKER = key("lightning_striker");

    public ModEnchantments(){
    }

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<DamageType> damageTypeHolderGetter = context.lookup(Registries.DAMAGE_TYPE);
        HolderGetter<Enchantment> enchantmentHolderGetter = context.lookup(Registries.ENCHANTMENT);
        HolderGetter<Item> itemHolderGetter = context.lookup(Registries.ITEM);
        HolderGetter<Block> blockHolderGetter = context.lookup(Registries.BLOCK);

        register(context, FLOWER_WALKER,
                Enchantment.enchantment(Enchantment.definition(itemHolderGetter.getOrThrow(ItemTags.FOOT_ARMOR),
                        1, 2, Enchantment.constantCost(25), Enchantment.constantCost(50),
                        8, new EquipmentSlotGroup[]{EquipmentSlotGroup.FEET}))
                        .withEffect(ModEnchantmentEffectComponents.FLOWER_WALKER));
        register(context, LIGHTNING_STRIKER,
                Enchantment.enchantment(Enchantment.definition(itemHolderGetter.getOrThrow(ItemTags.CHEST_ARMOR),
                                1, 2, Enchantment.constantCost(25), Enchantment.constantCost(50),
                                8, new EquipmentSlotGroup[]{EquipmentSlotGroup.CHEST}))
                        .withEffect(ModEnchantmentEffectComponents.LIGHTNING_STRIKER));
    }


    public static final DeferredRegister<DataComponentType<?>> ENCHANTMENTS = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, MOD_ID);
    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }


    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
    }

    public static <T> Holder<T> unwrap(Level level, ResourceKey<T> key) {
        return level.registryAccess().registryOrThrow(key.registryKey()).getHolderOrThrow(key);
    }
}
