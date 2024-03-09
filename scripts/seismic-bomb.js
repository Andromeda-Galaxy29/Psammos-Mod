Events.on(BlockBuildEndEvent, event => {
    if (event.tile.block() == Vars.content.block("psammos-3a-seismic-bomb")) {
        Damage.damage(event.tile.x * 8, event.tile.y * 8, 3 * 8, 60);
        Fx.instBomb.at(event.tile.x * 8, event.tile.y * 8)
        for (let x = event.tile.x - 1; x <= event.tile.x + 1; x++) {
            for (let y = event.tile.y - 1; y <= event.tile.y + 1; y++) {
                if (Vars.world.tile(x, y).floor() == Vars.content.block("psammos-3d-oil-deposit") ||
                    Vars.world.tile(x, y).floor() == Vars.content.block("psammos-3e-small-oil-deposit")) {
                    
                    Vars.world.tile(x, y).setFloorNet(Vars.content.block("tar"))
                    Vars.world.tile(x, y).setOverlayNet(Vars.content.block("air"))
                    Vars.world.tile(x, y).setNet(Vars.content.block("air"))
                }
            }
        }
    }
});