package psammos.type;

import arc.graphics.Color;
import mindustry.type.Planet;
import mindustry.type.SectorPreset;
import psammos.content.PsammosPlanets;

public class PsammosSector extends SectorPreset {
    public PsammosSector(String name, Planet planet, int sector) {
        super(name, planet, sector);

        outlineRadius = 3;
        outlineColor = Color.valueOf("#333333");
    }

    public PsammosSector(String name, int sector) {
        super(name, PsammosPlanets.psammos, sector);
    }
}
