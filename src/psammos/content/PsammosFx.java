package psammos.content;

import mindustry.content.Fx;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.units.UnitAssembler.*;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public class PsammosFx extends Fx {
    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();

    public static final Effect

    glassMeteorExplosion = new Effect(40f, e -> {
        rand.setSeed(e.id);

        float randSize = 0.1f;
        float fout = e.fout() * rand.random(1f - randSize, 1f);
        float fin = e.fin()  * rand.random(1f - randSize, 1f);
        float coreRadius = 30f * e.fout(Interp.smooth2);

        Color coreColor = Tmp.c1.set(e.color).mul(0.8f);
        Color edgeColor = e.color;

        e.scaled(10, i -> {
            stroke(4f * i.fout());
            Lines.circle(e.x, e.y, 2f + i.fin() * 40f);
        });

        int count = 8;
        for(int i = 0; i < count; i++){
            float t = (i + 1f) / count;
            float radius = coreRadius + 5f;
            color(Tmp.c1.set(coreColor).mul(1f + fout / 8f));
            alpha(Mathf.pow(1f - t, 2.5f) * fout * 0.5f);
            Fill.circle(e.x, e.y, Mathf.lerp(coreRadius * 0.6f, coreRadius * 1.7f, t));
        }

        color(Tmp.c1.set(edgeColor).mul(1.2f));
        e.scaled(fout * 0.8f, i -> {
            stroke(3f * i.fout());
            Lines.circle(e.x, e.y, coreRadius * 0.6f);
        });

        color(coreColor);
        alpha(0.5f * e.fout(Interp.smooth) + 0.8f);
        stroke(e.fout(Interp.pow2InInverse) * 3f);
        float circleRad = e.finpow() * 72f;
        Lines.circle(e.x, e.y, circleRad);

        stroke(e.fout(Interp.smooth) * 3f);
        for(int i = 0; i < 9; i++){
            float angle = rand.random(360f);
            float lenRand = rand.random(0.5f, 1.2f);
            Tmp.v1.trns(angle, circleRad);

            for(int s : Mathf.signs){
                Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.fout() * 10f, e.fout() * 10f * lenRand + 8f, angle + 90f + s * 90f);
            }
        }

        color(edgeColor);
        alpha(e.fout(Interp.pow2InInverse) + 0.5f);

        for(int i = 0; i < rand.random(8, 13); i++){
            float randomPos = rand.random(0.9f, 1.1f);
            float angle = rand.random(360f);
            float len = rand.random(0.7f, 1.3f) * 10f + fout * 2f;
            float width = rand.random(1f, 4f) * 1.5f * fout + 1f;
            float dist = 8f + coreRadius * rand.random(0.8f, 1.4f);
            Tmp.v1.trns(angle, circleRad);

            for(int s : Mathf.signs){
                Drawf.tri(e.x + Angles.trnsx(angle, dist) - Tmp.v1.x / 2, e.y + Angles.trnsy(angle, dist) * randomPos - Tmp.v1.y * randomPos / 2, width, len, angle + 90f + s * 90f);
            }
        }

        reset();
    });
}
