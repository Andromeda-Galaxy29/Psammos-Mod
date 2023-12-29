Events.on(WorldLoadEvent, event => {
    Timer.schedule(e => {
        Groups.build.each(b => {burnAura(b)})
    }, 0, 1);
});

function burnAura(b){
    if(GameState.isPaused()){
        return
    }
    if(b.block == Vars.content.block("psammos-1a-sieve") && b.power.status > 0){
        Fx.launchPod.at(b.x, b.y)
        Groups.unit.each(u => {
            if(Math.abs(u.x - b.x) <= 5*8 && Math.abs(u.y - b.y) <= 5*8){
                u.apply(Vars.content.statusEffect("burning"), 1*60)
            }
        })
    }
}