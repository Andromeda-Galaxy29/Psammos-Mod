package psammos.content.blocks;

import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
import mindustry.content.*;
import mindustry.entities.effect.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.power.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import psammos.content.*;
import psammos.type.*;
import psammos.world.blocks.legacy.LegacyPowerGenerator;
import psammos.world.blocks.power.*;
import psammos.world.draw.*;

import static mindustry.type.ItemStack.*;

public class PsammosPowerBlocks {
    public static Block
            //Distribution
            electricPole, electricDistributor, led,
            //Storage
            accumulator,
            //Generation
            windTurbine, piezoelectricGenerator, impulseGenerator, liquidFuelBurner, thermoelectricGenerator, heatEngine,
            photovoltaicCell,
            //Legacy
            liquidFuelBurnerOld;

    public static void load() {
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
            squareSprite = false;
            range = 6;
        }};

        piezoelectricGenerator = new ConsumeGenerator("piezoelectric-generator"){{
            requirements(Category.power, with(PsammosItems.silver, 30, PsammosItems.quartz, 20));

            size = 2;
            powerProduction = 3;
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
            squareSprite = true;
            itemDuration = 120f;
            drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
            generateEffect = Fx.blastExplosion;

            consume(new ConsumeItemExplosive(1f));
        }};

        liquidFuelBurner = new ConsumeGenerator("liquid-fuel-burner"){{
            requirements(Category.power, with(PsammosItems.osmium, 60, PsammosItems.silver, 30, PsammosItems.refinedMetal, 50, PsammosItems.quartz, 40));

            size = 3;
            powerProduction = 21;
            squareSprite = false;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(Liquids.ozone, 2),
                    new DrawPistons(){{
                        sinMag = 2f;
                        sinScl = 3f;
                        sides = 4;
                        sideOffset = Mathf.PI;
                    }},
                    new DrawRegion("-mid"),
                    new DrawLiquidTile(PsammosLiquids.fuel, 9),
                    new DrawDefault(),
                    new DrawGlowRegion(){{
                        alpha = 0.8f;
                        glowScale = 5f;
                        color = Pal.slagOrange;
                    }},
                    new DrawBurnerFlame()
            );

            outputLiquid = new LiquidStack(Liquids.water, 6 / 60f);

            consumeLiquid(PsammosLiquids.fuel, 2 / 60f);
            consumeLiquid(Liquids.ozone, 4 / 60f);
        }};

        thermoelectricGenerator = new ConsumeThermalGenerator("thermoelectric-generator"){{
            requirements(Category.power, with(PsammosItems.silver, 40, PsammosItems.refinedMetal, 40, Items.silicon, 35, PsammosItems.quartz, 40));
            powerProduction = 4f;
            generateEffect = Fx.redgeneratespark;
            effectChance = 0.1f;
            size = 3;
            squareSprite = false;
            floating = true;
            ambientSound = Sounds.loopElectricHum;
            ambientSoundVolume = 0.06f;

            drawer = new DrawMulti(
                    new DrawDefault(),
                    new DrawGlowRegion(){{
                        alpha = 0.8f;
                        glowScale = 5f;
                        color = Pal.slagOrange;
                    }}
            );

            consumeLiquid(PsammosLiquids.coldWater, 3 / 60f);
        }};

        heatEngine = new VariableReactor("heat-engine"){{
            requirements(Category.power, with(PsammosItems.silver, 60, Items.silicon, 20, PsammosItems.memoryAlloy, 40, PsammosItems.aerogel, 40, PsammosItems.refinedMetal, 60));

            size = 4;
            powerProduction = 46;
            squareSprite = false;
            effect = Fx.none;
            flashColor2 = Color.valueOf("d194f3");
            explosionRadius = 10;
            explosionDamage = 800;
            explodeEffect = new MultiEffect(Fx.bigShockwave, Fx.titanSmoke.wrap(Pal.slagOrange));
            explodeSound = Sounds.explosionReactor;
            explosionPuddles = 50;
            explosionPuddleRange = 32;
            explosionPuddleLiquid = Liquids.slag;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(PsammosLiquids.ammonia, 4),
                    new DrawPistons(){{
                        sinMag = 2f;
                        sinScl = 3f;
                        sides = 4;
                        angleOffset = 45;
                        sideOffset = Mathf.PI;
                    }},
                    new DrawRegion("-rotator", 10,  true),
                    new DrawDefault(),
                    new DrawHeatInput()
            );

            consumeLiquid(PsammosLiquids.ammonia, 4 / 60f);
            maxHeat = 16;
        }
            @Override
            public void setBars() {
                super.setBars();
                removeBar("instability");
                addBar("overheat", (entity) -> new Bar("bar.psammos-overheat", Pal.redderDust, () -> ((VariableReactorBuild)entity).instability));
            }
        };

        photovoltaicCell = new RadiationPowerGenerator("photovoltaic-cell"){{
            requirements(Category.power, with(PsammosItems.refinedMetal, 30, PsammosItems.silver, 10, Items.silicon, 20, PsammosItems.desertGlassShard, 25));

            size = 2;
            powerProduction = 2f;
            squareSprite = false;

            radiationRequirements = Seq.with(new RadiationStack(RadiationType.light, 12f));

            drawer = new DrawMulti(
                    new DrawDefault(),
                    new DrawGlowRegion(){{
                        alpha = 0.6f;
                        glowScale = 5f;
                        color = Pal.accent;
                    }}
            );
        }};

        liquidFuelBurnerOld = new LegacyPowerGenerator("3b-liquid-fuel-burner"){{
            size = 2;
        }};
    }
}
