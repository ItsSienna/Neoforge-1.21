package net.sienna.mccourse;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sienna.mccourse.block.ModBlocks;
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
                output.accept(ModItems.PEAT_BRICK.get());// Add the example item to the tab
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
            }).build());

}
