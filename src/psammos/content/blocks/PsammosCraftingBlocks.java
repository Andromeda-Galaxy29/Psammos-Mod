package psammos.content.blocks;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.Seq;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.effect.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.production.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import psammos.content.*;
import psammos.type.RadiationStack;
import psammos.type.RadiationType;
import psammos.world.blocks.crafting.RadiationCrafter;
import psammos.world.blocks.heat.RadiationHeatProducer;
import psammos.world.blocks.radiation.HeatRadiationProducer;
import psammos.world.draw.*;

import static mindustry.type.ItemStack.with;

public class PsammosCraftingBlocks {
    public static Block
            //Factories
            sieve, filter, siliconSynthesizer, siliconSynthesisChamber,
            centrifuge, purifier, thermolysisChamber, refinery,
            blastManufacturer, oilDistillationTower, atmosphericSeparator,
            //Heat
            heatExchanger, ozoneHeater, peatHeater, ammoniaHeater, heatRadiator, infraredHeater, heatPump, heatPumpRouter,
            //More factories
            aerogelPressurizer, steamReformer, ammoniaCompressor, memoryAlloyCrucible, splitterCell,
            //Obliterator
            obliterator;

    public static void load() {
        sieve = new GenericCrafter("1a-sieve"){{
            requirements(Category.crafting, with(PsammosItems.osmium, 35, PsammosItems.silver, 15, Items.sand, 10));

            size = 2;
            squareSprite = false;
            craftEffect = Fx.pulverizeSmall;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawRegion("-rotator", 3, false),
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
                    new DrawRegion("-rotator", 4, false),
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
            craftEffect = new Effect(100f, e -> {
                Vec2 v = new Vec2();

                Draw.color(e.color, Pal.vent2, e.fin());

                Draw.alpha(e.fslope() * 0.78f);

                float length = 1f + e.finpow() * 5f;
                Mathf.rand.setSeed(e.id);
                for(int i = 0; i < Mathf.rand.random(3, 5); i++){
                    v.trns(Mathf.rand.random(360f), Mathf.rand.random(length));
                    Fill.circle(e.x + v.x, e.y + v.y, Mathf.rand.random(1.2f, 3.5f) + e.fslope() * 1.1f);
                }
            }).layer(Layer.darkness - 1);

            drawer = new DrawMulti(
                    new DrawDefault(),
                    new DrawGlowRegion(){{color = Color.valueOf("#ffef99");}}
            );

            outputItem = new ItemStack(Items.silicon, 1);
            craftTime = 40;

            consumeItem(PsammosItems.quartz, 2);
            consumePower(0.5f);
        }};

        siliconSynthesisChamber = new HeatCrafter("silicon-synthesis-chamber"){{
            requirements(Category.crafting, with(PsammosItems.osmium, 40, Items.silicon, 30, PsammosItems.memoryAlloy, 20, PsammosItems.aerogel, 10, PsammosItems.refinedMetal, 30));

            size = 4;
            squareSprite = false;
            craftEffect = new RadialEffect(Fx.surgeCruciSmoke, 4, 90f, 8f);

            drawer = new DrawMulti(
                    new DrawDefault(),
                    new DrawGlowRegion(){{color = Color.valueOf("#ffef99");}},
                    new DrawHeatInput()
            );

            outputItem = new ItemStack(Items.silicon, 2);
            outputLiquid = new LiquidStack(Liquids.water, 3/60f);
            craftTime = 30;

            heatRequirement = 6f;
            consumeItem(PsammosItems.quartz, 3);
            consumeLiquid(PsammosLiquids.methane, 6/60f);
            consumePower(2.5f);
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
                    new DrawRegion("-rotator", 8, true),
                    new DrawDefault()
            );

            outputItem = new ItemStack(Items.sand, 2);
            outputLiquid = new LiquidStack(Liquids.water, 6 / 60f);
            craftTime = 30;

            consumeLiquid(PsammosLiquids.quicksand, 8 / 60f);
            consumePower(2f);
        }};

