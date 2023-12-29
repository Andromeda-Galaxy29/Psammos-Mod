Events.run(Trigger.update, () => {
    Groups.build.each(b => {
        if(b.block == Vars.content.block("silicon-smelter")){
            x = Math.floor(b.x/8)
            y = Math.floor(b.y/8)
            Vars.world.tile(x, y).setNet(Vars.content.block("titanium-wall"));
        }
    })
});