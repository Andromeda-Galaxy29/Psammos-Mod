Events.on(WorldLoadEvent, event => {
    Timer.schedule(e => {
        Groups.build.each(b => {obliterator(b)})
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