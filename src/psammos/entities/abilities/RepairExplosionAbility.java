package psammos.entities.abilities;

import arc.Core;
import arc.scene.ui.layout.Table;
import arc.util.Strings;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;

public class RepairExplosionAbility extends Ability {
    public float amount = 1f;
    public float range = 20f;
    public Effect healEffect;
    public Effect explosionEffect;
    protected boolean shouldExplode;

    public RepairExplosionAbility() {
        this.healEffect = Fx.heal;
        this.explosionEffect = Fx.healWaveDynamic;
        this.shouldExplode = false;
    }

    public RepairExplosionAbility(float amount, float range) {
        this.healEffect = Fx.heal;
        this.explosionEffect = Fx.healWaveDynamic;
        this.shouldExplode = false;
        this.amount = amount;
        this.range = range;
    }

    public void update(Unit unit) {
        Units.nearby(unit.team, unit.x, unit.y, this.range, (other) -> {
            if (other != unit && other.damaged()){
                healEffect.at(other);
                shouldExplode = true;
                other.heal(amount);
            }

        });
        if (shouldExplode) {
            explosionEffect.at(unit, this.range);
            unit.remove();
        }
    }
}
