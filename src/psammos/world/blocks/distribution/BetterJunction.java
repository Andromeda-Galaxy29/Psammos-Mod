package psammos.world.blocks.distribution;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.Seq;
import arc.util.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.liquid.LiquidBlock;

import java.util.Arrays;

import static mindustry.Vars.*;

public class BetterJunction extends Junction {
    public TextureRegion bottomRegion, plugRegion1, plugRegion2;
    public TextureRegion[] inRegions, outRegions;

    public BetterJunction(String name) {
        super(name);
        drawCached = false;
        drawDynamic = true;
    }

    @Override
    public void load(){
        super.load();
        bottomRegion = Core.atlas.find(name+"-bottom");
        plugRegion1 = Core.atlas.find(name+"-plug0");
        plugRegion2 = Core.atlas.find(name+"-plug1");
        inRegions = new TextureRegion[4];
        outRegions = new TextureRegion[4];
        for(int i = 0; i < 4; i++){
            inRegions[i] = Core.atlas.find(name+"-in-"+i);
            outRegions[i] = Core.atlas.find(name+"-out-"+i);
        }
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{bottomRegion, region};
    }

    public class BetterJunctionBuild extends JunctionBuild {

        @Override
        public void draw(){
            Draw.z(Layer.block - 0.2f);
            Draw.rect(bottomRegion, x, y);

            for(int i = 0; i <= 1; i++){
                Building other = findNextBuild(tileX(), tileY(), i);
                Building other2 = findNextBuild(tileX(), tileY(), i + 2);

                int dir = 0;
                float conveyorSpeed = 0;

                if(other != null && other.team == this.team() && other instanceof Conveyor.ConveyorBuild b) {
                    conveyorSpeed = Math.max(conveyorSpeed, ((Conveyor) b.block).speed);
                    dir += b.rotation == (i + 2) % 4 ? -1 : (b.block.noSideBlend && b.rotation != i ? 0 : 1);
                }
                if(other2 != null && other2.team == this.team() && other2 instanceof Conveyor.ConveyorBuild b) {
                    conveyorSpeed = Math.max(conveyorSpeed, ((Conveyor) b.block).speed);
                    dir += b.rotation == i ? 1 : (b.block.noSideBlend && b.rotation != (i + 2) % 4 ? 0 : -1);
                }

                int frame = enabled ? (int)(((Time.time * conveyorSpeed * 8f * timeScale * efficiency)) % 4) : 0;

                if (dir < 0) {
                    Draw.rect(inRegions[frame], x, y, i * 90);
                    Draw.rect(outRegions[frame], x, y, (i + 2) * 90);
                } else if (dir > 0) {
                    Draw.rect(inRegions[frame], x, y, (i + 2) * 90);
                    Draw.rect(outRegions[frame], x, y, i * 90);
                }
            }

            for (int i = 0; i < 4; i++) {
                drawItems(i);
            }

            Draw.z(Layer.block);
            Draw.rect(region, x, y);

            for(int i = 0; i < 4; i++) {
                TextureRegion plugRegion = i <= 1 ? plugRegion1 : plugRegion2;
                if ((nearby(i) == null || !nearby(i).block.hasItems) && !(nearby(i) instanceof BetterJunctionBuild)) {
                    Draw.rect(plugRegion, x, y, i * 90);
                }
            }
        }

        public Building findNextBuild(int x, int y, int rot){
            int cx = x + Geometry.d4x(rot);
            int cy = y + Geometry.d4y(rot);
            Building b = world.build(cx, cy);
            while(b instanceof JunctionBuild){ //Skips all other junctions until a different building is found
                cx += Geometry.d4x(rot);
                cy += Geometry.d4y(rot);
                b = world.build(cx, cy);
            }
            return b;
        }

        public void drawItems(int dir){
            // TODO;
        }
    }
}
