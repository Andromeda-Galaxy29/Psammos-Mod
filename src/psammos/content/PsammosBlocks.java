package psammos.content;

import arc.graphics.*;
import mindustry.content.*;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.effect.*;
import mindustry.entities.part.RegionPart;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;
import psammos.blocks.Bomb;
import psammos.blocks.ExplodableFloor;

import java.awt.geom.GeneralPath;

import static mindustry.type.ItemStack.*;

// Some of the block IDs have number prefixes before them.
// This is leftover from the json version and I can't remove them without breaking things.

public class PsammosBlocks {
    public static Block
    //Turrets
    cross, disseminate, influence, gunslinger,

    //Drills/Production
    osmiumDrill, detonationDrill, excavatorDrill, seismicBomb,

    //Distribution
    heatproofConveyor, heatproofJunction, heatproofRouter, heatproofTunnelConveyor,
    heatproofOverflowGate, heatproofUnderflowGate,

    //Liquid
    heatproofPump, pipe, pipeJunction, pipeRouter, tunnelPipe,

    //Power
    electricPole, electricDistributor, accumulator, windTurbine, liquidFuelBurner,

    //Defense
    osmiumWall, osmiumWallLarge, silverWall, silverWallLarge,
    refinedMetalWall, refinedMetalWallLarge,

    //Crafting
    sieve, siliconSynthesizer, centrifuge, thermolysisChamber,
    refinery, blastManufacturer, oilDistillationTower, heatExchanger,
    peatHeater, heatPump, heatPumpRouter, aerogelPressurizer,
    steamReformer, obliterator,

    //Units/Payload
    specialistUnitForge, assaultUnitForge, supportUnitForge, scoutUnitForge,

    //Effect/Storage
    coreDust, heatproofContainer, heatproofUnloader, healingProjector,

    //Environment
    osmiumOre, silverOre,
    quicksand,
    peatFloor, quartzFloor, smallOilDeposit, oilDeposit,
    peatWall, quartzWall,
    peatBoulder, quartzBoulder;

