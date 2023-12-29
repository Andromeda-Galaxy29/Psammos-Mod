Events.run(Trigger.update, () => {
    Groups.build.each(b => {
        if(b.block == Vars.content.block("copper-wall")){
            Vars.world.tile(b.x, b.y).setNet(Vars.content.block("titanium-wall"));
        }
    })
});