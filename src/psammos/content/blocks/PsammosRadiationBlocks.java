package psammos.content.blocks;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawGlowRegion;
import mindustry.world.draw.DrawMulti;
import psammos.content.PsammosItems;
import psammos.content.PsammosLiquids;
import psammos.content.PsammosRadTypes;
import psammos.type.RadiationType;
import psammos.world.blocks.radiation.*;
import psammos.world.draw.DrawDirectionalRegion;
import psammos.world.draw.DrawRadiationBeams;

import static mindustry.type.ItemStack.with;

public class PsammosRadiationBlocks {
    public static Block
            radiationSource, radiationVoid, focuser, mirror, convexLens, concaveLens, solarCollector, ultravioletLamp;

    public static void load() {
        radiationSource = new RadiationSource("radiation-source"){{
            category = Category.power;
        }};

        radiationVoid = new RadiationVoid("radiation-void"){{
            category = Category.power;
        }};

        focuser = new Focuser("focuser"){{
            requirements(Category.power, with(PsammosItems.silver, 5, PsammosItems.desertGlassShard, 3));
        }};

        mirror = new Mirror("mirror"){{
            requirements(Category.power, with(PsammosItems.silver, 7, PsammosItems.desertGlassShard, 5));
        }};

        convexLens = new Lens("convex-lens"){{
            requirements(Category.power, with(PsammosItems.osmium, 5, PsammosItems.silver, 10, PsammosItems.desertGlassShard, 20));
            concave = false;
        }};

        concaveLens = new Lens("concave-lens"){{
            requirements(Category.power, with(PsammosItems.osmium, 5, PsammosItems.silver, 10, PsammosItems.desertGlassShard, 20));
            concave = true;
        }};

        solarCollector = new SolarCollector("solar-collector") {{
            requirements(Category.power, with(PsammosItems.refinedMetal, 10, PsammosItems.silver, 30, PsammosItems.desertGlassShard, 30));
            size = 3;
            radOutputAmount = 3f;
            radOutputType = PsammosRadTypes.light;
        }};

        ultravioletLamp = new RadiationProducer("uv-lamp"){{
            requirements(Category.power, with( Items.silicon, 20, PsammosItems.refinedMetal, 30, PsammosItems.desertGlassShard, 20, PsammosItems.osmium, 60));

            size = 3;
            squareSprite = false;
            hasItems = true;
            hasLiquids = false;
            itemCapacity = 10;

            drawer = new DrawMulti(
                    new DrawDefault(),
                    new DrawDirectionalRegion(),
                    new DrawGlowRegion(){{
                        color = new Color(1f, 0.22f, 0.22f, 0.8f);
                        alpha = 0.8f;
                    }},
                    new DrawRadiationBeams()
            );
            rotate = true;
            rotateDraw = false;

            consumePower(240f/60f*4f);
            consumeItem(PsammosItems.peat, 2);
            consumeItem(PsammosItems.quartz, 1);

            radOutputAmount = 40;
            radOutputType = PsammosRadTypes.UV;
            craftTime = 240;
        }};
    }
}
