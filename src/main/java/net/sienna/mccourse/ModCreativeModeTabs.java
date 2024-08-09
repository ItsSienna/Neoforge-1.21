package net.sienna.mccourse;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sienna.mccourse.block.ModBlocks;
import net.sienna.mccourse.fluid.ModFluids;
import net.sienna.mccourse.item.ModItems;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MCCourseMod.MOD_ID);
    public static String modcourse_creative_mode_items = "modcourse_creative_mode_items";
    public static String modcourse_creative_mode_blocks = "modcourse_creative_mode_blocks";
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MODCOURSE_ITEMS = CREATIVE_MODE_TABS.register(modcourse_creative_mode_items, () -> CreativeModeTab.builder()
            .title(Component.literal("Mod Items"))
            .icon(() -> ModItems.ALEXANDRITE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.ALEXANDRITE.get());
                output.accept(ModItems.RAW_ALEXANDRITE.get());
                output.accept(ModItems.METAL_DETECTOR.get());
                output.accept(ModItems.KOHLRABI.get());
                output.accept(ModItems.PEAT_BRICK.get());
                output.accept(ModItems.ALEXANDRITE_SWORD.get());
                output.accept(ModItems.ALEXANDRITE_PICKAXE.get());
                output.accept(ModItems.ALEXANDRITE_SHOVEL.get());
                output.accept(ModItems.ALEXANDRITE_AXE.get());
                output.accept(ModItems.ALEXANDRITE_HOE.get());
                output.accept(ModItems.ALEXANDRITE_PAXEL.get());
                output.accept(ModItems.ALEXANDRITE_HAMMER.get());
                output.accept(ModItems.SLOWING_SWORD.get());
                output.accept(ModItems.ALEXANDRITE_BOOTS.get());
                output.accept(ModItems.ALEXANDRITE_HELMET.get());
                output.accept(ModItems.ALEXANDRITE_CHESTPLATE.get());
                output.accept(ModItems.ALEXANDRITE_LEGGINGS.get());
                output.accept(ModItems.ALEXANDRITE_HORSE_ARMOR.get());
                output.accept(ModItems.KOHLRABI_SEEDS.get());
                output.accept(ModItems.BAR_BRAWL_RECORD.get());
                output.accept(ModItems.RADIATION_STAFF.get());
                output.accept(ModItems.ALEXANDRITE_BOW.get());
                output.accept(ModItems.ALEXANDRITE_SHIELD.get());
                output.accept(ModFluids.SOAP_WATER_BUCKET.get());

            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MODCOURSE_BLOCKS = CREATIVE_MODE_TABS.register(modcourse_creative_mode_blocks, () -> CreativeModeTab.builder()
            .title(Component.literal("Mod Blocks"))
            .icon(() -> ModBlocks.ALEXANDRITE_BLOCK.asItem().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModBlocks.ALEXANDRITE_BLOCK.get());
                output.accept(ModBlocks.RAW_ALEXANDRITE_BLOCK.get());
                output.accept(ModBlocks.ALEXANDRITE_ORE.get());
                output.accept(ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get());
                output.accept(ModBlocks.NETHER_ALEXANDRITE_ORE.get());
                output.accept(ModBlocks.END_STONE_ALEXANDRITE_ORE.get());
                output.accept(ModBlocks.SOUND_BLOCK.get());// Add the example item to the tab
                output.accept(ModBlocks.ALEXANDRITE_STAIRS.get());
                output.accept(ModBlocks.ALEXANDRITE_SLAB.get());
                output.accept(ModBlocks.ALEXANDRITE_BUTTON.get());
                output.accept(ModBlocks.ALEXANDRITE_PRESSURE_PLATE.get());
                output.accept(ModBlocks.ALEXANDRITE_FENCE.get());
                output.accept(ModBlocks.ALEXANDRITE_FENCE_GATE.get());
                output.accept(ModBlocks.ALEXANDRITE_WALL.get());
                output.accept(ModBlocks.ALEXANDRITE_DOOR.get());
                output.accept(ModBlocks.ALEXANDRITE_TRAPDOOR.get());
                output.accept(ModBlocks.ALEXANDRITE_LAMP.get());
                output.accept(ModBlocks.SNAPDRAGON.get());
                output.accept(ModBlocks.GEM_EMPOWERING_STATION.get());
                output.accept(ModBlocks.WALNUT_WOOD.get());
                output.accept(ModBlocks.WALNUT_LOG.get());
                output.accept(ModBlocks.WALNUT_LEAVES.get());
                output.accept(ModBlocks.WALNUT_PLANKS.get());
                output.accept(ModBlocks.STRIPPED_WALNUT_LOG.get());
                output.accept(ModBlocks.STRIPPED_WALNUT_WOOD.get());
                output.accept(ModBlocks.WALNUT_SAPLING.get());
                output.accept(ModBlocks.WALNUT_SIGN.get());
                output.accept(ModBlocks.WALNUT_HANGING_SIGN.get());
            }).build());

}
