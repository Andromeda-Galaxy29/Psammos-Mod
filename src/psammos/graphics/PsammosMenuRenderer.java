package psammos.graphics;

import mindustry.graphics.*;
import arc.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;
import arc.math.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.noise.*;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import psammos.content.*;

import static mindustry.Vars.*;

public class PsammosMenuRenderer extends MenuRenderer {
    private static final float darkness = 0.3f;
    private final int width = !mobile ? 100 : 60, height = !mobile ? 50 : 40;

    private int cacheFloor, cacheWall;
    private Camera camera = new Camera();
    private Mat mat = new Mat();
    private FrameBuffer shadows;
    private CacheBatch batch;
    private float time = 0f;
    private final float flyerRot = 45f;
    private final int flyers = (int) (Math.random() < 0.2 ? random(35) : random(15));
    private final UnitType flyerType = (UnitType) randomFromArray(new UnitType[]{PsammosUnitTypes.sine, PsammosUnitTypes.helix, PsammosUnitTypes.trisect, PsammosUnitTypes.gradient, PsammosUnitTypes.ascent});
    private final Block[][] biomes = new Block[][]{
            new Block[]{Blocks.sand, Blocks.ferricStone},
            new Block[]{Blocks.sand, PsammosBlocks.quartzFloor},
            new Block[]{PsammosBlocks.peatFloor, PsammosBlocks.burningPeatFloor},
            new Block[]{PsammosBlocks.burningPeatFloor, PsammosBlocks.ash},
            new Block[]{PsammosBlocks.peatFloor, PsammosBlocks.packedPeatFloor},
            new Block[]{PsammosBlocks.peatFloor, PsammosBlocks.decayingFloor},
            new Block[]{Blocks.darksand, Blocks.carbonStone},
            new Block[]{Blocks.carbonStone, PsammosBlocks.slate},
            new Block[]{PsammosBlocks.slate, PsammosBlocks.osmicStone},
            new Block[]{PsammosBlocks.osmicStone, PsammosBlocks.desertGlass},
            new Block[]{PsammosBlocks.metallicFloor, PsammosBlocks.rustedMetallicFloor}
    };
    private final Block[] noOreFloors = {
            PsammosBlocks.burningPeatFloor,
            PsammosBlocks.ash,
            PsammosBlocks.decayingFloor,
            PsammosBlocks.metallicFloor,
            PsammosBlocks.rustedMetallicFloor
    };

    public PsammosMenuRenderer(){
        Time.mark();
        generate();
        cache();
        Log.debug("Time to generate menu: @", Time.elapsed());
    }

    private float random(float to){
        return random(0, to);
    }
    private float random(float from, float to){
        return (float) (Math.random() * (to - from)) + from;
    }
    private Object randomFromArray(Object[] array){
        return array[(int)random(array.length)];
    }

