Events.on(WorldLoadEvent, event => {
    Timer.schedule(e => {
        Vars.world.tiles.each((x, y) => {
            let tile = Vars.world.tile(x, y)
            if(tile.block() == Vars.content.block("psammos-Zz-obliterator")){
                obliterator(tile)
            }
        })
    }, 0, 1);
});

function obliterator(tile){
    if(Vars.state.isPaused()){
        return
    }
    let b = tile.build
    if(b.items.has(Vars.content.item("blast-compound"))){
        Fx.smokeCloud.at(b.x, b.y)
        b.items.clear()
    }
}