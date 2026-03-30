package psammos.world.meta;

import arc.graphics.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.ui.*;
import psammos.type.*;
import mindustry.world.meta.*;

import static mindustry.Vars.iconMed;

public class PsammosStats {
    public static final StatUnit radiationUnits = new StatUnit("psammos-radiation-units", "[#6ddac3]\uECC8[]");

    public static StatValue radiations(Seq<RadiationStack> stacks){
        return table -> {
            stacks.forEach(stack -> {
                table.add(displayRadiation(stack)).padRight(5);
            });
        };
    }

    public static StatValue radiations(RadiationStack... stacks){
        return table -> {
            for(RadiationStack stack : stacks){
                table.add(displayRadiation(stack)).padRight(5);
            }
        };
    }

    public static Table displayRadiation(RadiationStack stack){
        Table t = new Table();

        t.add(new Stack(){{
            add(new Image(stack.type.uiIcon).setScaling(Scaling.fit));

            if(stack.amount != 0){
                Table t = new Table().left().bottom();
                t.add(Strings.autoFixed(stack.amount, 2)).style(Styles.outlineLabel);
                add(t);
            }
        }}).size(iconMed).padRight(3  + (stack.amount != 0 ? (Strings.autoFixed(stack.amount, 2).length() - 1) * 10 : 0));

        t.add(stack.type.localizedName);

        return t;
    }
}
