package psammos.content;

import arc.graphics.*;
import mindustry.ai.types.*;
import mindustry.content.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.type.*;
import psammos.units.weapons.*;

public class PsammosUnitTypes {

    public static UnitType

    //Core
    gradient, ascent, uprising,

    //Specialist
    fang, jaw, maw, gorge, gluttony,

    //Assault
    tip, spike, spear, javelin, trident,

    //Support
    sine, helix, trisect, quadrifol, lemniscate,

    //Scout
    sciur, glirid, exilis, pteromys, paraxerus;

    public static void load() {
        gradient = new UnitType("1a-gradient"){{
            aiController = BuilderAI::new;
            constructor = UnitEntity::create;

            lowAltitude = true;
            speed = 3;
            rotateSpeed = 15;
            accel = 0.1f;
            drag = 0.04f;
            flying = true;
            health = 240;
            hitSize = 8;
            armor = 1;
            itemCapacity = 30;
            outlineColor = Color.valueOf("#3c3835");
            engineOffset = 6;
            faceTarget = true;
            mineTier = 2;
            mineSpeed = 5;
            buildSpeed = 0.5f;
            buildBeamOffset = 5;
            isEnemy = false;

            weapons.addAll(
                new Weapon("psammos-gradient-gun"){{
                    x = 0;
                    y = 0;
                    reload = 12;
                    bullet = new BasicBulletType(){{
                        speed = 2.5f;
                        damage = 9;
                        width = 7;
                        height = 8;
                        lifetime = 60;
                        shootEffect = Fx.shootSmall;
                        smokeEffect = Fx.shootSmallSmoke;
                        buildingDamageMultiplier = 0.01f;
                    }};
                }}
            );
        }};

        ascent = new UnitType("ascent"){{
            aiController = BuilderAI::new;
            constructor = UnitEntity::create;

            lowAltitude = true;
            speed = 2.9f;
            rotateSpeed = 15;
            accel = 0.1f;
            drag = 0.04f;
            flying = true;
            health = 380;
            hitSize = 10;
            armor = 1;
            itemCapacity = 50;
            outlineColor = Color.valueOf("#3c3835");
            engineOffset = 5.5f;
            faceTarget = false;
            mineTier = 2;
            mineSpeed = 5;
            buildSpeed = 0.5f;
            buildBeamOffset = 4.5f;
            isEnemy = false;

            weapons.addAll(
                    new Weapon("psammos-ascent-gun"){{
                        x = 2.5f;
                        y = -2.5f;
                        reload = 14;
                        mirror = true;
                        rotate = true;
                        bullet = new BasicBulletType(){{
                            speed = 3f;
                            damage = 12;
                            width = 6;
                            height = 9;
                            lifetime = 70;
                            buildingDamageMultiplier = 0.01f;
                        }};
                    }}
            );
        }};

        fang = new UnitType("2a-fang"){{
            researchCostMultiplier = 0;
            aiController = HugAI::new;
            constructor = CrawlUnit::create;

            speed = 1;
            omniMovement = false;
            rotateSpeed = 2;
            health = 120;
            hitSize = 9;
            armor = 0;
            outlineColor = Color.valueOf("#3c3835");
            faceTarget = true;
            targetAir = false;
            crushDamage = 0.2f;

            segments = 3;
            drawBody = false;
            segmentScl = 3;
            segmentPhase = 5;
            segmentMag = 0.5f;
        }};

        jaw = new UnitType("2b-jaw"){{
            aiController = HugAI::new;
            constructor = CrawlUnit::create;

            speed = 1.2f;
            omniMovement = false;
            rotateSpeed = 2;
            health = 420;
            hitSize = 11;
            armor = 3;
            outlineColor = Color.valueOf("#3c3835");
            faceTarget = true;
            targetAir = false;
            crushDamage = 0.5f;

            segments = 3;
            drawBody = false;
            segmentScl = 3;
            segmentPhase = 5;
            segmentMag = 0.5f;

            weapons.addAll(
                new Weapon("psammos-jaw-gun"){{
                    x = 0;
                    y = 3;
                    reload = 20;
                    shootSound = Sounds.shootAlt;
                    mirror = false;
                    bullet = new ArtilleryBulletType(){{
                        damage = 25;
                        width = 6;
                        height = 6;
                        speed = 2;
                        hitColor = Color.valueOf("#ffffff");
                        backColor = Color.valueOf("#ffffff");
                        frontColor = Color.valueOf("#ffffff");
                        trailColor = Color.valueOf("#cccccc");
                        lifetime = 60;
                        splashDamage = 34;
                        splashDamageRadius = 6;
                    }};
                }}
            );
        }};

        maw = new UnitType("2c-maw"){{
            aiController = HugAI::new;
            constructor = CrawlUnit::create;

            speed = 0.9f;
            omniMovement = true;
            rotateSpeed = 2.6f;
            health = 680;
            hitSize = 17;
            armor = 5;
            outlineColor = Color.valueOf("#3c3835");
            faceTarget = true;
            targetAir = false;
            crushDamage = 0.8f;

            segments = 3;
            drawBody = false;
            segmentScl = 3;
            segmentPhase = 5;
            segmentMag = 0.5f;

            weapons.addAll(
                new Weapon("psammos-maw-gun"){{
                    x = 0;
                    y = 7;
                    reload = 50;
                    shootSound = Sounds.cannon;
                    mirror = false;
                    bullet = new ArtilleryBulletType(){{
                        damage = 30;
                        width = 16;
                        height = 16;
                        speed = 1;
                        hitColor = Color.valueOf("#ffffff");
                        backColor = Color.valueOf("#ffffff");
                        frontColor = Color.valueOf("#ffffff");
                        trailColor = Color.valueOf("#cccccc");
                        lifetime = 100;
                        lightning = 6;
                        lightningLength = 8;
                        lightningDamage = 24;
                        lightningColor = Color.valueOf("#ffffff");
                    }};
                }}
            );
        }};

        tip = new UnitType("3a-tip"){{
            researchCostMultiplier = 0;
            constructor = LegsUnit::create;

            speed = 0.75f;
            drag = 0.2f;
            hitSize = 9;
            rotateSpeed = 3;
            health = 180;
            armor = 0;
            outlineColor = Color.valueOf("#3c3835");
            faceTarget = false;
            targetAir = true;

            hovering = false;

            legStraightness = 0.3f;
            stepShake = 0;
            legCount = 4;
            legLength = 8;
            lockLegBase = true;
            legContinuousMove = true;
            legExtension = -1;
            legBaseOffset = 3;
            legMaxLength = 1.1f;
            legMinLength = 0.2f;
            legLengthScl = 0.96f;
            legForwardScl = 1.1f;
            legGroupSize = 2;
            legMoveSpace = 1;

            shadowElevation = 0.1f;

            weapons.addAll(
                new Weapon("psammos-tip-gun"){{
                    x = 0;
                    y = -4;
                    reload = 24;
                    shootSound = Sounds.shootAlt;
                    mirror = false;
                    rotate = true;
                    bullet = new BasicBulletType(){{
                        speed = 3.5f;
                        damage = 10;
                        width = 8;
                        height = 8;
                        trailWidth = 3;
                        trailLength = 4;
                        lifetime = 60;
                        shootEffect = Fx.shootSmall;
                        smokeEffect = Fx.shootSmallSmoke;
                    }};
                }}
            );
        }};

        spike = new UnitType("3b-spike"){{
            constructor = LegsUnit::create;

            speed = 0.7f;
            drag = 0.2f;
            hitSize = 12;
            rotateSpeed = 3;
            health = 500;
            armor = 4;
            outlineColor = Color.valueOf("#3c3835");
            faceTarget = false;
            targetAir = true;

            hovering = false;
            legPhysicsLayer = true;

            legCount = 4;
            legLength = 14;
            lockLegBase = true;
            legContinuousMove = true;
            legExtension = -3f;
            legBaseOffset = 5f;
            legMaxLength = 1.1f;
            legMinLength = 0.2f;
            legLengthScl = 0.95f;
            legForwardScl = 0.7f;

            shadowElevation = 0.2f;

            weapons.addAll(
                new Weapon("psammos-spike-gun"){{
                    x = 6;
                    y = -4;
                    reload = 18;
                    shootSound = Sounds.artillery;
                    mirror = true;
                    rotate = true;
                    bullet = new BasicBulletType(){{
                        speed = 4;
                        damage = 20;
                        width = 3;
                        height = 8;
                        trailWidth = 1;
                        trailLength = 6;
                        lifetime = 60;
                        homingRange = 45;
                        homingPower = 0.05f;
                        shootEffect = Fx.shootSmall;
                        smokeEffect = Fx.shootSmallSmoke;
                    }};
                }}
            );
        }};

        spear = new UnitType("3c-spear"){{
            constructor = LegsUnit::create;

            speed = 0.4f;
            drag = 0.1f;
            hitSize = 16;
            rotateSpeed = 2;
            health = 700;
            armor = 5;
            outlineColor = Color.valueOf("#3c3835");
            faceTarget = false;
            targetAir = true;

            hovering = false;
            legPhysicsLayer = true;

            legCount = 4;
            legLength = 18;
            legGroupSize = 2;
            lockLegBase = true;
            legContinuousMove = true;
            legExtension = -3;
            legBaseOffset = 7;
            legMaxLength = 1.1f;
            legMinLength = 0.2f;
            legLengthScl = 0.95f;
            legForwardScl = 0.9f;
            legMoveSpace = 1;

            shadowElevation = 0.4f;

            weapons.addAll(
                new Weapon("psammos-spear-gun"){{
                    x = -8;
                    y = 0;
                    reload = 8;
                    shootSound = Sounds.shoot;
                    mirror = true;
                    rotate = true;
                    bullet = new BasicBulletType(){{
                        speed = 4;
                        damage = 18;
                        width = 6;
                        height = 6;
                        trailWidth = 1.5f;
                        trailLength = 3;
                        lifetime = 70;
                        shootEffect = Fx.shootSmall;
                        smokeEffect = Fx.shootSmallSmoke;
                    }};
                }},
                new Weapon("psammos-spear-cannon"){{
                    x = 0;
                    y = -6;
                    reload = 80;
                    shootSound = Sounds.cannon;
                    mirror = false;
                    rotate = true;
                    bullet = new ArtilleryBulletType(){{
                        speed = 2;
                        damage = 20;
                        width = 16;
                        height = 16;
                        lifetime = 100;
                        splashDamageRadius = 35;
                        splashDamage = 20;
                        shootEffect = Fx.shootSmall;
                        smokeEffect = Fx.shootSmallSmoke;
                    }};
                }}
            );
        }};

        sine = new UnitType("4a-sine"){{
            constructor = UnitEntity::create;
            researchCostMultiplier = 0;

            lowAltitude = true;
            speed = 2.2f;
            rotateSpeed = 12;
            accel = 0.1f;
            drag = 0.04f;
            flying = true;
            health = 275;
            hitSize = 8;
            armor = 2;
            outlineColor = Color.valueOf("#3c3835");
            engineOffset = 5;
            faceTarget = true;
            mineTier = 2;
            mineSpeed = 3;

            weapons.addAll(
                new Weapon("psammos-sine-gun"){{
                    x = 3;
                    y = -2;
                    reload = 18;
                    shootSound = Sounds.lasershoot;
                    ejectEffect = Fx.none;
                    bullet = new LaserBoltBulletType(){{
                        speed = 3;
                        damage = 14;
                        lifetime = 20;
                        healPercent = 4;
                        collidesTeam = true;
                        backColor = Color.valueOf("#84f491");
                        frontColor = Color.valueOf("#ffffff");
                        weaveScale = 1;
                        weaveMag = 8;
                    }};
                }}
            );
        }};

        helix = new UnitType("4b-helix"){{
            constructor = UnitEntity::create;

            lowAltitude = true;
            speed = 2.3f;
            rotateSpeed = 10;
            accel = 0.1f;
            drag = 0.04f;
            flying = true;
            health = 550;
            hitSize = 12;
            armor = 4;
            outlineColor = Color.valueOf("#3c3835");
            engineOffset = 7;
            faceTarget = true;
            mineTier = 2;
            mineSpeed = 5;

            abilities.addAll(
                new RepairFieldAbility(25, 200, 80)
            );

            weapons.addAll(
                new Weapon("psammos-helix-gun"){{
                    x = 0;
                    y = 3;
                    reload = 24;
                    shootSound = Sounds.lasershoot;
                    ejectEffect = Fx.none;
                    shoot = new ShootHelix(){{
                        scl = 2;
                        mag = 3;
                    }};
                    bullet = new BasicBulletType(){{
                        speed = 5;
                        damage = 24;
                        lifetime = 20;
                        healPercent = 6;
                        collidesTeam = true;
                        width = 8;
                        height = 8;
                        trailWidth = 2;
                        trailLength = 8;
                        backColor = Color.valueOf("#84f491");
                        frontColor = Color.valueOf("#ffffff");
                        trailColor = Color.valueOf("#84f491");
                    }};
                }}
            );
        }};

        trisect = new UnitType("4c-trisect"){{
            constructor = UnitEntity::create;

            lowAltitude = false;
            speed = 2.1f;
            rotateSpeed = 9;
            accel = 0.2f;
            drag = 0.04f;
            flying = true;
            health = 750;
            hitSize = 17;
            armor = 4;
            outlineColor = Color.valueOf("#3c3835");
            faceTarget = true;
            mineTier = 3;
            mineSpeed = 9;
            buildSpeed = 0.5f;
            buildBeamOffset = 6;

            engines.addAll(
                new UnitEngine(0, -11, 3, -90),
                new UnitEngine(5, -8, 2.2f, -90),
                new UnitEngine(-5, -8, 2.2f, -90)
            );

            abilities.addAll(
                new RepairFieldAbility(35, 170, 110)
            );

            weapons.addAll(
                new Weapon("psammos-trisect-weapon"){{
                    x = -8;
                    y = 1;
                    reload = 38;
                    layerOffset = -0.01f;
                    shootSound = Sounds.malignShoot;
                    ejectEffect = Fx.none;
                    bullet = new BasicBulletType(){{
                        speed = 3;
                        damage = 28;
                        lifetime = 38;
                        healPercent = 8;
                        collidesTeam = true;
                        width = 10;
                        height = 10;
                        trailWidth = 3;
                        trailLength = 12;
                        backColor = Color.valueOf("#84f491");
                        frontColor = Color.valueOf("#ffffff");
                        trailColor = Color.valueOf("#84f491");
                        fragBullets = 3;
                        fragSpread = 30;
                        fragRandomSpread = 0;
                        fragVelocityMin = 1;
                        fragVelocityMax = 1;
                        fragBullet = new BasicBulletType(){{
                            speed = 4;
                            damage = 10;
                            lifetime = 15;
                            healPercent = 1;
                            collidesTeam = true;
                            width = 6;
                            height = 6;
                            trailWidth = 2;
                            trailLength = 6;
                            backColor = Color.valueOf("#84f491");
                            frontColor = Color.valueOf("#ffffff");
                            trailColor = Color.valueOf("#84f491");
                        }};
                    }};
                }},
                new Weapon("psammos-trisect-laser"){{
                    x = 0;
                    y = 5;
                    reload = 110;
                    shootSound = Sounds.laser;
                    mirror = false;
                    rotate = false;
                    bullet = new LaserBulletType(){{
                        damage = 40;
                        colors = new Color[]{
                            Color.valueOf("#84f491"),
                            Color.valueOf("#ffffff")
                        };
                        hitEffect = Fx.hitLancer;
                        hitSize = 5;
                        lifetime = 18;
                        drawSize = 500;
                        collidesAir = true;
                        healPercent = 10;
                        collidesTeam = true;
                        length = 120;
                        width = 16;
                        pierceCap = 1;
                    }};
                }}
            );
        }};

        sciur = new UnitType("5a-sciur"){{
            constructor = ElevationMoveUnit::create;
            researchCostMultiplier = 0;

            speed = 2.5f;
            drag = 0.1f;
            hitSize = 9;
            rotateSpeed = 8;
            health = 180;
            armor = 0;
            outlineColor = Color.valueOf("#3c3835");
            faceTarget = true;
            targetAir = true;

            hovering = true;

            shadowElevation = 0.1f;

            parts.addAll(
                new HoverPart(){{
                    x = 0;
                    y = -5;
                    mirror = false;
                    radius = 6;
                    phase = 90;
                    stroke = 2;
                    layerOffset = -0.001f;
                    color = Color.valueOf("#c3aedd");
                }},
                new HoverPart(){{
                    x = 3.8f;
                    y = 1.4f;
                    mirror = true;
                    radius = 4;
                    phase = 90;
                    stroke = 2;
                    layerOffset = -0.001f;
                    color = Color.valueOf("#c3aedd");
                }}
            );

            weapons.addAll(
                new Weapon("psammos-sciur-missile"){{
                    x = 0;
                    y = -3;
                    reload = 28;
                    shootSound = Sounds.missile;
                    inaccuracy = 2;
                    velocityRnd = 0.1f;
                    mirror = false;
                    rotate = false;
                    bullet = new MissileBulletType(){{
                        speed = 2;
                        damage = 7;
                        width = 8;
                        height = 8;
                        shrinkY = 0;
                        drag = -0.003f;
                        homingRange = 60;
                        keepVelocity = false;
                        splashDamageRadius = 18;
                        splashDamage = 9;
                        lifetime = 40;
                        trailColor = Color.valueOf("#c3aedd");
                        backColor = Color.valueOf("#c3aedd");
                        frontColor = Color.valueOf("#e8def4");
                        hitEffect = Fx.blastExplosion;
                        despawnEffect = Fx.blastExplosion;
                        weaveScale = 5;
                        weaveMag = 0.8f;
                    }};
                }},
                new TrailWeapon(4.5f, -4.5f, true, 1, 10, Color.valueOf("#c3aedd"))
            );
        }};

        glirid = new UnitType("5b-glirid"){{
            constructor = ElevationMoveUnit::create;

            speed = 2.8f;
            drag = 0.1f;
            hitSize = 12;
            rotateSpeed = 9;
            health = 485;
            armor = 2;
            outlineColor = Color.valueOf("#3c3835");
            faceTarget = false;
            targetAir = true;

            hovering = true;

            shadowElevation = 0.15f;

            parts.addAll(
                new HoverPart(){{
                    x = 5f;
                    y = 3.2f;
                    mirror = true;
                    radius = 5;
                    phase = 90;
                    stroke = 2;
                    layerOffset = -0.001f;
                    color = Color.valueOf("#c3aedd");
                }},
                new HoverPart(){{
                    x = 5f;
                    y = -5.4f;
                    mirror = true;
                    radius = 6;
                    phase = 90;
                    stroke = 2;
                    layerOffset = -0.001f;
                    color = Color.valueOf("#c3aedd");
                }}
            );

            weapons.addAll(
                new Weapon("psammos-glirid-laser"){{
                    x = -4;
                    y = 0;
                    reload = 28;
                    shootSound = Sounds.laser;
                    mirror = true;
                    rotate = true;
                    bullet = new LaserBulletType(){{
                        damage = 22;
                        colors = new Color[]{
                            Color.valueOf("#c3aedd"),
                            Color.valueOf("#ffffff")
                        };
                        hitEffect = Fx.hitLancer;
                        hitSize = 3;
                        lifetime = 16;
                        drawSize = 300;
                        collidesAir = true;
                        length = 80;
                        width = 6;
                        pierceCap = 2;
                    }};
                }},
                new TrailWeapon(0f, -4f, false, 2f, 15, Color.valueOf("#c3aedd"))
            );
        }};

        exilis = new UnitType("exilis"){{
            constructor = ElevationMoveUnit::create;

            speed = 3.2f;
            drag = 0.1f;
            hitSize = 16;
            rotateSpeed = 4;
            health = 690;
            armor = 3;
            outlineColor = Color.valueOf("#3c3835");
            faceTarget = false;
            targetAir = true;

            hovering = true;

            shadowElevation = 0.18f;

            parts.addAll(
                    new HoverPart(){{
                        x = 6f;
                        y = 3f;
                        mirror = true;
                        radius = 6;
                        phase = 90;
                        stroke = 2;
                        layerOffset = -0.001f;
                        color = Color.valueOf("#c3aedd");
                        sides = 6;
                    }}
            );

            weapons.addAll(
                    new Weapon("psammos-exilis-cannon"){{
                        x = -5;
                        y = -2;
                        reload = 50;
                        shootY = 3f;
                        shootSound = Sounds.pulseBlast;
                        mirror = true;
                        rotate = true;
                        bullet = new LaserBulletType(){{
                            damage = 55;
                            colors = new Color[]{
                                    Color.valueOf("#c3aedd"),
                                    Color.valueOf("#ffffff")
                            };
                            hitEffect = Fx.hitLancer;
                            hitSize = 4;
                            lifetime = 20;
                            drawSize = 800;
                            collidesAir = true;
                            length = 110;
                            width = 10;
                            pierceCap = 3;
                            sideAngle = 25f;
                            sideWidth = 1.5f;
                            sideLength = 70f;
                        }};
                    }},
                    new TrailWeapon(0f, -5.8f, false, 2f, 15, Color.valueOf("#c3aedd")),
                    new TrailWeapon(5.5f, -5.3f, true, 1.2f, 12, Color.valueOf("#c3aedd"))
            );
        }};
    }
}
