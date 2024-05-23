package psammos;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.type.*;
import psammos.content.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class PsammosMod extends Mod{

    public PsammosMod(){

    }

    @Override
    public void init(){
        for (Planet planet : Vars.content.planets()) {
            if (planet != PsammosPlanets.psammos) {
                planet.hiddenItems.addAll(PsammosItems.psammosItems);
            }
        }

        loadSettings();

        Events.on(ClientLoadEvent.class, e -> {
            if(settings.getBool("psammos-warning")) {
                Time.runTask(10f, () -> {
                    BaseDialog dialog = new BaseDialog("Psammos Mod");
                    dialog.cont.add(bundle.get("dialog.psammos-warning")).row();
                    dialog.cont.table(t -> {
                        t.button("OK", dialog::hide).size(100f, 50f).pad(10f);
                        t.button(bundle.get("dialog.dont-show-this-again"), ()->{
                            settings.put("psammos-warning", false);
                            dialog.hide();
                        }).size(300f, 50f).pad(10f);
                    });
                    dialog.show();
                });
            }
        });
    }

    @Override
    public void loadContent(){
        PsammosShaders.load();
        PsammosCacheLayers.load();

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

    void loadSettings(){
        ui.settings.addCategory(bundle.get("setting.psammos-category"), "psammos-settings-icon", t -> {
            t.checkPref("psammos-warning", true);
        });
    }

}
