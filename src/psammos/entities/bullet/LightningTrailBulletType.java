package psammos.entities.bullet;

import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Time;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Trail;

public class LightningTrailBulletType extends BasicBulletType {
    float maxOffset = 6;

    @Override
    public void updateTrail(Bullet b){
        if(trailLength > 0){
            if(b.trail == null){
                b.trail = new Trail(trailLength);
            }
            b.trail.length = trailLength;
            float addX = Mathf.random(-maxOffset, maxOffset);
            float addY = Mathf.random(-maxOffset, maxOffset);
            b.trail.update(b.x + addX, b.y + addY, trailInterp.apply(b.fin()) * (1f + (trailSinMag > 0 ? Mathf.absin(Time.time, trailSinScl, trailSinMag) : 0f)));
        }
    }
}
