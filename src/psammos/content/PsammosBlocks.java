package psammos.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.unit.MissileUnitType;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import psammos.*;
import psammos.ai.AntiAirMissileAI;
import psammos.blocks.defense.*;
import psammos.blocks.environment.*;
import psammos.blocks.liquid.*;
import psammos.blocks.power.*;
import psammos.blocks.production.*;
import psammos.blocks.units.*;
import psammos.draw.DrawProgressGlowRegion;
import psammos.entities.bullet.*;

import static mindustry.type.ItemStack.*;

// Some of the block IDs have number prefixes before them.
// This is leftover from the json version and I can't remove them without breaking things.

public class PsammosBlocks {
    public static Block
    //Turrets
    cross, disseminate, hurl, influence, gunslinger, spray, seize, burst, dawn, burden,

    //Drills/Production
    osmiumDrill, detonationDrill, excavatorDrill, seismicBomb, ammoniaBomb,

    //Distribution
    heatproofConveyor, platedConveyor, heatproofJunction, heatproofRouter,
    heatproofTunnelConveyor, heatproofOverflowGate, heatproofUnderflowGate,

    //Liquid
    heatproofPump, pipe, vacuumPipe, pipeJunction, pipeRouter, heatproofLiquidContainer, tunnelPipe, overflowPipe, underflowPipe,

    //Power
    electricPole, electricDistributor, led, accumulator,
    windTurbine, piezoelectricGenerator, impulseGenerator, liquidFuelBurner,

    //Defense
    osmiumWall, osmiumWallLarge, silverWall, silverWallLarge,
    gate, gateLarge, refinedMetalWall, refinedMetalWallLarge,
    memoryWall, memoryWallLarge,

    //Crafting
    sieve, filter, siliconSynthesizer, centrifuge, thermolysisChamber,
    refinery, blastManufacturer, oilDistillationTower, atmosphericSeparator,
    heatExchanger, ozoneHeater, peatHeater, ammoniaHeater,
    heatPump, heatPumpRouter, aerogelPressurizer, steamReformer,
    ammoniaCompressor, memoryAlloyCrucible, obliterator,

    //Units/Payload
    specialistUnitForge, assaultUnitForge, supportUnitForge, scoutUnitForge, frontlineUnitForge,
    specialistUnitRecombiner, assaultUnitRecombiner, supportUnitRecombiner, scoutUnitRecombiner, frontlineUnitRecombiner,
    overclockTower,
    heatproofPayloadConveyor, heatproofPayloadRouter, heatproofPayloadGate,

    //Effect/Storage
    coreDust, coreDune, heatproofContainer, heatproofUnloader, healingProjector,

    //Logic
    heatproofMessage,

    //Environment
    osmiumOre, silverOre,
    quicksand, darkQuicksand,
    peatFloor, quartzFloor, smallOilDeposit, oilDeposit,
    peatWall, quartzWall,
    peatBoulder, quartzBoulder,
    crystalQuartz,

    //Internal/Compatibility
    influenceOld;

    public static void load(){

        // Turrets

        cross = new ItemTurret("1a-cross"){{
            requirements(Category.turret, with(PsammosItems.osmium, 15, Items.sand, 5));
            alwaysUnlocked = true;

            ammo(
                PsammosItems.osmium, new BasicBulletType(){{
                    width = 4;
                    height = 20;
                    speed = 10;
                    damage = 18;
                    lifetime = 18;
                    hitColor = backColor = Color.valueOf("#66a4b4");
                    trailColor = Color.valueOf("#66a4b4");
                    trailWidth = 1;
                    trailLength = 3;
                }},
                Items.silicon, new BasicBulletType(){{
                    width = 4;
                    height = 20;
                    speed = 10;
                    damage = 26;
                    lifetime = 18;
                    trailWidth = 1;
                    trailLength = 3;
                    homingRange = 45;
                    homingPower = 0.1f;
                }},
                PsammosItems.memoryAlloy, new BasicBulletType(){{
                    width = 4;
                    height = 20;
                    speed = 10;
                    damage = 32;
                    lifetime = 18;
                    hitColor = backColor = PPal.memoryAlloy;
                    trailColor = PPal.memoryAlloy;
                    trailWidth = 1;
                    trailLength = 3;

                    rangeChange = 50f;

                    fragBullets = 3;
                    fragSpread = 30;
                    fragRandomSpread = 0;
                    fragVelocityMin = 1;
                    fragVelocityMax = 1;
                    fragBullet = new LaserBulletType(){{
                        damage = 12;
                        colors = new Color[]{
                                PPal.memoryAlloy,
                                Color.valueOf("#ffffff")
                        };
                        hitEffect = Fx.hitLancer;
                        laserEffect = Fx.none;
                        hitSize = 3;
                        lifetime = 12;
                        drawSize = 300;
                        collidesAir = true;
                        length = 40;
                        width = 5;
                        pierceCap = 6;
                        sideWidth = 0f;
                        sideLength = 0;
                    }};
                }}
            );
            shoot.firstShotDelay = 30;

            size = 2;
            health = 350;
            squareSprite = false;
            shootSound = Sounds.shootAlt;
            outlineColor = PPal.turretOutline;
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
                    lifetime = 25;
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
                    shootEffect = Fx.shootBig;
                    smokeEffect = Fx.shootSmokeSquare;

                    despawnEffect = hitEffect = Fx.hitSquaresColor;
                }},
                PsammosItems.peat, new BasicBulletType(){{
                    collidesGround = false;
                    speed = 4;
                    lifetime = 25;
                    damage = 5;
                    splashDamage = 30;
                    splashDamageRadius = 18;
                    width = 6;
                    height = 8;
                    ammoMultiplier = 4;
                    rangeChange = -24;

                    trailWidth = 2;
                    trailLength = 4;
                    hitColor = Color.valueOf("#cfa681");
                    backColor = Color.valueOf("#a17f61");
                    frontColor = Color.valueOf("#cfa681");
                    trailColor = Color.valueOf("#a17f61");
                    shootEffect = Fx.shootBig;
                    smokeEffect = Fx.shootSmokeSquare;
                    despawnEffect = hitEffect = Fx.hitSquaresColor;

                    status = StatusEffects.burning;
                }},
                Items.blastCompound, new BasicBulletType(){{
                    collidesGround = false;
                    speed = 2.8f;
                    lifetime = 25;
                    damage = 7;
                    splashDamage = 35;
                    splashDamageRadius = 21;
                    width = 6;
                    height = 8;
                    ammoMultiplier = 5;
                    rangeChange = -60;

                    trailWidth = 2;
                    trailLength = 4;
                    hitColor = Color.valueOf("#fdb380");
                    backColor = Color.valueOf("#eb8778");
                    frontColor = Color.valueOf("#fdb380");
                    trailColor = Color.valueOf("#eb8778");
                    shootEffect = Fx.shootBig;
                    smokeEffect = Fx.shootSmokeSquare;
                    despawnEffect = hitEffect = Fx.hitSquaresColor;

                    status = StatusEffects.blasted;
                }}
            );
            shoot.shots = 8;

