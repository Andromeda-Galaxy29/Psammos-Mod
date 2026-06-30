package psammos.world.draw;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.math.geom.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import psammos.graphics.*;
import psammos.type.RadiationStack;
import psammos.world.blocks.radiation.*;

import static mindustry.Vars.tilesize;

public class DrawRadiationBeams extends DrawBlock {
    public TextureRegion beam, beamEnd;

    public float maxRadiation = 80;
    public float minBeamScale = 0.2f;
    public float maxBeamScale = 1f;

    @Override
    public void load(Block block) {
        super.load(block);
        beam = Core.atlas.find("psammos-radiation-beam");
        beamEnd = Core.atlas.find("psammos-radiation-beam-end");
    }

    @Override
    public void draw(Building build) {
        if (!(build instanceof RadiationEmitter emitter)){
            return;
        }

        Draw.z(Layer.effect);
        for (int rotation = 0; rotation < 4; rotation++){
            RadiationStack radStack = emitter.outputRadiation()[rotation];

            if (radStack == null || radStack.type == null || radStack.amount == 0){
                continue;
            }

            Tile target = emitter.findRadiationTarget(build, rotation);
            float dx = Geometry.d4x[rotation];
            float dy = Geometry.d4y[rotation];
            float emittedOffset = build.block.size * tilesize * 0.5f + emitter.emittedBeamOffset();
            Color color = radStack.type.color.cpy();
            float scale = Mathf.lerp(minBeamScale, maxBeamScale, Mathf.clamp(radStack.amount / maxRadiation));

            if (target != null){
                float incomingOffset = tilesize * 0.5f;
                if (target.build instanceof RadiationConsumer consumer){
                    incomingOffset += consumer.incomingBeamOffset();
                }
                PDraw.laser(radStack.type.beam, radStack.type.beamEnd,
                        build.x + dx * emittedOffset, build.y + dy * emittedOffset,
                        target.worldx() - dx * incomingOffset, target.worldy() - dy * incomingOffset,
                        scale, color);
                Draw.color();
            }else{
                float rangeOffset = emitter.radBeamRange() * tilesize * 1.2f;
                PDraw.laser(radStack.type.beam, radStack.type.beamEnd,
                        build.x + dx * emittedOffset, build.y + dy * emittedOffset, color,
                        build.x + dx * rangeOffset, build.y + dy * rangeOffset, color.cpy().a(0),
                        scale);
            }
        }
        Draw.z(Layer.block);
    }
}
