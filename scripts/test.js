Events.on(WorldLoadEvent, event => {
    Timer.schedule(e => {
        Groups.build.each(b => {
            if(b.block == Vars.content.block("psammos-1a-sieve")){
                Fx.shockwave.at(b.x, b.y)
            }
        })
    }, 0, 3);
});