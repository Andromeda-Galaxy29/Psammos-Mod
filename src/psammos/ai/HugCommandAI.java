package psammos.ai;

import mindustry.ai.types.CommandAI;
import mindustry.entities.Sized;

public class HugCommandAI extends CommandAI {
    @Override
    public void circleAttack(float circleLength) {
        if(unit.within(target, (unit.hitSize + (target instanceof Sized s ? s.hitSize() : 1f)) * 0.5f)){
            //circle target
            unit.movePref(vec.set(target).sub(unit).rotate(90f).setLength(unit.speed()));
        }else{
            //move toward target in a straight line
            unit.movePref(vec.set(target).sub(unit).limit(unit.speed()));
        }
    }
}
