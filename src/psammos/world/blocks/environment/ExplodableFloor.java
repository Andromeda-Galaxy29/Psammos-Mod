package psammos.world.blocks.environment;

import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

public class ExplodableFloor extends Floor implements ExplodableBlock{
    public Floor replacement;
    public int hardness = 1;

    public ExplodableFloor(String name){
        super(name);

        this.replacement = Blocks.sand.asFloor();
    }

    @Override
    public void explode(Tile tile){
        tile.setFloorNet(replacement);
        if(replacement.isLiquid){
            tile.setOverlayNet(Blocks.air);
            tile.setNet(Blocks.air);
        }
    }

    @Override
    public void setBombStatsTable(Table b){
        b.image(uiIcon).size(40).pad(10f).left().scaling(Scaling.fit);
        b.image(Icon.right).size(30).center().grow();

        Block outputBlock = replacement;
        if(outputBlock instanceof Floor && ((Floor) outputBlock).isLiquid) {
            b.image(((Floor) outputBlock).liquidDrop.uiIcon).size(40).pad(10f).right().scaling(Scaling.fit);
        }else{
            b.image(outputBlock.uiIcon).size(40).pad(10f).right().scaling(Scaling.fit);
        }
    }

    @Override
    public int hardness() {
        return hardness;
    }
}
