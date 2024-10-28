package psammos.type.unit;

import arc.struct.*;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.type.*;
import mindustry.type.*;
import mindustry.world.blocks.storage.CoreBlock;

import static mindustry.Vars.tilesize;

/** Band-aid fix to blast compound bombing.*/
public class ItemBlacklistUnitType extends UnitType {
    /** Items that this unit cannot pick up */
    public Seq<Item> itemBlacklist = new Seq<>();


    public ItemBlacklistUnitType(String name) {
        super(name);
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);
        if(unit.stack().amount > 0 && itemBlacklist.contains(unit.item())){
            Effect.shake(unit.item().explosiveness, 2, unit.x, unit.y);
            Fx.blastExplosion.at(unit.x, unit.y);
            Sounds.dullExplosion.at(unit.x, unit.y, 1f, 1f);
            unit.damage(unit.item().explosiveness * unit.stack().amount * 5);
            unit.clearItem();
        }
    }
}
