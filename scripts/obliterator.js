Events.on(WorldLoadEvent, event => {
    Timer.schedule(e => {
        Groups.build.each(b => {f(b)})
    }, 0, 1);
});

function f(b){
    if(b.block == Vars.content.block("psammos-Zz-obliterator")){
        let result = false
        Vars.content.items().each(i => {
            if(i == Vars.content.item("blast-compound")){
                return
            }
            if(b.items.has(i)){
                result = true
            }
        })
        b.enabled = result
    }
}