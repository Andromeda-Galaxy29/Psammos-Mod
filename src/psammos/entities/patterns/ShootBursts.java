package psammos.entities.patterns;

import arc.util.Nullable;
import mindustry.entities.pattern.*;

public class ShootBursts extends ShootPattern {
    public int shotsPerBurst = 1;
    public float spread = 5f;
    public int bursts = 3;
    public float burstDelay = 30f;

    public ShootBursts(int shots, int bursts, float spread){
        this.shotsPerBurst = shots;
        this.bursts = bursts;
        this.spread = spread;

        // This is so that stats work correctly
        this.shots = bursts * shotsPerBurst;
    }

    public ShootBursts(){
    }

    @Override
    public void shoot(int totalShots, BulletHandler handler, @Nullable Runnable barrelIncrementer){
        for(int i = 0; i < bursts; i++){
            for(int j = 0; j < shotsPerBurst; j++){
                float angleOffset = j * spread - (shotsPerBurst - 1) * spread / 2f;
                handler.shoot(0, 0, angleOffset, firstShotDelay + shotDelay * j + burstDelay * i);
            }
        }
    }
}
