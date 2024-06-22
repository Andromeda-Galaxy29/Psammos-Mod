package psammos.blocks.production;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.world.blocks.production.BurstDrill;

public class RotaryBurstDrill extends BurstDrill {

    public RotaryBurstDrill(String name){
        super(name);
        this.rotateSpeed = 16f;
        this.speedCurve = Interp.sineIn;
        this.arrows = 0;
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region, rotatorRegion, topRegion};
    }

    public class RotaryBurstDrillBuild extends BurstDrillBuild{
        @Override
        public void draw(){
            Draw.rect(region, x, y);
            drawDefaultCracks();

            if(drawSpinSprite){
                Drawf.spinSprite(rotatorRegion, x, y, rotateSpeed * timeDrilled);
            }else{
                Draw.rect(rotatorRegion, x, y, rotateSpeed * timeDrilled);
            }

            Draw.rect(topRegion, x, y);
            if(invertTime > 0 && topInvertRegion.found()){
                Draw.alpha(Interp.pow3Out.apply(invertTime));
                Draw.rect(topInvertRegion, x, y);
                Draw.color();
            }

            if(dominantItem != null && drawMineItem){
                Draw.color(dominantItem.color);
                Draw.rect(itemRegion, x, y);
                Draw.color();
            }

            float fract = smoothProgress;
            Draw.color(arrowColor);
            for(int i = 0; i < 4; i++){
                for(int j = 0; j < arrows; j++){
                    float arrowFract = (arrows - 1 - j);
                    float a = Mathf.clamp(fract * arrows - arrowFract);
                    Tmp.v1.trns(i * 90 + 45, j * arrowSpacing + arrowOffset);

                    Draw.z(Layer.block);
                    Draw.color(baseArrowColor, arrowColor, a);
                    Draw.rect(arrowRegion, x + Tmp.v1.x, y + Tmp.v1.y, i * 90);

                    Draw.color(arrowColor);

                    if(arrowBlurRegion.found()){
                        Draw.z(Layer.blockAdditive);
                        Draw.blend(Blending.additive);
                        Draw.alpha(Mathf.pow(a, 10f));
                        Draw.rect(arrowBlurRegion, x + Tmp.v1.x, y + Tmp.v1.y, i * 90);
                        Draw.blend();
                    }
                }
            }
            Draw.color();

            if(glowRegion.found()){
                Drawf.additive(glowRegion, Tmp.c2.set(glowColor).a(Mathf.pow(fract, 3f) * glowColor.a), x, y);
            }
        }
    }
}
