package psammos.draw;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Angles;
import mindustry.content.*;
import mindustry.gen.Building;
import mindustry.type.*;
import mindustry.world.draw.*;

import static mindustry.Vars.*;

public class DrawItemPile extends DrawBlock {
    public Item item = Items.copper;
    public float radius = 32;
    public int itemAmount = 8;

    public DrawItemPile(){
        super();
    }

    public DrawItemPile(Item item, float radius, int itemAmount){
        super();
        this.item = item;
        this.radius = radius;
        this.itemAmount = itemAmount;
    }

    @Override
    public void draw(Building build) {
        Angles.randLenVectors(build.id, Math.round(build.items.get(item) / (float)build.block.itemCapacity * itemAmount), radius, (x, y) -> {
            Draw.rect(item.fullIcon, build.x + x, build.y + y, itemSize, itemSize);
        });
    }
}
