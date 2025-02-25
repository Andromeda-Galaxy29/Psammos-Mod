package psammos.world.blocks.production;

import arc.Core;
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
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.input.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import psammos.world.blocks.environment.ExplodableBlock;

import static mindustry.Vars.*;

public class Bomb extends Block {
    public int damage = 15;
    public int range = 3;
    public int tier = 1;
    public Color baseColor = Color.white;
    public float explodeTime = 15;
    /** Whether the bomb can be placed near enemy buildings*/
    public boolean protectEnemyBlocks = false;

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

        Drawf.dashSquare(baseColor, x*tilesize+offset, y*tilesize+offset, range * tilesize);

        if(!protectEnemyBlocks) return;
        if(nearEnemyBuildings(world.tile(x, y), player.team())) {
            drawPlaceText(Core.bundle.get("dialog.psammos-enemy-buildings"), x, y, valid);
        }
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
        stats.add(Stat.damage, damage);
        stats.add(new Stat("psammos-explodables", StatCat.crafting), table -> {
            table.row();
            table.table(c -> {
                int i = 0;
                for(Block block : content.blocks()){
                    if(!(block instanceof ExplodableBlock)) continue;
                    if(((ExplodableBlock) block).hardness() > tier) continue;
                    c.table(Styles.grayPanel, b -> { ((ExplodableBlock)block).setBombStatsTable(b); }).growX().pad(5);
                    if(++i % 2 == 0) c.row();
                }
            }).growX().colspan(table.getColumns());
        });
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

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        super.canPlaceOn(tile, team, rotation);

        if(protectEnemyBlocks) return !nearEnemyBuildings(tile, team);
        return true;
    }

    public boolean nearEnemyBuildings(Tile tile, Team team){
        if(tile == null) return false;

        int x1 = (int) (tile.x - Math.floor((range+2) / 2) + (range%2==0 ? 1 : 0));
        int x2 = (int) (tile.x + Math.floor((range+2) / 2));
        int y1 = (int) (tile.y - Math.floor((range+2) / 2) + (range%2==0 ? 1 : 0));
        int y2 = (int) (tile.y + Math.floor((range+2) / 2));
        for (int ix = x1; ix <= x2; ix++) {
            for (int iy = y1; iy <= y2; iy++) {
                Tile currentTile = world.tile(ix, iy);
                if (currentTile == null) continue;

                Building build = currentTile.build;
                if (build == null) continue;

                if(!(build.team == team || build.team == Team.derelict)){
                    return true;
                }
            }
        }
        return false;
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

            world.tile(tileX(), tileY()).setNet(Blocks.air);

            // Some bad math. It works though
            int x1 = (int) (tileX() - Math.floor(range / 2) + (range%2==0 ? 1 : 0));
            int x2 = (int) (tileX() + Math.floor(range / 2));
            int y1 = (int) (tileY() - Math.floor(range / 2) + (range%2==0 ? 1 : 0));
            int y2 = (int) (tileY() + Math.floor(range / 2));
            for (int ix = x1; ix <= x2; ix++) {
                for (int iy = y1; iy <= y2; iy++) {
                    Tile tile = world.tile(ix, iy);
                    if (tile == null) continue;

                    //Checks the floor, overlay, and block for explodability and explodes them
                    if(tile.floor() instanceof ExplodableBlock){
                        if(((ExplodableBlock)tile.floor()).hardness() <= tier){
                            ((ExplodableBlock)tile.floor()).explode(tile);
                        }
                    }
                    if(tile.overlay() instanceof ExplodableBlock){
                        if(((ExplodableBlock)tile.overlay()).hardness() <= tier){
                            ((ExplodableBlock)tile.overlay()).explode(tile);
                        }
                    }
                    if(tile.block() instanceof ExplodableBlock){
                        if(((ExplodableBlock)tile.block()).hardness() <= tier){
                            ((ExplodableBlock)tile.block()).explode(tile);
                        }
                    }
                }
            }
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
