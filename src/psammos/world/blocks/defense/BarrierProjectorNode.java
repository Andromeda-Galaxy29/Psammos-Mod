package psammos.world.blocks.defense;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Intersector;
import arc.math.geom.Point2;
import arc.struct.IntSeq;
import arc.struct.Seq;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.input.Placement;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Tile;
import psammos.PPal;

public class BarrierProjectorNode extends Block {
    public float range = 10 * Vars.tilesize;
    public int maxNodes = 3;

    public BarrierProjectorNode(String name) {
        super(name);
        update = true;
        configurable = true;
        swapDiagonalPlacement = true;

        config(Integer.class, (entity, pos) -> {
            Building other = Vars.world.build(pos);
            if (!(other instanceof BarrierProjectorNodeBuild otherNode && entity instanceof BarrierProjectorNodeBuild node)){
                return;
            }

            boolean contains = node.links.contains(pos);

            if(contains){
                node.links.removeValue(pos);
                otherNode.links.removeValue(node.pos());
                node.graph.trySplitGraph();
            }else if(linkValid(node, otherNode)){
                node.links.addUnique(otherNode.pos());
                otherNode.links.addUnique(node.pos());
                node.graph.addGraph(otherNode.graph);
            }
        });
    }

    public boolean linkValid(BarrierProjectorNodeBuild node, BarrierProjectorNodeBuild otherNode){
        return linkValid(node, otherNode, true);
    }

    public boolean linkValid(BarrierProjectorNodeBuild node, BarrierProjectorNodeBuild otherNode, boolean checkMaxNodes) {
        if (checkMaxNodes) {
            if (node.links.size >= maxNodes || otherNode.links.size >= ((BarrierProjectorNode) otherNode.block).maxNodes) {
                return false;
            }
        }
        return node != otherNode &&
                node.team == otherNode.team &&
                Intersector.overlaps(Tmp.cr1.set(node.x, node.y, range), otherNode.tile.getHitbox(Tmp.r1));
    }

    @Override
    public void changePlacementPath(Seq<Point2> points, int rotation){
        Placement.calculateNodes(points, this, rotation, (point, other) -> overlaps(Vars.world.tile(point.x, point.y), Vars.world.tile(other.x, other.y)));
    }

    public boolean overlaps(@Nullable Tile src, @Nullable Tile other){
        if(src == null || other == null) return true;
        return Intersector.overlaps(Tmp.cr1.set(src.worldx() + offset, src.worldy() + offset, range), Tmp.r1.setSize(size * Vars.tilesize).setCenter(other.worldx() + offset, other.worldy() + offset));
    }


    @Override
    public void setBars() {
        super.setBars();
        this.addBar("connections", (BarrierProjectorNodeBuild build) -> new Bar(
                () -> Core.bundle.format("bar.powerlines", build.links.size, maxNodes),
                () -> Pal.items,
                () -> (float) build.links.size / maxNodes));
    }

    public class BarrierProjectorNodeBuild extends Building {
        public IntSeq links = new IntSeq();
        public BarrierGraph graph = new BarrierGraph(this);

        public float nodeEfficiency() {
            return 1;
        }

        public void unlinkAll() {
            for (int pos : links.toArray()) {
                configure(pos);
            }
        }

        @Override
        public void onRemoved() {
            super.onRemoved();
            unlinkAll();
            graph.remove(this);
        }

        @Override
        public boolean onConfigureBuildTapped(Building other) {
            // TODO link all nearby
            if (other instanceof BarrierProjectorNodeBuild otherNode && linkValid(this, otherNode, false)) {
                configure(otherNode.pos());
                return false;
            } else if (this == other) {
                unlinkAll();
                graph.remove(this);
                graph = new BarrierGraph(this);
                deselect();
                return false;
            }
            return true;
        }

        @Override
        public Point2[] config() {
            Point2[] out = new Point2[links.size];

            for(int i = 0; i < out.length; ++i) {
                out[i] = Point2.unpack(links.get(i)).sub(tile.x, tile.y);
            }

            return out;
        }

        @Override
        public void draw() {
            super.draw();
            Draw.color(Color.HSVtoRGB((graph.getID() * 60) % 360, 100, 100));
            links.each(pos -> {
                Lines.line(x, y, (Point2.x(pos) * 8 + x) / 2, (Point2.y(pos) * 8 + y) / 2);
            });
            Draw.rect("white", x, y, 4, 4);
        }

        @Override
        public void drawSelect() {
            super.drawSelect();
            Lines.stroke(1.0F);
            Drawf.circles(x, y, range, PPal.desertGlass);
            Draw.reset();
        }

        @Override
        public void drawConfigure() {
            Drawf.circles(x, y, (tile.block().size * Vars.tilesize) / 2f + 1f + Mathf.absin(Time.time, 4f, 1f), PPal.desertGlass);
            Drawf.circles(x, y, range, PPal.desertGlass);

            for (int pos : links.toArray()) {
                Tile link = Vars.world.tile(pos);
                if (link == null || link.block() == null) continue;;
                Drawf.square(link.x * Vars.tilesize, link.y * Vars.tilesize, link.block().size * Vars.tilesize / 2f + 1f, Pal.place);
            }
        }
    }
}
