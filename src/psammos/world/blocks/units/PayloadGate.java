package psammos.world.blocks.units;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.units.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class PayloadGate extends UnitBlock {
    /** How often the gate captures units */
    public float reload = 40f;
    /** Arrow glow interpolation */
    public Interp interp = Interp.pow5;
    /** The maximum size of units captured */
    public float payloadLimit = 3f;
    /** The effect that plays when a unit is captured */
    public Effect effect = new Effect(30, e -> {
        Lines.stroke(2f * e.fout());
        Draw.color(Pal.accent);
        Lines.poly(e.x, e.y, 4, 5f + e.fout() * 12f);
    });

    public TextureRegion arrowRegion;

    public PayloadGate(String name){
        super(name);
        solid = false;
        size = 5;
        outputsPayload = true;
    }

    @Override
    public void load(){
        super.load();
        arrowRegion = Core.atlas.find(name+"-arrow");
        topRegion = Core.atlas.find(name+"-top");
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.payloadCapacity, StatValues.squared(payloadLimit, StatUnit.blocksSquared));
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        Draw.rect(region, plan.drawx(), plan.drawy());
        Draw.rect(outRegion, plan.drawx(), plan.drawy(), plan.rotation * 90);
        Draw.rect(arrowRegion, plan.drawx(), plan.drawy(), plan.rotation * 90);
        Draw.rect(topRegion, plan.drawx(), plan.drawy());
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region, outRegion, arrowRegion, topRegion};
    }

    public class PayloadGateBuild extends UnitBuild {
        public float progress = 0f;
        public Unit target = null;

        @Override
        public boolean acceptPayload(Building source, Payload payload){
            return false;
        }

        @Override
        public void updateTile(){
            if(!enabled) return;
            if(payload == null && fract() >= 0.95f) {
                float wh = size * 0.5f * tilesize;
                Units.nearby(team, x - wh/2, y - wh/2, wh, wh, u -> {
                    if(!u.spawnedByCore && controllable(u) && !u.dead && u.hitSize / tilesize <= payloadLimit){
                        target = u;
                    }
                });
                if(target != null){
                    if(target.isCommandable()) target.command().targetPos = null;

                    payload = new UnitPayload(target);
                    payVector.setZero();
                    target.remove();
                    target = null;

                    effect.at(x, y);
                }
            }
            moveOutPayload();

            progress = Time.time % reload;
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            Draw.rect(outRegion, x, y, rotdeg());

            float dst = 0.8f;

            Draw.mixcol(team.color, Math.max((dst - (Math.abs(fract() - 0.5f) * 2)) / dst, 0));
            Draw.rect(arrowRegion, x, y, rotdeg());
            Draw.reset();

            Draw.z(Layer.blockOver);

            payRotation = rotdeg();
            drawPayload();

            Draw.z(Layer.blockOver + 0.1f);

            Draw.rect(topRegion, x, y);
        }

        public float fract(){
            return interp.apply(progress / reload);
        }

        private boolean controllable(Unit unit){
            return unit.type.playerControllable || unit.type.logicControllable;
        }

    }
}
