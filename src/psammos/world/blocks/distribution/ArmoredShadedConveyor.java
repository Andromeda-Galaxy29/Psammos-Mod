package psammos.world.blocks.distribution;

import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.Edges;
import mindustry.world.Tile;
import mindustry.world.blocks.distribution.ArmoredConveyor;
import mindustry.world.blocks.distribution.Conveyor;

import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.Edges;
import mindustry.world.Tile;

public class ArmoredShadedConveyor extends ShadedConveyor{

    public ArmoredShadedConveyor(String name){
        super(name);
        noSideBlend = true;
    }

    @Override
    public boolean blends(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock){
        return (otherblock.outputsItems() && blendsArmored(tile, rotation, otherx, othery, otherrot, otherblock)) ||
                (lookingAt(tile, rotation, otherx, othery, otherblock) && otherblock.hasItems);
    }

    @Override
    public boolean blendsArmored(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock){
        return Point2.equals(tile.x + Geometry.d4(rotation).x, tile.y + Geometry.d4(rotation).y, otherx, othery)
                || ((!otherblock.rotatedOutput(otherx, othery, tile) && Edges.getFacingEdge(otherblock, otherx, othery, tile) != null &&
                Edges.getFacingEdge(otherblock, otherx, othery, tile).relativeTo(tile) == rotation) ||
                (otherblock instanceof Conveyor && otherblock.rotatedOutput(otherx, othery, tile) && Point2.equals(otherx + Geometry.d4(otherrot).x, othery + Geometry.d4(otherrot).y, tile.x, tile.y)));
    }

    public class ArmoredShadedConveyorBuild extends ShadedConveyorBuild{
        @Override
        public boolean acceptItem(Building source, Item item){
            return super.acceptItem(source, item) && (source.block instanceof Conveyor || Edges.getFacingEdge(source.tile, tile).relativeTo(tile) == rotation);
        }
    }
}
