package net.sienna.mccourse.item.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.sienna.mccourse.item.ModArmorMaterials;

import java.util.Map;

//Now we're extending the ArmorItem in order to give it a custom full-armour effect.
public class ModArmorItem extends ArmorItem {

    //We're going to make a map. It will map an armour material to an effect.
    //This is expandable (just use more .put()s), so we just need to make a new entry in the map every time we add a new armour set with another effect.
    //This ONLY WORKS WITH CUSTOM ARMOUR MATERIALS. Vanilla ones will require events and checking to make sure all armour is on.
    public static final Map<Holder<ArmorMaterial>, MobEffectInstance> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<Holder<ArmorMaterial>, MobEffectInstance>())
                    .put(ModArmorMaterials.ALEXANDRITE, new MobEffectInstance(MobEffects.JUMP, 200, 2))
                    .build();


    public ModArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    //Override inventoryTick method - essentially, when this (ModArmorItem) is inside the armor slot.
    //Don't need to worry about using a slotId because we cover that in later methods.
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        //We're giving effects, so make sure we're on the server!!
        if (!level.isClientSide() && (entity instanceof Player)) {
            Player player = (Player) entity;
            evaluateArmorEffects(player);
        }

    }

    //Basically just checking that the full armor set is in fact equipped
    //Boots are in armour slot 0, Leggings = 1, Chestplate = 2, Helmet = 3
    private boolean hasFullArmorEquipped(Player player) {
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack chestplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        //Returns true only iff everything is true - ie, if there is armour in every slot.
        return !boots.isEmpty() && !leggings.isEmpty() && !chestplate.isEmpty() && !helmet.isEmpty();
    }

    //This is where we apply the armour.
    private void evaluateArmorEffects(Player player) {
        for (Map.Entry<Holder<ArmorMaterial>, MobEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            Holder<ArmorMaterial> mapArmorMaterial = entry.getKey();
            MobEffectInstance mapEffect = entry.getValue();

            if(hasPlayerCorrectArmorOn(player, mapArmorMaterial)) {
                addEffectToPlayer(player, mapEffect);
            }
        }
    }

    //Where we're adding the effect
    private void addEffectToPlayer(Player player, MobEffectInstance mapEffect) {
        boolean hasPlayerEffect = player.hasEffect(mapEffect.getEffect());
        if(!hasPlayerEffect) {
            player.addEffect(new MobEffectInstance(mapEffect.getEffect(), mapEffect.getDuration(), mapEffect.getAmplifier(), false, false));
        }
    }

    //Now, we check that the armour is the CORRECT suit of armour (no mismatching!)
    private boolean hasPlayerCorrectArmorOn(Player player, Holder<ArmorMaterial> mapArmorMaterial) {
        for(ItemStack armorStack : player.getArmorSlots()) {
            //If the item isn't an armor item (eg an elytra) then we're getting false
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem boots = ((ArmorItem) player.getInventory().getArmor(0).getItem());
        ArmorItem leggings = ((ArmorItem) player.getInventory().getArmor(1).getItem());
        ArmorItem chestplate = ((ArmorItem) player.getInventory().getArmor(2).getItem());
        ArmorItem helmet = ((ArmorItem) player.getInventory().getArmor(3).getItem());

        return boots.getMaterial() == mapArmorMaterial && leggings.getMaterial() == mapArmorMaterial && chestplate.getMaterial() == mapArmorMaterial && helmet.getMaterial() == mapArmorMaterial;
    }

}
