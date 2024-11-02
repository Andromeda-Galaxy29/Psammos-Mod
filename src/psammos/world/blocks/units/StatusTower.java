package psammos.world.blocks.units;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class StatusTower extends Block {
    static final float refreshInterval = 6f;

    public float range = 80f;
    public Color baseColor = Pal.accent, glowColor = Pal.accent.cpy().a(0.5f);
    public float circleSpeed = 90f, circleStroke = 3f;
    public float polyRad = 4f, polySpinScl = 0.8f, polyStroke = 1.5f;
    public float glowMag = 0.5f, glowScl = 8f;
    public int sides = 6;
    public StatusEffect status = StatusEffects.overclock;
    public float statusDuration = 1500;
    public TextureRegion glow;

    public StatusTower(String name){
        super(name);
        update = true;
        solid = true;
    }

    @Override
    public void load(){
        super.load();
        glow = Core.atlas.find(name+"-glow");
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.range, range / tilesize, StatUnit.blocks);
        stats.add(
            new Stat("psammos-status", StatCat.function),
            (status.hasEmoji() ? status.emoji() : "") + "[stat]" + status.localizedName + (status.reactive ? "" : "[lightgray] ~ [stat]" + ((int)(statusDuration / 60f)) + "[lightgray] " + Core.bundle.get("unit.seconds"))
        );
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.placing);
    }

    public class StatusTowerBuild extends Building implements Ranged{
        public float refresh = Mathf.random(refreshInterval);
        public float warmup = 0f;
        public float totalProgress = 0f;
        public Seq<Unit> targets = new Seq<>();

        @Override
        public void updateTile(){

            if(potentialEfficiency > 0 && (refresh += Time.delta) >= refreshInterval){
                targets.clear();
                refresh = 0f;
                Units.nearby(team, x, y, range, u -> {
                    targets.add(u);
                });
            }

            if(efficiency > 0){
                for(Unit target : targets){
                    target.apply(status, statusDuration);
                }
            }

            warmup = Mathf.lerp(warmup, efficiency, 0.08f);
            totalProgress += Time.delta / circleSpeed;
        }

        @Override
        public boolean shouldConsume(){
            return targets.size > 0;
        }

        @Override
        public void draw(){
            super.draw();

            if(warmup <= 0.001f) return;

            Draw.z(Layer.effect);
            float mod = totalProgress % 1f;
            Draw.color(baseColor);

            Lines.stroke(circleStroke * (1f - mod) * warmup);
            Lines.poly(x, y, sides, range * mod, mod * 180);
            Lines.poly(x, y, sides, range * mod * 0.8f, (360f / sides / 2f) - (mod * 180));

            Lines.stroke(polyStroke);
            Lines.poly(x, y, sides, polyRad * warmup, Time.time / polySpinScl);
            Draw.reset();

            Drawf.additive(glow, glowColor, warmup * (1f - glowMag + Mathf.absin(Time.time, glowScl, glowMag)), x, y, 0f, Layer.blockAdditive);
        }

        @Override
        public float range(){
            return range;
        }

        @Override
        public float warmup(){
            return warmup;
        }

        @Override
        public void drawSelect(){
            Drawf.dashCircle(x, y, range, Pal.placing);
        }
    }
}
