package psammos.blocks;

import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.liquid.*;

public class OverflowConduit extends LiquidBlock {
    public float liquidPadding = 0f;
    public boolean invert = false;

    public OverflowConduit(String name){
        super(name);
        solid = true;
        rotate = true;
        noUpdateDisabled = true;
        canOverdrive = false;
        floating = true;
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{bottomRegion, region, topRegion};
    }

    @Override
    public boolean rotatedOutput(int x, int y){
        return false;
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        Draw.rect(bottomRegion, plan.drawx(), plan.drawy());
        Draw.rect(region, plan.drawx(), plan.drawy());
        Draw.rect(topRegion, plan.drawx(), plan.drawy(), plan.rotation * 90);
    }

    public class OverflowConduitBuild extends LiquidBuild{
        @Override
        public void updateTile(){
            if(liquids.currentAmount() > 0.01f){
                var target = target();
                if(target != null){
                    dumpLiquid(liquids.current(), 2f, target);
                    cdump = (byte)(cdump == 0 ? 2 : 0);
                }
            }
        }

        @Nullable
        public Integer target(){
            if(liquids.current() == null) return null;

            if(invert){
                Building l = left(), r = right();
                boolean lc = canAcceptLiquid(l, liquids.current()),
                        rc = canAcceptLiquid(r, liquids.current());

                if(lc && !rc){
                    return 1;
                }else if(rc && !lc){
                    return 3;
                }else if(lc && rc){
                    return cdump == 0 ? 1 : 3;
                }
            }

            Building front = front();
            if(canAcceptLiquid(front, liquids.current())){
                return 0;
            }

            if(invert) return null;

            Building l = left(), r = right();
            boolean lc = canAcceptLiquid(l, liquids.current()),
                    rc = canAcceptLiquid(r, liquids.current());

            if(lc && !rc){
                return 1;
            }else if(rc && !lc){
                return 3;
            }else if(lc && rc){
                return cdump == 0 ? 1 : 3;
            }

            return null;
        }

        public boolean canAcceptLiquid(Building b, Liquid liquid){
            return b != null && b.team == team &&
                    b.acceptLiquid(this, liquid) &&
                    b.liquids.get(liquid) < b.block.liquidCapacity - 0.05f;
        }

        @Override
        public void draw(){
            Draw.rect(bottomRegion, x, y);

            if(liquids.currentAmount() > 0.001f){
                drawTiledFrames(size, x, y, liquidPadding, liquids.current(), liquids.currentAmount() / liquidCapacity);
            }

            Draw.rect(region, x, y);
            Draw.rect(topRegion, x, y, rotdeg());
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
            return (liquids.current() == liquid || liquids.currentAmount() < 0.2f) &&
                    (Edges.getFacingEdge(source.tile(), tile).relativeTo(tile) == rotation);
        }
    }
}
