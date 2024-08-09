package net.sienna.mccourse.enchantment;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Unit;

import java.util.function.UnaryOperator;

public interface ModEnchantmentEffectComponents {
    Codec<DataComponentType<?>> COMPONENT_CODEC = Codec.lazyInitialized(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE::byNameCodec);
    Codec<DataComponentMap> CODEC = DataComponentMap.makeCodec(COMPONENT_CODEC);

    DataComponentType<Unit> FLOWER_WALKER = register("flower_walker", (operator) -> {
        return operator.persistent(Unit.CODEC);
    });
    DataComponentType<Unit> LIGHTNING_STRIKER = register("lightning_striker", (operator) -> {
        return operator.persistent(Unit.CODEC);
    });


    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> operator) {
            return Registry.register(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, name, operator.apply(DataComponentType.builder()).build());
    }
}
