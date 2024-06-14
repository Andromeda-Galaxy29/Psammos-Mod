package psammos.entities.bullet;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.Pal;

public class VortexBulletType extends BulletType {
    public float width = 15f, height = 15f;
    public Color backColor = Pal.missileYellowBack, frontColor = Pal.missileYellow;

    public TextureRegion backRegion;
    public TextureRegion frontRegion;

    public VortexBulletType(){
        this(1f, 1f);
    }

    public VortexBulletType(float speed, float damage){
        super(speed, damage);

        lifetime = 120f;
        despawnEffect = Fx.none;
        hitEffect = Fx.pulverize;
        smokeEffect = Fx.none;
        shootEffect = Fx.none;
        hitColor = Pal.missileYellow;
        hitSize = 10f;
        drag = 0.005f;
        displayAmmoMultiplier = false;
        homingRange = 200f;
        homingPower = 0.1f;
        pierceCap = 4;
        pierceBuilding = true;
    }

    @Override
    public void load(){
        super.load();

        frontRegion = Core.atlas.find("psammos-vortex");
        backRegion = Core.atlas.find("psammos-vortex-back");
    }

    @Override
    public void draw(Bullet b){
        super.draw(b);

        Draw.color(backColor);
        Draw.rect(backRegion, b.x, b.y, width, height, -Time.time * 5);

        Draw.color(backColor, frontColor, 0.33f);
        Draw.rect(backRegion, b.x, b.y, width, height, Time.time * 6);

        Draw.color(backColor, frontColor, 0.66f);
        Draw.rect(frontRegion, b.x, b.y, width, height, -Time.time * 7);

        Draw.color(frontColor);
        Draw.rect(frontRegion, b.x, b.y, width, height, Time.time * 8);

    }

    @Override
    public void updateHoming(Bullet b){
        if(homingPower > 0.0001f && b.time >= homingDelay){
            float realAimX = b.aimX < 0 ? b.x : b.aimX;
            float realAimY = b.aimY < 0 ? b.y : b.aimY;

            Teamc target;
            //home in on allies if possible
            if(heals()){
                target = Units.closestTarget(null, realAimX, realAimY, homingRange,
                        e -> e.checkTarget(collidesAir, collidesGround) && e.team != b.team,
                        t -> collidesGround && (t.team != b.team || t.damaged())
                );
            }else{
                if(b.aimTile != null && b.aimTile.build != null && b.aimTile.build.team != b.team && collidesGround){
                    target = b.aimTile.build;
                }else{
                    target = Units.closestTarget(b.team, realAimX, realAimY, homingRange,
                            e -> e != null && e.checkTarget(collidesAir, collidesGround),
                            t -> t != null && collidesGround
                    );
                }
            }

            if(target != null){
                b.vel.setAngle(Angles.moveToward(b.rotation(), b.angleTo(target), homingPower * Time.delta * 50f));
            }
        }
    }
}
