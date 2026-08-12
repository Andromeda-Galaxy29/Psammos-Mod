package psammos.world.blocks.liquid;

import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.liquid.*;

public class OverflowConduit extends LiquidBlock {
    public float liquidPadding = 0f;
    public boolean invert = false;
    /** The percentage of liquid in the nearby block required for the gate to start overflowing */
    public float highThreshold = 0.9f;
    /** The percentage of liquid in the nearby block required for the gate to stop overflowing */
    public float lowThreshold = 0.8f;

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
        /** Whether the block in front (on the left when reversed) is considered to be overflowing */
        public boolean overflowing = false;
        /** Whether the block on the right is considered to be overflowing */
        public boolean overflowing2 = false;

        @Override
        public void updateTile(){
            updateOverflowState();

            if(liquids.currentAmount() > 0.01f){
                var target = target();
                if(target != null){
                    dumpLiquid(liquids.current(), 2f, target);
                    cdump = (byte)(cdump == 0 ? 2 : 0);
                }
            }
        }

        public void updateOverflowState() {
            if(liquids.current() == null) return;

            if (!invert) {
                if (shouldOverflow(front(), liquids.current())) {
                    overflowing = true;
                } else if (shouldStopOverflowing(front(), liquids.current())) {
                    overflowing = false;
                }
            } else {
                if (shouldOverflow(left(), liquids.current())) {
                    overflowing = true;
                } else if (shouldStopOverflowing(left(), liquids.current())) {
                    overflowing = false;
                }

                if (shouldOverflow(right(), liquids.current())) {
                    overflowing2 = true;
                } else if (shouldStopOverflowing(right(), liquids.current())) {
                    overflowing2 = false;
                }
            }
        }

        public boolean shouldOverflow(Building b, Liquid liquid) {
            return !canAcceptLiquid(b, liquid) || liquidAboveThreshold(b, liquid, highThreshold);
        }

        public boolean shouldStopOverflowing(Building b, Liquid liquid) {
            return canAcceptLiquid(b, liquid) && !liquidAboveThreshold(b, liquid, lowThreshold);
        }

        @Nullable
        public Integer target(){
            if(liquids.current() == null) return null;

            int forward = 0;
            int leftward = 1;
            int rightward = 3;

            if(!invert){
                if(!overflowing){
                    return forward;
                }

                Building l = left(), r = right();
                boolean lc = canAcceptLiquid(l, liquids.current()),
                        rc = canAcceptLiquid(r, liquids.current());

                if(lc && !rc){
                    return leftward;
                }else if(rc && !lc){
                    return rightward;
                }else if(lc && rc){
                    return cdump == 0 ? leftward : rightward;
                }
            } else {
                boolean lc = !overflowing,
                        rc = !overflowing2;

                if(lc && !rc){
                    return leftward;
                }else if(rc && !lc){
                    return rightward;
                }else if(lc && rc){
                    return cdump == 0 ? leftward : rightward;
                }

                if(canAcceptLiquid(front(), liquids.current())){
                    return forward;
                }
            }

            return null;
        }

        public boolean canAcceptLiquid(Building b, Liquid liquid){
            if (b == null || b.team != team) return false;
            if (b.block instanceof LiquidJunction) {
                b = b.getLiquidDestination(this, liquid);
            }
            return b.acceptLiquid(this, liquid) &&
                    b.liquids.get(liquid) < b.block.liquidCapacity - 0.05f;
        }

        public boolean liquidAboveThreshold(Building b, Liquid liquid, float threshold) {
            return b.liquids.get(liquid) / b.block.liquidCapacity >= threshold;
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
                    (source.relativeTo(tile) == rotation);
        }
    }
}
