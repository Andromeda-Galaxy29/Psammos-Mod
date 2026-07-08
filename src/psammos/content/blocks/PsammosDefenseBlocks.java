package psammos.content.blocks;

import arc.graphics.*;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import psammos.content.*;
import psammos.type.RadiationStack;
import psammos.world.blocks.defense.*;
import psammos.world.blocks.defense.barrier.BarrierNode;
import psammos.world.blocks.defense.barrier.RadiationBarrierNode;

import static mindustry.type.ItemStack.mult;
import static mindustry.type.ItemStack.with;

public class PsammosDefenseBlocks {
    static final int wallHealthMultiplier = 4;

    public static Block
            osmiumWall, osmiumWallLarge, silverWall, silverWallLarge,
            refinedMetalWall, refinedMetalWallLarge, gate, gateLarge,
            floatingWall, floatingWallLarge, memoryWall, memoryWallLarge,
            barrierProjectorNode;

    public static void load() {
        osmiumWall = new Wall("1a-osmium-wall"){{
            requirements(Category.defense, with(PsammosItems.osmium, 6));
            researchCostMultiplier = 0.01f;
            health = 60 * wallHealthMultiplier;
            armor = 6;
        }};

        osmiumWallLarge = new Wall("1b-osmium-wall-large"){{
            requirements(Category.defense, mult(osmiumWall.requirements, 4));
            health = 60 * wallHealthMultiplier * 4;
            size = 2;
            armor = 6;
        }};

        silverWall = new Wall("2a-silver-wall"){{
            requirements(Category.defense, with(PsammosItems.silver, 6));
            health = 100 * wallHealthMultiplier;
            conductivePower = true;
        }};

        silverWallLarge = new Wall("2b-silver-wall-large"){{
            requirements(Category.defense, mult(silverWall.requirements, 4));
            health = 100 * wallHealthMultiplier * 4;
            size = 2;
            conductivePower = true;
        }};

        refinedMetalWall = new Wall("3a-refined-metal-wall"){{
            requirements(Category.defense, with(PsammosItems.refinedMetal, 6));
            health = 120 * wallHealthMultiplier;
        }};

        refinedMetalWallLarge = new Wall("3b-refined-metal-wall-large"){{
            requirements(Category.defense, mult(refinedMetalWall.requirements, 4));
            health = 120 * wallHealthMultiplier * 4;
            size = 2;
        }};

        gate = new AutoDoor("gate"){{
            requirements(Category.defense, with(PsammosItems.quartz, 6, Items.silicon, 4));
            health = 100 * wallHealthMultiplier;
        }};

        gateLarge = new AutoDoor("gate-large"){{
            requirements(Category.defense, mult(gate.requirements, 4));
            health = 100 * wallHealthMultiplier * 4;
            size = 2;
        }};

        floatingWall = new Wall("floating-wall"){{
            requirements(Category.defense, with(PsammosItems.refinedMetal, 2, PsammosItems.aerogel, 5));
            health = 80 * wallHealthMultiplier;
            placeableLiquid = true;
        }};

        floatingWallLarge = new Wall("floating-wall-large"){{
            requirements(Category.defense, mult(floatingWall.requirements, 4));
            health = 80 * wallHealthMultiplier * 4;
            size = 2;
            placeableLiquid = true;
        }};

        memoryWall = new RepairingWall("memory-wall"){{
            requirements(Category.defense, with(PsammosItems.memoryAlloy, 6));
            health = 120 * wallHealthMultiplier;
            healPercent = 8;
            baseColor = Color.valueOf("#b15dc3");
        }};

        memoryWallLarge = new RepairingWall("memory-wall-large"){{
            requirements(Category.defense, mult(memoryWall.requirements, 4));
            health = 120 * wallHealthMultiplier * 4;
            size = 2;
            healPercent = 8;
            baseColor = Color.valueOf("#b15dc3");
        }};

        barrierProjectorNode = new RadiationBarrierNode("barrier-projector-node"){{
            requirements(Category.defense, with(PsammosItems.refinedMetal, 80, PsammosItems.desertGlassShard, 50, Items.silicon, 60));
            size = 3;
            shieldHealth = 200;
            baseCollisionDamage = 0.1f;
            maxNodes = 4;
            range = 20 * Vars.tilesize;
            nodeShieldRadius = 3f * Vars.tilesize;
            linkShieldThickness = 2.5f * Vars.tilesize;
            radiationRequirements = Seq.with(new RadiationStack(PsammosRadTypes.light, 4.5f));
        }};
    }
}
