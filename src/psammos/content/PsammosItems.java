package psammos.content;

import arc.graphics.*;
import arc.struct.*;
import mindustry.content.Items;
import mindustry.type.*;

public class PsammosItems {
    public static Item
    osmium, silver, quartz, refinedMetal, peat, aerogel, memoryAlloy, desertGlassShard;

    public static final Seq<Item> psammosItems = new Seq<>();

    public static void load() {
        osmium = new Item("1-osmium", Color.valueOf("#66a4b4")) {{
            hardness = 2;
            cost = 0.5f;
        }};

        silver = new Item("2-silver", Color.valueOf("#b9d1e1")) {{
            hardness = 2;
            cost = 0.8f;
        }};

        quartz = new Item("3-quartz", Color.valueOf("#d4caba")) {{
            hardness = 2;
        }};

        refinedMetal = new Item("4-refined-metal", Color.valueOf("#c2c2d1"));

        peat = new Item("5-peat", Color.valueOf("#82624e")) {{
            hardness = 1;
            explosiveness = 0.1f;
            flammability = 1f;
            lowPriority = true;
        }};

        aerogel = new Item("aerogel", Color.valueOf("#a6ffda"));

        memoryAlloy = new Item("memory-alloy", Color.valueOf("#b675cf")) {{
            charge = 0.05f;
        }};

        desertGlassShard = new Item("desert-glass-shard", Color.valueOf("#82f3c8")) {{
            charge = 0.5f;
            radioactivity = 0.01f;
        }};

        psammosItems.addAll(
                osmium, silver, quartz, refinedMetal, peat, aerogel, memoryAlloy, desertGlassShard
        );
    }
}
