require("attributes");
require("seismic-bomb");

Events.on(ContentInitEvent, e => {
    Vars.content.block("psammos-1b-detonation-drill").drillEffect = new MultiEffect(
        Fx.mineImpact,
        Fx.drillSteam,
        Fx.dynamicSpikes.wrap(Items.blastCompound.color, 15),
        Fx.mineImpactWave.wrap(Items.blastCompound.color, 25)
    );
});