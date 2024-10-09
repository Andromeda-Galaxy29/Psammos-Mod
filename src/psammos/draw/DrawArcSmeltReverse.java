package psammos.draw;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.world.draw.*;

public class DrawArcSmeltReverse extends DrawArcSmelt {

    @Override
    public void draw(Building build){
        if(build.warmup() > 0f && flameColor.a > 0.001f){
            Lines.stroke(circleStroke * build.warmup());

            float si = Mathf.absin(flameRadiusScl, flameRadiusMag);
            float a = alpha * build.warmup();
            Draw.blend(blending);

            Draw.color(midColor, a);
            if(drawCenter) Fill.circle(build.x, build.y, flameRad + si);

            Draw.color(flameColor, a);
            if(drawCenter) Lines.circle(build.x, build.y, (flameRad + circleSpace + si) * build.warmup());

            Lines.stroke(particleStroke * build.warmup());

            float base = (Time.time / particleLife);
            rand.setSeed(build.id);
            for(int i = 0; i < particles; i++){
                float fin = (rand.random(1f) + base) % 1f, fout = 1f - fin;
                float angle = rand.random(360f);
                float len = particleRad * Interp.pow2Out.apply(fout);
                Lines.lineAngle(build.x + Angles.trnsx(angle, len), build.y + Angles.trnsy(angle, len), angle, particleLen * fin * build.warmup());
            }

            Draw.blend();
            Draw.reset();
        }
    }
}
