Events.on(BlockBuildEndEvent, event => {
    Damage.damage(event.tile.x*8, event.tile.y*8, 6 * 8, 60);
    Fx.instBomb.at(event.tile.x*8, event.tile.y*8)
});