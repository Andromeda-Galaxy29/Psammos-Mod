package psammos.blocks.environment;

import arc.scene.ui.layout.Table;
import mindustry.world.Tile;

//An interface for blocks that interact with bombs.
public interface ExplodableBlock {
    //Runs when the block is exploded by a bomb
    void explode(Tile tile);

    //Sets the way the block is shown under "Explodables" in bomb stats
    void setBombStatsTable(Table b);

    //Block hardness for bomb tiers
    int hardness();
}
