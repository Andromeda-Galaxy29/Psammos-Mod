package psammos.content;

import mindustry.type.*;
import psammos.type.PsammosSector;

public class PsammosSectors {
    public static SectorPreset

    landing, quartzValley, cavern, driedRiver, excavationSite, shatteredPathway, ancientSwamp,
    evaporatedBasin, depositoryCrevice, oilRefiningFacility, ferricSummit, craterousRange, erodedDesert,
    enfer, ruinousHollow, scaldedPlains, searingChasms, weaponTestingSite;

    public static void load(){
        landing = new PsammosSector("landing", 0){{
            difficulty = 1;
            captureWave = 15;
            alwaysUnlocked = true;
        }};

        quartzValley = new PsammosSector("quartz-valley", 3){{
            difficulty = 1;
            captureWave = 21;
        }};

        cavern = new PsammosSector("cavern", 5){{
            difficulty = 4;
            captureWave = 30;
        }};

        driedRiver = new PsammosSector("dried-river", 4){{
            difficulty = 2;
        }};

        excavationSite = new PsammosSector("excavation-site", 6){{
            difficulty = 2;
        }};

        shatteredPathway = new PsammosSector("shattered-pathway", 10){{
            difficulty = 2;
        }};

        ancientSwamp = new PsammosSector("ancient-swamp", 14){{
            difficulty = 3;
            captureWave = 18;
        }};

        evaporatedBasin = new PsammosSector("evaporated-basin", 28){{
            difficulty = 3;
        }};

        depositoryCrevice = new PsammosSector("depository-crevice", 35){{
            difficulty = 3;
            captureWave = 35;
        }};

        oilRefiningFacility = new PsammosSector("oil-refining-facility", 25){{
            difficulty = 4;
        }};

        ferricSummit = new PsammosSector("ferric-summit", 18){{
            difficulty = 4;
        }};

        craterousRange = new PsammosSector("craterous-range", 32){{
            difficulty = 4;
        }};

        erodedDesert = new PsammosSector("eroded-desert", 27){{
            difficulty = 4;
            captureWave = 35;
        }};

        enfer = new PsammosSector("enfer", 31){{
            difficulty = 2;
            captureWave = 40;
        }};

        ruinousHollow = new PsammosSector("ruinous-hollow", 39){{
            difficulty = 4;
        }};

        scaldedPlains = new PsammosSector("scalded-plains", 45){{
            difficulty = 5;
        }};

        searingChasms = new PsammosSector("searing-chasms", 48){{
            difficulty = 6;
        }};

        weaponTestingSite = new PsammosSector("weapon-testing-site", 49){{
            captureWave = 40;
            difficulty = 6;
        }};
    }
}
