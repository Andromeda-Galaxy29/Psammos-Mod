package psammos.type.unit;

import mindustry.ai.types.*;
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
}
