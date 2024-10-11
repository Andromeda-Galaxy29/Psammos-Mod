package psammos.world.blocks.legacy;

import arc.util.io.*;
import mindustry.gen.*;
import mindustry.world.blocks.legacy.*;

public class LegacyPowerGenerator extends LegacyBlock {

    public LegacyPowerGenerator(String name){
        super(name);
        sync = true;
        update = true;
        solid = true;
        hasPower = true;
    }

    public class LegacyPowerGeneratorBuild extends Building {

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            read.f();
            if(revision >= 1){
                read.f();
            }
        }
    }
}
