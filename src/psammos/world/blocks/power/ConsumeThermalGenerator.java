package psammos.world.blocks.power;

import mindustry.world.blocks.power.ThermalGenerator;

public class ConsumeThermalGenerator extends ThermalGenerator {
    public ConsumeThermalGenerator(String name) {
        super(name);
    }

    public class ConsumeThermalGeneratorBuild extends ThermalGeneratorBuild {
        @Override
        public void updateTile() {
            if (efficiency > 0){
                super.updateTile();
            }else{
                productionEfficiency = 0;
            }
        }

        @Override
        public void drawLight() {
            if (efficiency > 0){
                super.drawLight();
            }
        }

        @Override
        public float totalProgress() {
            return efficiency > 0 ? super.totalProgress() : 0;
        }
    }
}
