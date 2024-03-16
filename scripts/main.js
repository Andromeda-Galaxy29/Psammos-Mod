require("attributes");
require("seismic-bomb");

Events.on(ContentInitEvent, e => {
    Vars.content.block("psammos-1b-detonation-drill").drillEffect = new MultiEffect(
        Fx.mineImpact,
        Fx.drillSteam,
        Fx.mineImpactWave.wrap(Items.blastCompound.color, 15)
    );

    Vars.content.planet("psammos-psammos").ruleSetter = r => {
        r.waveTeam = Team.blue;
        r.showSpawns = true;

        let permanentSandstorm = new Weather.WeatherEntry(Weathers.sandstorm);
        permanentSandstorm.always = true;
        r.weather.add(permanentSandstorm);
    };
});