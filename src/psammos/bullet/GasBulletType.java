package psammos.bullet;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;

import static mindustry.Vars.*;

public class GasBulletType extends BulletType {
    public Liquid gas;
    public float orbSize = 3f;

    public GasBulletType(@Nullable Liquid gas){
        super(3.5f, 0);

        if(gas != null){
            this.gas = gas;
            this.status = gas.effect;
            hitColor = gas.gasColor;
            lightColor = gas.lightColor;
            lightOpacity = gas.lightColor.a;
        }

        ammoMultiplier = 1f;
        lifetime = 34f;
        statusDuration = 60f * 2f;
        despawnEffect = Fx.none;
        hitEffect = Fx.vaporSmall;
        smokeEffect = Fx.none;
        shootEffect = Fx.none;
        drag = 0.03f;
        knockback = 0.55f;
        displayAmmoMultiplier = false;
    }

    public GasBulletType(){
        this(null);
    }

    @Override
    public void update(Bullet b){
        super.update(b);

        new Effect(20f, e -> {
            Draw.color(e.color);
            Draw.alpha(e.fout());

            Angles.randLenVectors(e.id, 4, 2f + e.finpow() * 5f, (x, y) -> {
                Fill.circle(e.x + x, e.y + y, 1f + e.fin() * 2f);
            });
        }).at(b.x, b.y, gas.gasColor);

        if(gas.canExtinguish()){
            Tile tile = world.tileWorld(b.x, b.y);
            if(tile != null && Fires.has(tile.x, tile.y)){
                Fires.extinguish(tile, 100f);
                b.remove();
                hit(b);
            }
        }
    }

    @Override
    public void draw(Bullet b){
        super.draw(b);
    }

    @Override
    public void despawned(Bullet b){
        super.despawned(b);

        hitEffect.at(b.x, b.y, b.rotation(), gas.gasColor);
    }

    @Override
    public void hit(Bullet b, float hitx, float hity){
        hitEffect.at(hitx, hity, gas.gasColor);

        if(gas.temperature <= 0.5f && gas.flammability < 0.3f){
            float intensity = 400f;
            Fires.extinguish(world.tileWorld(hitx, hity), intensity);
            for(Point2 p : Geometry.d4){
                Fires.extinguish(world.tileWorld(hitx + p.x * tilesize, hity + p.y * tilesize), intensity);
            }
        }
    }
}
