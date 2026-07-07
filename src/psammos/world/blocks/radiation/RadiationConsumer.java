package psammos.world.blocks.radiation;

import arc.struct.*;
import mindustry.gen.Building;
import psammos.type.RadiationStack;
import psammos.type.RadiationType;

public interface RadiationConsumer {
    void addRadiationInput(Building build);
    default boolean acceptsRadiation(RadiationType type, int from) {return true;};
    default float incomingBeamOffset() {
        return 0;
    }

    default RadiationStack calculateHighestRadiation(Building build, Seq<Building> inputs){
        ArrayMap<RadiationType, Float> all = calculateRadiationTypes(build, inputs);
        RadiationType type = null;
        float amount = 0;
        for (int i = 0; i < all.size; i++) {
            if (type == null || all.getValueAt(i) > amount) {
                type = all.getKeyAt(i);
                amount = all.getValueAt(i);
            }
        }
        return new RadiationStack(type, amount);
    }

    default RadiationStack[] calculateSideRadiation(Building build, Seq<Building> inputs){
        RadiationStack[] sideRadiation = new RadiationStack[4];
        inputs.forEach(b -> {
            if (!(b instanceof RadiationEmitter emitter) || b.dead){
                return;
            }
            int rotation = build.relativeTo(b);

            if (emitter.outputRadiation()[(rotation + 2) % 4] != null){
                RadiationType type = emitter.outputRadiation()[(rotation + 2) % 4].type;
                float amount = emitter.outputRadiation()[(rotation + 2) % 4].amount;

                if (sideRadiation[rotation] == null){
                    sideRadiation[rotation] = new RadiationStack(type, 0);
                }
                if (sideRadiation[rotation].type == type){
                    sideRadiation[rotation].amount += amount;
                }
            }
        });
        inputs.clear();
        return sideRadiation;
    }

    default ArrayMap<RadiationType, Float> calculateRadiationTypes(Building build, Seq<Building> inputs){
        ArrayMap<RadiationType, Float> radiations = new ArrayMap<>();
        inputs.forEach(b -> {
            if (!(b instanceof RadiationEmitter emitter) || b.dead){
                return;
            }
            int rotation = build.relativeTo(b);

            if (emitter.outputRadiation()[(rotation + 2) % 4] != null){
                RadiationType type = emitter.outputRadiation()[(rotation + 2) % 4].type;
                float amount = emitter.outputRadiation()[(rotation + 2) % 4].amount;

                if (!radiations.containsKey(type)){
                    radiations.put(type, amount);
                }else{
                    radiations.setValue(radiations.indexOfKey(type), radiations.get(type) + amount);
                }
            }
        });
        inputs.clear();
        return radiations;
    }
}
