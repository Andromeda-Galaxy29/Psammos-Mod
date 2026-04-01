package psammos.content.blocks;

import arc.struct.*;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.units.*;
import psammos.content.*;
import psammos.type.RadiationStack;
import psammos.type.RadiationType;
import psammos.world.blocks.units.*;

import static mindustry.type.ItemStack.with;

public class PsammosPayloadBlocks {
    public static Block
            //Unit production
            specialistUnitForge, assaultUnitForge, supportUnitForge, scoutUnitForge, frontlineUnitForge,
            //Unit recombiners
            specialistUnitRecombiner, assaultUnitRecombiner, supportUnitRecombiner, scoutUnitRecombiner, frontlineUnitRecombiner,
            //Block production
            centralProcessingUnit, lithographicPrinter,
            //Unit welders
            specialistUnitWelder, assaultUnitWelder, supportUnitWelder, scoutUnitWelder, frontlineUnitWelder,
            //Unit buff towers
            overclockTower, repairDroneAssembler,
            //Distribution
            heatproofPayloadConveyor, heatproofPayloadRouter, heatproofPayloadGate;

    public static void load() {
        specialistUnitForge = new UnitFactory("1a-specialist-unit-forge"){{
            requirements(Category.units, with(PsammosItems.osmium, 20, PsammosItems.silver, 50, Items.silicon, 25));
            size = 3;
            configurable = false;
            plans.add(new UnitPlan(PsammosUnitTypes.fang, 600, with(PsammosItems.silver, 25, Items.silicon, 15)));
            researchCost = with(PsammosItems.osmium, 100, PsammosItems.silver, 120, Items.silicon, 100);
            consumePower(1.4f);
        }};

        assaultUnitForge = new UnitFactory("1b-assault-unit-forge"){{
            requirements(Category.units, with(PsammosItems.osmium, 50, PsammosItems.silver, 20, Items.silicon, 25));
            size = 3;
            configurable = false;
            plans.add(new UnitPlan(PsammosUnitTypes.glaive, 1080, with(PsammosItems.osmium, 25, Items.silicon, 15)));
            researchCost = with(PsammosItems.osmium, 120, PsammosItems.silver, 100, Items.silicon, 100);
            consumePower(1.4f);
        }};

        supportUnitForge = new UnitFactory("1c-support-unit-forge"){{
            requirements(Category.units, with(PsammosItems.osmium, 25, PsammosItems.silver, 20, Items.silicon, 50));
            size = 3;
            configurable = false;
            plans.add(new UnitPlan(PsammosUnitTypes.sine, 1200, with(PsammosItems.osmium, 15, Items.silicon, 25)));
            consumePower(1.4f);
        }};

        scoutUnitForge = new UnitFactory("1d-scout-unit-forge"){{
            requirements(Category.units, with(PsammosItems.osmium, 50, Items.sand, 60, Items.silicon, 25));
            size = 3;
            configurable = false;
            plans.add(new UnitPlan(PsammosUnitTypes.sciur, 1320, with(PsammosItems.osmium, 20, PsammosItems.quartz, 10, Items.silicon, 20)));
            consumePower(1.4f);
        }};

        frontlineUnitForge = new UnitFactory("frontline-unit-forge"){{
            requirements(Category.units, with(PsammosItems.silver, 50, Items.silicon, 20, Items.blastCompound, 25));
            size = 3;
            configurable = false;
            plans.add(new UnitPlan(PsammosUnitTypes.pawn, 1080, with(PsammosItems.silver, 20, Items.blastCompound, 10, Items.silicon, 20)));
            consumePower(1.4f);
        }};

        specialistUnitRecombiner = new Reconstructor("specialist-unit-recombiner"){{
            requirements(Category.units, with(Items.silicon, 60, PsammosItems.refinedMetal, 70, PsammosItems.silver, 40));

            size = 3;
            consumePower(3f);
            consumeLiquid(Liquids.oil, 2f / 60f);
            consumeItems(with(Items.silicon, 30, PsammosItems.refinedMetal, 20));

            constructTime = 60f * 18f;

            upgrades.addAll(
                    new UnitType[]{PsammosUnitTypes.fang, PsammosUnitTypes.jaw}
            );
        }};

        assaultUnitRecombiner = new Reconstructor("assault-unit-recombiner"){{
            requirements(Category.units, with(Items.silicon, 60, PsammosItems.refinedMetal, 70, PsammosItems.osmium, 40));

            size = 3;
            consumePower(3f);
            consumeLiquid(Liquids.oil, 2f / 60f);
            consumeItems(with(Items.silicon, 30, PsammosItems.refinedMetal, 40));

            constructTime = 60f * 28f;

            upgrades.addAll(
                    new UnitType[]{PsammosUnitTypes.glaive, PsammosUnitTypes.pike}
            );
        }};

        supportUnitRecombiner = new Reconstructor("support-unit-recombiner"){{
            requirements(Category.units, with(Items.silicon, 75, PsammosItems.refinedMetal, 60, PsammosItems.osmium, 30));

            size = 3;
            consumePower(3f);
            consumeLiquid(Liquids.oil, 2f / 60f);
            consumeItems(with(Items.silicon, 50, PsammosItems.refinedMetal, 20));

            constructTime = 60f * 30f;

            upgrades.addAll(
                    new UnitType[]{PsammosUnitTypes.sine, PsammosUnitTypes.helix}
            );
        }};

        scoutUnitRecombiner = new Reconstructor("scout-unit-recombiner"){{
            requirements(Category.units, with(Items.silicon, 60, PsammosItems.refinedMetal, 70, PsammosItems.quartz, 40));

            size = 3;
            consumePower(3f);
            consumeLiquid(Liquids.oil, 2f / 60f);
            consumeItems(with(Items.silicon, 30, PsammosItems.refinedMetal, 50));

            constructTime = 60f * 33f;

            upgrades.addAll(
                    new UnitType[]{PsammosUnitTypes.sciur, PsammosUnitTypes.glirid}
            );
        }};

        frontlineUnitRecombiner = new Reconstructor("frontline-unit-recombiner"){{
            requirements(Category.units, with(Items.silicon, 60, PsammosItems.refinedMetal, 70, Items.blastCompound, 40));

            size = 3;
            consumePower(3f);
            consumeLiquid(Liquids.oil, 2f / 60f);
            consumeItems(with(Items.silicon, 40, PsammosItems.refinedMetal, 40));

            constructTime = 60f * 30f;

            upgrades.addAll(
                    new UnitType[]{PsammosUnitTypes.pawn, PsammosUnitTypes.knight}
            );
        }};

        centralProcessingUnit = new Wall("central-processing-unit"){{
            requirements(Category.units, with(Items.silicon, 50, PsammosItems.silver, 40));
            placeablePlayer = false;
            size = 2;
        }};

        lithographicPrinter = new RadiationConstructor("lithographic-printer"){{
            requirements(Category.units, with(PsammosItems.quartz, 60, PsammosItems.refinedMetal, 70, PsammosItems.desertGlassShard, 30));

            size = 3;
            color = PsammosRadTypes.UV.color;
            radiationRequirements = Seq.with(new RadiationStack(PsammosRadTypes.UV, 10f));
            hasPower = consumesPower = false;
            filter = Seq.with(centralProcessingUnit);
        }};

        specialistUnitWelder = new UnitAssembler("specialist-unit-welder"){{
            requirements(Category.units, with(Items.silicon, 200, PsammosItems.refinedMetal, 120, PsammosItems.quartz, 80, PsammosItems.osmium, 120, PsammosItems.desertGlassShard, 60));

            droneType = PsammosUnitTypes.weldingDrone;
            size = 5;
            plans.add(
                    new AssemblerUnitPlan(PsammosUnitTypes.maw, 60f * 25f, PayloadStack.list(PsammosUnitTypes.fang, 3, centralProcessingUnit, 1))
            );
            areaSize = 11;

            consumePower(3f);
            consumeLiquid(Liquids.hydrogen, 9 / 60f);
        }};

        assaultUnitWelder = new UnitAssembler("assault-unit-welder"){{
            requirements(Category.units, with(Items.silicon, 200, PsammosItems.refinedMetal, 120, PsammosItems.quartz, 80, PsammosItems.osmium, 120, PsammosItems.desertGlassShard, 60));

            droneType = PsammosUnitTypes.weldingDrone;
            size = 5;
            plans.add(
                    new AssemblerUnitPlan(PsammosUnitTypes.spear, 60f * 35f, PayloadStack.list(PsammosUnitTypes.glaive, 4, centralProcessingUnit, 1))
            );
            areaSize = 11;

            consumePower(3f);
            consumeLiquid(Liquids.hydrogen, 9 / 60f);
        }};

        supportUnitWelder = new UnitAssembler("support-unit-welder"){{
            requirements(Category.units, with(Items.silicon, 200, PsammosItems.refinedMetal, 120, PsammosItems.quartz, 80, PsammosItems.osmium, 120, PsammosItems.desertGlassShard, 60));

            droneType = PsammosUnitTypes.weldingDrone;
            size = 5;
            plans.add(
                    new AssemblerUnitPlan(PsammosUnitTypes.trisect, 60f * 40f, PayloadStack.list(PsammosUnitTypes.sine, 3, centralProcessingUnit, 1))
            );
            areaSize = 11;

            consumePower(3f);
            consumeLiquid(Liquids.hydrogen, 9 / 60f);
        }};

        scoutUnitWelder = new UnitAssembler("scout-unit-welder"){{
            requirements(Category.units, with(Items.silicon, 200, PsammosItems.refinedMetal, 120, PsammosItems.quartz, 80, PsammosItems.osmium, 120, PsammosItems.desertGlassShard, 60));

            droneType = PsammosUnitTypes.weldingDrone;
            size = 5;
            plans.add(
                    new AssemblerUnitPlan(PsammosUnitTypes.exilis, 60f * 45f, PayloadStack.list(PsammosUnitTypes.sciur, 4, centralProcessingUnit, 1))
            );
            areaSize = 11;

            consumePower(3f);
            consumeLiquid(Liquids.hydrogen, 9 / 60f);
        }};

        frontlineUnitWelder = new UnitAssembler("frontline-unit-welder"){{
            requirements(Category.units, with(Items.silicon, 200, PsammosItems.refinedMetal, 120, PsammosItems.quartz, 80, PsammosItems.osmium, 120, PsammosItems.desertGlassShard, 60));

            droneType = PsammosUnitTypes.weldingDrone;
            size = 5;
            plans.add(
                    new AssemblerUnitPlan(PsammosUnitTypes.bishop, 60f * 40f, PayloadStack.list(PsammosUnitTypes.pawn, 3, centralProcessingUnit, 1))
            );
            areaSize = 11;

            consumePower(3f);
            consumeLiquid(Liquids.hydrogen, 9 / 60f);
        }};

        repairDroneAssembler = new RepairDroneAssembler("repair-drone-assembler"){{
            requirements(Category.units, with(PsammosItems.osmium, 20, Items.silicon, 80, PsammosItems.refinedMetal, 80));
            size = 2;
            unitBuildTime = 2f * 60f;
            range = 10 * 8f;
            statsRepairSpeed = 30;

            unitType = PsammosUnitTypes.repairDrone;

            squareSprite = false;

            consumeLiquid(Liquids.oil, 10f / 60f);
            consumePower(1f);
        }};

        overclockTower = new StatusTower("overclock-tower"){{
            requirements(Category.units, with(PsammosItems.silver, 40, Items.silicon, 60, PsammosItems.refinedMetal, 80));

            size = 2;
            range = 80f;
            status = StatusEffects.overclock;
            statusDuration = 1500;

            squareSprite = false;

            consumeLiquid(Liquids.nitrogen, 4 /60f);
            consumePower(60 /60f);
        }};

        heatproofPayloadConveyor = new PayloadConveyor("heatproof-payload-conveyor"){{
            requirements(Category.units, with(PsammosItems.refinedMetal, 15));
            canOverdrive = false;
            health = 500;
            moveTime = 40f;
        }};

        heatproofPayloadRouter = new PayloadRouter("heatproof-payload-router"){{
            requirements(Category.units, with(PsammosItems.refinedMetal, 20));
            canOverdrive = false;
            health = 500;
            moveTime = 40f;
        }};

        heatproofPayloadGate = new PayloadGate("heatproof-payload-gate"){{
            requirements(Category.units, with(PsammosItems.refinedMetal, 35, Items.silicon, 20));
            canOverdrive = false;
            health = 800;
            reload = 40f;
        }};
    }
}
