package psammos.ai;

import mindustry.entities.Units;
import mindustry.entities.units.AIController;
import mindustry.gen.BuildingTetherc;
import mindustry.gen.Teamc;
import psammos.world.blocks.units.RepairDroneAssembler;

public class RepairDroneAI extends AIController {

    public RepairDroneAI() {
        super();
    }

    @Override
    public void updateMovement(){
        if (unit instanceof BuildingTetherc tether) {
            if (tether.building() != null && tether.building().block instanceof RepairDroneAssembler block) {
                target = target(unit.x, unit.y, block.range, true, true);
                if(target != null){
                    unit.lookAt(target.x(), target.y());
                    unit.moveAt(vec.trns(unit.rotation, unit.speed()));
                }else{
                    moveTo(tether.building(), 1);
                }
            }
        }
    }

    @Override
    public Teamc target(float x, float y, float range, boolean air, boolean ground) {
        return Units.closest(unit.team, unit.x, unit.y, range, u -> (u != unit && u.damaged()));
    }
}