Events.run(Trigger.update, () => {
    Groups.build.each(b => {
        if(b.block == Vars.content.block("silicon-smelter")){
            let x = Math.floor(b.x/8)
            let y = Math.floor(b.y/8)
            Vars.world.tile(x, y).setNet(Vars.content.block("titanium-wall"));
        }
    })
});