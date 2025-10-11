package psammos.world.blocks.production;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Scaling;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Fx;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.logic.Ranged;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.blocks.environment.TallBlock;
import mindustry.world.blocks.heat.HeatConsumer;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;
import psammos.graphics.PDraw;

import static mindustry.Vars.*;


//This code is probably attrociously bad but if it works it works I guess
public class MechanicalArm extends Block {

    public float heatRequirement = 10f;
    public float maxEfficiency = 4f;
    public Color heatColor = new Color(1f, 0.22f, 0.22f, 0.8f);
    public float heatPulse = 0.3f, heatPulseScl = 10f;

    public int outputAmount = 5;
    public float range = tilesize * 5;
    /**Visual speed of the arm*/
    public float armSpeed = 0.05f;
    public float produceTime = 60f;

    public Color baseColor = Color.white;
    public float itemOffset = 5f;

    public TextureRegion heatRegion;
    public TextureRegion topRegion;
    public TextureRegion jointRegion;
    public TextureRegion clawRegion;
    public TextureRegion armRegion;

    public MechanicalArm(String name) {
        super(name);
        solid = true;
        update = true;
        hasItems = true;
    }

    @Override
    public void load() {
        super.load();
        heatRegion = Core.atlas.find(name + "-heat");
        topRegion = Core.atlas.find(name + "-top");
        jointRegion = Core.atlas.find(name + "-joint");
        clawRegion = Core.atlas.find(name + "-claw");
        armRegion = Core.atlas.find(name + "-arm");
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("heat", (MechanicalArmBuild entity) ->
            new Bar(() ->
                Core.bundle.format("bar.heatpercent", (int)(entity.heat + 0.01f), (int)(entity.efficiencyScale() * 100 + 0.01f)),
                () -> Pal.lightOrange,
                () -> entity.heat / heatRequirement));
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.input, heatRequirement, StatUnit.heatUnits);
        stats.add(Stat.maxEfficiency, (int)(maxEfficiency * 100f), StatUnit.percent);
        stats.add(Stat.range, range / tilesize, StatUnit.blocks);
        stats.add(Stat.drillTier, table -> {
            table.row();
            table.table(c -> {
                int i = 0;
                for(Block block : content.blocks()){
                    if(!(block instanceof TallBlock && block.itemDrop != null && (indexer.isBlockPresent(block) || state.isMenu()))) continue;
                    c.table(Styles.grayPanel, b -> {
                        b.image(block.uiIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                        b.table(info -> {
                            info.left();
                            info.add(block.localizedName).left().row();
                            info.add(block.itemDrop.emoji()).with(l -> StatValues.withTooltip(l, block.itemDrop)).left();
                        }).grow();
                    }).growX().pad(5);
                    if(++i % 2 == 0) c.row();
                }
            }).growX().colspan(table.getColumns());
        });
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashCircle(x*tilesize+offset, y*tilesize+offset, range, baseColor);

        Seq<Vec2> targets = getPossibleTargets(x, y);
        for (Vec2 v : targets){
            Drawf.selected(world.tileWorld(v.x, v.y), baseColor);
        }
    }

    public Seq<Vec2> getPossibleTargets(int x, int y){
        Seq<Vec2> targets = new Seq<>();

        int tileRange = Mathf.ceil(range / tilesize);
        int x1 = x - tileRange;
        int x2 = x + tileRange + 1;
        int y1 = y - tileRange;
        int y2 = y + tileRange + 1;
        for (int ix = x1; ix <= x2; ix++) {
            for (int iy = y1; iy <= y2; iy++) {
                if (new Vec2(x * tilesize + offset, y * tilesize + offset).dst(ix * tilesize, iy * tilesize) > range) continue;
                if (world.tile(ix, iy) != null && world.tile(ix, iy).block() instanceof TallBlock tb && tb.itemDrop != null) {
                    targets.add(new Vec2(ix * tilesize, iy * tilesize));
                }
            }
        }

        return targets;
    }

    public class MechanicalArmBuild extends Building implements HeatConsumer, Ranged {
        public float[] sideHeat = new float[4];
        public float heat = 0f;

        public Vec2 armPos = null;
        public Vec2 target = null;

        public Seq<Vec2> targets = new Seq<>();
        public int targetIndex = 0;

        public float progress = 0;

