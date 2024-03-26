package psammos.content;

import mindustry.content.Blocks;
import mindustry.world.meta.*;

public class PsammosAttributes {
    public static void load(){
        Attribute.add("ferric-stone");
    }

    public static void setAttributes(){
        Blocks.ferricStone.attributes.set(Attribute.get("ferric-stone"), 1);
        Blocks.ferricCraters.attributes.set(Attribute.get("ferric-stone"), 1);
    }
}
