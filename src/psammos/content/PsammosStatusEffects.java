package psammos.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.type.*;

public class PsammosStatusEffects {
    public static StatusEffect quicksandSlowed, infested, swarmProtection, combustible;

    public static void load(){
        quicksandSlowed = new StatusEffect("quicksand-slowed"){{
            color = Color.valueOf("d3ae8d");
            speedMultiplier = 0.6f;

            effect = new Effect(40f, e -> {
                Draw.color(PsammosLiquids.quicksand.color);

                Angles.randLenVectors(e.id, 2, 1f + e.fin() * 2f, (x, y) -> {
                    Fill.square(e.x + x, e.y + y, e.fslope() * 1.1f, 45f);
                });
            });
        }
            @Override
            public String emoji() {
                return "\uECC4";
            }
            @Override
            public boolean hasEmoji() {
                return true;
            }
        };

        infested = new StatusEffect("infested"){{
            color = Color.white;
            damage = 0.17f;
            transitionDamage = 6f;
        }
            @Override
            public String emoji() {
                return "\uECC5";
            }
            @Override
            public boolean hasEmoji() {
                return true;
            }
        };

        swarmProtection = new StatusEffect("swarm-protection"){{
            color = Color.white;
            healthMultiplier = 1.2f;
        }
            @Override
            public String emoji() {
                return "\uECC6";
            }
            @Override
            public boolean hasEmoji() {
                return true;
            }
        };

        combustible = new StatusEffect("combustible"){{
            color = Liquids.ozone.color;
            effectChance = 0.05f;

            effect = new Effect(23, e -> {
                Angles.randLenVectors(e.id, 3, e.fin() * 9f, (x, y) -> {
                    Draw.color(Liquids.ozone.color.a(e.fout()));
                    Fill.circle(e.x + x, e.y + y, e.fout() * 2f);
                });
            });

            init(() -> {
                affinity(StatusEffects.melting, (unit, result, time) -> {
                    result.set(StatusEffects.melting, time + result.time);
                });
                affinity(StatusEffects.burning, (unit, result, time) -> {
                    result.set(StatusEffects.burning, time + result.time);
                });
            });
        }
            @Override
            public void update(Unit unit, float time) {
                super.update(unit, time);
                if(unit.hasEffect(StatusEffects.burning) || unit.hasEffect(StatusEffects.melting)){
                    unit.damageContinuousPierce(0.2f);
                }
            }
            @Override
            public String emoji() {
                return "\uECC7";
            }
            @Override
            public boolean hasEmoji() {
                return true;
            }
        };
    }
}
