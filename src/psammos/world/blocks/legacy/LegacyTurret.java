package psammos.world.blocks.legacy;

import arc.struct.*;
import arc.util.io.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.blocks.legacy.*;
import mindustry.world.meta.*;

public class LegacyTurret extends LegacyBlock {
    public Block replacement = Blocks.duo;

    public LegacyTurret(String name){
        super(name);
        liquidCapacity = 20f;
        update = true;
        solid = true;
        attacks = true;
        priority = TargetPriority.turret;
        group = BlockGroup.turrets;
        flags = EnumSet.of(BlockFlag.turret);
    }

    @Override
    public void removeSelf(Tile tile){
        int rot = tile.build == null ? 0 : tile.build.rotation;
        tile.setBlock(replacement, tile.team(), rot);
    }

    public class LegacyTurretBuild extends Building {

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            if(revision >= 1){
                read.f();
                read.f();
            }
        }
    }
}
