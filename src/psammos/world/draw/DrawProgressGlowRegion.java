package psammos.world.draw;

import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.gen.*;
import mindustry.world.draw.*;

public class DrawProgressGlowRegion extends DrawGlowRegion {

    public Interp interp = Interp.sineIn;

    public DrawProgressGlowRegion(){
        super();
    }
    public DrawProgressGlowRegion(float layer){
        super(layer);
    }
    public DrawProgressGlowRegion(boolean rotate){
        super(rotate);
    }
    public DrawProgressGlowRegion(String suffix){
        super(suffix);
    }

    @Override
    public void draw(Building build){
        float z = Draw.z();
        if(layer > 0) Draw.z(layer);
        Draw.blend(blending);
        Draw.color(color);
        Draw.alpha((Mathf.absin(build.totalProgress(), glowScale, alpha) * glowIntensity + 1f - glowIntensity) * interp.apply(build.progress()) * alpha);
        Draw.rect(region, build.x, build.y, build.totalProgress() * rotateSpeed + (rotate ? build.rotdeg() : 0f));
        Draw.reset();
        Draw.blend();
        Draw.z(z);
    }

}
