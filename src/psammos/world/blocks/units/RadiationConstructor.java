package psammos.world.blocks.units;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import mindustry.gen.Building;
import mindustry.graphics.*;
import mindustry.ui.Bar;
import mindustry.world.blocks.payloads.*;
import mindustry.world.meta.*;
import psammos.type.*;
import psammos.world.blocks.radiation.RadiationConsumer;
import psammos.world.meta.*;

import static mindustry.Vars.*;

public class RadiationConstructor extends Constructor {
    public Seq<RadiationStack> radiationRequirements = new Seq<>();
    public float maxEfficiency = 3f;
    public Color color = Pal.accent;

    public RadiationConstructor(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.input, PsammosStats.radiations(radiationRequirements));
        stats.add(Stat.maxEfficiency, (int)(maxEfficiency * 100f), StatUnit.percent);
    }

    @Override
    public void setBars() {
        super.setBars();
        radiationRequirements.forEach(req -> {
            addBar(req.type.name, (RadiationConstructorBuild b) -> new Bar(
                    () -> Core.bundle.format("bar.psammos-radiation-percent",
                            req.type.localizedName,
                            b.radiations.containsKey(req.type) ? b.radiations.get(req.type) : 0,
                            b.radiations.containsKey(req.type) ? (int) Math.min(b.radiations.get(req.type) / req.amount * 100, maxEfficiency * 100) : 0),
                    () -> req.type.color,
                    () -> b.radiations.containsKey(req.type) ? b.radiations.get(req.type) / req.amount : 0
            ));
        });
    }

    public class RadiationConstructorBuild extends ConstructorBuild implements RadiationConsumer {
        public Seq<Building> radiationInputs = new Seq<>();
        public ArrayMap<RadiationType, Float> radiations = new ArrayMap<>();

        @Override
        public float efficiencyScale() {
            float efficiencyPercent = maxEfficiency;
            for(RadiationStack req : radiationRequirements){
                if (radiations.containsKey(req.type)){
                    if (radiations.get(req.type) / req.amount < efficiencyPercent){
                        efficiencyPercent = radiations.get(req.type) / req.amount;
                    }
                }else{
                    efficiencyPercent = 0;
                }
            }
            return Math.min(efficiencyPercent, maxEfficiency);
        }

        @Override
        public void updateTile() {
            radiations = calculateRadiationTypes(this, radiationInputs);
            super.updateTile();
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            Draw.rect(outRegion, x, y, rotdeg());

            var recipe = recipe();
            if(recipe != null){
                Drawf.shadow(x, y, recipe.size * tilesize * 2f, progress / recipe.buildTime);
                Draw.draw(Layer.blockBuilding, () -> {
                    Draw.color(color);

                    for(TextureRegion region : recipe.getGeneratedIcons()){
                        Shaders.blockbuild.region = region;
                        Shaders.blockbuild.time = time;
                        Shaders.blockbuild.progress = progress / recipe.buildTime;

                        Draw.rect(region, x, y, recipe.rotate ? rotdeg() : 0);
                        Draw.flush();
                    }

                    Draw.color();
                });
                Draw.z(Layer.blockBuilding + 1);
                Draw.color(color, heat);

                Lines.lineAngleCenter(x + Mathf.sin(time, 10f, tilesize / 2f * recipe.size + 1f), y, 90, recipe.size * tilesize + 1f);

                Draw.reset();
            }

            drawPayload();

            Draw.z(Layer.blockBuilding + 1.1f);
            Draw.rect(topRegion, x, y);
        }

        @Override
        public void addRadiationInput(Building build) {
            if (!radiationInputs.contains(build)){
                radiationInputs.add(build);
            }
        }

        @Override
        public boolean acceptsRadiation(RadiationType type, int from) {
            for (RadiationStack req : radiationRequirements){
                if (type == req.type){
                    return true;
                }
            }
            return false;
        }
    }
}
