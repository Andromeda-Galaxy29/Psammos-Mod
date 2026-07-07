package psammos.world.blocks.radiation;

import arc.*;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.geom.Geometry;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.Bar;
import mindustry.world.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import psammos.PPal;
import psammos.type.*;
import psammos.world.draw.*;

import static mindustry.Vars.tilesize;

public class Lens extends Block {

    public DrawBlock drawer = new DrawMulti(new DrawDefault(), new DrawDirectionalRegion("-arrow"), new DrawRadiationBeams());

    public float visualMaxRadiation = 50;
    public int range = 10;
    public float shadowOffset = -0.25f;
    public float shadowAlpha = 0.25f;
    public boolean concave = false;

    TextureRegion topRegion;
    TextureRegion topShadowRegion;

    public Lens(String name) {
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
        topRegion = Core.atlas.find(name + "-top");
        topShadowRegion = Core.atlas.find(name + "-top-shadow");
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.range, range, StatUnit.blocks);
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("psammos-radiation", (LensBuild b) -> new Bar(
                () -> b.barRad() == null ? Core.bundle.get("bar.psammos-radiation") : Core.bundle.format("bar.psammos-radiation-amount", b.barRad().type.localizedName, b.barRad().amount),
                () -> b.barRad() == null ? Color.clear : b.barRad().type.color,
                () -> b.barRad() == null ?  0f : b.barRad().amount / visualMaxRadiation
        ));
    }

    @Override
    protected TextureRegion[] icons() {
        return new TextureRegion[]{region, topRegion};
    }

    @Override
    public TextureRegion getPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        return region;
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        drawer.drawPlan(this, plan, list);

        Draw.alpha(shadowAlpha);
        Draw.rect(topShadowRegion, plan.drawx() + shadowOffset, plan.drawy() + shadowOffset, plan.rotation * 90);
        Draw.alpha(1);
        if (plan.rotation % 2 == 0) {
            Draw.rect(topRegion, plan.drawx(), plan.drawy());
        }else{
            Draw.rect(topRegion, plan.drawx(), plan.drawy(), 90);
        }
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);
        int maxLen = range + size/2;
        for(int i = 0; i < 4; i++){
            if(concave ? i == (rotation + 2) % 4 : i != rotation) continue;

            int gx = Geometry.d4x[i];
            int gy = Geometry.d4y[i];
            Drawf.dashLine(PPal.desertGlass,
                    x * tilesize + gx * (tilesize * size / 2f + 2),
                    y * tilesize + gy * (tilesize * size / 2f + 2),
                    x * tilesize + gx * maxLen * tilesize,
                    y * tilesize + gy * maxLen * tilesize
            );
        }
    }

    public class LensBuild extends Building implements RadiationEmitter, RadiationConsumer {
        public Seq<Building> radiationInputs = new Seq<>();
        public RadiationStack[] sideRadiation;

        public RadiationStack barRad(){
            if (sideRadiation == null) {
                return null;
            }

            RadiationStack radiation = null;
            if (!concave){
                for (int i = 0; i < 4; i++){
                    if (i != rotation && sideRadiation[i] != null){
                        if (radiation == null){
                            radiation = new RadiationStack(sideRadiation[i].type, 0);
                        }
                        if (radiation.type == sideRadiation[i].type){
                            radiation.amount += sideRadiation[i].amount;
                        }
                    }
                }
                return radiation;
            }
            return sideRadiation[(rotation + 2) % 4];
        }

        @Override
        public void draw() {
            drawer.draw(this);

            Draw.z(Layer.blockOver);
            Draw.alpha(shadowAlpha);
            Draw.rect(topShadowRegion, x + shadowOffset, y + shadowOffset, rotdeg());
            Draw.alpha(1);
            if (rotation % 2 == 0) {
                Draw.rect(topRegion, x, y);
            }else{
                Draw.rect(topRegion, x, y, 90);
            }
            Draw.reset();
        }

        @Override
        public void drawLight(){
            super.drawLight();
            drawer.drawLight(this);
        }

        @Override
        public void updateTile() {
            super.updateTile();
            sideRadiation = calculateSideRadiation(this, radiationInputs);
            handleRadiationEmission(this);
        }

        @Override
        public RadiationStack[] outputRadiation() {
            if (sideRadiation == null) {
                return null;
            }

            RadiationStack radiation = null;
            RadiationStack[] output = new RadiationStack[4];
            if (!concave){
                for (int i = 0; i < 4; i++){
                    if (i != rotation && sideRadiation[i] != null){
                        if (radiation == null){
                            radiation = new RadiationStack(sideRadiation[i].type, 0);
                        }
                        if (radiation.type == sideRadiation[i].type){
                            radiation.amount += sideRadiation[i].amount;
                        }
                    }
                }
                output[rotation] = radiation;
            }else{
                radiation = sideRadiation[(rotation + 2) % 4];
                if (radiation != null) {
                    output[rotation] = new RadiationStack(radiation.type, radiation.amount / 3f);
                    output[(rotation + 1) % 4] = new RadiationStack(radiation.type, radiation.amount / 3f);
                    output[(rotation + 3) % 4] = new RadiationStack(radiation.type, radiation.amount / 3f);
                }
            }
            return output;
        }

        @Override
        public float radBeamRange() {
            return range;
        }

        @Override
        public void addRadiationInput(Building build) {
            if (!radiationInputs.contains(build)){
                radiationInputs.add(build);
            }
        }

        @Override
        public boolean acceptsRadiation(RadiationType type, int from) {
            return true;
        }
    }
}
