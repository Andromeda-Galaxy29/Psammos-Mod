package psammos.ai;

import arc.util.Log;
import mindustry.ai.types.CommandAI;
import mindustry.entities.Sized;

public class HugCommandAI extends CommandAI {

    @Override
    public void circleAttack(float circleLength) {
        if (target == null) return;

        vec.set(target).sub(unit);

        float attackDst = (unit.hitSize + (target instanceof Sized s ? s.hitSize() : 1f)) * 0.5f;
        if (unit.within(target, attackDst)) {
            vec.rotate(90f);
        }

        vec.setLength(prefSpeed());
        unit.movePref(vec);
    }
}
