package psammos.content.blocks;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.graphics.Layer;
import mindustry.type.Category;
import mindustry.world.*;
import mindustry.world.blocks.defense.RegenProjector;
import mindustry.world.blocks.distribution.DirectionalUnloader;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.draw.*;
import psammos.content.PsammosItems;
import psammos.content.PsammosLiquids;
import psammos.content.PsammosUnitTypes;

import static mindustry.type.ItemStack.with;

public class PsammosEffectBlocks {
    public static Block
            coreDust, coreDune, heatproofContainer, heatproofVault, heatproofUnloader, healingProjector;

    public static void load() {
        coreDust = new CoreBlock("1a-core-dust"){{
            requirements(Category.effect, with(PsammosItems.osmium, 1000, PsammosItems.silver, 1000));
            alwaysUnlocked = true;

            isFirstTier = true;
            health = 1600;
            size = 3;
            absorbLasers = true;
            itemCapacity = 6000;
            unitCapModifier = 12;
            squareSprite = false;
            unitType = PsammosUnitTypes.gradient;
        }};

        coreDune = new CoreBlock("core-dune"){{
            requirements(Category.effect, with(PsammosItems.osmium, 3000, PsammosItems.silver, 3000, Items.silicon, 2000, PsammosItems.refinedMetal, 1000));
            researchCostMultiplier = 0.07f;

            health = 3800;
            size = 4;
            absorbLasers = true;
            itemCapacity = 11000;
            unitCapModifier = 20;
            squareSprite = false;
            unitType = PsammosUnitTypes.ascent;
        }};

        heatproofContainer = new StorageBlock("2a-heatproof-container"){{
            requirements(Category.effect, with(PsammosItems.refinedMetal, 80));
            researchCostMultiplier = 0.25f;

            health = 250;
            size = 2;
            itemCapacity = 400;
            squareSprite = false;
        }};

        heatproofVault = new StorageBlock("heatproof-vault"){{
            requirements(Category.effect, with(PsammosItems.refinedMetal, 200, PsammosItems.memoryAlloy, 80));
            researchCostMultiplier = 0.5f;

            health = 520;
            size = 3;
            itemCapacity = 1080;
            squareSprite = false;
        }};

        heatproofUnloader = new DirectionalUnloader("3a-heatproof-unloader"){{
            requirements(Category.effect, with(PsammosItems.refinedMetal, 15, Items.silicon, 10));

            size = 1;
            squareSprite = false;
            allowCoreUnload = true;
        }};

        healingProjector = new RegenProjector("4a-healing-projector"){{
            requirements(Category.effect, with(PsammosItems.osmium, 15, PsammosItems.refinedMetal, 15, Items.silicon, 20));

            health = 120;
            size = 2;
            squareSprite = false;
            range = 20;
            healPercent = 4 / 60f;
            baseColor = Color.valueOf("#84f491");
            hasPower = false;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(PsammosLiquids.methane, 2),
                    new DrawDefault(),
                    new DrawPulseShape(false){{
                        layer = Layer.effect;
                        color = Color.valueOf("#84f491");
                    }}
            );

            consumeLiquid(PsammosLiquids.methane, 1 / 60f);
        }};
    }
}
