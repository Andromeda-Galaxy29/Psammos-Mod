package psammos.content;

import mindustry.type.*;

public class PsammosSectors {
    public static SectorPreset

    landing, quartzValley, cavern, driedRiver, ancientSwamp, evaporatedBasin, coruscatedCrevice,
    oilRefiningFacility, ferricSummit, craterousRange, erodedDesert, ruinousHollow, scaldedPlains;

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

        evaporatedBasin = new SectorPreset("evaporated-basin", PsammosPlanets.psammos, 28){{
            difficulty = 3;
        }};

        coruscatedCrevice = new SectorPreset("coruscated-crevice", PsammosPlanets.psammos, 35){{
            difficulty = 3;
            captureWave = 35;
        }};

        oilRefiningFacility = new SectorPreset("oil-refining-facility", PsammosPlanets.psammos, 25){{
            difficulty = 4;
        }};

        ferricSummit = new SectorPreset("ferric-summit", PsammosPlanets.psammos, 18){{
            difficulty = 4;
        }};

        craterousRange = new SectorPreset("craterous-range", PsammosPlanets.psammos, 32){{
            difficulty = 4;
        }};

        erodedDesert = new SectorPreset("eroded-desert", PsammosPlanets.psammos, 27){{
            difficulty = 4;
            captureWave = 35;
        }};

        ruinousHollow = new SectorPreset("ruinous-hollow", PsammosPlanets.psammos, 39){{
            difficulty = 4;
        }};

        scaldedPlains = new SectorPreset("scalded-plains", PsammosPlanets.psammos, 45){{
            difficulty = 5;
        }};
    }
}
