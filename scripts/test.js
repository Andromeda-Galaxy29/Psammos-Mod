Events.on(WorldLoadEvent, event => {
    Timer.schedule(e => {
        Groups.build.each(b => {
            if(b.block == Vars.content.block("psammos-1a-sieve")){
                let x = Math.floor(b.x/8)
                let y = Math.floor(b.y/8)
                Vars.world.tile(x, y).setNet(Vars.content.block("titanium-wall-large"));
            }
        })
    }, 0, 1);
});