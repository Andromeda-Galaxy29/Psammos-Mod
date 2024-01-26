Events.on(BlockBuildEndEvent, event => {
    if (event.tile.block == Vars.content.block("psammos-3a-seismic-bomb")){
        Damage.damage(event.tile.x*8, event.tile.y*8, 6 * 8, 60);
        Fx.instBomb.at(event.tile.x*8, event.tile.y*8)
    }
});