    public static void load(){

        // Turrets

        cross = new ItemTurret("1a-cross"){{
            requirements(Category.turret, with(PsammosItems.osmium, 15, Items.sand, 5));
            alwaysUnlocked = true;

            ammo(
                PsammosItems.osmium, new BasicBulletType(){{
                    width = 6;
                    height = 16;
                    speed = 10;
                    damage = 18;
                    lifetime = 18;
                    hitColor = backColor = frontColor = Color.valueOf("#66a4b4");
                    trailColor = Color.valueOf("#94d4db");
                    trailWidth = 1;
                    trailLength = 3;
                }}
            );
            shoot.firstShotDelay = 30;

            size = 2;
            health = 260;
            squareSprite = false;
            shootSound = Sounds.shootAlt;
            outlineColor = Color.valueOf("#534d4a");
            targetAir = true;
            targetGround = true;
            range = 160;
            reload = 50;
            recoil = -0.8f;
            velocityRnd = 0.1f;
            inaccuracy = 0;

            drawer = new DrawTurret("heatproof-"){{
                parts.addAll(
                    new RegionPart("-arm"){{
                        progress = PartProgress.charge;
                        y = 4;
                        moveRot = -15;
                        mirror = true;
                        turretShading = true;
                    }},
                    new RegionPart("-center"){{
                        mirror = false;
                    }},
                    new RegionPart("-string"){{
                        progress = PartProgress.charge;
                        moveY = -2;
                        mirror = false;
                    }}
                );
            }};

            coolant = consumeCoolant(0.1f);
        }};

        disseminate = new ItemTurret("1b-disseminate"){{
            requirements(Category.turret, with(PsammosItems.osmium, 18, PsammosItems.silver, 10));
            researchCostMultiplier = 0.2f;

            ammo(
                Items.sand, new BasicBulletType(){{
                    collidesGround = false;
                    speed = 5;
                    damage = 5;
                    splashDamage = 28;
                    splashDamageRadius = 16;
                    width = 6;
                    height = 8;
                    ammoMultiplier = 4;
                    trailWidth = 2;
                    trailLength = 4;
                    hitColor = Color.valueOf("#f7cba4");
                    backColor = Color.valueOf("#d3ae8d");
                    frontColor = Color.valueOf("#f7cba4");
                    trailColor = Color.valueOf("#d3ae8d");
                    lifetime = 25;
                    shootEffect = Fx.shootBig;
                    smokeEffect = Fx.shootSmokeSquare;
                    despawnEffect = hitEffect = Fx.hitSquaresColor;
                }},
                Items.blastCompound, new BasicBulletType(){{
                    collidesGround = false;
                    speed = 2.8f;
                    damage = 7;
                    splashDamage = 35;
                    splashDamageRadius = 21;
                    width = 6;
                    height = 8;
                    ammoMultiplier = 5;
                    trailWidth = 2;
                    trailLength = 4;
                    hitColor = Color.valueOf("#fdb380");
                    backColor = Color.valueOf("#fdb380");
                    frontColor = Color.valueOf("#fdb380");
                    trailColor = Color.valueOf("#eb8778");
                    lifetime = 25;
                    shootEffect = Fx.shootBig;
                    smokeEffect = Fx.shootSmokeSquare;
                    despawnEffect = hitEffect = Fx.hitSquaresColor;
                    status = StatusEffects.blasted;
                }}
            );
            shoot.shots = 8;

            size = 2;
            health = 260;
            squareSprite = false;
            shootSound = Sounds.cannon;
            outlineColor = Color.valueOf("#534d4a");
            targetAir = true;
            targetGround = false;
            range = 120;
            reload = 26;
            velocityRnd = 0.8f;
            inaccuracy = 45;
            shootY = 4;

            drawer = new DrawTurret("heatproof-"){{
                parts.addAll(
                    new RegionPart("-barrel"){{
                        progress = PartProgress.recoil;
                        moveY = -1.25f;
                        mirror = false;
                    }},
                    new RegionPart("-top"){{
                        mirror = false;
                    }}
                );
            }};

            coolant = consumeCoolant(0.1f);
        }};

        influence = new PowerTurret("2a-influence"){{
            requirements(Category.turret, with(PsammosItems.silver, 20, Items.silicon, 10));
            researchCostMultiplier = 0.5f;

            shootType = new BasicBulletType(){{
                collidesAir = false;
                width = 10;
                height = 10;
                speed = 5;
                damage = 16;
                lifetime = 30;
                hitColor = backColor = trailColor = lightningColor = Color.valueOf("#a9d8ff");
                frontColor = lightColor = Color.valueOf("#ffffff");
                trailWidth = 2;
                trailLength = 5;
                lightning = 4;
                lightningLength = 8;
                lightningDamage = 6;
            }};

            size = 2;
            health = 320;
            squareSprite = false;
            shootSound = Sounds.shockBlast;
            outlineColor = Color.valueOf("#534d4a");
            targetAir = false;
            targetGround = true;
            range = 140;
            reload = 60;
            inaccuracy = 0;
            shootY = -1.5f;

            drawer = new DrawTurret("heatproof-");

            consumePower(1);
            coolant = consumeCoolant(0.1f);
        }};

        gunslinger = new ItemTurret("3a-gunslinger"){{
            requirements(Category.turret, with(PsammosItems.refinedMetal, 25, PsammosItems.silver, 30, Items.blastCompound, 10));

            ammo(
                PsammosItems.refinedMetal, new BasicBulletType(){{
                    speed = 4;
                    damage = 35;
                    width = 8;
                    height = 12;
                    lifetime = 45;
                    sprite = "psammos-revolver-bullet";
                }}
            );
            shoot.shots = 6;
            shoot.shotDelay = 5;
            ammoUseEffect = Fx.casing2Double;

            size = 3;
            health = 520;
            squareSprite = false;
            shootSound = Sounds.shootSnap;
            outlineColor = Color.valueOf("#534d4a");
            targetAir = true;
            targetGround = true;
            range = 180;
            reload = 100;
            inaccuracy = 2;
            shootY = 6;

            drawer = new DrawTurret("heatproof-"){{
                parts.addAll(
                    new RegionPart("-cylinder"){{
                        mirror = false;
                        progress = PartProgress.reload;
                        moveY = 2;
                    }},
                    new RegionPart("-barrel"){{
                        mirror = false;
                        progress = PartProgress.recoil;
                        moveY = -2.3f;
                    }},
                    new RegionPart("-top"){{
                        mirror = false;
                    }}
                );
            }};

            coolant = consumeCoolant(0.1f);
        }};

        // Drills/Production

        osmiumDrill = new Drill("1a-osmium-drill"){{
            requirements(Category.production, with(PsammosItems.osmium, 15));
            alwaysUnlocked = true;

            size = 2;
            tier = 2;
            drillTime = 400;
            ambientSoundVolume = 0.05f;

            consumeLiquid(PsammosLiquids.coldWater, 0.05f).boost();
        }};

        detonationDrill = new BurstDrill("1b-detonation-drill"){{
            requirements(Category.production, with(PsammosItems.osmium, 20, PsammosItems.refinedMetal, 30, Items.blastCompound, 8));

            itemCapacity = 40;
            size = 3;
            tier = 3;
            drillTime = 210;
            squareSprite = false;
            shake = 4;
            arrows = 3;
            baseArrowColor = Color.valueOf("#a4a098");
            arrowSpacing = 3;
            drillEffect = new MultiEffect(
                    Fx.mineImpact,
                    Fx.drillSteam,
                    Fx.mineImpactWave.wrap(Items.blastCompound.color)
            );

            consumePower(1);
            consumeLiquid(PsammosLiquids.coldWater, 0.025f);
        }};

        excavatorDrill = new Drill("2a-excavator-drill"){{
            requirements(Category.production, with(PsammosItems.osmium, 25, PsammosItems.quartz, 15));

            size = 3;
            tier = 1;
            drillTime = 240;
            rotateSpeed = 5;
            drawMineItem = false;
            ambientSoundVolume = 0.05f;

            consumeLiquid(PsammosLiquids.coldWater, 0.05f).boost();
        }};
        
        seismicBomb = new Bomb("3a-seismic-bomb"){{
            requirements(Category.production, with(PsammosItems.silver, 4, Items.blastCompound, 10));

            size = 1;
            squareSprite = false;
            damage = 15;
            explodeTime = 5;
            range = 3;
            baseColor = Items.blastCompound.color;
            effect = Fx.dynamicSpikes.wrap(Items.blastCompound.color, 18);
            explosionSound = Sounds.dullExplosion;
            shake = 2f;
        }};

        // Distribution

        heatproofConveyor = new Conveyor("1a-osmium-conveyor"){{
            requirements(Category.distribution, with(PsammosItems.osmium, 1));
            alwaysUnlocked = true;

            health = 45;
            speed = 0.05f;
            displayedSpeed = 7f;
            junctionReplacement = heatproofJunction;
            bridgeReplacement = heatproofTunnelConveyor;
            buildCostMultiplier = 2f;
            itemCapacity = 3;
        }};

        heatproofJunction = new Junction("2a-osmium-junction"){{
            requirements(Category.distribution, with(PsammosItems.osmium, 2));
            alwaysUnlocked = true;

            health = 30;
            speed = 26;
            capacity = 6;
            buildCostMultiplier = 2f;
        }};
        ((Conveyor) heatproofConveyor).junctionReplacement = heatproofJunction;

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
            speed = 80;
            arrowSpacing = 4;
            bufferCapacity = 14;
            buildCostMultiplier = 3;
            bridgeWidth = 8;
        }};
        ((Conveyor) heatproofConveyor).bridgeReplacement = heatproofTunnelConveyor;

        heatproofOverflowGate = new OverflowDuct("5a-osmium-overflow-gate"){{
            requirements(Category.distribution, with(PsammosItems.osmium, 4, PsammosItems.silver, 2));

            health = 40;
            speed = 4;
            buildCostMultiplier = 3f;
        }};

        heatproofUnderflowGate = new OverflowDuct("5b-osmium-underflow-gate"){{
            requirements(Category.distribution, with(PsammosItems.osmium, 4, PsammosItems.silver, 2));

            health = 40;
            speed = 4;
            buildCostMultiplier = 3f;
            invert = true;
        }};

        // Liquid

        heatproofPump = new Pump("1a-quartz-pump"){{
            requirements(Category.liquid, with(PsammosItems.quartz, 30, PsammosItems.silver, 20, Items.silicon, 15));

            health = 60;
            size = 2;
            squareSprite = false;
            liquidCapacity = 15;
            pumpAmount = 0.1f;
            consumePower(0.5f);
        }};

        pipe = new Conduit("2a-quartz-conduit"){{
            requirements(Category.liquid, with(PsammosItems.quartz, 1));
            buildCostMultiplier = 2f;

            health = 45;
            liquidCapacity = 10;
        }};

        pipeJunction = new LiquidJunction("3a-quartz-liquid-junction"){{
            requirements(Category.liquid, with(PsammosItems.quartz, 8, PsammosItems.silver, 4));
            buildCostMultiplier = 2f;

            health = 45;
        }};
        ((Conduit) pipe).junctionReplacement = pipeJunction;

        pipeRouter = new LiquidRouter("4a-quartz-liquid-router"){{
            requirements(Category.liquid, with(PsammosItems.quartz, 2, PsammosItems.silver, 4));
            buildCostMultiplier = 2f;

            health = 45;
            liquidCapacity = 20;
            squareSprite = false;
            liquidPadding = 1;
        }};

        tunnelPipe = new LiquidBridge("5a-quartz-bridge-conduit"){{
            requirements(Category.liquid, with(PsammosItems.quartz, 6, PsammosItems.silver, 6));
            buildCostMultiplier = 3f;

            health = 40;
            range = 4;
            liquidCapacity = 20;
            arrowSpacing = 4;
            bridgeWidth = 8;
        }};
        ((Conduit) pipe).bridgeReplacement = tunnelPipe;

        // Power

        electricPole = new BeamNode("1a-electric-pole"){{
            requirements(Category.power, with(PsammosItems.silver, 4));

            size = 1;
            laserColor1 = Color.valueOf("#ffffff").a(1);
            laserColor2 = Color.valueOf("#969696").a(1);
            pulseMag = 0;
            pulseScl = 0;
            range = 10;
        }};

        electricDistributor = new BeamNode("1b-electric-distributor"){{
            requirements(Category.power, with(PsammosItems.silver, 25, Items.silicon, 10, PsammosItems.refinedMetal, 10));

            size = 3;
            laserColor1 = Color.valueOf("#ffffff").a(1);
            laserColor2 = Color.valueOf("#969696").a(1);
            pulseMag = 0;
            pulseScl = 0;
            range = 25;
        }};

        accumulator = new Battery("2a-accumulator"){{
            requirements(Category.power, with(PsammosItems.silver, 10, Items.silicon, 5));

            size = 2;
            emptyLightColor = Color.valueOf("#829cae");
            fullLightColor = Color.valueOf("#b9d1e1");

            consumePowerBuffered(4000);
        }};

        //TODO: Make a separate class for wind turbines
        windTurbine = new ConsumeGenerator("3a-wind-turbine"){{
            requirements(Category.power, with(PsammosItems.osmium, 27, PsammosItems.silver, 27));

            size = 2;
            powerProduction = 1.5f;
            hasPower = true;
            outputsPower = true;
            squareSprite = false;

            drawer = new DrawMulti(
                    new DrawDefault(),
                    new DrawRegion("-rotator", 6, true),
                    new DrawRegion("-top")
            );
        }};

        liquidFuelBurner = new ConsumeGenerator("3b-liquid-fuel-burner"){{
            requirements(Category.power, with(PsammosItems.osmium, 30, PsammosItems.silver, 20, PsammosItems.refinedMetal, 12));

            size = 2;
            powerProduction = 6;
            hasPower = true;
            outputsPower = true;
            squareSprite = false;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawDefault(),
                    new DrawCrucibleFlame()
            );

            consumeLiquid(PsammosLiquids.fuel, 1 / 60f);
        }};

        // Defense
        int wallHealthMultiplier = 4;

        osmiumWall = new Wall("1a-osmium-wall"){{
            requirements(Category.defense, with(PsammosItems.osmium, 6));
            researchCostMultiplier = 0.01f;
            health = 90 * wallHealthMultiplier;
        }};

        osmiumWallLarge = new Wall("1b-osmium-wall-large"){{
            requirements(Category.defense, mult(osmiumWall.requirements, 4));
            health = 90 * wallHealthMultiplier * 4;
            size = 2;
        }};

        silverWall = new Wall("2a-silver-wall"){{
            requirements(Category.defense, with(PsammosItems.silver, 6));
            health = 100 * wallHealthMultiplier;
        }};

        silverWallLarge = new Wall("2b-silver-wall-large"){{
            requirements(Category.defense, mult(silverWall.requirements, 4));
            health = 100 * wallHealthMultiplier * 4;
            size = 2;
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

        // Crafting

        sieve = new GenericCrafter("1a-sieve"){{
            requirements(Category.crafting, with(PsammosItems.osmium, 40, PsammosItems.silver, 15));

            size = 2;
            squareSprite = false;
            craftEffect = Fx.pulverizeSmall;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-rotator", 3, true),
                    new DrawDefault()
            );

            outputItem = new ItemStack(PsammosItems.quartz, 1);
            craftTime = 40;

            consumeItem(Items.sand, 3);
            consumePower(0.25f);
        }};

        siliconSynthesizer = new GenericCrafter("2a-silicon-synthesizer"){{
            requirements(Category.crafting, with(PsammosItems.osmium, 25, PsammosItems.silver, 35));

            size = 3;
            squareSprite = false;

            drawer = new DrawMulti(
                    new DrawDefault(),
                    new DrawFlame(Color.valueOf("#ffef99"))
            );

            outputItem = new ItemStack(Items.silicon, 1);
            craftTime = 40;

            consumeItem(PsammosItems.quartz, 2);
            consumePower(0.5f);
        }};

        centrifuge = new GenericCrafter("3a-centrifuge"){{
            requirements(Category.crafting, with(PsammosItems.osmium, 20, PsammosItems.quartz, 40, Items.silicon, 10));

            size = 2;
            squareSprite = false;
            hasLiquids = true;
            liquidCapacity = 15;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(PsammosLiquids.quicksand, 1),
                    new DrawDefault(),
                    new DrawRegion("-rotator", 8, true),
                    new DrawRegion("-top")
            );

            outputItem = new ItemStack(Items.sand, 2);
            outputLiquid = new LiquidStack(Liquids.water, 6 / 60f);
            craftTime = 30;

            consumeLiquid(PsammosLiquids.quicksand, 8 / 60f);
            consumePower(2f);
        }};

        thermolysisChamber = new AttributeCrafter("4a-thermolysis-chamber"){{
            requirements(Category.crafting, with(PsammosItems.osmium, 20, PsammosItems.silver, 40, PsammosItems.quartz, 60, Items.silicon, 10));

            size = 3;
            squareSprite = false;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawCrucibleFlame(),
                    new DrawDefault(),
                    new DrawGlowRegion(){{color = Color.valueOf("#ff9633");}},
                    new DrawLiquidOutputs()
            );
            rotate = true;
            rotateDraw = false;

            baseEfficiency = 0;
            boostScale = 1/9f;
            minEfficiency = 1f;
            attribute = Attribute.get("ferric-stone");
            outputLiquids = LiquidStack.with(Liquids.slag, 4/60f, Liquids.ozone, 2/60f);
            liquidOutputDirections = new int[]{1, 3};
            craftTime = 30;

            consumePower(2f);
        }};

        refinery = new GenericCrafter("5a-refinery"){{
            requirements(Category.crafting, with(PsammosItems.silver, 35, PsammosItems.quartz, 25, Items.silicon, 15));

            size = 2;
            squareSprite = false;
            craftEffect = Fx.ventSteam;
            hasLiquids = true;
            liquidCapacity = 15;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(Liquids.slag, 5.6f),
                    new DrawLiquidRegion(Liquids.ozone){{suffix = "-ozone";}},
                    new DrawDefault(),
                    new DrawGlowRegion(){{alpha = 0.5f;}}
            );

            outputItem = new ItemStack(PsammosItems.refinedMetal, 2);
            craftTime = 120;

            consumeLiquid(Liquids.slag, 8 / 60f);
            consumeLiquid(Liquids.ozone, 4 / 60f);
            consumeLiquid(Liquids.water, 4 / 60f);
            consumePower(1f);
        }};

        blastManufacturer = new GenericCrafter("6a-blast-manufacturer"){{
            requirements(Category.crafting, with(PsammosItems.osmium, 35, PsammosItems.silver, 20, Items.silicon, 20));

            size = 2;
            squareSprite = false;
            craftEffect = Fx.pulverizeRed;
            hasLiquids = true;
            liquidCapacity = 15;

            outputItem = new ItemStack(Items.blastCompound, 1);
            craftTime = 60;

            consumeLiquid(Liquids.ozone, 1 / 60f);
            consumeItem(PsammosItems.peat, 3);
            consumePower(1f);
        }};

        oilDistillationTower = new HeatCrafter("7a-oil-distillation-tower"){{
            requirements(Category.crafting, with(PsammosItems.osmium, 20, PsammosItems.refinedMetal, 18, PsammosItems.quartz, 60, Items.silicon, 10));

            size = 3;
            squareSprite = false;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawCultivator(){{
                        plantColorLight = Color.valueOf("#61615b");
                        plantColor = Color.valueOf("#313131");
                        bottomColor = Color.valueOf("#1d1d23");
                        strokeMin = 0.3f;
                    }},
                    new DrawDefault(),
                    new DrawLiquidOutputs(),
                    new DrawHeatInput()
            );
            rotate = true;
            rotateDraw = false;

            heatRequirement = 6;
            outputLiquids = LiquidStack.with(PsammosLiquids.fuel, 4/60f, PsammosLiquids.methane, 4/60f);
            liquidOutputDirections = new int[]{1, 3};
            craftTime = 60;

            consumePower(2f);
            consumeLiquid(Liquids.oil, 8 / 60f);
        }};

        heatExchanger = new HeatProducer("8a-heat-exchanger"){{
            requirements(Category.crafting, with(PsammosItems.osmium, 15, PsammosItems.silver, 20, PsammosItems.quartz, 40, PsammosItems.refinedMetal, 20));

            size = 2;
            squareSprite = true;
            hasLiquids = true;
            liquidCapacity = 15;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(PsammosLiquids.coldWater),
                    new DrawDefault(),
                    new DrawHeatOutput()
            );
            rotate = true;
            rotateDraw = false;

            outputLiquid = new LiquidStack(PsammosLiquids.coldWater, 6 / 60f);
            heatOutput = 2;
            craftTime = 90;

            consumePower(3f);
            consumeLiquid(Liquids.water, 6 / 60f);
        }};

        obliterator = new Incinerator("Zz-obliterator"){{
            requirements(Category.crafting, with(PsammosItems.refinedMetal, 16, Items.blastCompound, 10));

            size = 1;
            hasPower = false;
        }};

        // Units/Payload

        specialistUnitForge = new UnitFactory("1a-specialist-unit-forge"){{
            requirements(Category.units, with(PsammosItems.osmium, 20, PsammosItems.silver, 50, Items.silicon, 25));
            size = 3;
            configurable = false;
            plans.add(new UnitPlan(PsammosUnitTypes.fang, 960, with(PsammosItems.silver, 25, Items.silicon, 15)));
            researchCost = with(PsammosItems.osmium, 100, PsammosItems.silver, 120, Items.silicon, 100);
            consumePower(1.4f);
        }};

        assaultUnitForge = new UnitFactory("1b-assault-unit-forge"){{
            requirements(Category.units, with(PsammosItems.osmium, 50, PsammosItems.silver, 20, Items.silicon, 25));
            size = 3;
            configurable = false;
            plans.add(new UnitPlan(PsammosUnitTypes.tip, 1080, with(PsammosItems.osmium, 25, Items.silicon, 15)));
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
            requirements(Category.units, with(PsammosItems.osmium, 50, PsammosItems.silver, 30, Items.silicon, 25));
            size = 3;
            configurable = false;
            plans.add(new UnitPlan(PsammosUnitTypes.sciur, 1320, with(PsammosItems.osmium, 20, PsammosItems.quartz, 10, Items.silicon, 20)));
            consumePower(1.4f);
        }};

        // Effect/Storage

        coreDust = new CoreBlock("1a-core-dust"){{
            requirements(Category.effect, with(PsammosItems.osmium, 1000, PsammosItems.silver, 800));
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

        heatproofContainer = new StorageBlock("2a-heatproof-container"){{
            requirements(Category.effect, with(PsammosItems.refinedMetal, 80));
            researchCostMultiplier = 0.25f;

            health = 250;
            size = 2;
            itemCapacity = 400;
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
                    new DrawLiquidTile(PsammosLiquids.fuel, 2),
                    new DrawDefault(),
                    new DrawPulseShape(false){{
                        layer = Layer.effect;
                        color = Color.valueOf("#84f491");
                    }}
            );

            consumeLiquid(PsammosLiquids.fuel, 1 / 60f);
        }};

        //Environment

        osmiumOre = new OreBlock("1a-osmium-ore", PsammosItems.osmium){{
            variants = 1;
        }};

        silverOre = new OreBlock("1b-silver-ore", PsammosItems.silver){{
            variants = 1;
        }};

        quicksand = new Floor("3a-quicksand"){{
            variants = 3;
            cacheLayer = CacheLayer.mud;
            speedMultiplier = 0.1f;
            liquidDrop = PsammosLiquids.quicksand;
            drownTime = 200;
            isLiquid = true;
        }};

        peatFloor = new Floor("3b-peat-floor"){{
            variants = 3;
            itemDrop = PsammosItems.peat;
            playerUnmineable = true;
        }};

        quartzFloor = new Floor("3c-quartz-floor"){{
            variants = 3;
        }};

        smallOilDeposit = new ExplodableFloor("3e-small-oil-deposit"){{
            variants = 3;
            itemDrop = Items.sand;
            playerUnmineable = true;
            replacement = Blocks.tar;
        }};

        oilDeposit = new ExplodableFloor("3d-oil-deposit"){{
            variants = 3;
            itemDrop = Items.sand;
            playerUnmineable = true;
            replacement = Blocks.tar;
        }};

        peatWall = new StaticWall("4a-peat-wall"){{
            variants = 2;
            itemDrop = PsammosItems.peat;
            playerUnmineable = true;
        }};

        quartzWall = new StaticWall("4b-quartz-wall"){{
            variants = 2;
            itemDrop = PsammosItems.quartz;
            playerUnmineable = true;
        }};

        quartzBoulder = new Prop("5b-quartz-boulder"){{
            variants = 1;
        }};
    }
}
