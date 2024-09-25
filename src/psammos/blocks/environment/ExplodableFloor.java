package psammos.blocks.environment;

import arc.math.Interp;
import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

public class ExplodableFloor extends Floor {
    public Floor replacement;

    public ExplodableFloor(String name){
        super(name);

        this.replacement = Blocks.sand.asFloor();
    }
}
