package psammos.entities.bullet;

import arc.graphics.Color;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;

import static mindustry.Vars.*;

/** Bullet that creates a trail of liquid behind itself */
public class WaveBulletType extends BasicBulletType {
    public Liquid liquid;
    public float puddleSize = 6f;
    public float puddleInterval = 5f;

    public WaveBulletType(@Nullable Liquid liquid){
        sprite = "psammos-rectangle";
        backSprite = "psammos-rectangle-back";
        if(liquid != null){
            this.liquid = liquid;
            this.status = liquid.effect;
            hitColor = liquid.color;
            lightColor = liquid.lightColor;
            backColor = liquid.color;
            trailColor = liquid.color;
        }
        frontColor = Color.white;
        hitEffect = despawnEffect = Fx.hitLiquid;
    }

    public WaveBulletType(){
        this(null);
    }

    @Override
    public void update(Bullet b){
        super.update(b);

        if(liquid.canExtinguish()){
            Tile tile = world.tileWorld(b.x, b.y);
            if(tile != null && Fires.has(tile.x, tile.y)){
                Fires.extinguish(tile, 100f);
                b.remove();
                hit(b);
            }
        }

        if(liquid != null && b.timer.get(2, puddleInterval)){
            Puddles.deposit(world.tileWorld(b.x, b.y), liquid, puddleSize);
        }
    }
}
