package psammos.type.unit;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.ai.types.*;
import mindustry.gen.*;
import mindustry.type.*;
import psammos.ai.*;

public class CrawlUnitType extends UnitType {

    public TextureRegion[] segmentCellRegions;

    public CrawlUnitType(String name) {
        super(name);
        aiController = HugAI::new;
        controller = u -> !playerControllable || (u.team.isAI() && !u.team.rules().rtsAi) ? aiController.get() : new HugCommandAI();
        constructor = CrawlUnit::create;
        omniMovement = false;
        circleTarget = true;
        faceTarget = true;
        targetAir = false;
        drawBody = false;
        drawCell = false;
    }

    @Override
    public void load() {
        super.load();
        segmentCellRegions = new TextureRegion[segments];
        for(int i = 0; i < segments; i++) {
            segmentCellRegions[i] = Core.atlas.find(name + "-segment" + i + "-cell");
        }
    }

    @Override
    public void drawCrawl(Crawlc crawl) {
        Unit unit = (Unit)crawl;
        for(int p = 0; p < 2; p++){
            TextureRegion[] regions = p == 0 ? segmentOutlineRegions : segmentRegions;

            for(int i = 0; i < segments; i++){
                float trns = Mathf.sin(crawl.crawlTime() + i * segmentPhase, segmentScl, segmentMag);

                float rot = Mathf.slerp(crawl.segmentRot(), unit.rotation, i / (float)(segments - 1));
                float tx = Angles.trnsx(rot, trns), ty = Angles.trnsy(rot, trns);

                applyColor(unit);
                Draw.rect(regions[i], unit.x + tx, unit.y + ty, rot - 90);

                // Draws the cells
                if(p != 0 && segmentCellRegions[i].found()){
                    Draw.color(cellColor(unit));
                    Draw.rect(segmentCellRegions[i], unit.x + tx, unit.y + ty, rot - 90);
                    Draw.reset();
                }
            }
        }
    }
}