    private void generate(){
        //suppress tile change events.
        world.setGenerating(true);

        Tiles tiles = world.resize(width, height);
        Seq<Block> ores = Seq.with(PsammosBlocks.osmiumOre, PsammosBlocks.silverOre);
        shadows = new FrameBuffer(width, height);
        int offset = (int) random(100000);
        int s1 = offset, s2 = offset + 1, s3 = offset + 2;
        Block[] selected = (Block[]) randomFromArray(biomes);

        Block ore1 = ores.get((int)random(ores.size));
        ores.remove(ore1);
        Block ore2 = ores.get((int)random(ores.size));

        double tr1 = random(0.65f, 0.85f);
        double tr2 = random(0.65f, 0.85f);

        Block floord = selected[0], walld = selected[0].asFloor().wall;
        Block floord2 = selected[1], walld2 = selected[1].asFloor().wall;

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                Block floor = floord;
                Block ore = Blocks.air;
                Block wall = Blocks.air;

                if(Simplex.noise2d(s1, 3, 0.5, 1/20.0, x, y) > 0.5){
                    wall = walld;
                }

                if(Simplex.noise2d(s3, 3, 0.5, 1/20.0, x, y) > 0.5){
                    floor = floord2;
                    if(wall != Blocks.air){
                        wall = walld2;
                    }
                }

                if(Simplex.noise2d(s2, 3, 0.3, 1/30.0, x, y) > tr1){
                    ore = ore1;
                }

                if(Simplex.noise2d(s2, 2, 0.2, 1/15.0, x, y+99999) > tr2){
                    ore = ore2;
                }

                Tile tile;
                tiles.set(x, y, (tile = new CachedTile()));
                tile.x = (short)x;
                tile.y = (short)y;
                tile.setFloor(floor.asFloor());
                tile.setBlock(wall);

                boolean canPlaceOre = true;
                for (Block b : noOreFloors){
                    if(floor == b){
                        canPlaceOre = false;
                        break;
                    }
                }
                if (canPlaceOre){
                    tile.setOverlay(ore);
                }
            }
        }

        //don't fire a world load event, it just causes lag and confusion
        world.setGenerating(false);
    }

    private void cache(){

        //draw shadows
        Draw.proj().setOrtho(0, 0, shadows.getWidth(), shadows.getHeight());
        shadows.begin(Color.clear);
        Draw.color(Color.black);

        for(Tile tile : world.tiles){
            if(tile.block() != Blocks.air){
                Fill.rect(tile.x + 0.5f, tile.y + 0.5f, 1, 1);
            }
        }

        Draw.color();
        shadows.end();

        Batch prev = Core.batch;

        Core.batch = batch = new CacheBatch(new SpriteCache(width * height * 6, false));
        batch.beginCache();

        for(Tile tile : world.tiles){
            tile.floor().drawBase(tile);
        }

        for(Tile tile : world.tiles){
            tile.overlay().drawBase(tile);
        }

        cacheFloor = batch.endCache();
        batch.beginCache();

        for(Tile tile : world.tiles){
            tile.block().drawBase(tile);
        }

        cacheWall = batch.endCache();

        Core.batch = prev;
    }

    public void render(){
        time += Time.delta;
        float scaling = Math.max(Scl.scl(4f), Math.max(Core.graphics.getWidth() / ((width - 1f) * tilesize), Core.graphics.getHeight() / ((height - 1f) * tilesize)));
        camera.position.set(width * tilesize / 2f, height * tilesize / 2f);
        camera.resize(Core.graphics.getWidth() / scaling,
                Core.graphics.getHeight() / scaling);

        mat.set(Draw.proj());
        Draw.flush();
        Draw.proj(camera);
        batch.setProjection(camera.mat);
        batch.beginDraw();
        batch.drawCache(cacheFloor);
        batch.endDraw();
        Draw.color();
        Draw.rect(Draw.wrap(shadows.getTexture()),
                width * tilesize / 2f - 4f, height * tilesize / 2f - 4f,
                width * tilesize, -height * tilesize);
        Draw.flush();
        batch.beginDraw();
        batch.drawCache(cacheWall);
        batch.endDraw();

        drawFlyers();

        Draw.proj(mat);
        Draw.color(0f, 0f, 0f, darkness);
        Fill.crect(0, 0, Core.graphics.getWidth(), Core.graphics.getHeight());
        Draw.color();
    }

    private void drawFlyers(){
        Draw.color(0f, 0f, 0f, 0.4f);

        TextureRegion icon = flyerType.fullIcon;

        flyers((x, y) -> {
            Draw.rect(icon, x - 12f, y - 13f, flyerRot - 90);
        });

        float size = Math.max(icon.width, icon.height) * icon.scl() * 1.6f;

        flyers((x, y) -> {
            Draw.rect("circle-shadow", x, y, size, size);
        });
        Draw.color();

        flyers((x, y) -> {
            float engineOffset = flyerType.engineOffset, engineSize = flyerType.engineSize, rotation = flyerRot;

            Draw.color(Pal.engine);
            Fill.circle(x + Angles.trnsx(rotation + 180, engineOffset), y + Angles.trnsy(rotation + 180, engineOffset),
                    engineSize + Mathf.absin(Time.time, 2f, engineSize / 4f));

            Draw.color(Color.white);
            Fill.circle(x + Angles.trnsx(rotation + 180, engineOffset - 1f), y + Angles.trnsy(rotation + 180, engineOffset - 1f),
                    (engineSize + Mathf.absin(Time.time, 2f, engineSize / 4f)) / 2f);
            Draw.color();

            Draw.rect(icon, x, y, flyerRot - 90);
        });
    }

    private void flyers(Floatc2 cons){
        float tw = width * tilesize * 1f + tilesize;
        float th = height * tilesize * 1f + tilesize;
        float range = 500f;
        float offset = -100f;

        for(int i = 0; i < flyers; i++){
            Tmp.v1.trns(flyerRot, time * (flyerType.speed));

            cons.get(
                    (Mathf.randomSeedRange(i, range) + Tmp.v1.x + Mathf.absin(time + Mathf.randomSeedRange(i + 2, 500), 10f, 3.4f) + offset) % (tw + Mathf.randomSeed(i + 5, 0, 500)),
                    (Mathf.randomSeedRange(i + 1, range) + Tmp.v1.y + Mathf.absin(time + Mathf.randomSeedRange(i + 3, 500), 10f, 3.4f) + offset) % th
            );
        }
    }

    @Override
    public void dispose(){
        batch.dispose();
        shadows.dispose();
    }
}
