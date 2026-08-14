package psammos.world.blocks.liquid;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.liquid.*;

public class BetterLiquidJunction extends LiquidJunction {
    public TextureRegion plugRegion1, plugRegion2;

    public BetterLiquidJunction(String name) {
        super(name);
        drawCached = false;
        drawDynamic = true;
        update = true;
    }

    @Override
    public void load(){
        super.load();
        bottomRegion = Core.atlas.find(name+"-bottom");
        plugRegion1 = Core.atlas.find(name+"-plug0");
        plugRegion2 = Core.atlas.find(name+"-plug1");
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{bottomRegion, region};
    }

    public class BetterLiquidJunctionBuild extends LiquidJunctionBuild {
        public Building[] sources = new Building[4];
        public Liquid[] junctionLiquids = new Liquid[4];
        public float[] junctionAmounts = {0, 0, 0, 0};

        @Override
        public void updateTile() {
            super.updateTile();
            for (int i = 0; i< 4; i++) {
                junctionAmounts[i] = Mathf.lerp(
                        junctionAmounts[i],
                        sources[i] == null || sources[i].dead() ? 0f :
                        sources[i] instanceof BetterLiquidJunctionBuild blj ? blj.junctionAmounts[i] :
                        sources[i].liquids.get(junctionLiquids[i]) / sources[i].block.liquidCapacity,
                        0.15f
                );
            }
        }

        @Override
        public void draw() {
            Draw.rect(bottomRegion, x, y);

            for(int i = 0; i < 4; i++){
                if (junctionLiquids[i] != null) {
                    Draw.color(junctionLiquids[i].color);
                    Draw.alpha(junctionAmounts[i] * (junctionLiquids[i].gas ? 0.6f : 1f));
                    Draw.rect(liquidRegion, x, y, i * 90);
                    if (junctionAmounts[(i + 2) % 4] < 0.01f || junctionLiquids[(i + 2) % 4] == null) {
                        Draw.rect(liquidRegion, x, y, (i + 2) * 90);
                    }
                    Draw.reset();
                }
            }

            Draw.rect(region, x, y);

            for(int i = 0; i < 4; i++) {
                TextureRegion plugRegion = i <= 1 ? plugRegion1 : plugRegion2;
                if (nearby(i) == null || !nearby(i).block.hasLiquids) {
                    Draw.rect(plugRegion, x, y, i * 90);
                }
            }
        }

        @Override
        public Building getLiquidDestination(Building source, Liquid liquid) {
            int fromDir = this.relativeTo(source);
            sources[fromDir] = source;
            junctionLiquids[fromDir] = liquid;

            return super.getLiquidDestination(source, liquid);
        }
    }
}
