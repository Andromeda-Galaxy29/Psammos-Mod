package psammos.world.blocks.liquid;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.Junction;
import mindustry.world.blocks.liquid.*;

import static mindustry.Vars.renderer;
import static mindustry.Vars.world;

public class BetterLiquidJunction extends LiquidJunction {

    public TextureRegion midRegion;

    public BetterLiquidJunction(String name) {
        super(name);
    }

    @Override
    public void load(){
        super.load();
        bottomRegion = Core.atlas.find(name+"-bottom");
        midRegion = Core.atlas.find(name+"-mid");
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{bottomRegion, region};
    }

    public class BetterLiquidJunctionBuild extends LiquidJunctionBuild {

        @Override
        public void draw() {
            Draw.rect(bottomRegion, x, y);

            for(int i = 0; i < 4; i++){

                Building other = findNextBuild(tileX(), tileY(), i);

                if(other != null && other.team == this.team() && other instanceof Conduit.ConduitBuild){
                    Conduit.ConduitBuild b = (Conduit.ConduitBuild) other;

                    Draw.color(b.liquids.current().color);
                    Draw.alpha(b.liquids.currentAmount() / b.block.liquidCapacity * (b.liquids.current().gas ? 0.6f : 1));
                    Draw.rect(liquidRegion, x, y, i * 90);
                    Draw.rect(liquidRegion, x, y, (i + 2) * 90);
                    Draw.reset();
                }
            }

            Draw.rect(region, x, y);
        }

        public Building findNextBuild(int x, int y, int rot){
            int cx = x + Geometry.d4x(rot);
            int cy = y + Geometry.d4y(rot);
            Building b = world.build(cx, cy);
            while(b instanceof LiquidJunctionBuild){ //Skips all other junctions until a different building is found
                cx += Geometry.d4x(rot);
                cy += Geometry.d4y(rot);
                b = world.build(cx, cy);
            }
            return b;
        }
    }
}
