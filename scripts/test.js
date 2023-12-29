Events.on(WorldLoadEvent, event => {
    Timer.schedule(e => {
        Groups.build.each(b => {
            if(b.block == Vars.content.block("psammos-1a-sieve") && b.power.status > 0){
                Fx.launchPod.at(b.x, b.y)
            }
        })
    }, 0, 1);
});