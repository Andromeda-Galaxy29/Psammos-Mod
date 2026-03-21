package psammos.content.blocks;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
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
import mindustry.type.unit.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import psammos.*;
import psammos.ai.*;
import psammos.content.*;
import psammos.entities.bullet.*;
import psammos.entities.patterns.*;
import psammos.type.RadiationStack;
import psammos.type.RadiationType;
import psammos.world.blocks.legacy.LegacyTurret;
import psammos.world.blocks.turrets.ContinuousRadiationTurret;
import psammos.world.blocks.turrets.RadiationItemTurret;

import static mindustry.type.ItemStack.*;

public class PsammosTurrets {
    public static Block
            //Turrets
            cross, disseminate, hurl, influence, gunslinger,
            spray, discharge, burst, overflow, dawn, burden,
            luminosity, meteor,
            //Legacy
            influenceOld, seize, confine;

    public static void load(){
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
                        reloadMultiplier = 1.5f;
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
            shootSound = Sounds.shoot;
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

            coolant = consume(new ConsumeCoolant(0.1f){{maxFlammability = 1f; allowGas = true;}});
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
                        hitColor = PPal.blast;
                        backColor = PPal.blast2;
                        frontColor = PPal.blast;
                        trailColor = PPal.blast2;
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
            shootSound = Sounds.shootCyclone;
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

            coolant = consume(new ConsumeCoolant(0.1f){{maxFlammability = 1f; allowGas = true;}});
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
                        splashDamageRadius = 20f;
                        splashDamage = 11f;
                        buildingDamageMultiplier = 0.01f;
                    }},
                    Items.silicon, new ArtilleryBulletType(){{
                        collidesAir = true;
                        width = 11;
                        height = 11;
                        speed = 3;
                        lifetime = 80;
                        collidesTiles = false;
                        splashDamageRadius = 20f;
                        splashDamage = 11f;
                        reloadMultiplier = 1.2f;
                        ammoMultiplier = 3f;
                        homingPower = 0.1f;
                        homingRange = 50f;
                        buildingDamageMultiplier = 0.01f;
                    }},
                    Items.blastCompound, new ArtilleryBulletType(){{
                        collidesAir = true;
                        width = 11;
                        height = 11;
                        speed = 3.5f;
                        lifetime = 70;
                        collidesTiles = false;
                        splashDamageRadius = 40f;
                        splashDamage = 17f;
                        reloadMultiplier = 1f;
                        ammoMultiplier = 3f;
                        buildingDamageMultiplier = 0.01f;
                        frontColor = PPal.blast;
                        backColor = PPal.blast2;
                        despawnEffect = hitEffect = Fx.blastExplosion;
                    }}
            );

            size = 2;
            health = 450;
            squareSprite = false;
            outlineColor = PPal.turretOutline;
            shootSound = Sounds.shootArtillerySmall;
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

            coolant = consume(new ConsumeCoolant(0.1f){{maxFlammability = 1f; allowGas = true;}});
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
                        hitColor = backColor = trailColor = lightningColor = PPal.electric;
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
            shootSound = Sounds.shootArc;
            outlineColor = PPal.turretOutline;
            heatColor = PPal.electric;
            targetAir = false;
            targetGround = true;
            range = 140;
            reload = 60;
            inaccuracy = 0;
            shootY = -1.5f;

            drawer = new DrawTurret("heatproof-");

            consumePower(1);
            coolant = consume(new ConsumeCoolant(0.1f){{maxFlammability = 1f; allowGas = true;}});
        }};

        gunslinger = new ItemTurret("3a-gunslinger"){{
            requirements(Category.turret, with(PsammosItems.refinedMetal, 25, PsammosItems.silver, 30, Items.blastCompound, 10));

            ammo(
                    Items.silicon, new BasicBulletType(){{
                        speed = 4;
                        damage = 30;
                        width = 12;
                        height = 16;
                        lifetime = 45;
                        trailWidth = 2.2f;
                        trailLength = 5;
                        trailEffect = Fx.colorTrail;
                        trailInterval = 10f;
                        sprite = "psammos-revolver-bullet";
                        smokeEffect = Fx.shootSmokeSquareSparse;
                        hitEffect = despawnEffect = Fx.hitBulletBig;
                        homingRange = 45;
                        homingPower = 0.1f;
                        reloadMultiplier = 1.2f;
                    }},
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
                    }},
                    PsammosItems.memoryAlloy, new BasicBulletType(){{
                        speed = 5;
                        damage = 40;
                        width = 12;
                        height = 16;
                        lifetime = 45;
                        trailWidth = 2.2f;
                        trailLength = 5;
                        trailEffect = Fx.colorTrail;
                        trailInterval = 10f;
                        trailColor = hitColor = PPal.memoryAlloy;
                        backColor = PPal.memoryAlloy;
                        sprite = "psammos-revolver-bullet";
                        smokeEffect = Fx.shootSmokeSquareSparse;
                        hitEffect = despawnEffect = Fx.hitSquaresColor;

                        fragBullets = 3;
                        fragSpread = 40;
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
                            drawSize = 400;
                            collidesAir = true;
                            length = 30;
                            width = 5;
                            pierceCap = 6;
                            sideWidth = 0f;
                            sideLength = 0;
                        }};
                    }}
            );
            shoot.shots = 6;
            shoot.shotDelay = 7;
            ammoUseEffect = Fx.casing2Double;

            size = 3;
            health = 600;
            squareSprite = false;
            shootSound = Sounds.shootDisperse;
            outlineColor = PPal.turretOutline;
            targetAir = true;
            targetGround = true;
            range = 200;
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

            coolant = consume(new ConsumeCoolant(0.1f){{maxFlammability = 1f; allowGas = true;}});
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
            shootEffect = Fx.shootLiquid;
            shootSound = Sounds.shoot;
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

        discharge = new ItemTurret("discharge"){{
            requirements(Category.turret, with(Items.silicon, 30, PsammosItems.refinedMetal, 30, PsammosItems.silver, 20, Items.blastCompound, 15));

            ammo(
                    PsammosItems.silver, new LightningTrailBulletType(){{
                        sprite = "mine-bullet";
                        backSprite = "mine-bullet-back";
                        collidesAir = true;
                        collidesGround = false;
                        width = 15;
                        height = 15;
                        speed = 7;
                        damage = 45;
                        lifetime = 30;
                        hitColor = backColor = trailColor = lightningColor = PPal.electric;
                        frontColor = lightColor = Color.valueOf("#ffffff");
                        trailWidth = 3;
                        trailLength = 8;
                        trailEffect = Fx.shootSmokeSquare;
                        trailInterval = 5;
                        trailRotation = true;
                        hitEffect = despawnEffect = Fx.hitSquaresColor;
                    }},
                    PsammosItems.memoryAlloy, new LightningTrailBulletType(){{
                        sprite = "mine-bullet";
                        backSprite = "mine-bullet-back";
                        collidesAir = true;
                        collidesGround = false;
                        width = 15;
                        height = 15;
                        speed = 7;
                        damage = 70;
                        lifetime = 30;
                        hitColor = backColor = trailColor = lightningColor = PPal.memoryAlloy;
                        frontColor = lightColor = Color.valueOf("#ffffff");
                        trailWidth = 3;
                        trailLength = 8;
                        trailEffect = Fx.shootSmokeSquare;
                        trailInterval = 5;
                        trailRotation = true;
                        hitEffect = despawnEffect = Fx.hitSquaresColor;
                        fragOnHit = true;
                        fragBullets = 1;
                        fragRandomSpread = 0;
                        fragSpread = 0;
                        fragOnAbsorb = true;
                        fragBullet = new LaserBulletType(){{
                            damage = 12;
                            frontColor = Color.white;
                            backColor = PPal.memoryAlloy;
                            hitEffect = Fx.hitLancer;
                            laserEffect = Fx.none;
                            hitSize = 3;
                            lifetime = 12;
                            drawSize = 400;
                            collidesAir = true;
                            collidesGround = false;
                            collidesTiles = false;
                            length = 45;
                            width = 7;
                            pierceCap = 6;
                            sideWidth = 1f;
                            sideLength = 2;
                        }};
                    }}
            );
            shoot = new ShootBursts(4, 3,6f);
            shoot.firstShotDelay = 20;
            ((ShootBursts)shoot).burstDelay = 10;

            size = 3;
            health = 600;
            squareSprite = false;
            shootSound = Sounds.shootToxopidShotgun;
            outlineColor = PPal.turretOutline;
            heatColor = PPal.electric;
            targetAir = true;
            targetGround = false;
            range = 180;
            reload = 90;
            velocityRnd = 0.2f;
            rotateSpeed = 4f;
            shootY = -1;

            consumePower(3);

            drawer = new DrawTurret("heatproof-"){{
                parts.addAll(
                        new RegionPart("-side"){{
                            mirror = true;
                            turretShading = true;
                            progress = PartProgress.warmup;
                            heatProgress = PartProgress.recoil;
                            heatColor = PPal.electric;
                            moveX = 1f;
                            moveRot = -15;
                        }},
                        new RegionPart("-barrel"){{
                            heatProgress = progress = PartProgress.recoil;
                            heatColor = PPal.electric;
                            moveY = -4f;
                            mirror = false;
                        }}
                );
            }};

            coolant = consume(new ConsumeCoolant(0.1f){{maxFlammability = 1f; allowGas = true;}});
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
            health = 650;
            reload = 50f;
            outlineColor = PPal.turretOutline;
            squareSprite = false;
            recoil = 2f;
            range = 140;
            shootY = 5f;
            inaccuracy = 0.2f;
            velocityRnd = 0.2f;
            shake = 1f;
            shootSound = Sounds.shootArtillerySap;
            shootEffect = Fx.shootPyraFlame;

            drawer = new DrawTurret("heatproof-"){{
                parts.add(new RegionPart("-front"){{
                    progress = PartProgress.warmup;
                    heatProgress = PartProgress.recoil;
                    moveRot = -10f;
                    mirror = true;
                    moves.add(new PartMove(PartProgress.recoil, 0f, -2f, -5f));
                }});
            }};
        }};

        overflow = new LiquidTurret("overflow"){{
            requirements(Category.turret, with(PsammosItems.silver, 40, PsammosItems.quartz, 30, PsammosItems.aerogel, 30, PsammosItems.refinedMetal, 25));

            ammo(
                    Liquids.water, new LiquidBulletType(Liquids.water){{
                        lifetime = 49f;
                        speed = 4f;
                        knockback = 0.9f;
                        puddleSize = 8f;
                        orbSize = 5f;
                        drag = 0.001f;
                        ammoMultiplier = 0.4f;
                        statusDuration = 60f * 2f;
                        damage = 0.6f;
                        layer = Layer.bullet - 2f;

                        intervalBullet = new LightningBulletType(){{
                            damage = 6;
                            lightningColor = Liquids.water.color;
                            lightningLength = 5;
                            lightningLengthRand = 4;
                            status = StatusEffects.electrified;

                            lightningType = new BulletType(0.0001f, 0f){{
                                lifetime = Fx.lightning.lifetime;
                                hitEffect = Fx.hitLancer;
                                despawnEffect = Fx.none;
                                status = StatusEffects.electrified;
                                statusDuration = 60f * 4f;
                                hittable = false;
                                lightColor = Color.white;
                            }};
                        }};
                        bulletInterval = 6f;
                        intervalAngle = 0;
                        intervalRandomSpread = 15;
                    }},
                    PsammosLiquids.coldWater, new LiquidBulletType(PsammosLiquids.coldWater){{
                        lifetime = 49f;
                        speed = 4f;
                        knockback = 0.7f;
                        puddleSize = 8f;
                        orbSize = 5f;
                        drag = 0.001f;
                        ammoMultiplier = 0.4f;
                        statusDuration = 60f * 4f;
                        damage = 0.8f;
                        layer = Layer.bullet - 2f;

                        intervalBullet = new LightningBulletType(){{
                            damage = 6;
                            lightningColor = PsammosLiquids.coldWater.color;
                            lightningLength = 5;
                            lightningLengthRand = 4;
                            status = StatusEffects.electrified;

                            lightningType = new BulletType(0.0001f, 0f){{
                                lifetime = Fx.lightning.lifetime;
                                hitEffect = Fx.hitLancer;
                                despawnEffect = Fx.none;
                                status = StatusEffects.electrified;
                                statusDuration = 60f * 4f;
                                hittable = false;
                                lightColor = Color.white;
                            }};
                        }};
                        bulletInterval = 6f;
                        intervalAngle = 0;
                        intervalRandomSpread = 15;
                    }}
            );

            size = 3;
            health = 650;
            squareSprite = false;
            outlineColor = PPal.turretOutline;
            reload = 5f;
            shoot.shots = 2;
            velocityRnd = 0.2f;
            inaccuracy = 3f;
            recoil = 1f;
            shootCone = 45f;
            liquidCapacity = 40f;
            shootEffect = Fx.shootLiquid;
            shootSound = Sounds.shootAtrax;
            range = 190f;
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);

            consumePower(4f);

            drawer = new DrawTurret("heatproof-"){{
                parts.addAll(
                        new RegionPart("-back"){{
                            progress = PartProgress.warmup;
                            under = true;
                            moveY = -2;
                            mirror = false;
                        }}
                );
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

            shootSound = Sounds.shootAfflict;

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
                        new RegionPart("-turret-missile"){{
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
                        damage = 95;
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
            chargeSound = Sounds.chargeLancer;
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

        luminosity = new ContinuousRadiationTurret("luminosity"){{
            requirements(Category.turret, with(Items.sand, 80, PsammosItems.refinedMetal, 80, PsammosItems.desertGlassShard, 30, PsammosItems.memoryAlloy, 30, PsammosItems.silver, 130));

            ammo(
                    RadiationType.light, new LightBeamBulletType(){{
                        damage = 6f;
                        length = 110;
                        angleWidth = 60;
                        buildingDamageMultiplier = 0f;
                        timescaleDamage = true;

                        collidesAir = false;
                        collidesTiles = false;

                        color = Pal.accent.cpy().a(0.8f);
                        drawFlare = true;
                    }}
            );

            size = 4;
            health = 1020;
            squareSprite = false;
            targetGround = true;
            targetAir = false;
            targetBlocks = false;
            range = 110;
            shootY = 2;
            recoil = 0;
            loopSound = Sounds.loopFlux;
            loopSoundVolume = 4.5f;
            shootSound = Sounds.shootFlamePlasma;
            Color hc = Pal.accent;
            heatColor = hc;

            drawer = new DrawTurret("heatproof-"){{
                parts.addAll(
                        new RegionPart("-wing"){{
                            progress = PartProgress.warmup;
                            moveRot = -25f;
                            moveY = -1f;
                            moveX = 1f;
                            x = 5;
                            y = -3;
                            mirror = true;
                            turretShading = true;
                            heatColor = hc;
                        }},
                        new RegionPart("-arm"){{
                            progress = PartProgress.warmup;
                            moveRot = -35f;
                            moveY = 2f;
                            moveX = 1f;
                            mirror = true;
                            turretShading = true;
                            heatColor = hc;
                        }}
                );
            }};
        }};

        meteor = new RadiationItemTurret("meteor"){{
            requirements(Category.turret, with(PsammosItems.osmium, 200, Items.silicon, 100, Items.blastCompound, 40, PsammosItems.aerogel, 80, PsammosItems.desertGlassShard, 30));
            researchCostMultiplier = 0.5f;
            ammo(
                    Items.blastCompound, new ArtilleryBulletType(){{
                        sprite = "large-bomb";
                        backSprite = "large-bomb-back";
                        collidesAir = false;
                        width = 20;
                        height = 20;
                        speed = 2;
                        lifetime = 170;
                        collidesTiles = true;
                        splashDamageRadius = 48f;
                        splashDamage = 240f;
                        buildingDamageMultiplier = 0.01f;
                        status = StatusEffects.blasted;
                        frontColor = PPal.blast;
                        backColor = PPal.blast2;
                        trailColor = PPal.blast2;
                        trailWidth = 3;
                        trailLength = 32;
                        statusDuration = 300f;
                        fragSpread = 360;
                        fragBullets = 7;
                        fragOnHit = true;
                        shootEffect = new WrapEffect(Fx.colorSparkBig, PPal.blast);
                        hitSound = despawnSound = Sounds.explosionMissile;
                        hitEffect = despawnEffect = new MultiEffect(
                                new ExplosionEffect(){{
                                    waveColor = PPal.blast;
                                    smokeColor = Color.gray;
                                    sparkColor = PPal.blast;
                                    waveStroke = 6f;
                                    waveRad = 48f;
                                    lifetime = 48f;
                                }},
                                new WaveEffect(){{
                                    colorFrom = PPal.blast;
                                    colorTo = PPal.blast2;
                                    strokeFrom = 8f;
                                    strokeTo = 0f;
                                    sizeFrom = 1f;
                                    sizeTo = 48f;
                                    lifetime = 48f;
                                }}
                        );
                        fragBullet = new ArtilleryBulletType(){{
                            collidesAir = false;
                            width = 4;
                            height = 4;
                            speed = 2;
                            lifetime = 24;
                            collidesTiles = false;
                            splashDamageRadius = 8f;
                            splashDamage = 22f;
                            buildingDamageMultiplier = 0.01f;
                            status = StatusEffects.blasted;
                            frontColor = PPal.blast;
                            backColor = PPal.blast2;
                            statusDuration = 300f;
                            hitSound = despawnSound = Sounds.explosionDull;
                            hitEffect = despawnEffect = new ExplosionEffect(){{
                                waveColor = PPal.blast;
                                smokeColor = Color.gray;
                                sparkColor = PPal.blast;
                                waveStroke = 3f;
                                waveRad = 16f;
                                lifetime = 32f;
                            }};
                        }};
                    }},
                    PsammosItems.desertGlassShard, new ArtilleryBulletType(){{
                        sprite = "large-bomb";
                        backSprite = "large-bomb-back";
                        collidesAir = false;
                        width = 18;
                        height = 18;
                        speed = 2;
                        lifetime = 190;
                        rangeChange = 40f;
                        collidesTiles = true;
                        splashDamageRadius = 72f;
                        splashDamage = 480f;
                        reloadMultiplier = 0.8f;
                        buildingDamageMultiplier = 0.01f;
                        frontColor = Color.white;
                        backColor = PPal.desertGlass;
                        trailColor = PPal.desertGlass;
                        trailWidth = 3;
                        trailLength = 32;
                        shootEffect = new WrapEffect(Fx.colorSparkBig, PPal.desertGlass);
                        hitSound = despawnSound = Sounds.explosionNavanax;
                        hitEffect = despawnEffect = new MultiEffect(
                                new WrapEffect(PsammosFx.glassMeteorExplosion, PPal.desertGlass),
                                new WrapEffect(Fx.dynamicExplosion,PPal.desertGlass),
                                new WrapEffect(Fx.sparkExplosion,PPal.desertGlass)
                        );
                    }}
            );
            size = 4;
            health = 1280;
            squareSprite = false;
            outlineColor = PPal.turretOutline;
            shootSound = Sounds.shootConquer;
            shootSoundVolume = 0.6f;
            targetAir = false;
            targetGround = true;
            range = 340;
            shootY = -2f;
            inaccuracy = 0f;
            velocityRnd = 0f;
            ammoPerShot = 9;
            reload = 180;

            drawer = new DrawTurret("heatproof-"){{
                parts.addAll(
                        new RegionPart("-side"){{
                            mirror = true;
                            progress = PartProgress.recoil;
                            moveY = 1f;
                        }},
                        new RegionPart("-bottom"){{
                            mirror = true;
                            turretShading = true;
                            moveY = 2f;
                        }},
                        new RegionPart("-low-wing"){{
                            mirror = true;
                            turretShading = true;
                            progress = PartProgress.warmup;
                            moveX = -2f;
                            moveY = -2f;
                            moveRot = 12f;
                        }},
                        new RegionPart("-high-wing"){{
                            mirror = true;
                            turretShading = true;
                            progress = PartProgress.warmup;
                            moveRot = 3f;
                            moveY = 1f;
                        }},
                        new RegionPart("-barrel"){{
                            mirror = false;
                            progress = PartProgress.recoil;
                            moveY = -4f;
                        }}
                );
            }};
            rotateSpeed = 0.9f;
            radiationRequirements = Seq.with(new RadiationStack(RadiationType.UV, 3f));
            maxRadiationEfficiency = 1f;
            coolant = consume(new ConsumeCoolant(0.1f){{maxFlammability = 1f; allowGas = true;}});
        }};

        influenceOld = new LegacyTurret("2a-influence"){{
            size = 2;
            replacement = influence;
        }};

        confine = new LiquidTurret("confine"){{
            requirements(Category.turret, with(PsammosItems.quartz, 35, PsammosItems.osmium, 15, Items.sand, 20));
            buildVisibility = BuildVisibility.hidden;

            ammo(
                    PsammosLiquids.quicksand, new WaveBulletType(PsammosLiquids.quicksand){{
                        width = 16;
                        height = 12;
                        lifetime = 30f;
                        speed = 4f;
                        damage = 6f;
                        drag = 0.05f;
                        statusDuration = 60f * 3f;
                        trailEffect = new Effect(40f, e -> {
                            Draw.color(PsammosLiquids.quicksand.color);

                            Angles.randLenVectors(e.id, 2, 1f + e.fin() * 2f, (x, y) -> {
                                Fill.square(e.x + x, e.y + y, e.fout() * 1.2f, 45f);
                            });
                        });
                        trailInterval = 4;
                    }},
                    PsammosLiquids.coldWater, new WaveBulletType(PsammosLiquids.coldWater){{
                        width = 16;
                        height = 12;
                        lifetime = 30f;
                        speed = 4f;
                        damage = 6f;
                        drag = 0.05f;
                        statusDuration = 60f * 3f;
                        trailEffect = new Effect(40f, e -> {
                            Draw.color(PsammosLiquids.coldWater.color);

                            Angles.randLenVectors(e.id, 2, 1f + e.fin() * 2f, (x, y) -> {
                                Fill.circle(e.x + x, e.y + y, e.fout() * 1.2f);
                            });
                        });
                        trailInterval = 4;
                    }},
                    PsammosLiquids.fuel, new WaveBulletType(PsammosLiquids.fuel){{
                        width = 16;
                        height = 12;
                        lifetime = 30f;
                        speed = 5f;
                        damage = 6f;
                        drag = 0.05f;
                        statusDuration = 60f * 3f;
                        trailEffect = Fx.fire;
                        trailInterval = 2;
                        status = StatusEffects.burning;
                    }}
            );

            shoot = new ShootSpread(4, 12f);

            hasPower = true;

            size = 2;
            reload = 30f;
            outlineColor = PPal.turretOutline;
            squareSprite = false;
            recoil = 2f;
            range = 80;
            velocityRnd = 0f;
            shootEffect = Fx.shootSmokeSquareSparse;

            consumePower(1);
        }};

        seize = new TractorBeamTurret("seize"){{
            requirements(Category.turret, with(PsammosItems.refinedMetal, 80, Items.silicon, 60, PsammosItems.quartz, 40));
            buildVisibility = BuildVisibility.hidden;

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

            coolant = consume(new ConsumeCoolant(0.1f){{maxFlammability = 1f; allowGas = true;}});
        }};
    }
}
