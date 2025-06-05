package psammos.world.blocks.units;

import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.blocks.units.UnitCargoLoader;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.tilesize;

// Unit cargo loaders already do what I'm trying to do, so why rewrite all the code for it
public class RepairDroneAssembler extends UnitCargoLoader {
    public float range = 10 / 8f;
    // Used only for stats, the actual repair speed is defined in the unit
    public float statsRepairSpeed = 0;

    public RepairDroneAssembler(String name) {
        super(name);
        hasItems = false;
        polyRadius = 5f;
        polyStroke = 1.6f;
        polySides = 4;
        polyColor = Pal.heal;
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("progress", (UnitTransportSourceBuild e) -> new Bar("bar.progress", Pal.ammo, e::progress));
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.range, range / 8f, StatUnit.blocks);
        stats.add(Stat.productionTime, unitBuildTime / 60f, StatUnit.seconds);
        stats.add(Stat.repairSpeed, statsRepairSpeed, StatUnit.perSecond);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        drawPotentialLinks(x, y);
        drawOverlay((float)(x * 8) + this.offset, (float)(y * 8) + this.offset, rotation);
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.placing);
    }

    public class RepairDroneAssemblerBuild extends UnitTransportSourceBuild {
        public RepairDroneAssemblerBuild() {
            super();
        }

        @Override
        public void drawSelect(){
            super.drawSelect();

            Drawf.dashCircle(x + offset, y + offset, range, Pal.placing);
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            if(!hasItems){
                return false;
            }
            return items.total() < itemCapacity;
        }
    }
}
