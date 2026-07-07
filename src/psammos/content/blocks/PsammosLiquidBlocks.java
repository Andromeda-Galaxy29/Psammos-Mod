package psammos.content.blocks;

import arc.graphics.*;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.production.Pump;
import psammos.content.*;
import psammos.world.blocks.liquid.*;

import static mindustry.type.ItemStack.with;

public class PsammosLiquidBlocks {
    public static Block
            heatproofPump, pipe, vacuumPipe, pipeJunction, pipeRouter,
            heatproofLiquidContainer, heatproofLiquidTank, tunnelPipe, overflowPipe, underflowPipe;

    public static void load() {
        heatproofPump = new Pump("1a-quartz-pump"){{
            requirements(Category.liquid, with(PsammosItems.quartz, 30, PsammosItems.silver, 20, Items.silicon, 15));

            health = 60;
            size = 2;
            squareSprite = false;
            liquidCapacity = 15;
            pumpAmount = 0.1f;
            consumePower(0.5f);
        }};

        pipe = new ShadedConduit("2a-quartz-conduit"){{
            requirements(Category.liquid, with(PsammosItems.quartz, 1));
            buildCostMultiplier = 2f;

            health = 45;
            liquidCapacity = 10;
            botColor = Color.valueOf("#534d4a");
        }};

        vacuumPipe = new ArmoredShadedConduit("vacuum-pipe"){{
            requirements(Category.liquid, with(PsammosItems.aerogel, 1, PsammosItems.refinedMetal, 2));
            buildCostMultiplier = 2f;

            liquidPressure = 1.03f;
            health = 60;
            liquidCapacity = 16;
            botColor = Color.valueOf("#534d4a");
        }};

        pipeJunction = new BetterLiquidJunction("3a-quartz-liquid-junction"){{
            requirements(Category.liquid, with(PsammosItems.quartz, 8, PsammosItems.silver, 4));
            buildCostMultiplier = 2f;

            health = 45;
        }};
        ((Conduit) pipe).junctionReplacement = pipeJunction;
        ((Conduit) vacuumPipe).junctionReplacement = pipeJunction;

        pipeRouter = new LiquidRouter("4a-quartz-liquid-router"){{
            requirements(Category.liquid, with(PsammosItems.quartz, 2, PsammosItems.silver, 4));
            buildCostMultiplier = 2f;

            health = 45;
            liquidCapacity = 20;
            squareSprite = false;
            liquidPadding = 1;
            solid = false;
        }};

        heatproofLiquidContainer = new LiquidRouter("heatproof-liquid-container"){{
            requirements(Category.liquid, with(PsammosItems.quartz, 16, PsammosItems.refinedMetal, 10));

            size = 2;
            liquidCapacity = 800;
            squareSprite = false;
            liquidPadding = 2;
            solid = true;
        }};

        heatproofLiquidTank = new LiquidRouter("heatproof-liquid-tank"){{
            requirements(Category.liquid, with(PsammosItems.quartz, 50, PsammosItems.refinedMetal, 40));

            size = 3;
            liquidCapacity = 2000;
            squareSprite = false;
            liquidPadding = 4;
            solid = true;
        }};

        tunnelPipe = new LiquidBridge("5a-quartz-bridge-conduit"){{
            requirements(Category.liquid, with(PsammosItems.quartz, 6, PsammosItems.silver, 6));
            buildCostMultiplier = 3f;

            health = 40;
            range = 4;
            liquidCapacity = 20;
            arrowSpacing = 4;
            bridgeWidth = 8;
            hasPower = false;
        }};
        ((Conduit) pipe).bridgeReplacement = tunnelPipe;
        ((Conduit) vacuumPipe).bridgeReplacement = tunnelPipe;

        overflowPipe = new OverflowConduit("overflow-pipe"){{
            requirements(Category.liquid, with(PsammosItems.quartz, 2, PsammosItems.silver, 4));
            buildCostMultiplier = 2f;

            health = 45;
            liquidCapacity = 20;
            squareSprite = false;
            liquidPadding = 1;
        }};

        underflowPipe = new OverflowConduit("underflow-pipe"){{
            requirements(Category.liquid, with(PsammosItems.quartz, 2, PsammosItems.silver, 4));
            buildCostMultiplier = 2f;

            health = 45;
            liquidCapacity = 20;
            squareSprite = false;
            liquidPadding = 1;
            invert = true;
        }};
    }
}
