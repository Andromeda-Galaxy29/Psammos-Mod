package psammos.type.unit;

import arc.util.Log;
import mindustry.ai.types.*;
import mindustry.entities.units.UnitController;
import mindustry.gen.*;
import mindustry.type.*;
import psammos.ai.*;

public class CrawlUnitType extends UnitType {

    public CrawlUnitType(String name) {
        super(name);
        aiController = HugAI::new;
        controller = u -> !playerControllable || (u.team.isAI() && !u.team.rules().rtsAi) ? aiController.get() : new HugCommandAI();
        constructor = CrawlUnit::create;
        omniMovement = false;
        circleTarget = true;
        faceTarget = true;
        targetAir = false;
        drawBody = false;
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);
        // It resets to CommandAI on map reload so this is the best way I could come up to fix that
        UnitController c = unit.controller();
        if (c instanceof CommandAI ai && !(c instanceof HugCommandAI)) {
            HugCommandAI aiNew = new HugCommandAI();

            aiNew.commandQueue = ai.commandQueue;
            aiNew.targetPos = ai.targetPos;
            aiNew.attackTarget = ai.attackTarget;
            aiNew.group = ai.group;
            aiNew.groupIndex = ai.groupIndex;
            aiNew.unreachableBuildings = ai.unreachableBuildings;
            aiNew.command = ai.command;
            aiNew.stances = ai.stances;

            unit.controller(aiNew);
        }
    }
}
