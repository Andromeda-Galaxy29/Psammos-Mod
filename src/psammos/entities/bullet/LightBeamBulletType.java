package psammos.entities.bullet;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Units;
import mindustry.entities.bullet.ContinuousBulletType;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;

public class LightBeamBulletType extends ContinuousBulletType {
    public float lightStroke = 40f;
    public float oscScl = 1.2f, oscMag = 0.02f;
    public float angleWidth = 20f;

    public boolean drawFlare = true;
    public Color flareColor = Pal.accent;
    public float flareWidth = 3f, flareInnerScl = 0.5f, flareLength = 20f, flareInnerLenScl = 0.5f, flareLayer = Layer.bullet - 0.0001f, flareRotSpeed = 1.2f;
    public boolean rotateFlare = false;
    public Interp lengthInterp = Interp.slope;

    public Color color = Pal.accent;

    public LightBeamBulletType(float damage){
        this();
        this.damage = damage;
    }

    public LightBeamBulletType(){
        optimalLifeFract = 0.5f;
        length = 120f;
        knockback = 0f;
        hitEffect = Fx.hitFlameBeam;
        hitSize = 4;
        drawSize = 420f;
        lifetime = 16f;
        hitColor = color;
        lightColor = hitColor;
        lightOpacity = 0.6f;
        ammoMultiplier = 1f;

        //Do not change these
        laserAbsorb = false;
        pierce = true;
        pierceCap = -1;
        pierceArmor = true;
    }
    
    @Override
    public void applyDamage(Bullet b){
        float damage = b.damage;
        if(timescaleDamage && b.owner instanceof Building build){
            b.damage *= build.timeScale();
        }

        Units.nearbyEnemies(b.team, b.x, b.y, length, (u) -> {
            Tmp.v2.set(u.x - b.x, u.y - b.y); // Vector from bullet to enemy

            float angleToEnemy = Tmp.v1.set(1, 0).rotate(b.rotation()).angleTo(Tmp.v2);
            float angleDist = Angles.angleDist(b.rotation(), angleToEnemy);

            float enemyRadius = u.type.hitSize / 2f;
            float angleOffset = (float) Math.asin(enemyRadius / Tmp.v2.len()) * Mathf.radiansToDegrees;

            if (angleDist - angleOffset <= angleWidth / 2f){
                if (u.checkTarget(b.type.collidesAir, b.type.collidesGround) && u.hittable()){
                    u.collision(b, u.x, u.y);
                    b.collision(u, u.x, u.y);
                }
            }
        });

        b.damage = damage;
    }

    @Override
    public void draw(Bullet b){
        float mult = b.fin(lengthInterp);
        float realLength = length * mult * 1.2f;

        float sin = Mathf.sin(Time.time, oscScl, oscMag);

        Tmp.v1.set(realLength, 0).rotate(b.rotation() - angleWidth / 2f);
        Tmp.v2.set(realLength, 0).rotate(b.rotation());
        Tmp.v3.set(realLength, 0).rotate(b.rotation() + angleWidth / 2f);
        Tmp.c1.set(color).a(0);
        Fill.quad(Core.atlas.white(),
                b.x, b.y, color.toFloatBits(),
                b.x + Tmp.v1.x, b.y + Tmp.v1.y, Tmp.c1.toFloatBits(),
                b.x + Tmp.v2.x, b.y + Tmp.v2.y, Tmp.c1.toFloatBits(),
                b.x + Tmp.v3.x, b.y + Tmp.v3.y, Tmp.c1.toFloatBits()
        );

        if(drawFlare){
            Draw.color(flareColor);
            Draw.z(flareLayer);

            float angle = Time.time * flareRotSpeed + (rotateFlare ? b.rotation() : 0f);

            for(int i = 0; i < 4; i++){
                Drawf.tri(b.x, b.y, flareWidth, flareLength * (mult + sin), i*90 + 45 + angle);
            }

            Draw.color();
            for(int i = 0; i < 4; i++){
                Drawf.tri(b.x, b.y, flareWidth * flareInnerScl, flareLength * flareInnerLenScl * (mult + sin), i*90 + 45 + angle);
            }
        }

        Tmp.v1.trns(b.rotation(), realLength * 1.1f);
        Drawf.light(b.x, b.y, b.x + Tmp.v1.x, b.y + Tmp.v1.y, lightStroke, lightColor, lightOpacity);
        Draw.reset();
    }

    @Override
    public float currentLength(Bullet b){
        return length * b.fin(lengthInterp);
    }

    @Override
    public void drawLight(Bullet b){
        //no light drawn here
    }

}