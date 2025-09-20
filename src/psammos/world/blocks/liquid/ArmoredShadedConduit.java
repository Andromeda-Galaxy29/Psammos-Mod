package psammos.world.blocks.liquid;

import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.liquid.*;

public class ArmoredShadedConduit extends ShadedConduit{

    public ArmoredShadedConduit(String name){
        super(name);
        leaks = false;
    }

    @Override
    public boolean blends(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock){
        return (otherblock.outputsLiquid && blendsArmored(tile, rotation, otherx, othery, otherrot, otherblock)) ||
                (lookingAt(tile, rotation, otherx, othery, otherblock) && otherblock.hasLiquids) || otherblock instanceof LiquidJunction;
    }

    public class ArmoredShadedConduitBuild extends ShadedConduitBuild{
        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
            return super.acceptLiquid(source, liquid) && (tile == null || source.block instanceof Conduit || source.block instanceof DirectionLiquidBridge || source.block instanceof LiquidJunction ||
                    source.tile.absoluteRelativeTo(tile.x, tile.y) == rotation || !source.proximity.contains(this));
        }
    }
}