            size = 2;
            health = 350;
            squareSprite = false;
            shootSound = Sounds.cannon;
            outlineColor = PPal.turretOutline;
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

        hurl = new ItemTurret("hurl"){{
            requirements(Category.turret, with(PsammosItems.quartz, 30, Items.silicon, 10));
            researchCostMultiplier = 0.5f;

            ammo(
                    PsammosItems.quartz, new ArtilleryBulletType(){{
                        collidesAir = true;
                        width = 11;
                        height = 11;
                        speed = 3;
                        lifetime = 80;
                        collidesTiles = false;
                        splashDamageRadius = 25f;
                        splashDamage = 18f;
                        buildingDamageMultiplier = 0.01f;
                    }},
                    Items.silicon, new ArtilleryBulletType(){{
                        collidesAir = true;
                        width = 11;
                        height = 11;
                        speed = 3;
                        lifetime = 80;
                        collidesTiles = false;
                        splashDamageRadius = 25f;
                        splashDamage = 18f;
                        reloadMultiplier = 1.2f;
                        ammoMultiplier = 3f;
                        homingPower = 0.1f;
                        homingRange = 50f;
                        buildingDamageMultiplier = 0.01f;
                    }}
            );

            size = 2;
            health = 450;
            squareSprite = false;
            outlineColor = PPal.turretOutline;
            shootSound = Sounds.bang;
            targetAir = true;
            targetGround = true;
            range = 200;
            inaccuracy = 2.5f;
            velocityRnd = 0.2f;
            shootCone = 10f;
            shoot.shots = 3;
            ammoPerShot = 3;
            reload = 60;

            drawer = new DrawTurret("heatproof-"){{
                parts.addAll(
                        new RegionPart("-barrel"){{
                            mirror = false;
                            progress = PartProgress.recoil;
                            moveY = -3f;
                        }},
                        new RegionPart("-middle"){{
                            mirror = false;
                            progress = PartProgress.recoil;
                            moveY = -2f;
                        }},
                        new RegionPart("-side"){{
                            mirror = true;
                            turretShading = true;
                            progress = PartProgress.warmup;
                            moveRot = -20f;
                            moveX = 1f;
                        }}
                );
            }};

            coolant = consumeCoolant(0.1f);
        }};

        //Old version of Influence. Left here to prevent crashes from loading a map created in an older version
        influenceOld = new PowerTurret("2a-influence"){{
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
            health = 450;
            squareSprite = false;
            shootSound = Sounds.shockBlast;
            outlineColor = PPal.turretOutline;
            targetAir = false;
            targetGround = true;
            range = 140;
            reload = 60;
            inaccuracy = 0;
            shootY = -1.5f;

            drawer = new DrawTurret("ohno");

            consumePower(1);
            coolant = consumeCoolant(0.1f);
        }};

