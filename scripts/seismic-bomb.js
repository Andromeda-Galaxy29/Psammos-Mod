Events.on(BlockBuildEndEvent, event => {
    print(event.tile.x, event.tile.y, event.tile.block)
    Fx.instBomb.at(event.tile.x, event.tile.y)
});