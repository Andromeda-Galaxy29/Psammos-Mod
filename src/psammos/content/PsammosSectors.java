package psammos.content;

import mindustry.type.*;

public class PsammosSectors {
    public static SectorPreset

    landing, quartzValley, cavern, driedRiver, ancientSwamp, oilRefiningFacility;

    public static void load(){
        landing = new SectorPreset("landing", PsammosPlanets.psammos, 0){{
            difficulty = 1;
            captureWave = 15;
            alwaysUnlocked = true;
        }};

        quartzValley = new SectorPreset("quartz-valley", PsammosPlanets.psammos, 3){{
            difficulty = 1;
            captureWave = 20;
        }};

        cavern = new SectorPreset("cavern", PsammosPlanets.psammos, 5){{
            difficulty = 3;
            captureWave = 30;
        }};

        driedRiver = new SectorPreset("dried-river", PsammosPlanets.psammos, 4){{
            difficulty = 2;
        }};

        ancientSwamp = new SectorPreset("ancient-swamp", PsammosPlanets.psammos, 14){{
            difficulty = 3;
            captureWave = 25;
        }};

        oilRefiningFacility = new SectorPreset("oil-refining-facility", PsammosPlanets.psammos, 14){{
            difficulty = 5;
        }};
    }
}
