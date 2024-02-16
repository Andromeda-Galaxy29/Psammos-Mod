Events.on(WorldLoadEvent, event => {
    Timer.schedule(e => {
        Groups.build.each(b => {f(b)})
    }, 0, 1);
});

function f(b){
    if(b.block == Vars.content.block("psammos-Zz-obliterator")){
        let result = false
        for(let i of Vars.content.items()){
            if(i == Vars.content.item("blast-compound")){
                continue
            }
            if(b.items.has(i)){
                result = true
                break
            }
        }
        b.enabled = result
    }
}