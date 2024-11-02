package psammos.world.blocks.environment;

import arc.math.*;
import arc.scene.ui.layout.*;
import arc.util.Scaling;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.gen.Icon;
import mindustry.gen.Sounds;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

public class ExplodableWall extends StaticWall implements ExplodableBlock {
    /** The chance that a boulder appears upon the wall being exploded */
    public float boulderChance = 0.3f;
    /** The boulder that appears upon the wall being exploded */
    public Block boulder = Blocks.air;
    public int hardness = 2;

    public ExplodableWall(String name) {
        super(name);
    }

    @Override
    public void explode(Tile tile) {
        tile.setNet(Mathf.chance(boulderChance) ? boulder : Blocks.air);
        Fx.breakProp.wrap(mapColor).at(tile.getX(), tile.getY());
        Sounds.rockBreak.at(tile.getX(), tile.getY());
    }

    @Override
    public void setBombStatsTable(Table b) {
        b.image(uiIcon).size(40).pad(10f).left().scaling(Scaling.fit);
        b.image(Icon.right).size(30).center().grow();
        b.image(Icon.cancel).size(40).pad(10f).right().scaling(Scaling.fit);
    }

    @Override
    public int hardness() {
        return hardness;
    }
}
