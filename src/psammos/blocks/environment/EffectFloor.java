package psammos.blocks.environment;

import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;

public class EffectFloor extends Floor {
    public Effect effect = Fx.fire;
    public Color effectColor = Pal.vent;
    public float effectChance = 1;

    public EffectFloor(String name){
        super(name);
    }

    @Override
    public boolean updateRender(Tile tile) {
        return true;
    }

    @Override
    public void renderUpdate(UpdateRenderState state) {
        if(Mathf.chanceDelta(effectChance) && state.tile.block() == Blocks.air) {
            effect.at(state.tile.worldx(), state.tile.worldy(), effectColor);
        }
    }
}
