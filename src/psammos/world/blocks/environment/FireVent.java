package psammos.world.blocks.environment;

import mindustry.content.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.Attribute;

public class FireVent extends SteamVent {

//    public BulletType bullet = Bullets.fireball;
//    public float bulletChance = 0.01f;

    public FireVent(String name) {
        super(name);
        this.effect = Fx.fire;
        this.effectSpacing = 6;
        this.status = StatusEffects.burning;
        this.attributes.set(Attribute.heat, 1f);
    }

//    public void renderUpdate(UpdateRenderState state) {
//        super.renderUpdate(state);
//        if(Mathf.chanceDelta(bulletChance) && state.tile.block() == Blocks.air) {
//            bullet.createNet(Team.derelict, state.tile.worldx()-Vars.tilesize, state.tile.worldy()-Vars.tilesize, Mathf.random(360f), -1f, 1, 1);
//        }
//    }

}
