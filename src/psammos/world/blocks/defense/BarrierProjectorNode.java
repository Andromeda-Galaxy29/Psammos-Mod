package psammos.world.blocks.defense;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
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
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.input.Placement;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Tile;
import psammos.PPal;

public class BarrierProjectorNode extends Block {
    public float range = 20 * Vars.tilesize;
    public int maxNodes = 3;
    public float shieldHealth = 100;
    public float nodeShieldRadius = 3f * Vars.tilesize;
    public float linkShieldThickness = 2.5f * Vars.tilesize;
    public Color shieldColor = PPal.desertGlass;

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
        addBar("shield", (BarrierProjectorNodeBuild build) -> new Bar(
                "stat.shieldhealth",
                Pal.accent,
                () -> build.graph.shieldHealth / build.graph.maxShieldHealth));
        addBar("connections", (BarrierProjectorNodeBuild build) -> new Bar(
                () -> Core.bundle.format("bar.powerlines", build.links.size, maxNodes),
                () -> Pal.items,
                () -> (float) build.links.size / maxNodes));
    }

    public class BarrierProjectorNodeBuild extends Building {
        public IntSeq links = new IntSeq();
        public BarrierGraph graph;

        @Override
        public void updateTile() {
            super.updateTile();
            if (graph.isController(this)) {
                graph.update();
            }

            // Bullet collision
            Groups.bullet.intersect(x - range, y - range, range * 2, range * 2, (bullet) -> {
                if (bullet.team != team && bullet.type.absorbable) {
                    if(bullet.within(this, realNodeShieldRadius()) || bulletIntersectsAnyLink(bullet)){
                        bullet.absorb();
                    }
                }
            });

            //Unit collision
            Units.nearbyEnemies(team, x, y, range, (unit) -> {
                // Collision with node shield
                float overlapDst = unit.hitSize / 2f + realNodeShieldRadius() - unit.dst(this);
                if(overlapDst > 0f){
                    unit.vel.setZero();
                    unit.move(Tmp.v1.set(unit).sub(this).setLength(overlapDst + 0.01f));
                    if (Mathf.chanceDelta(0.12f * Time.delta)) {
                        Fx.circleColorSpark.at(unit.x, unit.y, shieldColor);
                    }
                }

                // Collision with link shields
                for (int pos : links.toArray()) {
                    float linkX = Point2.x(pos) * Vars.tilesize;
                    float linkY = Point2.y(pos) * Vars.tilesize;

                    if(intersectsLine(unit.x, unit.y, x, y, linkX, linkY, (unit.hitSize + realLinkShieldThickness()) / 2f)){
                        float distanceToLine = Math.abs(Tmp.v1.set(linkY - y, x - linkX).nor().dot(unit.x - x, unit.y - y));
                        overlapDst = unit.hitSize / 2f + realLinkShieldThickness() / 2f - distanceToLine;
                        unit.vel.setZero();
                        unit.move(Tmp.v1.set(linkY - y, x - linkX).setLength(overlapDst + 0.01f));
                        if (Mathf.chanceDelta(0.12f * Time.delta)) {
                            Fx.circleColorSpark.at(unit.x, unit.y, shieldColor);
                        }
                    }
                }
            });
        }

        public boolean bulletIntersectsAnyLink(Bullet bullet) {
            for (int pos : links.toArray()) {
                float linkX = Point2.x(pos) * Vars.tilesize;
                float linkY = Point2.y(pos) * Vars.tilesize;

                if(intersectsLine(bullet.x, bullet.y, x, y, linkX, linkY, (bullet.hitSize + realLinkShieldThickness()) / 2f)){
                    return true;
                }
            }
            return false;
        }

        public boolean intersectsLine(float x, float y, float lx1, float ly1, float lx2, float ly2, float minDistance) {
            float lineLength = Tmp.v1.set(lx2 - lx1, ly2 - ly1).len();
            float distanceAlongLine = Tmp.v1.set(lx2 - lx1, ly2 - ly1).nor().dot(x - lx1, y - ly1);
            float distanceToLine = Math.abs(Tmp.v1.set(ly2 - ly1, lx1 - lx2).nor().dot(x - lx1, y - ly1));

            return 0 <= distanceAlongLine && distanceAlongLine <= lineLength && distanceToLine <= minDistance;
        }

        public float nodeEfficiency() {
            return 1;
        }

        public float realNodeShieldRadius(){
            return nodeShieldRadius * graph.averageEfficiency;
        }

        public float realLinkShieldThickness(){
            return linkShieldThickness * graph.averageEfficiency;
        }

        public void unlinkAll() {
            for (int pos : links.toArray()) {
                configure(pos);
            }
        }

        @Override
        public void created() {
            super.created();
            graph = new BarrierGraph(this);
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

            // Bebug draw
            Draw.color(Color.HSVtoRGB((graph.getID() * 60) % 360, 100, 100));
            links.each(pos -> {
                Lines.line(x, y, (Point2.x(pos) * 8 + x) / 2, (Point2.y(pos) * 8 + y) / 2);
            });
            Draw.rect("white", x, y, 6, 6);
            if (graph.isController(this)) {
                Draw.color(Pal.accent);
                Draw.rect("white", x, y, 3, 3);
            }
            // Bebug draw end

            //TODO change draw with turned off shield animations
            Draw.color(shieldColor);
            Draw.z(Layer.shields);
            // Node shield
            Fill.circle(x, y, realNodeShieldRadius());
            // Link shields
            Lines.stroke(realLinkShieldThickness());
            links.each(pos -> {
                Lines.line(x, y, Point2.x(pos) * Vars.tilesize, Point2.y(pos) * Vars.tilesize);
            });
        }

        @Override
        public void drawSelect() {
            super.drawSelect();
            Lines.stroke(1.0F);
            Drawf.circles(x, y, range, shieldColor);
            Draw.reset();
        }

        @Override
        public void drawConfigure() {
            Drawf.circles(x, y, (tile.block().size * Vars.tilesize) / 2f + 1f + Mathf.absin(Time.time, 4f, 1f), shieldColor);
            Drawf.circles(x, y, range, shieldColor);

            for (int pos : links.toArray()) {
                Tile link = Vars.world.tile(pos);
                if (link == null || link.block() == null) continue;;
                Drawf.square(link.x * Vars.tilesize, link.y * Vars.tilesize, link.block().size * Vars.tilesize / 2f + 1f, Pal.place);
            }
        }
    }
}
