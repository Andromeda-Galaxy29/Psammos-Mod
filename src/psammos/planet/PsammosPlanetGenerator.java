package psammos.planet;

import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.Tmp;
import arc.util.noise.*;
import mindustry.content.Blocks;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.world.Block;

public class PsammosPlanetGenerator extends PlanetGenerator {
    public float heightScl = 1f, octaves = 8, persistence = 0.7f, heightPow = 3f, heightMult = 1.2f;

    @Override
    public float getHeight(Vec3 position){
        return Mathf.pow(rawHeight(position), heightPow) * heightMult;
    }

    float rawHeight(Vec3 position){
        return Simplex.noise3d(seed, octaves, persistence, 1f/heightScl, 10f + position.x, 10f + position.y, 10f + position.z);
    }

    @Override
    public void getColor(Vec3 position, Color out) {
        Block block = rawHeight(position) < 0.55 ? Blocks.sand : Blocks.ferricStone;
        out.set(block.mapColor).a(1f - block.albedo);
    }
}
