require("attributes");
require("seismic-bomb");

Events.on(ContentInitEvent, e => {
    Vars.content.block("psammos-1b-detonation-drill").drillEffect = new MultiEffect(
        Fx.mineImpact,
        Fx.drillSteam,
        Fx.dynamicSpikes.wrap(Items.blastCompound.color, 30f),
        Fx.mineImpactWave.wrap(Items.blastCompound.color, 45f)
    );
});