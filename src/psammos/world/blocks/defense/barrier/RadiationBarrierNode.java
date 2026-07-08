package psammos.world.blocks.defense.barrier;

import arc.Core;
import arc.struct.ArrayMap;
import arc.struct.Seq;
import mindustry.gen.Building;
import mindustry.ui.Bar;
import mindustry.world.meta.Stat;
import psammos.type.RadiationStack;
import psammos.type.RadiationType;
import psammos.world.blocks.power.RadiationPowerGenerator;
import psammos.world.blocks.radiation.RadiationConsumer;
import psammos.world.meta.PsammosStats;

public class RadiationBarrierNode extends BarrierNode{
    /** Radiation requirements for 100% efficiency of one node */
    public Seq<RadiationStack> radiationRequirements = new Seq<>();

    public RadiationBarrierNode(String name) {
        super(name);
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.input, PsammosStats.radiations(radiationRequirements));
    }

    @Override
    public void setBars() {
        super.setBars();
        radiationRequirements.forEach(req -> {
            addBar(req.type.name, (RadiationBarrierNodeBuild b) -> new Bar(
                    () -> Core.bundle.format("bar.psammos-radiation-amount",
                            req.type.localizedName,
                            b.radiations.containsKey(req.type) ? b.radiations.get(req.type) : 0),
                    () -> req.type.color,
                    () -> b.radiations.containsKey(req.type) ? b.radiations.get(req.type) / req.amount : 0
            ));
        });
    }

    public class RadiationBarrierNodeBuild extends BarrierNodeBuild implements RadiationConsumer {
        public Seq<Building> radiationInputs = new Seq<>();
        public ArrayMap<RadiationType, Float> radiations = new ArrayMap<>();

        @Override
        public void updateTile() {
            radiations = calculateRadiationTypes(this, radiationInputs);
            super.updateTile();
        }

        @Override
        public float nodeEfficiency() {
            return efficiencyFromRequirements(radiations, radiationRequirements, graph.nodes.size);
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
