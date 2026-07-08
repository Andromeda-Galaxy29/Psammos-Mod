package psammos.world.blocks.crafting;

import arc.Core;
import arc.struct.ArrayMap;
import arc.struct.Seq;
import mindustry.gen.Building;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import psammos.type.RadiationStack;
import psammos.type.RadiationType;
import psammos.world.blocks.radiation.RadiationConsumer;
import psammos.world.meta.PsammosStats;

/** A crafter that requires radiation to craft. */
public class RadiationCrafter extends GenericCrafter {
    /** Base radiation requirements for 100% efficiency. */
    public Seq<RadiationStack> radiationRequirements = new Seq<>();
    /** After radiation meets this requirement, excess radiation will be scaled by this number. */
    public float overexposureScale = 1f;
    /** Maximum possible efficiency after overexposure. */
    public float maxEfficiency = 3f;

    public RadiationCrafter(String name) {
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
        radiationRequirements.forEach(req -> {
            addBar(req.type.name, (RadiationCrafterBuild b) -> new Bar(
                    () -> Core.bundle.format("bar.psammos-radiation-percent",
                            req.type.localizedName,
                            b.radiations.containsKey(req.type) ? b.radiations.get(req.type) : 0,
                            b.radiations.containsKey(req.type) ? (int) Math.min(b.radiations.get(req.type) / req.amount * 100, maxEfficiency * 100) : 0),
                    () -> req.type.color,
                    () -> b.radiations.containsKey(req.type) ? b.radiations.get(req.type) / req.amount : 0
            ));
        });
    }

    public class RadiationCrafterBuild extends GenericCrafterBuild implements RadiationConsumer {
        public Seq<Building> radiationInputs = new Seq<>();
        public ArrayMap<RadiationType, Float> radiations = new ArrayMap<>();

        @Override
        public void updateTile() {
            radiations = calculateRadiationTypes(this, radiationInputs);

            super.updateTile();
        }

        @Override
        public float efficiencyScale(){
            return efficiencyFromRequirements(radiations, radiationRequirements, maxEfficiency);
        }

        @Override
        public void addRadiationInput(Building build) {
            if (!radiationInputs.contains(build)){
                radiationInputs.add(build);
            }
        }

        @Override
        public boolean acceptsRadiation(RadiationType type, int from) {
            for(int i = 0; i < radiationRequirements.size; i++){
                RadiationStack req = radiationRequirements.get(i);
                if (type == req.type){
                    return true;
                }
            }
            return false;
        }
    }
}
