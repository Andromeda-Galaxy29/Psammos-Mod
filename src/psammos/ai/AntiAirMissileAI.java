package psammos.ai;

import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.ai.types.MissileAI;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;

public class AntiAirMissileAI extends MissileAI {
    @Override
    public void updateMovement(){
        unloadPayloads();

        float time = unit instanceof TimedKillc t ? t.time() : 1000000f;

        if(time >= unit.type.homingDelay && shooter != null && !shooter.dead()){
            unit.lookAt(shooter.aimX, shooter.aimY);
        }

        //move forward forever
        unit.moveAt(vec.trns(unit.rotation, unit.type.missileAccelTime <= 0f ? unit.speed() : Mathf.pow(Math.min(time / unit.type.missileAccelTime, 1f), 2f) * unit.speed()));
    }

    @Override
    public Teamc target(float x, float y, float range, boolean air, boolean ground){
        return Units.closestTarget(unit.team, x, y, range, u -> u.checkTarget(air, ground), t -> false);
    }
}
