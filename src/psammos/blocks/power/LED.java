package psammos.blocks.power;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.ui.*;
import mindustry.world.blocks.power.PowerDiode;

import static mindustry.Vars.*;

public class LED extends PowerDiode {
    public float brightness = 0.9f;
    public float radius = 200f;

    public LED(String name){
        super(name);

        configurable = true;
        saveConfig = true;

        config(Integer.class, (LEDBuild tile, Integer value) -> tile.color = value);
    }

    @Override
    public void init(){
        lightRadius = radius*2.5f;
        clipSize = Math.max(clipSize, lightRadius * 3f);
        emitLight = true;

        super.init();
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, radius * 0.75f, Pal.placing);
    }

    public class LEDBuild extends PowerDiodeBuild{
        public int color = Pal.accent.rgba();
        public float smoothTime = 1f;

        @Override
        public void control(LAccess type, double p1, double p2, double p3, double p4){
            if(type == LAccess.color){
                color = Tmp.c1.fromDouble(p1).rgba8888();
            }

            super.control(type, p1, p2, p3, p4);
        }

        @Override
        public double sense(LAccess sensor){
            if(sensor == LAccess.color) return Tmp.c1.set(color).toDoubleBits();
            return super.sense(sensor);
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y, 0);
            Draw.rect(arrow, x, y, rotate ? rotdeg() : 0);

            Draw.color(Tmp.c1.set(color), 0.5f);
            Draw.rect(arrow, x, y, rotate ? rotdeg() : 0);
            Draw.color();
        }

        @Override
        public void updateTile(){
            super.updateTile();
            smoothTime = Mathf.lerpDelta(smoothTime, timeScale, 0.1f);
        }

        @Override
        public void buildConfiguration(Table table){
            table.button(Icon.pencil, Styles.cleari, () -> {
                ui.picker.show(Tmp.c1.set(color).a(0.5f), false, res -> configure(res.rgba()));
                deselect();
            }).size(40f);
        }

        @Override
        public boolean onConfigureBuildTapped(Building other){
            if(this == other){
                deselect();
                return false;
            }

            return true;
        }

        @Override
        public void drawLight(){
            Drawf.light(x, y, lightRadius * Math.min(smoothTime, 2f), Tmp.c1.set(color), brightness);
        }

        @Override
        public Integer config(){
            return color;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.i(color);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            color = read.i();
        }
    }
}
