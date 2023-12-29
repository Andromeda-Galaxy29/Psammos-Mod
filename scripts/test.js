Events.on(WorldLoadEvent, event => {
    Timer.schedule(e => {
        Groups.build.each(b => {
            if(b.block == Vars.content.block("copper-wall")){
                Vars.world.tile(b.x, b.y).setNet(Vars.content.block("titanium-wall"))
            }
        })
    }, 0, 1);
});