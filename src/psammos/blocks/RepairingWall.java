package psammos.blocks;

import arc.graphics.*;
import mindustry.content.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.meta.*;

public class RepairingWall extends Wall {
    public Color baseColor = Color.valueOf("84f491");
    public float healPercent = 4f;
    public float reload = 200f;

    public RepairingWall(String name){
        super(name);
        update = true;
        suppressable = true;
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.repairSpeed, (int)(health * healPercent / 100 * 60 / reload), StatUnit.perSecond);
    }

    public class RepairingWallBuild extends WallBuild {
        public float charge = 0;

        @Override
        public void updateTile(){
            boolean canHeal = !checkSuppression();

            charge += 1;

            if(charge >= reload && canHeal && health() < maxHealth()){
                charge = 0f;

                heal(maxHealth() * (healPercent) / 100f);
                recentlyHealed();
                Fx.healBlockFull.at(x, y, block.size, baseColor, block);
            }
        }
    }
}
