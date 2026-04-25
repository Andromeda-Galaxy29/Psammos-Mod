package psammos.world.blocks.defense;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Intersector;
import arc.math.geom.Point2;
import arc.struct.IntSeq;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.power.PowerNode;

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
            }else if(linkValid(node, otherNode) && node.links.size < maxNodes){
                node.links.addUnique(otherNode.pos());
                otherNode.links.addUnique(node.pos());
                node.graph.addGraph(otherNode.graph);
            }
        });
    }

    public boolean linkValid(BarrierProjectorNodeBuild node, BarrierProjectorNodeBuild otherNode) {
        return node != otherNode &&
                node.team == otherNode.team &&
                Intersector.overlaps(Tmp.cr1.set(node.x, node.y, range), otherNode.tile.getHitbox(Tmp.r1));
    }

    public class BarrierProjectorNodeBuild extends Building {
        public IntSeq links = new IntSeq();
        public BarrierGraph graph = new BarrierGraph();

        public float nodeEfficiency() {
            return 1;
        }

        @Override
        public void created() {
            super.created();
            graph.add(this);
        }

        @Override
        public void onRemoved() {
            super.onRemoved();
            for (int pos : links.toArray()) {
                Tile tile = Vars.world.tile(pos);
                if (tile == null || !(tile.build instanceof BarrierProjectorNodeBuild linked)) {
                    continue;
                }
                linked.links.removeValue(this.pos());
            }
            graph.remove(this);
        }

        @Override
        public boolean onConfigureBuildTapped(Building other) {
            if (other instanceof BarrierProjectorNodeBuild otherNode && linkValid(this, otherNode)) {
                configure(otherNode.pos());
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
            links.each(pos -> {
                Draw.color(Color.HSVtoRGB((graph.getID() * 20) % 360, 100, 100));
                Lines.line(x, y, (Point2.x(pos) * 8 + x) / 2, (Point2.y(pos) * 8 + y) / 2);
            });
        }
    }
}
