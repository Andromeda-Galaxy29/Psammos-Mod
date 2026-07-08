package psammos.world.blocks.heat;

import arc.Core;
import arc.struct.ArrayMap;
import arc.struct.Seq;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.heat.HeatProducer;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import psammos.type.RadiationStack;
import psammos.type.RadiationType;
import psammos.world.blocks.radiation.RadiationConsumer;
import psammos.world.meta.PsammosStats;

public class RadiationHeatProducer extends HeatProducer {
    public Seq<RadiationStack> radiationRequirements = new Seq<>();
    public float maxEfficiency = 3f;

    public RadiationHeatProducer(String name) {
        super(name);
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.input, PsammosStats.radiations(radiationRequirements));
        stats.add(Stat.maxEfficiency, (int)(maxEfficiency * 100f), StatUnit.percent);
    }

    @Override
    public void setBars() {
        super.setBars();
        removeBar("heat");
        addBar("heat", (RadiationHeatProducerBuild b) -> new Bar(() ->
                Core.bundle.format("bar.heatpercent", (int)(b.heat + 0.01f), (int)(b.efficiencyScale() * 100 + 0.01f)),
                () -> Pal.lightOrange,
                b::heatFrac));

        radiationRequirements.forEach(req -> {
            addBar(req.type.name, (RadiationHeatProducerBuild b) -> new Bar(
                    () -> Core.bundle.format("bar.psammos-radiation-percent",
                            req.type.localizedName,
                            b.radiations.containsKey(req.type) ? b.radiations.get(req.type) : 0,
                            b.radiations.containsKey(req.type) ? (int) Math.min(b.radiations.get(req.type) / req.amount * 100, maxEfficiency * 100) : 0),
                    () -> req.type.color,
                    () -> b.radiations.containsKey(req.type) ? b.radiations.get(req.type) / req.amount : 0
            ));
        });
    }

    public class RadiationHeatProducerBuild extends HeatProducerBuild implements RadiationConsumer {
        public Seq<Building> radiationInputs = new Seq<>();
        public ArrayMap<RadiationType, Float> radiations = new ArrayMap<>();

        @Override
        public float efficiencyScale() {
            return efficiencyFromRequirements(radiations, radiationRequirements, maxEfficiency);
        }

        @Override
        public void updateTile() {
            radiations = calculateRadiationTypes(this, radiationInputs);
            super.updateTile();
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
