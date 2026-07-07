package psammos.content.blocks;

import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.logic.MessageBlock;
import psammos.content.PsammosItems;

import static mindustry.type.ItemStack.with;

public class PsammosLogicBlocks {
    public static Block heatproofMessage;

    public static void load() {
        heatproofMessage = new MessageBlock("heatproof-message"){{
            requirements(Category.logic, with(PsammosItems.quartz, 5, PsammosItems.osmium, 5));
        }};
    }
}
