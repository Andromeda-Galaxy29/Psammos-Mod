package psammos.blocks;

import arc.audio.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.input.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class Bomb extends Block {
    public int damage = 15;
    public int range = 3;
    public Color baseColor = Color.white;
    public float explodeTime = 15;

    public DrawBlock drawer = new DrawDefault();

    public Effect effect = Fx.blastExplosion;
    public Sound explosionSound = Sounds.dullExplosion;
    public float shake = 2f;

    public Bomb(String name){
        super(name);
        solid = true;
        update = true;
        rotateDraw = false;
        rebuildable = false;
        allowDiagonal = false;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        x *= tilesize;
        y *= tilesize;
        x += offset;
        y += offset;

        Drawf.dashSquare(baseColor, x, y, range * tilesize);
    }

    @Override
    public boolean outputsItems(){
        return false;
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        drawer.drawPlan(this, plan, list);
    }

    @Override
    public TextureRegion[] icons(){
        return drawer.finalIcons(this);
    }

    @Override
    public void load(){
        super.load();
        drawer.load(this);
    }

    @Override
    public void setStats(){
        stats.timePeriod = explodeTime;
        super.setStats();

        stats.add(Stat.range, range, StatUnit.blocks);
        stats.add(Stat.productionTime, explodeTime / 60f, StatUnit.seconds);
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("psammos-bomb-timer", (BombBuild entity) -> new Bar("bar.psammos-bomb-timer", Items.blastCompound.color, () -> entity.progress));
    }

    @Override
    public void changePlacementPath(Seq<Point2> points, int rotation, boolean diagonal){
        if(!diagonal){
            Placement.calculateNodes(points, this, rotation, (point, other) -> Math.max(Math.abs(point.x - other.x), Math.abs(point.y - other.y)) <= range);
        }
    }

    public class BombBuild extends Building {
        public float progress;

        @Override
        public void updateTile(){
            if(efficiency > 0) {
                progress += getProgressIncrease(explodeTime);
            }

            if(progress >= 1f){
                explode();
            }
        }

        public void explode(){
            consume();

            Effect.shake(shake, shake, this);
            effect.at(x, y);
            explosionSound.at(x, y, 1f, 1f);
            Damage.damage(x, y, range * tilesize, damage);

            // Some bad math. It works though
            int bx = (int) Math.floor(x / tilesize);
            int by = (int) Math.floor(y / tilesize);
            int x1 = (int) (bx - Math.floor(range / 2) + (range%2==0 ? 1 : 0));
            int x2 = (int) (bx + Math.floor(range / 2));
            int y1 = (int) (by - Math.floor(range / 2) + (range%2==0 ? 1 : 0));
            int y2 = (int) (by + Math.floor(range / 2));
            for (int ix = x1; ix <= x2; ix++) {
                for (int iy = y1; iy <= y2; iy++) {
                    Block tile = world.tile(ix, iy).floor();
                    if(tile instanceof ExplodableFloor){
                        world.tile(ix, iy).setFloorNet(((ExplodableFloor)tile).replacement);
                        world.tile(ix, iy).setOverlayNet(Blocks.air);
                        world.tile(ix, iy).setNet(Blocks.air);
                    }
                }
            }
            world.tile(bx, by).setNet(Blocks.air);
        }

        @Override
        public void drawSelect(){
            super.drawSelect();

            Drawf.dashSquare(baseColor, x, y, range * tilesize);
        }

        @Override
        public float progress(){
            return Mathf.clamp(progress);
        }

        @Override
        public float totalProgress(){
            return progress;
        }

        @Override
        public int getMaximumAccepted(Item item){
            return itemCapacity;
        }

        @Override
        public void draw(){
            drawer.draw(this);
        }

        @Override
        public void drawLight(){
            super.drawLight();
            drawer.drawLight(this);
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(progress);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            progress = read.f();
        }
    }
}
