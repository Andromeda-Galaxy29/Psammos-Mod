package psammos.draw;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.Interp.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.world.draw.*;

public class DrawBurnerFlame extends DrawBlock {
    public Color flameColor = Color.valueOf("f58349"), smokeColor = Color.valueOf("101010");

    public float alpha = 0.5f;
    public int particles = 30;
    public float particleLife = 70f, particleRad = 7f, particleSize = 3f, fadeMargin = 0.4f, rotateScl = 1.5f;
    public Interp particleInterp = new PowIn(1.5f);

    @Override
    public void draw(Building build){

        if(build.warmup() > 0f && flameColor.a > 0.001f){
            float a = alpha * build.warmup();
            Draw.blend(Blending.additive);

            float base = (Time.time / particleLife);
            rand.setSeed(build.id);
            for(int i = 0; i < particles; i++){
                float fin = (rand.random(1f) + base) % 1f, fout = 1f - fin;
                float angle = rand.random(360f) + (Time.time / rotateScl) % 360f;
                float len = particleRad * particleInterp.apply(fin);
                float randLen = rand.random(particleRad/5f);
                Draw.alpha(a * (1f - Mathf.curve(fin, 1f - fadeMargin)));
                Draw.color(flameColor, smokeColor, fin);
                Fill.circle(
                        build.x + Angles.trnsx(angle, len + randLen),
                        build.y + Angles.trnsy(angle, len + randLen),
                        particleSize * fout * build.warmup()
                );
            }

            Draw.blend();
            Draw.reset();
        }
    }

}
