package psammos.ai;

import arc.util.Tmp;
import mindustry.ai.ControlPathfinder;
import mindustry.ai.UnitStance;
import mindustry.ai.types.CommandAI;
import mindustry.entities.Sized;

import static mindustry.Vars.*;

public class HugCommandAI extends CommandAI {

    @Override
    public void circleAttack(float circleLength) {
        if (target == null) return;

        vec.set(target).sub(unit);

        if (vec.len() < unit.range()) {
            float attackDst = (unit.hitSize + (target instanceof Sized s ? s.hitSize() : 1f)) * 0.5f;
            if (unit.within(target, attackDst)) {
                vec.rotate(90f);
            }
        } else if (!hasStance(UnitStance.ram)){
            ControlPathfinder.PathfindResult result = controlPath.getPathPosition(unit, Tmp.v1.set(target));
            if(result.move) vec.set(result.dest).sub(unit);
        }
        
        vec.setLength(prefSpeed());
        unit.movePref(vec);
    }

    @Override
    public void updateTargeting() {
        updateWeapons();
    }
}