        purifier = new GenericCrafter("purifier"){{
            requirements(Category.crafting, with(PsammosItems.refinedMetal, 80, PsammosItems.quartz, 80, Items.silicon, 60, PsammosItems.aerogel, 35));

            size = 3;
            squareSprite = false;
            hasLiquids = true;
            liquidCapacity = 30;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(PsammosLiquids.quicksand, 3),
                    new DrawRegion("-rotator", 12, true),
                    new DrawDefault()
            );

            outputItem = new ItemStack(Items.sand, 6);
            outputLiquid = new LiquidStack(Liquids.water, 20 / 60f);
            craftTime = 60;

            consumeLiquid(PsammosLiquids.quicksand, 24 / 60f);
            consumeLiquid(PsammosLiquids.ammonia, 1 / 60f);
            consumePower(4f);
        }};

        thermolysisChamber = new AttributeCrafter("4a-thermolysis-chamber"){{
            requirements(Category.crafting, with(PsammosItems.osmium, 20, PsammosItems.silver, 40, PsammosItems.quartz, 60, Items.silicon, 10));

            size = 3;
            squareSprite = false;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawCrucibleFlame(),
                    new DrawDefault(),
                    new DrawGlowRegion(){{
                        color = Pal.slagOrange;
                        alpha = 0.6f;
                    }},
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
            consumeLiquid(Liquids.ozone, 2 / 60f);
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
            squareSprite = false;
            hasLiquids = true;
            liquidCapacity = 15;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(PsammosLiquids.coldWater, 1),
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
                    new DrawLiquidTile(Liquids.ozone, 1),
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

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawItemPile(PsammosItems.peat, 8, 20),
                    new DrawSoftParticles(){{
                        alpha = 0.2f;
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

            consumeLiquid(PsammosLiquids.ammonia, 0.5f / 60f);
        }};

        heatRadiator = new HeatRadiationProducer("heat-radiator"){{
            requirements(Category.crafting, with(PsammosItems.aerogel, 40, Items.silicon, 20, PsammosItems.refinedMetal, 20, PsammosItems.desertGlassShard, 20));

            size = 3;
            squareSprite = false;

            heatRequirement = 100;
            maxEfficiency = 1f;

            drawer = new DrawMulti(
                    new DrawDefault(),
                    new DrawHeatInput(),
                    new DrawDirectionalRegion(),
                    new DrawGlowRegion(){{
                        color = new Color(1f, 0.22f, 0.22f, 0.8f);
                        alpha = 0.8f;
                    }},
                    new DrawRadiationBeams()
            );
            rotate = true;
            rotateDraw = false;

            radOutputAmount = 95;
            radOutputType = RadiationType.IR;
            craftTime = 60;
        }};
        
        infraredHeater = new RadiationHeatProducer("infrared-heater"){{
            requirements(Category.crafting, with(PsammosItems.aerogel, 20, Items.silicon, 40, PsammosItems.refinedMetal, 20, PsammosItems.desertGlassShard, 20));

            size = 3;
            squareSprite = false;

            radiationRequirements = Seq.with(new RadiationStack(RadiationType.IR, 100f));
            maxEfficiency = 1f;

            drawer = new DrawMulti(
                    new DrawDefault(),
                    new DrawHeatOutput(),
                    new DrawGlowRegion(){{
                        color = new Color(1f, 0.22f, 0.22f, 0.8f);
                        alpha = 0.4f;
                    }}
            );
            rotate = true;
            rotateDraw = false;

            heatOutput = 95;
            craftTime = 60;
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
                        angleOffset = 45;
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
                    new DrawRegion("-bottom"),
                    new DrawArcSmeltReverse(){{
                        flameColor = midColor = PsammosItems.memoryAlloy.color;
                    }},
                    new DrawDefault(),
                    new DrawProgressGlowRegion(){{
                        color = PsammosItems.memoryAlloy.color.cpy();
                        alpha = 0.6f;
                    }}
            );

            craftEffect = new Effect(60f, e -> {
                Vec2 v = new Vec2();
                Draw.color(PsammosItems.memoryAlloy.color);
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
        
        splitterCell = new RadiationCrafter("splitter-cell"){{
            requirements(Category.crafting, with(PsammosItems.desertGlassShard, 20, Items.silicon, 60, PsammosItems.quartz, 20, PsammosItems.refinedMetal, 40));
            size = 3;
            craftTime = 10f;
            rotate = true;
            invertFlip = true;
            group = BlockGroup.liquids;
            itemCapacity = 0;
            squareSprite = false;

            liquidCapacity = 50f;

            consumeLiquid(Liquids.water, 12f / 60f);
            consumePower(2f);
            radiationRequirements = Seq.with(new RadiationStack(RadiationType.UV, 4f));
            maxEfficiency = 1f;

            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawLiquidTile(Liquids.water, 2f),
                new DrawBubbles(Color.valueOf("ffddaa")){{
                    sides = 10;
                    recurrence = 3f;
                    spread = 6;
                    radius = 1.5f;
                    amount = 20;
                }},
                new DrawRegion(),
                new DrawLiquidOutputs(),
                new DrawGlowRegion(){{
                    alpha = 0.7f;
                    color = Color.valueOf("ffddaa");
                    glowIntensity = 0.3f;
                    glowScale = 6f;
                }}
            );

            regionRotated1 = 3;
            outputLiquids = LiquidStack.with(Liquids.ozone, 8f / 60, Liquids.hydrogen, 4f / 60);
            liquidOutputDirections = new int[]{1, 3};
        }};

        obliterator = new Incinerator("Zz-obliterator"){{
            requirements(Category.crafting, with(PsammosItems.refinedMetal, 16, Items.blastCompound, 10));

            size = 1;
            hasPower = false;
        }};
    }
}
