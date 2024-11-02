package psammos.world.blocks.environment;

import mindustry.content.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.Attribute;

public class FireVent extends SteamVent {

    public FireVent(String name) {
        super(name);
        this.effect = Fx.fire;
        this.effectSpacing = 6;
        this.status = StatusEffects.burning;
        this.attributes.set(Attribute.heat, 1f);
    }
}
