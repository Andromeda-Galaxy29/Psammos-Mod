package psammos.world.draw;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;

public class DrawDirectionalRegion extends DrawBlock {
    public TextureRegion region1, region2;
    public int rotOffset = 0;
    public String suffix = "-top";

    public DrawDirectionalRegion(){}

    public DrawDirectionalRegion(String suffix){
        this.suffix = suffix;
    }

    public DrawDirectionalRegion(int rotOffset){
        this.rotOffset = rotOffset;
    }

    public DrawDirectionalRegion(String suffix, int rotOffset){
        this.suffix = suffix;
        this.rotOffset = rotOffset;
    }

    @Override
    public void load(Block block){
        region1 = Core.atlas.find(block.name + suffix + "1");
        region2 = Core.atlas.find(block.name + suffix + "2");
    }

    @Override
    public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list){
        Draw.rect(Mathf.mod((plan.rotation + rotOffset), 4) > 1 ? region2 : region1, plan.drawx(), plan.drawy(), (plan.rotation + rotOffset) * 90);
    }

    @Override
    public void draw(Building build) {
        float rotdeg = (build.rotation + rotOffset) * 90;
        Draw.rect(Mathf.mod((build.rotation + rotOffset), 4) > 1 ? region2 : region1, build.x, build.y, rotdeg);
    }
}