        @Override
        public void updateTile() {
            super.updateTile();
            heat = calculateHeat(sideHeat);

            if (timer(timerDump, dumpTime / timeScale)) {
                items.each((item, n) -> {
                    dump(item);
                });
            }

            if(efficiency > 0) {
                progress += getProgressIncrease(produceTime);
            }

            if (armPos == null) armPos = new Vec2(x - tilesize * size / 2f, y + tilesize * size / 2f);

            //TODO: Maybe don't make this run every frame? Might be laggy
            targets = getPossibleTargets(tileX(), tileY());
            if (targets.isEmpty()) {
                return;
            }
            if (target == null){
                target = targets.get(0);
            }

            // Move arm to target
            if (progress >= 0.5f) {
                if (armPos.dst(x, y) >= tilesize) {
                    armPos.x = Mathf.lerpDelta(armPos.x, x, armSpeed * efficiency);
                    armPos.y = Mathf.lerpDelta(armPos.y, y, armSpeed * efficiency);
                }
            }else {
                if (target != null && armPos.dst(target) >= tilesize) {
                    armPos.x = Mathf.lerpDelta(armPos.x, target.x, armSpeed * efficiency);
                    armPos.y = Mathf.lerpDelta(armPos.y, target.y, armSpeed * efficiency);
                }
            }

            // Finished producing
            if (progress >= 1f) {
                if (target != null && items.total() + outputAmount <= itemCapacity) {
                    Item currentItem = world.tileWorld(target.x, target.y).block().itemDrop;

                    if (currentItem != null){
                        consume();
                        Fx.itemTransfer.create(armPos.x, armPos.y, 0, currentItem.color, new Vec2(x, y));
                        for(int i = 0; i < outputAmount; i++){
                            offload(currentItem);
                        }
                    }

                    // Start moving to next available target
                    targetIndex += 1;
                    if (targetIndex >= targets.size) {
                        targetIndex = 0;
                    }
                    target = targets.get(targetIndex);

                    progress -= 1f;
                }
            }
        }

        @Override
        public void draw() {
            super.draw();

            drawHeat();

            float armLength = range / 2f;
            float armAngle = new Vec2(x, y).angleTo(armPos.x, armPos.y);
            float dist = new Vec2(x, y).dst(armPos.x, armPos.y);

            float angleOffset = (float) Math.acos(Mathf.clamp(dist / range)) * Mathf.radDeg;
            float sideOffset = Mathf.sinDeg(angleOffset) * armLength;
            Vec2 sideVec = new Vec2(armPos.y - y, x - armPos.x).scl(sideOffset / dist);
            Vec2 forwardVec = new Vec2(armPos.x - x, armPos.y - y).scl(0.5f);

            Vec2 jointPos = new Vec2(x, y).add(forwardVec).add(sideVec);

            Draw.z(Layer.blockOver + 0.5f);

            PDraw.spinLineSprite(armRegion, (x + jointPos.x) / 2f, (y + jointPos.y) / 2f, armAngle - angleOffset);
            Draw.rect(topRegion, x, y);
            PDraw.spinLineSprite(armRegion, (armPos.x + jointPos.x) / 2f, (armPos.y + jointPos.y) / 2f, armAngle + angleOffset);
            Draw.rect(jointRegion, jointPos.x, jointPos.y);

            if (target != null) {
                Item currentItem = world.tileWorld(target.x, target.y).block().itemDrop;
                if (currentItem != null && progress >= 0.5f) {
                    Tmp.v1.set(armPos).sub(jointPos).nor().scl(itemOffset);
                    Draw.rect(currentItem.fullIcon, armPos.x + Tmp.v1.x, armPos.y + Tmp.v1.y, itemSize, itemSize);
                }
            }

            PDraw.spinLineSprite(clawRegion, armPos.x, armPos.y, armAngle + angleOffset);
            Draw.rect(jointRegion, armPos.x, armPos.y);
        }

        public void drawHeat(){
            Draw.z(Layer.blockAdditive);
            float[] side = sideHeat();
            for(int i = 0; i < 4; i++){
                if(side[i] > 0){
                    Draw.blend(Blending.additive);
                    Draw.color(heatColor, side[i] / heatRequirement() * (heatColor.a * (1f - heatPulse + Mathf.absin(heatPulseScl, heatPulse))));
                    Draw.rect(heatRegion, x, y, i * 90f);
                    Draw.blend();
                    Draw.color();
                }
            }
            Draw.z(Layer.block);
        }

        @Override
        public void drawSelect(){
            Drawf.dashCircle(x, y, range, baseColor);

            Seq<Vec2> targets = getPossibleTargets(tileX(), tileY());
            for (Vec2 v : targets){
                Drawf.selected(world.tileWorld(v.x, v.y), baseColor);
            }
        }

        @Override
        public float[] sideHeat() {
            return sideHeat;
        }

        @Override
        public float heatRequirement() {
            return heatRequirement;
        }

        @Override
        public float range() {
            return range;
        }

        @Override
        public float efficiencyScale(){
            return Math.min(heat / heatRequirement, maxEfficiency);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            armPos = new Vec2(read.f(), read.f());
            target = new Vec2(read.f(), read.f());
            progress = read.f();
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(armPos.x);
            write.f(armPos.y);

            write.f(target.x);
            write.f(target.y);

            write.f(progress);
        }
    }
}
