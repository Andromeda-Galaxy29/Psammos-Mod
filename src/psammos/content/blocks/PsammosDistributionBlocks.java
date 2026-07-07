package psammos.content.blocks;

import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.distribution.*;
import psammos.content.*;
import psammos.world.blocks.distribution.*;

import static mindustry.type.ItemStack.*;

public class PsammosDistributionBlocks {
    public static Block
            heatproofConveyor, platedConveyor, heatproofJunction, heatproofRouter,
            heatproofTunnelConveyor, heatproofOverflowGate, heatproofUnderflowGate;

    public static void load() {
        heatproofConveyor = new ShadedConveyor("1a-osmium-conveyor"){{
            requirements(Category.distribution, with(PsammosItems.osmium, 1));
            alwaysUnlocked = true;

            health = 45;
            speed = 0.09f;
            displayedSpeed = 12.75f;
            buildCostMultiplier = 2f;
            itemCapacity = 3;
        }};

        platedConveyor = new ArmoredShadedConveyor("plated-conveyor"){{
            requirements(Category.distribution, with(PsammosItems.memoryAlloy, 1, PsammosItems.refinedMetal, 1));

            health = 180;
            speed = 0.12f;
            displayedSpeed = 16f;
            buildCostMultiplier = 2f;
            itemCapacity = 3;
        }};

        heatproofJunction = new BetterJunction("2a-osmium-junction"){{
            requirements(Category.distribution, with(PsammosItems.osmium, 2));
            alwaysUnlocked = true;

            health = 30;
            speed = 22.5f;
            displayedSpeed = 16;
            capacity = 6;
            buildCostMultiplier = 2f;
        }};
        ((Conveyor) heatproofConveyor).junctionReplacement = heatproofJunction;
        ((Conveyor) platedConveyor).junctionReplacement = heatproofJunction;

        heatproofRouter = new DuctRouter("3a-osmium-router"){{
            requirements(Category.distribution, with(PsammosItems.osmium, 4));

            health = 40;
            speed = 4;
            buildCostMultiplier = 3f;
        }};

        heatproofTunnelConveyor = new BufferedItemBridge("4a-osmium-bridge-conveyor"){{
            requirements(Category.distribution, with(PsammosItems.osmium, 6, PsammosItems.silver, 6));

            health = 40;
            range = 4;
            speed = 55f;
            displayedSpeed = 14f;
            arrowSpacing = 4;
            bufferCapacity = 14;
            buildCostMultiplier = 3;
            bridgeWidth = 8;
        }};
        ((Conveyor) heatproofConveyor).bridgeReplacement = heatproofTunnelConveyor;
        ((Conveyor) platedConveyor).bridgeReplacement = heatproofTunnelConveyor;

        heatproofOverflowGate = new OverflowDuct("5a-osmium-overflow-gate"){{
            requirements(Category.distribution, with(PsammosItems.osmium, 4, PsammosItems.silver, 2));

            health = 40;
            speed = 4;
            buildCostMultiplier = 3f;
            squareSprite = false;
        }};

        heatproofUnderflowGate = new OverflowDuct("5b-osmium-underflow-gate"){{
            requirements(Category.distribution, with(PsammosItems.osmium, 4, PsammosItems.silver, 2));

            health = 40;
            speed = 4;
            buildCostMultiplier = 3f;
            squareSprite = false;
            invert = true;
        }};
    }
}
