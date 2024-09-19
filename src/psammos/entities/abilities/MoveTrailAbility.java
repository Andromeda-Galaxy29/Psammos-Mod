package psammos.entities.abilities;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;
import mindustry.graphics.*;

public class MoveTrailAbility extends Ability {
    public float x = 0, y = 0;
    public boolean mirror = false;
    public float width = 1f;
    public int length = 5;
    public Color color = Color.white;

    private Trail trail;
    private Trail secondTrail;
    private float sizeMult;

    public MoveTrailAbility(){
        display = false;
    }

    public MoveTrailAbility(float x, float y, boolean mirror, float width, int length, Color color){
        this.x = x;
        this.y = y;
        this.mirror = mirror;
        this.width = width;
        this.length = length;
        this.color = color;
        display = false;
    }

    @Override
    public void update(Unit unit){
        createTrail();

        Tmp.v1.trns(unit.rotation - 90f, x, y);
        Tmp.v2.trns(unit.rotation - 90f, -x, y);

        trail.update(Tmp.v1.x + unit.x, Tmp.v1.y + unit.y);
        if(mirror) secondTrail.update(Tmp.v2.x + unit.x, Tmp.v2.y + unit.y, width);
    }

    @Override
    public void draw(Unit unit){
        createTrail();

        Tmp.v1.trns(unit.rotation - 90f, x, y);
        Tmp.v2.trns(unit.rotation - 90f, -x, y);
        sizeMult = Mathf.lerpDelta(
                sizeMult,
                Mathf.clamp(unit.vel.len2(), 0, unit.speed()) / unit.speed(),
                0.05f
        );

        Draw.z(Layer.effect);

        trail.draw(color, width * sizeMult);
        Draw.color(color);
        Fill.circle(Tmp.v1.x + unit.x, Tmp.v1.y + unit.y, width * sizeMult);

        if(mirror) {
            secondTrail.draw(color, width * sizeMult);
            Draw.color(color);
            Fill.circle(Tmp.v2.x + unit.x, Tmp.v2.y + unit.y, width * sizeMult);
        };
    }

    public void createTrail(){
        if(trail == null) trail = new Trail(length);
        if(mirror && secondTrail == null) secondTrail = new Trail(length);
    }
}
