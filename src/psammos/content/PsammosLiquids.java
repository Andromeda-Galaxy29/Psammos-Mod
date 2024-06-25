package psammos.content;

import arc.graphics.*;
import mindustry.content.StatusEffects;
import mindustry.type.*;

public class PsammosLiquids {
    public static Liquid
    quicksand, coldWater, fuel, methane, ammonia;

    public static void load() {
        quicksand = new Liquid("quicksand", Color.valueOf("d3ae8d")){{
            heatCapacity = 0.3f;
            viscosity = 0.8f;
            coolant = false;
            effect = PsammosStatusEffects.quicksandSlowed;
            boilPoint = 0.5f;
            gasColor = Color.grays(0.9f);
        }};

        coldWater = new Liquid("cold-water", Color.valueOf("b6b2ff")){{
            heatCapacity = 0.7f;
            viscosity = 0.55f;
            temperature = 0.3f;
            coolant = true;
            effect = StatusEffects.freezing;
            boilPoint = 0.5f;
            gasColor = Color.grays(0.9f);
        }};

        fuel = new Liquid("fuel", Color.valueOf("d58f49")){{
            heatCapacity = 0.7f;
            flammability = 1.3f;
            explosiveness = 0.6f;
            viscosity = 0.55f;
            coolant = false;
            boilPoint = 0.5f;
            gasColor = Color.grays(0.5f);
        }};

        methane = new Liquid("methane", Color.valueOf("99a5aa")){{
            gas = true;
            flammability = 1.2f;
            explosiveness = 1.5f;
            coolant = false;
        }};

        ammonia = new Liquid("ammonia", Color.valueOf("d194f3")){{
            gas = true;
            heatCapacity = 0.9f;
            temperature = 0.45f;
            flammability = 0.75f;
            explosiveness = 1.75f;
            coolant = true;
        }};
    }
}
