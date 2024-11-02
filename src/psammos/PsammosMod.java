package psammos;

import arc.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.ui.fragments.*;
import psammos.content.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;
import psammos.graphics.*;
import psammos.ui.*;

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
            if (Core.settings.getBool("psammos-custom-menu", true)) {
                try {
                    Reflect.set(MenuFragment.class, Vars.ui.menufrag, "renderer", new PsammosMenuRenderer());
                } catch (Exception except) {
                    Log.err("Failed to replace renderer", except);
                }
            }


            if(settings.getBool("psammos-warning", true)) {
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

        PsammosStatusEffects.load();

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
            t.checkPref("psammos-custom-menu", true);

            // Thank you, developers of Subvoyage, for this code
            t.pref(new ButtonPref(bundle.get("setting.psammos-clear-tech-tree"),Icon.trash,() -> {
                ui.showConfirm("@confirm", bundle.get("setting.psammos-clear-tech-tree.confirm"), () -> {
                    PsammosPlanets.psammos.techTree.reset();
                    for(TechTree.TechNode node : PsammosPlanets.psammos.techTree.children){
                        node.reset();
                    }
                    content.each(c -> {
                        if(c instanceof UnlockableContent u && c.minfo != null && c.minfo.mod != null && c.minfo.mod.name.equals("psammos")){
                            u.clearUnlock();
                        }
                    });
                    settings.remove("unlocks");
                });
            }));
            t.pref(new ButtonPref(bundle.get("setting.psammos-clear-campaign"),Icon.trash,() -> {
                ui.showConfirm("@confirm", bundle.get("setting.psammos-clear-campaign.confirm"), () -> {
                    Seq<Saves.SaveSlot> toDelete = Seq.with();
                    control.saves.getSaveSlots().each(s -> {
                        if(s.getSector() == null) return;
                        if(s.getSector().planet == PsammosPlanets.psammos) {
                            toDelete.add(s);
                            Log.info("Deleted Psammos sector: "+ s.getSector().id+ (s.getSector().preset != null ? " "+s.getSector().preset.localizedName : ""));
                        }
                    });
                    toDelete.each(Saves.SaveSlot::delete);
                    ui.showInfoOnHidden(bundle.get("setting.psammos-clear-campaign-close.confirm"), () -> {
                        Core.app.exit();
                    });
                });
            }));
        });
    }
}
