Events.on(WorldLoadEvent, event => {
    Timer.schedule(e => {
        Groups.build.each(b => {burnAura(b)})
    }, 0, 1);
});

function burnAura(b){
    if(Vars.state.isPaused()){
        return
    }
    if(b.block == Vars.content.block("psammos-1a-sieve") && b.power.status > 0){
        Fx.spawnShockwave.at(b.x, b.y)
        Groups.unit.each(u => {
            let range = 8
            if(Math.abs(u.x - b.x) <= range*8 && Math.abs(u.y - b.y) <= range*8 && u.team != b.team){
                u.apply(Vars.content.statusEffect("burning"), 1*60)
            }
        })
    }
}