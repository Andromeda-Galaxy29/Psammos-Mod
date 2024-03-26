package psammos;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.type.*;
import psammos.content.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

public class PsammosMod extends Mod{

    public PsammosMod(){

        Events.on(ClientLoadEvent.class, e -> {
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("Psammos Mod");
                dialog.cont.add("Warning: Psammos Mod is under development. Everything can change in future updates.").row();
                dialog.cont.button("Ok", dialog::hide).size(100f, 50f);
                dialog.show();
            });
        });
    }

    @Override
    public void init(){
        for (Planet planet : Vars.content.planets()) {
            if (planet != PsammosPlanets.psammos) {
                planet.hiddenItems.addAll(PsammosItems.psammosItems);
            }
        }
    }

    @Override
    public void loadContent(){
        PsammosAttributes.load();

        PsammosItems.load();
        PsammosLiquids.load();
        PsammosUnitTypes.load();
        PsammosBlocks.load();

        PsammosAttributes.setAttributes();

        PsammosPlanets.load();
        PsammosSectors.load();
        PsammosTechTree.load();
    }

}
