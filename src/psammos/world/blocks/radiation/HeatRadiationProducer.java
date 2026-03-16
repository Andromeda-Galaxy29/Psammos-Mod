package psammos.world.blocks.radiation;

import arc.Core;
import arc.math.geom.Geometry;
import mindustry.graphics.Drawf;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.HeatCrafter;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import psammos.type.RadiationStack;
import psammos.type.RadiationType;
import psammos.world.meta.PsammosStats;

import static mindustry.Vars.tilesize;

public class HeatRadiationProducer extends HeatCrafter {
    public RadiationType radOutputType = RadiationType.light;
    public float radOutputAmount = 10f;
    public int range = 10;

    public HeatRadiationProducer(String name) {
        super(name);
        rotate = true;
        rotateDraw = false;
        clipSize = range * tilesize * 2;
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.range, range, StatUnit.blocks);
        stats.add(Stat.output, PsammosStats.radiations(new RadiationStack(radOutputType, radOutputAmount)));
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("psammos-radiation", (HeatRadiationProducerBuild b) -> new Bar(
                () -> Core.bundle.format("bar.psammos-radiation-amount", radOutputType.localizedName(), radOutputAmount * b.efficiency),
                () -> radOutputType.color,
                () -> b.efficiency
        ));
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        int maxLen = range + size/2;
        int gx = Geometry.d4x[rotation];
        int gy = Geometry.d4y[rotation];
        Drawf.dashLine(radOutputType.color,
                x * tilesize + gx * (tilesize * size / 2f + 2),
                y * tilesize + gy * (tilesize * size / 2f + 2),
                x * tilesize + gx * maxLen * tilesize,
                y * tilesize + gy * maxLen * tilesize
        );
    }

    public class HeatRadiationProducerBuild extends HeatCrafterBuild implements RadiationEmitter{

        @Override
        public void updateTile() {
            super.updateTile();
            handleRadiationEmission(this, rotation);
        }

        @Override
        public RadiationStack[] outputRadiation() {
            RadiationStack[] output = new RadiationStack[4];
            if (radOutputType != null) {
                output[rotation] = new RadiationStack(radOutputType, radOutputAmount * efficiency);
            }
            return output;
        }

        @Override
        public float radBeamRange() {
            return range;
        }
    }
}
