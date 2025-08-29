package psammos.content;

import arc.graphics.*;
import mindustry.game.Gamemode;
import psammos.planet.PsammosPlanetGenerator;
import mindustry.content.*;
import mindustry.game.Team;
import mindustry.graphics.g3d.*;
import mindustry.type.*;
import mindustry.world.meta.Env;

public class PsammosPlanets {
    public static Planet psammos;

    public static void load(){
        psammos = new Planet("psammos", Planets.sun, 1f, 2){{
            generator = new PsammosPlanetGenerator();

            meshLoader = () -> new HexMesh(this, 5);
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 42, 2f, 0.13f, 5, Color.valueOf("edcbac").a(0.75f), 3, 0.7f, 1f, 0.43f),
                    new HexSkyMesh(this, 69, 2.4f, 0.12f, 5, Color.valueOf("dfb794").a(0.75f), 3, 0.7f, 1f, 0.45f)
            );

            alwaysUnlocked = true;
            accessible = true;
            allowLaunchToNumbered = false;
            allowLaunchLoadout = false;
            allowSectorInvasion = false;
            startSector = 0;
            allowWaveSimulation = true;
            clearSectorOnLose = true;
            allowWaves = true;
            prebuildBase = false;
            defaultCore = PsammosBlocks.coreDust;

            defaultEnv = Env.oxygen | Env.terrestrial | Env.groundOil;

            orbitSpacing = 1;
            drawOrbit = true;
            orbitRadius = 35;
            rotateTime = 12 * 60;

            atmosphereRadIn = 0;
            atmosphereRadOut = 0.3f;
            sectorSeed = 1204;
            bloom = false;
            visible = true;
            landCloudColor = Color.valueOf("edcbac");
            atmosphereColor = Color.valueOf("1c513aaa");
            iconColor = Color.valueOf("31ffa4");
            hasAtmosphere = true;

            ruleSetter = r -> {
                r.waveTeam = PsammosTeams.erimos;
                r.showSpawns = true;
                r.teams.get(r.waveTeam).rtsAi = false;

                r.weather.addAll(
                        new Weather.WeatherEntry(PsammosWeathers.lightSandstorm){{
                            always = true;
                        }},
                        new Weather.WeatherEntry(PsammosWeathers.heavySandstorm)
                );

                if(r.mode() == Gamemode.attack || r.mode() == Gamemode.pvp){
                    r.weather.add(new Weather.WeatherEntry(PsammosWeathers.smog));
                }
            };
        }};
    }
}
