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

import java.util.Arrays;

import static mindustry.Vars.*;

public class BetterJunction extends Junction {

    public TextureRegion bottomRegion;
    public TextureRegion[] inRegions;
    public TextureRegion[] outRegions;

    public BetterJunction(String name) {
        super(name);
    }

    @Override
    public void load(){
        super.load();
        bottomRegion = Core.atlas.find(name+"-bottom");
        inRegions = new TextureRegion[4];
        for(int i = 0; i < 4; i++){
            inRegions[i] = Core.atlas.find(name+"-in-"+i);
        }
        outRegions = new TextureRegion[4];
        for(int i = 0; i < 4; i++){
            outRegions[i] = Core.atlas.find(name+"-out-"+i);
        }
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{bottomRegion, region};
    }

    public class BetterJunctionBuild extends JunctionBuild {

        private Seq<Item> dItems = new Seq<Item>();
        private Seq<Integer> dItemRotations = new Seq<Integer>();
        private Seq<Float> dItemMoves = new Seq<Float>();

        @Override
        public void draw(){
            Draw.z(Layer.block - 0.2f);
            Draw.rect(bottomRegion, x, y);

            for(int i = 0; i < 4; i++){

                Building other = findNextBuild(tileX(), tileY(), i);

                if(other != null && other.team == this.team() && other instanceof Conveyor.ConveyorBuild){
                    Conveyor.ConveyorBuild b = (Conveyor.ConveyorBuild) other;

                    int frame = enabled ? (int)(((Time.time * ((Conveyor)b.block).speed * 8f * timeScale * efficiency)) % 4) : 0;

                    Draw.z(Layer.block - 0.2f);
                    if(b.rotation == Mathf.mod((i + 2), 4)){ //If the conveyor faces this junction, draw input in its direction,
                        Draw.rect(inRegions[frame], x, y, i * 90);
                        Draw.rect(outRegions[frame], x, y, (i + 2) * 90);
                    }else{ // else draw output in the direction of the conveyor
                        if(b.block.noSideBlend && b.rotation != i) continue;

                        Draw.rect(inRegions[frame], x, y, (i + 2) * 90);
                        Draw.rect(outRegions[frame], x, y, i * 90);

                        if(nearby(i) instanceof JunctionBuild){ // Draws items between junctions
                            drawItems(i, ((Conveyor)b.block).speed);
                        }
                    }
                }
            }

            Draw.rect(region, x, y);
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

        public void drawItems(int dir, float speed){ //When erekir junctions are added, replace this with the better code from them
            Draw.z(Layer.block - 0.1f);
            for(int i = 0; i < dItems.size; i++){
                if(dItemRotations.get(i) != dir) continue;

                Draw.rect(dItems.get(i).fullIcon, x + Geometry.d4x(dir) * tilesize * dItemMoves.get(i), y + Geometry.d4y(dir) * tilesize * dItemMoves.get(i), itemSize, itemSize);
                dItemMoves.set(i, dItemMoves.get(i) + (enabled && !state.isPaused() ? speed * timeScale * efficiency : 0));
                if(dItemMoves.get(i) > 1){
                    dItems.remove(i);
                    dItemRotations.remove(i);
                    dItemMoves.remove(i);
                }
            }
        }

        @Override
        public void handleItem(Building source, Item item){
            super.handleItem(source, item);
            dItems.add(item);
            dItemRotations.add((int)source.relativeTo(this));
            dItemMoves.add(0f);
        }
    }
}
