Events.on(BlockBuildEndEvent, event => {
    Damage.damage(event.tile.x, event.tile.y, 6 * 8, 60);
    Fx.instBomb.at(event.tile.x, event.tile.y)
});