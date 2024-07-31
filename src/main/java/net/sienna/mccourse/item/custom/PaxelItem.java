package net.sienna.mccourse.item.custom;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.sienna.mccourse.util.ModTags;

public class PaxelItem extends DiggerItem {
    public PaxelItem(Tier tier, Properties properties) {
        super(tier, ModTags.Blocks.PAXEL_MINEABLE, properties);
        //Instead of "blocks", we're giving it a PAXEL_MINEABLE tag so that it can mine everything with this tag.
        //Later, we define this tag to be a combination of everything that can be mined with a pickaxe, shovel and axe.
    }
}
