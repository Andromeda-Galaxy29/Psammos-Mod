package psammos.world.blocks.radiation;

import arc.Core;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.ui.Bar;
import mindustry.world.*;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import psammos.PPal;
import psammos.content.PsammosRadTypes;
import psammos.type.*;
import psammos.world.draw.*;
import psammos.world.meta.PsammosStats;

import static mindustry.Vars.state;
import static mindustry.Vars.tilesize;

public class SolarCollector extends Block {

    public DrawBlock drawer = new DrawMulti(new DrawDefault(), new DrawDirectionalRegion(), new DrawRadiationBeams());

    public float radOutputAmount = 10f;
    public RadiationType radOutputType = PsammosRadTypes.light;
    public int range = 10;

    public SolarCollector(String name) {
        super(name);
        update = true;
        rotate = true;
        rotateDraw = false;
        clipSize = range * tilesize * 2;
        solid = true;
    }

    @Override
    public void load() {
        super.load();
        drawer.load(this);
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
        addBar("psammos-radiation", (SolarCollectorBuild b) -> new Bar(
                () -> Core.bundle.format("bar.psammos-radiation-amount", radOutputType.localizedName, radOutputAmount * b.efficiency),
                () -> radOutputType.color,
                () -> b.efficiency
        ));
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        drawer.drawPlan(this, plan, list);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        int maxLen = range + size / 2;
        int gx = Geometry.d4x[rotation];
        int gy = Geometry.d4y[rotation];
        Drawf.dashLine(radOutputType.color,
                x * tilesize + gx * (tilesize * size / 2f + 2),
                y * tilesize + gy * (tilesize * size / 2f + 2),
                x * tilesize + gx * maxLen * tilesize,
                y * tilesize + gy * maxLen * tilesize
        );
    }

    public class SolarCollectorBuild extends Building implements RadiationEmitter {

        @Override
        public void draw() {
            drawer.draw(this);
        }

        @Override
        public void drawLight(){
            super.drawLight();
            drawer.drawLight(this);
        }

        @Override
        public void updateTile() {
            super.updateTile();
            efficiency = enabled ?
                    state.rules.solarMultiplier * Mathf.maxZero(Attribute.light.env() +
                            (state.rules.lighting ?
                                    1f - state.rules.ambientLight.a :
                                    1f
                            )) : 0f;

            handleRadiationEmission(this);
        }

        @Override
        public RadiationStack[] outputRadiation() {
            RadiationStack[] output = new RadiationStack[4];
            output[rotation] = new RadiationStack(radOutputType, radOutputAmount * efficiency);
            return output;
        }

        @Override
        public float radBeamRange() {
            return range;
        }
    }
}
