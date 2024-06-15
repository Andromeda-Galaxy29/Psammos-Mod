package psammos.blocks;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Point2;
import arc.struct.Seq;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.Building;
import mindustry.graphics.*;
import mindustry.input.Placement;
import mindustry.ui.*;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.power.*;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.*;

public class WindTurbine extends PowerGenerator {
    public float warmupSpeed = 0.05f;
    public float effectChance = 0.01f;
    public Effect generateEffect = Fx.none;
    public float generateEffectRange = 3f;
    public float rotateSpeed = 6f;
    public TextureRegion topRegion;
    public TextureRegion rotatorRegion;
    public int range = 3;

    public WindTurbine(String name){
        super(name);
    }

    @Override
    public void init(){
        emitLight = false;
        super.init();
    }

    @Override
    public void load(){
        super.load();
        topRegion = Core.atlas.find(name+"-top");
        rotatorRegion = Core.atlas.find(name+"-rotator");
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.range, range, StatUnit.blocks);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        x *= tilesize;
        y *= tilesize;
        x += offset;
        y += offset;

        Drawf.dashSquare(Pal.techBlue, x, y, range * tilesize);
    }

    public class WindTurbineBuild extends GeneratorBuild{
        public float warmup, totalTime, efficiencyMultiplier = 1f;

        @Override
        public void updateEfficiencyMultiplier(){
            efficiencyMultiplier = 1f;

            int bx = (int) Math.floor(x / tilesize);
            int by = (int) Math.floor(y / tilesize);
            int x1 = (int) (bx - Math.floor(range / 2) + (range%2==0 ? 1 : 0));
            int x2 = (int) (bx + Math.floor(range / 2));
            int y1 = (int) (by - Math.floor(range / 2) + (range%2==0 ? 1 : 0));
            int y2 = (int) (by + Math.floor(range / 2));
            for (int ix = x1; ix <= x2; ix++) {
                for (int iy = y1; iy <= y2; iy++) {
                    Tile tile = world.tile(ix, iy);
                    if (tile == null) continue;

                    Building build = tile.build;
                    if(build != this && tile.block().solid){
                        efficiencyMultiplier -= 1f / (float)(range*range - size*size);
                    }
                }
            }
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y, 0);
            Drawf.spinSprite(rotatorRegion, x, y, totalProgress() * rotateSpeed * productionEfficiency + rotation);
            Draw.rect(topRegion, x, y, 0);
        }

        @Override
        public void drawSelect(){
            super.drawSelect();

            Drawf.dashSquare(Pal.techBlue, x, y, range * tilesize);
        }

        @Override
        public void updateTile(){
            boolean valid = efficiency > 0;

            warmup = Mathf.lerpDelta(warmup, valid ? 1f : 0f, warmupSpeed);

            productionEfficiency = efficiency * efficiencyMultiplier;
            totalTime += warmup * Time.delta;

            //randomly produce the effect
            if(valid && Mathf.chanceDelta(effectChance)){
                generateEffect.at(x + Mathf.range(generateEffectRange), y + Mathf.range(generateEffectRange));
            }

            generateTime = 1f;
        }

        @Override
        public float warmup(){
            return warmup;
        }

        @Override
        public float totalProgress(){
            return totalTime;
        }
    }
}
