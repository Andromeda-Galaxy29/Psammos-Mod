Events.on(WorldLoadEvent, event => {
    Timer.schedule(e => {
        Groups.build.each(b => {
            if(b.block == Vars.content.block("psammos-1a-sieve")){
                let x = Math.floor(b.x/8)
                let y = Math.floor(b.y/8)
                Fx.shockwave.at(x, y)
            }
        })
    }, 0, 3);
});