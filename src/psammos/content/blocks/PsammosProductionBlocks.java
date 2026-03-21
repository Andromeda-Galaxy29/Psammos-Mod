package psammos.content.blocks;

import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.effect.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.production.*;
import mindustry.world.draw.*;
import psammos.*;
import psammos.content.*;
import psammos.world.blocks.production.*;
import psammos.world.draw.*;

import static mindustry.Vars.*;
import static mindustry.type.ItemStack.*;

public class PsammosProductionBlocks {
    public static Block
            //Drills
            osmiumDrill, detonationDrill, excavatorDrill, quarryDrill,
            //Bombs
            seismicBomb, ammoniaBomb,
            //Other
            crystalSampler;

    public static void load() {
        osmiumDrill = new RotaryBurstDrill("1a-osmium-drill"){{
            requirements(Category.production, with(PsammosItems.osmium, 15));
            alwaysUnlocked = true;

            itemCapacity = 20;
            size = 2;
            tier = 2;
            shake = 0;
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
            rotateSpeed = 8;
            drawMineItem = false;
            ambientSoundVolume = 0.05f;

            consumeLiquid(PsammosLiquids.coldWater, 0.05f).boost();
        }};

        quarryDrill = new Drill("quarry-drill"){{
            requirements(Category.production, with(PsammosItems.osmium, 80, PsammosItems.quartz, 60, PsammosItems.memoryAlloy, 30, Items.silicon, 60));

            size = 4;
            tier = 1;
            drillTime = 180;
            rotateSpeed = 12;
            drillEffectChance = 2.5f;
            drawMineItem = false;
            ambientSoundVolume = 0.1f;

            liquidBoostIntensity = 1.4f;

            consumePower(90 /60f);
            consumeLiquid(PsammosLiquids.coldWater, 0.05f).boost();
        }};

        seismicBomb = new Bomb("3a-seismic-bomb"){{
            requirements(Category.production, with(PsammosItems.silver, 10, Items.blastCompound, 20));

            size = 1;
            squareSprite = false;
            damage = 100;
            explodeTime = 5;
            range = 3;
            tier = 1;
            baseColor = Items.blastCompound.color;
            effect = new MultiEffect(
                    Fx.blastExplosion,
                    Fx.dynamicSpikes.wrap(Items.blastCompound.color, 18)
            );
            explosionSound = Sounds.explosionDull;
            shake = 2f;
            protectEnemyBlocks = true;
        }};

        ammoniaBomb = new Bomb("ammonia-bomb"){{
            requirements(Category.production, with(PsammosItems.aerogel, 20, PsammosItems.silver, 12));

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(PsammosLiquids.ammonia, 1.5f),
                    new DrawDefault(),
                    new DrawProgressGlowRegion(){{
                        color = PsammosLiquids.ammonia.color.cpy();
                        alpha = 0.6f;
                    }}
            );

            size = 2;
            squareSprite = false;
            damage = 300;
            explodeTime = 80;
            range = 8;
            tier = 2;
            baseColor = PsammosLiquids.ammonia.color;
            effect = new MultiEffect(
                    Fx.missileTrailSmoke.wrap(Color.grays(0.5f)),
                    Fx.dynamicSpikes.wrap(PsammosLiquids.ammonia.color, 32)
            );
            explosionSound = Sounds.explosionArtillery;
            shake = 10f;

            consumeLiquid(PsammosLiquids.ammonia, 1f/60f);
        }};

        crystalSampler = new MechanicalArm("crystal-sampler"){{
            requirements(Category.production, with(PsammosItems.refinedMetal, 20, PsammosItems.memoryAlloy, 20, PsammosItems.aerogel, 15));

            size = 2;
            squareSprite = false;
            baseColor = PPal.memoryAlloy;
            range = tilesize * 5;
            armSpeed = 0.03f;
            produceTime = 70f;
            outputAmount = 2;
            itemCapacity = 30;

            consumeLiquid(PsammosLiquids.ammonia, 2f/60f);
        }};
    }
}
