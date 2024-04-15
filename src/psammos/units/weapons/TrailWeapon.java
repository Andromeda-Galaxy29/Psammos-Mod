package psammos.units.weapons;

import arc.graphics.Color;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Sounds;
import mindustry.type.Weapon;
import mindustry.content.Fx;

/* Does not shoot, but creates a visual trail behind the unit */
public class TrailWeapon extends Weapon {
    public float width;
    public int length;
    public Color color;

    public TrailWeapon(){
        this(1, 0, true, 1, 1, Color.white);
    }

    public TrailWeapon(float x, float y, boolean mirror, float width, int length, Color color){
        this.x = x;
        this.y = y;
        this.mirror = mirror;
        this.width = width;
        this.length = length;
        this.color = color;

        this.name = "";
        this.alwaysShooting = true;
        this.alwaysContinuous = true;
        this.alternate = false;
        this.shootY = 0;
        this.recoil = 0;
        this.shootSound = Sounds.none;
        this.bullet = new BulletType(){{
            damage = 0;
            keepVelocity = false;
            speed = 0;
            collides = false;
            hittable = false;
            absorbable = false;
            shootEffect = Fx.none;
            smokeEffect = Fx.none;
            hitEffect = Fx.none;
            despawnEffect = Fx.none;
            layer = 100;
            trailLength = length;
            trailWidth = width;
            trailColor = color;
        }};
    }
}
