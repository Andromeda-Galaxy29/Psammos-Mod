package psammos.world.blocks.radiation;

import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import mindustry.gen.Building;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.StaticWall;
import psammos.type.RadiationStack;

import static mindustry.Vars.world;

public interface RadiationEmitter {
    RadiationStack[] outputRadiation();
    float radBeamRange();
    default float emittedBeamOffset() {
        return 0;
    }

    /** Gets the center point of the specified side of the build*/
    default Point2 side(Building build, int rotation){
        int x = build.tileX() + Geometry.d4x[rotation] * Mathf.ceil(build.block.size / 2f);
        int y = build.tileY() + Geometry.d4y[rotation] * Mathf.ceil(build.block.size / 2f);
        return new Point2(x, y);
    }

    default void handleRadiationEmission(Building build, int rotation){
        Tile target = findRadiationTarget(build, rotation);
        if (target != null && target.build instanceof RadiationConsumer consumer){
            consumer.addRadiationInput(build);
        }
    }

    default void handleRadiationEmission(Building build){
        for (int rotation = 0; rotation < 4; rotation++){
            handleRadiationEmission(build, rotation);
        }
    }

    /** Finds the tile radiation hits if the building emits it in the specified direction.
     * Returns null if no target was found */
    default Tile findRadiationTarget(Building build, int rotation){
        if (!(build instanceof RadiationEmitter)){
            return null;
        }
        RadiationEmitter emitter = (RadiationEmitter) build;

        int x = side(build, rotation).x;
        int y = side(build, rotation).y;
        int dist = 0;
        while (!(world.tile(x, y) == null ||
                world.tile(x, y).block() instanceof StaticWall ||
                world.tile(x, y).build instanceof RadiationConsumer consumer &&
                        emitter.outputRadiation()[rotation] != null &&
                        world.tile(x, y).build.team == build.team &&
                        consumer.acceptsRadiation(emitter.outputRadiation()[rotation].type, (rotation + 2) % 4))){
            x += Geometry.d4x(rotation);
            y += Geometry.d4y(rotation);
            dist++;
            if (dist >= emitter.radBeamRange()){
                return null;
            }
        }
        return world.tile(x, y);
    }
}
