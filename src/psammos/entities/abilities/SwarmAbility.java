package psammos.entities.abilities;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.ui.layout.Table;
import arc.struct.*;
import arc.util.Strings;
import mindustry.Vars;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import psammos.content.PsammosStatusEffects;

/** Nearby units with this ability can form a "swarm".
 * All units in a swarm will try to move at the same speed.
 * Any enemy unit that enters the swarm gets a negative status effect */
public class SwarmAbility extends Ability {

    /** The units that this unit can make a swarm with.
     * They all must have SwarmAbility as well. */
    public Seq<UnitType> swarmWhitelist = new Seq<>();
    /** The radius at which units can join this swarm */
    public float range;
    /** Status effect which is applied to enemy units inside the swarm */
    public StatusEffect status;
    /** Duration of the status effect which is applied to enemy units inside the swarm */
    public float statusDuration;

    private boolean shouldUpdate = true;
    private Seq<Unit> swarm;
    private float warmup;

    public SwarmAbility(){
        super();
        this.range = 24f;
        this.status = PsammosStatusEffects.infested;
        this.statusDuration = 5*60;
    }

    public SwarmAbility(float range){
        this();
        this.range = range;
    }

    @Override
    public void addStats(Table t){
        super.addStats(t);
        t.add(Core.bundle.format("bullet.range", Strings.autoFixed(range / Vars.tilesize, 2)));
        t.row();
        t.add((status.hasEmoji() ? status.emoji() : "") + "[stat]" + status.localizedName + (status.reactive ? "" : "[lightgray] ~ [stat]" + ((int)(statusDuration / 60f)) + "[lightgray] " + Core.bundle.get("unit.seconds")));
    }

    @Override
    public void update(Unit unit){
        if(swarm == null) swarm = new Seq<>();

        if(shouldUpdate){
            updateSwarm(unit, swarm);
        }

        warmup = Mathf.lerp(warmup, swarm.size > 1 ? 1 : 0, 0.08f);

        // Sets the speeds to the average between all units
        float avgSpeed = 0;
        for (Unit u : swarm) {
            avgSpeed += u.type.speed;
        }
        avgSpeed /= swarm.size;
        for (Unit u : swarm) {
            u.speedMultiplier(avgSpeed / u.type.speed);
        }

        // Applies status effect to enemy units
        if(swarm.size > 1){
            Units.nearbyEnemies(unit.team, unit.x, unit.y, range, other -> {
                other.apply(status, statusDuration);
            });
        }

        shouldUpdate = true;
        swarm = new Seq<>();
    }

    public void updateSwarm(Unit unit, Seq<Unit> unitSeq){
        shouldUpdate = false; // Prevents it from updating multiple times per tick
        swarm = unitSeq;
        unitSeq.add(unit);
        Units.nearby(unit.team, unit.x, unit.y, range * 2, other -> {
            if(!other.dead() && other != unit && swarmWhitelist.contains(other.type)){
                SwarmAbility otherAbility = findSwarmAbility(other);
                if(otherAbility != null && !unitSeq.contains(other)){
                    otherAbility.updateSwarm(other, unitSeq);
                }
            }
        });
    }

    public SwarmAbility findSwarmAbility(Unit unit){
        for(int i = 0; i < unit.abilities.length; i++) {
            if (unit.abilities[i] instanceof SwarmAbility) return (SwarmAbility) unit.abilities[i];
        }
        return null;
    }

    @Override
    public void draw(Unit unit){
        if(warmup <= 0.08f) return;

        Draw.color(Color.white);
        Draw.z(Layer.shields);
        if(Vars.renderer.animateShields){
            Fill.circle(unit.x, unit.y, range * warmup);
        }else{
            Draw.alpha(0.09f);
            Fill.circle(unit.x, unit.y, range * warmup);

            Draw.alpha(1f);
            Lines.stroke(1.5f);
            Lines.circle(unit.x, unit.y, range * warmup);
        }

    }
}