        influence = new ItemTurret("influence"){{
            requirements(Category.turret, with(PsammosItems.silver, 20, Items.silicon, 10));
            researchCostMultiplier = 0.5f;

            ammo(
                    PsammosItems.silver, new BasicBulletType(){{
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
                    }}
            );

            size = 2;
            health = 450;
            squareSprite = false;
            shootSound = Sounds.shockBlast;
            outlineColor = PPal.turretOutline;
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
                    speed = 5;
                    damage = 35;
                    width = 12;
                    height = 16;
                    lifetime = 45;
                    trailWidth = 2.2f;
                    trailLength = 5;
                    trailEffect = Fx.colorTrail;
                    trailInterval = 10f;
                    trailColor = hitColor = Pal.bulletYellowBack;
                    sprite = "psammos-revolver-bullet";
                    smokeEffect = Fx.shootSmokeSquareSparse;
                    hitEffect = despawnEffect = Fx.hitBulletBig;
                }}
            );
            shoot.shots = 6;
            shoot.shotDelay = 7;
            ammoUseEffect = Fx.casing2Double;

            size = 3;
            health = 600;
            squareSprite = false;
            shootSound = Sounds.shootSnap;
            outlineColor = PPal.turretOutline;
            targetAir = true;
            targetGround = true;
            range = 180;
            reload = 100;
            inaccuracy = 2;
            shootY = 6;

            drawer = new DrawTurret("heatproof-"){{
                parts.addAll(
                    new RegionPart("-barrel"){{
                        mirror = false;
                        progress = PartProgress.recoil;
                        moveY = -1.5f;
                    }},
                    new RegionPart("-side"){{
                        progress = PartProgress.warmup;
                        moveY = -2f;
                        moveRot = 15f;
                        mirror = true;
                        turretShading = true;
                        under = true;
                        moves.add(new PartMove(PartProgress.recoil, 0f, 0f, -15f));
                    }}
                );
            }};

            coolant = consumeCoolant(0.1f);
        }};

        spray = new LiquidTurret("spray"){{
            requirements(Category.turret, with(PsammosItems.refinedMetal, 25, PsammosItems.quartz, 40, PsammosItems.osmium, 35));

            ammo(
                    Liquids.ozone, new GasBulletType(Liquids.ozone){{
                        damage = 3f;
                        knockback = 2.8f;
                        layer = Layer.bullet - 2f;
                        status = PsammosStatusEffects.combustible;
                    }},
                    Liquids.nitrogen, new GasBulletType(Liquids.nitrogen){{
                        damage = 7f;
                        knockback = 2.2f;
                        layer = Layer.bullet - 2f;
                    }},
                    PsammosLiquids.methane, new GasBulletType(PsammosLiquids.methane){{
                        damage = 0.5f;
                        knockback = 7.2f;
                        layer = Layer.bullet - 2f;
                    }},
                    Liquids.hydrogen, new GasBulletType(Liquids.hydrogen){{
                        damage = 9f;
                        knockback = 1.4f;
                        status = StatusEffects.blasted;
                        layer = Layer.bullet - 2f;
                    }},
                    PsammosLiquids.ammonia, new GasBulletType(PsammosLiquids.ammonia){{
                        damage = 14f;
                        knockback = 1.8f;
                        status = StatusEffects.burning;
                        layer = Layer.bullet - 2f;
                    }}
            );
            squareSprite = false;

            shoot = new ShootSpread(15, 4f);

            size = 3;
            health = 600;
            outlineColor = PPal.turretOutline;
            recoil = 0f;
            reload = 40f;
            inaccuracy = 6f;
            liquidCapacity = 10f;
            ammoPerShot = 2;
            shootEffect = Fx.shootLiquid;
            shootSound = Sounds.cannon;
            range = 90f;
            shootY = 8;
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);

            drawer = new DrawTurret("heatproof-"){{
                parts.addAll(
                        new RegionPart("-side"){{
                            progress = PartProgress.warmup;
                            moveRot = 20;
                            mirror = true;
                            turretShading = true;
                        }},
                        new RegionPart("-nozzle"){{
                            progress = PartProgress.recoil;
                            moveX = 2;
                            mirror = true;
                            turretShading = true;
                        }}
                );
            }};
        }};

        seize = new TractorBeamTurret("seize"){{
            requirements(Category.turret, with(PsammosItems.refinedMetal, 80, Items.silicon, 60, PsammosItems.quartz, 40));

            hasPower = true;
            size = 2;
            health = 600;
            force = 30f;
            scaledForce = 12f;
            range = 220f;
            damage = 0.4f;
            scaledHealth = 160;
            rotateSpeed = 10;
            targetGround = true;
            targetAir = false;
            laserColor = Pal.berylShot;
            outlineColor = PPal.turretOutline;
            status = StatusEffects.electrified;

            consumePower(3.5f);
        }};

        burst = new LiquidTurret("burst"){{
            requirements(Category.turret, with(PsammosItems.refinedMetal, 30, PsammosItems.quartz, 30, PsammosItems.aerogel, 20, Items.blastCompound, 15));

            ammo(
                    Liquids.slag, new LiquidBulletType(Liquids.slag){{
                        lifetime = 40f;
                        speed = 4f;
                        knockback = 0.5f;
                        puddleSize = 20f;
                        orbSize = 4f;
                        damage = 10f;
                        drag = 0.001f;
                        ammoMultiplier = 0.4f;
                        statusDuration = 60f * 4f;
                    }}
            );

            shoot = new ShootSpread(6, 4f);

            size = 3;
            reload = 50f;
            outlineColor = PPal.turretOutline;
            squareSprite = false;
            recoil = 2f;
            range = 140;
            shootY = 5f;
            inaccuracy = 0.2f;
            velocityRnd = 0.2f;
            shake = 1f;
            shootSound = Sounds.cannon;
            shootEffect = Fx.shootPyraFlame;

            drawer = new DrawTurret("heatproof-"){{
                parts.add(new RegionPart("-front"){{
                    progress = PartProgress.warmup;
                    moveRot = -10f;
                    mirror = true;
                    moves.add(new PartMove(PartProgress.recoil, 0f, -2f, -5f));
                }});
            }};
        }};

        dawn = new LiquidTurret("dawn"){{
            requirements(Category.turret, with(PsammosItems.refinedMetal, 25, PsammosItems.quartz, 30, PsammosItems.aerogel, 30));

            ammo(
                    Liquids.hydrogen, new BulletType(0f, 1){{
                        ammoMultiplier = 1f;

                        spawnUnit = new MissileUnitType("dawn-missile"){{
                            trailColor = engineColor = Liquids.hydrogen.color;
                            engineSize = 1.75f;
                            engineLayer = Layer.effect;
                            speed = 3.7f;
                            maxRange = 6f;
                            lifetime = 60f * 1.5f;
                            outlineColor = PPal.unitOutline;
                            health = 50;
                            controller = u -> new AntiAirMissileAI();

                            weapons.add(new Weapon(){{
                                shootCone = 360f;
                                mirror = false;
                                reload = 1f;
                                shootOnDeath = true;
                                bullet = new ExplosionBulletType(80f, 40f){{
                                    shootEffect = new MultiEffect(Fx.massiveExplosion, new WrapEffect(Fx.dynamicSpikes, Liquids.hydrogen.color, 24f), new WaveEffect(){{
                                        colorFrom = colorTo = Liquids.hydrogen.color;
                                        sizeTo = 40f;
                                        lifetime = 12f;
                                        strokeFrom = 4f;
                                    }});

                                    collidesAir = true;
                                    collidesGround = false;
                                    collidesTiles = false;
                                }};
                            }});
                        }};
                    }}
            );
            squareSprite = false;

            shoot.shots = 3;
            shoot.shotDelay = 6;
            recoil = 0.5f;

            shootSound = Sounds.largeCannon;

            minWarmup = 0.94f;
            shootWarmupSpeed = 0.03f;
            targetAir = true;
            targetGround = false;
            targetUnderBlocks = false;

            shake = 3f;
            shootY = -4f;
            outlineColor = PPal.turretOutline;
            size = 3;
            health = 650;
            reload = 160f;
            range = 400;
            shootCone = 1f;
            rotateSpeed = 3f;

            drawer = new DrawTurret("heatproof-"){{
                parts.addAll(
                        new RegionPart("-side"){{
                            progress = PartProgress.warmup;
                            moveRot = -25;
                            mirror = true;
                            turretShading = true;
                        }},
                        new RegionPart("-mid"){{
                            progress = PartProgress.recoil;
                            moveY = -3.8f;
                            mirror = false;
                        }},
                        new RegionPart("-missile"){{
                            progress = PartProgress.reload.curve(Interp.pow2In);

                            colorTo = new Color(1f, 1f, 1f, 0f);
                            color = Color.white;
                            mixColorTo = Pal.accent;
                            mixColor = new Color(1f, 1f, 1f, 0f);
                            outline = false;
                            under = true;

                            layerOffset = -0.01f;

                            moves.add(new PartMove(PartProgress.warmup.inv(), 0f, -2f, 0f));
                        }}
                );
            }};
        }};

        burden = new LiquidTurret("burden"){{
            requirements(Category.turret, with(PsammosItems.refinedMetal, 100, PsammosItems.quartz, 80, PsammosItems.aerogel, 60, PsammosItems.memoryAlloy, 60, PsammosItems.silver, 150));

            ammo(
                    PsammosLiquids.coldWater, new VortexBulletType(){{
                        damage = 75;
                        speed = 3.5f;

                        trailWidth = 4;
                        trailLength = 18;

                        intervalBullets = 3;
                        intervalAngle = 0;
                        intervalRandomSpread = 15;
                        bulletInterval = 15;

                        trailInterval = 15;
                        trailEffect = Fx.hitSquaresColor;

                        chargeEffect = new Effect(60f, 30f, e -> {
                            Draw.color(Pal.missileYellowBack);
                            Lines.stroke(e.fin());

                            Lines.poly(e.x, e.y, 4, 4f + e.fout() * 30f, e.rotation + e.fout()*90);
                            Lines.poly(e.x, e.y, 4, 4f + e.fout() * 30f, e.rotation + e.fin()*90);

                            Fill.poly(e.x, e.y, 4, e.fin() * 6f, e.rotation + e.fout()*90);
                            Fill.poly(e.x, e.y, 4, e.fin() * 6f, e.rotation + e.fin()*90);
                            Draw.color(Pal.missileYellow);
                            Fill.poly(e.x, e.y, 4, e.fin() * 4f, e.rotation + e.fout()*90);
                            Fill.poly(e.x, e.y, 4, e.fin() * 4f, e.rotation + e.fin()*90);
                        });

                        intervalBullet = new BasicBulletType(){{
                            backSprite = "large-bomb-back";
                            sprite = "mine-bullet";
                            speed = 3f;
                            damage = 10;
                            width = 8;
                            height = 8;
                            trailWidth = 1.5f;
                            trailLength = 6;
                            homingRange = 60f;
                            homingPower = 0.1f;
                            lifetime = 45;
                            backColor = trailColor = Pal.missileYellowBack;
                            frontColor = hitColor = Pal.missileYellow;
                        }};
                    }}
            );

            size = 4;
            health = 1020;
            squareSprite = false;
            outlineColor = PPal.turretOutline;
            targetAir = true;
            targetGround = true;
            range = 360;
            reload = 180;
            shootY = 3;
            shake = 6f;
            recoil = 3f;
            shoot.firstShotDelay = 60;
            moveWhileCharging = false;
            accurateDelay = false;
            shootEffect = Fx.shootSmokeSmite;
            chargeSound = Sounds.lasercharge2;
            shootSound = Sounds.shootSmite;
            extinguish = false;

            heatRequirement = 16f;

            drawer = new DrawTurret("heatproof-"){{
                parts.addAll(
                    new RegionPart("-side"){{
                        progress = PartProgress.charge;
                        moveY = -4f;
                        mirror = true;
                        turretShading = true;
                        layerOffset = -0.01f;
                        moves.add(new PartMove(PartProgress.recoil, 0f, 0f, -15f));
                    }},
                    new RegionPart("-front"){{
                        progress = PartProgress.recoil;
                        moveRot = -15f;
                        mirror = true;
                        turretShading = true;
                        layerOffset = -0.01f;
                    }}
                );
            }};
        }};

        // Drills/Production

        osmiumDrill = new RotaryBurstDrill("1a-osmium-drill"){{
            requirements(Category.production, with(PsammosItems.osmium, 15));
            alwaysUnlocked = true;

            itemCapacity = 20;
            size = 2;
            tier = 2;
            drillTime = 400;
            ambientSoundVolume = 0.05f;
            baseArrowColor = Color.valueOf("#a4a098");
            drillEffect = new MultiEffect(
                    Fx.mineHuge,
                    new Effect(30f, e -> {
                        Draw.color(e.color);
                        Lines.stroke(e.fout() * 1.5f);
                        Lines.stroke(5f * e.fout());
                        Lines.circle(e.x, e.y, e.finpow() * 12f);
                    })
            );

            consumeLiquid(PsammosLiquids.coldWater, 0.05f).boost();
        }};

        detonationDrill = new RotaryBurstDrill("1b-detonation-drill"){{
            requirements(Category.production, with(PsammosItems.osmium, 20, PsammosItems.refinedMetal, 30, Items.blastCompound, 8));

            itemCapacity = 40;
            size = 3;
            tier = 3;
            drillTime = 210;
            squareSprite = false;
            shake = 4;
            baseArrowColor = Color.valueOf("#a4a098");
            liquidBoostIntensity = 1f; //So it doesn't get boosted by the cold water it consumes
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
            damage = 80;
            explodeTime = 5;
            range = 3;
            baseColor = Items.blastCompound.color;
            effect = new MultiEffect(
                    Fx.blastExplosion,
                    Fx.dynamicSpikes.wrap(Items.blastCompound.color, 18)
            );
            explosionSound = Sounds.dullExplosion;
            shake = 2f;
            protectEnemyBlocks = true;
        }};

        ammoniaBomb = new Bomb("ammonia-bomb"){{
            requirements(Category.production, with(PsammosItems.aerogel, 10, PsammosItems.silver, 8));

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(PsammosLiquids.ammonia, 1.5f),
                    new DrawDefault(),
                    new DrawProgressGlowRegion(){{color = PsammosLiquids.ammonia.color.cpy();}}
            );

            size = 2;
            squareSprite = false;
            damage = 280;
            explodeTime = 80;
            range = 8;
            baseColor = PsammosLiquids.ammonia.color;
            effect = new MultiEffect(
                    Fx.missileTrailSmoke.wrap(Color.grays(0.5f)),
                    Fx.dynamicSpikes.wrap(PsammosLiquids.ammonia.color, 32)
            );
            explosionSound = Sounds.explosionbig;
            shake = 10f;

            consumeLiquid(PsammosLiquids.ammonia, 1f/60f);
        }};

        // Distribution

        heatproofConveyor = new Conveyor("1a-osmium-conveyor"){{
            requirements(Category.distribution, with(PsammosItems.osmium, 1));
            alwaysUnlocked = true;

            health = 45;
            speed = 0.09f;
            displayedSpeed = 9f;
            buildCostMultiplier = 2f;
            itemCapacity = 3;
        }};

        platedConveyor = new ArmoredConveyor("plated-conveyor"){{
            requirements(Category.distribution, with(PsammosItems.memoryAlloy, 1, PsammosItems.refinedMetal, 1));

            health = 180;
            speed = 0.12f;
            displayedSpeed = 12f;
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

        vacuumPipe = new ArmoredConduit("vacuum-pipe"){{
            requirements(Category.liquid, with(PsammosItems.aerogel, 1, PsammosItems.refinedMetal, 2));
            buildCostMultiplier = 2f;

            liquidPressure = 1.03f;
            health = 60;
            liquidCapacity = 16;
        }};

        pipeJunction = new LiquidJunction("3a-quartz-liquid-junction"){{
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

        // Power

        electricPole = new WireNode("1a-electric-pole"){{
            requirements(Category.power, with(PsammosItems.silver, 4));

            size = 1;
            pulseMag = 0;
            pulseScl = 0;
            range = 10;
        }};

        electricDistributor = new WireNode("1b-electric-distributor"){{
            requirements(Category.power, with(PsammosItems.silver, 25, Items.silicon, 10, PsammosItems.refinedMetal, 10));

            size = 3;
            pulseMag = 0;
            pulseScl = 0;
            range = 25;
        }};

        led = new LED("led"){{
            requirements(Category.power, with(PsammosItems.quartz, 10, PsammosItems.silver, 5, Items.silicon, 10));
            brightness = 0.75f;
            radius = 140f;
        }};

        accumulator = new Battery("2a-accumulator"){{
            requirements(Category.power, with(PsammosItems.silver, 10, Items.silicon, 5));

            size = 2;
            emptyLightColor = Color.valueOf("#829cae");
            fullLightColor = Color.valueOf("#b9d1e1");

            consumePowerBuffered(4000);
        }};

        windTurbine = new WindTurbine("3a-wind-turbine"){{
            requirements(Category.power, with(PsammosItems.osmium, 60, PsammosItems.silver, 60, Items.sand, 40));

            size = 2;
            powerProduction = 1/3f;
            hasPower = true;
            outputsPower = true;
            squareSprite = false;
            range = 6;
        }};

        piezoelectricGenerator = new ConsumeGenerator("piezoelectric-generator"){{
            requirements(Category.power, with(PsammosItems.silver, 30, PsammosItems.quartz, 20));

            size = 2;
            powerProduction = 3;
            hasPower = true;
            outputsPower = true;
            squareSprite = false;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(PsammosLiquids.quicksand, 1),
                    new DrawPlasma(){{
                        plasmas = 3;
                        plasma1 = PsammosLiquids.quicksand.color;
                    }},
                    new DrawDefault()
            );

            consumeLiquid(PsammosLiquids.quicksand, 10 / 60f);
        }};

        impulseGenerator = new ConsumeGenerator("impulse-generator"){{
            requirements(Category.power, with(PsammosItems.silver, 30, PsammosItems.refinedMetal, 15, Items.silicon, 20));

            size = 2;
            powerProduction = 250/60f;
            hasPower = true;
            outputsPower = true;
            squareSprite = true;
            itemDuration = 120f;
            drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
            generateEffect = Fx.blastExplosion;

            consume(new ConsumeItemExplosive(1f));
        }};

        liquidFuelBurner = new ConsumeGenerator("3b-liquid-fuel-burner"){{
            requirements(Category.power, with(PsammosItems.osmium, 40, PsammosItems.silver, 30, PsammosItems.refinedMetal, 40, PsammosItems.quartz, 20));

            size = 2;
            powerProduction = 8;
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
        }};

        silverWallLarge = new Wall("2b-silver-wall-large"){{
            requirements(Category.defense, mult(silverWall.requirements, 4));
            health = 100 * wallHealthMultiplier * 4;
            size = 2;
        }};

        gate = new AutoDoor("gate"){{
            requirements(Category.defense, with(PsammosItems.quartz, 6, Items.silicon, 4));
            health = 110 * wallHealthMultiplier;
        }};

        gateLarge = new AutoDoor("gate-large"){{
            requirements(Category.defense, mult(gate.requirements, 4));
            health = 110 * wallHealthMultiplier * 4;
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

        // Crafting

        sieve = new GenericCrafter("1a-sieve"){{
            requirements(Category.crafting, with(PsammosItems.osmium, 35, PsammosItems.silver, 15, Items.sand, 10));

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

        filter = new GenericCrafter("filter"){{
            requirements(Category.crafting, with(PsammosItems.osmium, 60, Items.silicon, 30, PsammosItems.refinedMetal, 20, Items.sand, 30));

            size = 3;
            squareSprite = false;
            craftEffect = Fx.pulverize;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-rotator", 4, true),
                    new DrawDefault()
            );

            outputItem = new ItemStack(PsammosItems.quartz, 2);
            craftTime = 30;

            consumeItem(Items.sand, 3);
            consumeLiquid(Liquids.nitrogen, 1/60f);
            consumePower(1.25f);
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
            requirements(Category.crafting, with(PsammosItems.silver, 35, PsammosItems.quartz, 25, Items.silicon, 15, Items.sand, 20));

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

        atmosphericSeparator = new GenericCrafter("atmospheric-separator"){{
            requirements(Category.crafting, with(PsammosItems.quartz, 45, PsammosItems.refinedMetal, 30, Items.silicon, 15, Items.sand, 20));

            size = 2;
            squareSprite = false;
            hasLiquids = true;
            liquidCapacity = 20;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(Liquids.nitrogen, 2f),
                    new DrawDefault(),
                    new DrawParticles(){{
                        color = Color.valueOf("d4f0ff");
                        alpha = 0.6f;
                        particleSize = 2f;
                        particles = 8;
                        particleRad = 10f;
                        particleLife = 100f;
                    }}
            );

            outputLiquid = new LiquidStack(Liquids.nitrogen, 2/60f);
            craftTime = 120;

            consumePower(1.5f);
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

        ozoneHeater = new HeatProducer("ozone-heater"){{
            requirements(Category.crafting, with(PsammosItems.osmium, 30, PsammosItems.silver, 30, Items.silicon, 30, PsammosItems.refinedMetal, 50));

            size = 2;
            squareSprite = false;
            hasLiquids = true;
            liquidCapacity = 15;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(Liquids.ozone),
                    new DrawDefault(),
                    new DrawHeatOutput(),
                    new DrawGlowRegion()
            );
            rotate = true;
            rotateDraw = false;

            heatOutput = 3;
            craftTime = 60;

            consumeLiquid(Liquids.ozone, 2 / 60f);
        }};

        peatHeater = new HeatProducer("peat-heater"){{
            requirements(Category.crafting, with(PsammosItems.silver, 30, PsammosItems.quartz, 10, PsammosItems.refinedMetal, 20, PsammosItems.aerogel, 15));

            size = 3;
            squareSprite = false;
            hasLiquids = true;
            liquidCapacity = 15;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawSoftParticles(){{
                        alpha = 0.35f;
                        color = Color.valueOf("#feb380");
                        color2 = Color.valueOf("#ea8878");
                        particleSize = 10;
                        particleRad = 12;
                    }},
                    new DrawDefault(),
                    new DrawHeatOutput()
            );
            rotate = true;
            rotateDraw = false;

            heatOutput = 5;
            craftTime = 60;

            consumeItem(PsammosItems.peat, 2);
        }};

        ammoniaHeater = new HeatProducer("ammonia-heater"){{
            requirements(Category.crafting, with(PsammosItems.memoryAlloy, 20, PsammosItems.aerogel, 15, Items.silicon, 40, PsammosItems.refinedMetal, 60));

            size = 3;
            squareSprite = false;
            hasLiquids = true;
            liquidCapacity = 15;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawCircles(){{
                        color = Color.valueOf("e3b7fb").a(0.24f);
                        strokeMax = 1.8f;
                        strokeMin = 0.4f;
                        radius = 10f;
                        amount = 4;
                    }},
                    new DrawDefault(),
                    new DrawHeatOutput(),
                    new DrawGlowRegion(){{
                        color = Color.valueOf("#e3b7fb");
                        alpha = 0.4f;
                    }}
            );
            rotate = true;
            rotateDraw = false;

            heatOutput = 8;
            craftTime = 60;

            consumeLiquid(PsammosLiquids.ammonia, 1f / 60f);
        }};

        heatPump = new HeatConductor("heat-pump"){{
            requirements(Category.crafting, with(PsammosItems.silver, 10, PsammosItems.aerogel, 8));
            group = BlockGroup.heat;
            size = 2;

            drawer = new DrawMulti(
                    new DrawDefault(),
                    new DrawHeatOutput(),
                    new DrawHeatInput()
            );
            rotate = true;
            rotateDraw = false;
        }};

        heatPumpRouter = new HeatConductor("heat-pump-router"){{
            requirements(Category.crafting, with(PsammosItems.silver, 12, PsammosItems.aerogel, 15));
            group = BlockGroup.heat;
            size = 2;
            splitHeat = true;

            drawer = new DrawMulti(
                    new DrawDefault(),
                    new DrawHeatOutput(-1, false),
                    new DrawHeatOutput(),
                    new DrawHeatOutput(1, false),
                    new DrawHeatInput()
            );
            rotate = true;
            rotateDraw = false;
        }};

        aerogelPressurizer = new GenericCrafter("aerogel-pressurizer"){{
            requirements(Category.crafting, with(PsammosItems.osmium, 40, PsammosItems.quartz, 50, PsammosItems.refinedMetal, 35, Items.silicon, 20, Items.sand, 35));

            size = 4;
            squareSprite = false;
            itemCapacity = 20;
            hasLiquids = true;
            liquidCapacity = 15;

            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidTile(PsammosLiquids.methane, 3),
                new DrawPistons(){{
                    sinMag = 2.5f;
                    sinScl = 3;
                }},
                new DrawDefault(),
                new DrawGlowRegion(){{
                    color = Color.valueOf("#5bffbd");
                    alpha = 0.3f;
                }}
            );

            craftEffect = Fx.artilleryTrailSmoke;
            outputItem = new ItemStack(PsammosItems.aerogel, 1);
            craftTime = 90;

            consumePower(2.5f);
            consumeLiquid(PsammosLiquids.methane, 2/60f);
            consumeItem(Items.silicon, 3);
        }};

        steamReformer = new HeatCrafter("steam-reformer"){{
            requirements(Category.crafting, with(PsammosItems.silver, 25, PsammosItems.refinedMetal, 20, PsammosItems.quartz, 40, Items.silicon, 10));

            size = 3;
            squareSprite = false;
            hasLiquids = true;
            liquidCapacity = 30;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawParticles(){{
                        color = Color.valueOf("#ffffff");
                        alpha = 0.25f;
                        particleSize = 3;
                        particles = 13;
                        particleRad = 9;
                        particleLife = 200;
                        reverse = true;
                    }},
                    new DrawGlowRegion(){{
                        alpha = 0.5f;
                    }},
                    new DrawDefault(),
                    new DrawHeatInput()
            );
            rotate = true;
            rotateDraw = false;

            outputLiquid = new LiquidStack(Liquids.hydrogen, 6 / 60f);
            craftTime = 60;

            heatRequirement = 4;
            consumeLiquid(Liquids.water, 2/60f);
            consumeLiquid(PsammosLiquids.methane, 2/60f);
        }};

        ammoniaCompressor = new HeatCrafter("ammonia-compressor"){{
            requirements(Category.crafting, with(PsammosItems.aerogel, 30, PsammosItems.osmium, 60, PsammosItems.refinedMetal, 75, Items.silicon, 30));

            size = 3;
            squareSprite = false;
            hasLiquids = true;
            liquidCapacity = 15;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawCircles(){{
                        color = Color.valueOf("e3b7fb").a(0.24f);
                        strokeMax = 2f;
                        radius = 10f;
                        amount = 4;
                    }},
                    new DrawPistons(){{
                        sinMag = 2f;
                        sinScl = 5f;
                        sides = 4;
                        angleOffset = 45;
                    }},
                    new DrawDefault(),
                    new DrawGlowRegion(){{
                        color = Color.valueOf("#e3b7fb");
                        alpha = 0.4f;
                    }},
                    new DrawHeatInput()
            );

            outputLiquid = new LiquidStack(PsammosLiquids.ammonia, 3/60f);
            craftTime = 90;

            heatRequirement = 8;
            consumePower(2f);
            consumeLiquid(Liquids.nitrogen, (2/3f)/60f);
            consumeLiquid(Liquids.hydrogen, 2/60f);
        }};

        memoryAlloyCrucible = new GenericCrafter("memory-alloy-crucible"){{
            requirements(Category.crafting, with(PsammosItems.refinedMetal, 75, PsammosItems.aerogel, 40, PsammosItems.silver, 75, Items.silicon, 60));

            size = 3;
            squareSprite = false;
            itemCapacity = 20;
            hasLiquids = true;
            liquidCapacity = 15;

            drawer = new DrawMulti(
                    new DrawDefault(),
                    new DrawFlame(Color.valueOf("#d194f3"))
            );

            craftEffect = new Effect(60f, e -> {
                Vec2 v = new Vec2();
                Draw.color(Color.valueOf("#d194f3"));
                Draw.alpha(0.5f);
                Mathf.rand.setSeed(e.id);
                for(int i = 0; i < 30; i++){
                    v.trns(e.rotation + 90 + Mathf.rand.range(20f), Mathf.rand.random(e.finpow() * 35f)).add(Mathf.rand.range(3f), Mathf.rand.range(2f));
                    e.scaled(e.lifetime * Mathf.rand.random(0.2f, 1f), b -> {
                        Fill.circle(e.x + v.x, e.y + v.y, b.fout() * 8f + 0.3f);
                    });
                }
            });
            outputItem = new ItemStack(PsammosItems.memoryAlloy, 1);
            craftTime = 75;

            consumePower(3.5f);
            consumeLiquid(Liquids.slag, 8/60f);
            consumeLiquid(Liquids.hydrogen, 3/60f);
            consumeItem(PsammosItems.silver, 3);
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
            plans.add(new UnitPlan(PsammosUnitTypes.fang, 600, with(PsammosItems.silver, 25, Items.silicon, 15)));
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
            consumeLiquid(PsammosLiquids.fuel, 2f / 60f);
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
            consumeLiquid(PsammosLiquids.fuel, 2f / 60f);
            consumeItems(with(Items.silicon, 30, PsammosItems.refinedMetal, 40));

            constructTime = 60f * 28f;

            upgrades.addAll(
                    new UnitType[]{PsammosUnitTypes.tip, PsammosUnitTypes.pike}
            );
        }};

        supportUnitRecombiner = new Reconstructor("support-unit-recombiner"){{
            requirements(Category.units, with(Items.silicon, 75, PsammosItems.refinedMetal, 60, PsammosItems.osmium, 30));

            size = 3;
            consumePower(3f);
            consumeLiquid(PsammosLiquids.fuel, 2f / 60f);
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
            consumeLiquid(PsammosLiquids.fuel, 2f / 60f);
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
            consumeLiquid(PsammosLiquids.fuel, 2f / 60f);
            consumeItems(with(Items.silicon, 40, PsammosItems.refinedMetal, 40));

            constructTime = 60f * 30f;

            upgrades.addAll(
                    new UnitType[]{PsammosUnitTypes.pawn, PsammosUnitTypes.knight}
            );
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

        // Effect/Storage

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

        //Logic
        heatproofMessage = new MessageBlock("heatproof-message"){{
            requirements(Category.logic, with(PsammosItems.quartz, 5, PsammosItems.osmium, 5));
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
            cacheLayer = PsammosCacheLayers.quicksand;
            speedMultiplier = 0.1f;
            liquidDrop = PsammosLiquids.quicksand;
            status = PsammosStatusEffects.quicksandSlowed;
            drownTime = 200;
            isLiquid = true;
        }};

        darkQuicksand = new Floor("dark-quicksand"){{
            variants = 3;
            cacheLayer = PsammosCacheLayers.darkQuicksand;
            speedMultiplier = 0.1f;
            liquidDrop = PsammosLiquids.quicksand;
            status = PsammosStatusEffects.quicksandSlowed;
            drownTime = 200;
            isLiquid = true;
        }};

        peatFloor = new Floor("3b-peat-floor"){{
            variants = 4;
            itemDrop = PsammosItems.peat;
            playerUnmineable = true;
        }};

        quartzFloor = new Floor("3c-quartz-floor"){{
            variants = 4;
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

        peatBoulder = new Prop("peat-boulder"){{
            variants = 2;
        }};

        quartzBoulder = new Prop("5b-quartz-boulder"){{
            variants = 2;
        }};

        crystalQuartz = new TallBlock("crystal-quartz"){{
            variants = 1;
            clipSize = 128f;
        }};
    }
}
