Events.on(WorldLoadEvent, event => {
    Timer.schedule(e => {
        Vars.world.tiles.each((x, y) => {
            let b = Vars.world.tile(x, y).build
            if(b != null){
                obliterator(b)
            }
        })
    }, 0, 1);
});

function obliterator(b){
    if(Vars.state.isPaused()){
        return
    }
    if(b.block == Vars.content.block("psammos-Zz-obliterator") && b.items.has(Vars.content.item("blast-compound"))){
        Fx.smokeCloud.at(b.x, b.y)
        b.items.clear()
    }
}