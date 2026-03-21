package psammos.content.blocks;

import arc.graphics.*;
import mindustry.content.*;
import mindustry.entities.effect.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;
import psammos.content.*;
import psammos.world.blocks.environment.*;

public class PsammosEnvironmentBlocks {
    public static Block
            //Ores
            osmiumOre, silverOre, peatOre,
            //Liquids
            quicksand, darkQuicksand,
            //Floors
            smallOilDeposit, oilDeposit, peatFloor, packedPeatFloor, decayingFloor, bog, burningPeatFloor, ash, quartzFloor, crystallineQuartzFloor,
            slate, smallSlateOilDeposit, slateOilDeposit, osmicStone, desertGlass, metallicFloor, rustedMetallicFloor,
            //Walls
            peatWall, packedPeatWall, decayingWall, ashWall, quartzWall, slateWall, osmicStoneWall, desertGlassWall,
            metallicWall, rustedMetallicWall,
            //Cracked Walls
            crackedSaltWall, crumblingSandWall, crumblingDuneWall, crumblingPeatWall, crumblingDecayingWall, crumblingAshWall, crackedQuartzWall,
            //Props
            peatBoulder, packedPeatBoulder, decayingBoulder, ashBoulder, slateBoulder, quartzBoulder, osmicBoulder, desertGlassBoulder,
            //Other
            ashPit,
            crystalQuartz, crystalDesertGlass;;

    public static void load() {
        osmiumOre = new OreBlock("1a-osmium-ore", PsammosItems.osmium){{
            variants = 3;
        }};

        silverOre = new OreBlock("1b-silver-ore", PsammosItems.silver){{
            variants = 3;
        }};

        peatOre = new OreBlock("peat-ore", PsammosItems.peat){{
            variants = 3;
        }};

        quicksand = new Floor("3a-quicksand"){{
            variants = 3;
            cacheLayer = PsammosCacheLayers.quicksand;
            speedMultiplier = 0.1f;
            liquidDrop = PsammosLiquids.quicksand;
            status = PsammosStatusEffects.quicksandSlowed;
            drownTime = 200;
            isLiquid = true;
        }};

        darkQuicksand = new Floor("dark-quicksand"){{
            variants = 3;
            cacheLayer = PsammosCacheLayers.darkQuicksand;
            speedMultiplier = 0.1f;
            liquidDrop = PsammosLiquids.quicksand;
            status = PsammosStatusEffects.quicksandSlowed;
            drownTime = 200;
            isLiquid = true;
        }};

        smallOilDeposit = new ExplodableFloor("3e-small-oil-deposit"){{
            variants = 3;
            itemDrop = Items.sand;
            playerUnmineable = true;
            blendGroup = Blocks.darksand;
        }};

        oilDeposit = new ExplodableFloor("3d-oil-deposit"){{
            variants = 3;
            itemDrop = Items.sand;
            playerUnmineable = true;
            replacement = Blocks.tar.asFloor();
            blendGroup = Blocks.darksand;
            ((ExplodableFloor) smallOilDeposit).replacement = this;
        }};

        peatFloor = new Floor("3b-peat-floor"){{
            variants = 4;
            itemDrop = PsammosItems.peat;
            playerUnmineable = true;
        }};

        packedPeatFloor = new Floor("packed-peat-floor"){{
            variants = 3;
            itemDrop = PsammosItems.peat;
            playerUnmineable = true;
        }};

        decayingFloor = new Floor("decaying-floor"){{
            variants = 3;
        }};

        bog = new EffectFloor("bog"){{
            speedMultiplier = 0.6f;
            variants = 3;
            status = StatusEffects.muddy;
            statusDuration = 30f;
            attributes.set(Attribute.water, 1f);
            effect = Fx.vapor;
            effectChance = 0.0005f;
            effectColor = Color.valueOf("#49492c");
            cacheLayer = CacheLayer.mud;
            walkSound = Sounds.stepMud;
            walkSoundVolume = 0.08f;
            walkSoundPitchMin = 0.4f;
            walkSoundPitchMax = 0.5f;
            drownTime = 300;
            isLiquid = true;
        }};

        burningPeatFloor = new EffectFloor("burning-peat-floor"){{
            variants = 4;
            itemDrop = PsammosItems.peat;
            playerUnmineable = true;

            effect = new MultiEffect(Fx.fire, Fx.coalSmeltsmoke);
            effectChance = 0.025f;
            status = StatusEffects.burning;
            attributes.set(Attribute.heat, 5f/3f/9f); // 1.666 / 9

            emitLight = true;
            lightRadius = 30f;
            lightColor = Color.orange.cpy().a(0.15f);
        }};

        ash = new EffectFloor("ash"){{
            variants = 4;
            effect = Fx.fire;
            effectChance = 0.001f;
            status = StatusEffects.burning;
            attributes.set(Attribute.heat, 1/9f);
        }};

        quartzFloor = new Floor("3c-quartz-floor"){{
            variants = 4;
        }};

        crystallineQuartzFloor = new Floor("crystalline-quartz-floor"){{
            variants = 4;
        }};

        slate = new Floor("slate"){{
            variants = 3;
        }};

        smallSlateOilDeposit = new ExplodableFloor("small-slate-oil-deposit"){{
            variants = 3;
            blendGroup = slate;
        }};

        slateOilDeposit = new ExplodableFloor("slate-oil-deposit"){{
            variants = 3;
            replacement = Blocks.tar.asFloor();
            blendGroup = slate;
            ((ExplodableFloor) smallSlateOilDeposit).replacement = this;
        }};

        osmicStone = new Floor("osmic-stone"){{
            variants = 2;
        }};

        desertGlass = new Floor("desert-glass"){{
            variants = 3;
        }};

        metallicFloor = new Floor("metallic-floor"){{
            variants = 3;
        }};

        rustedMetallicFloor = new Floor("rusted-metallic-floor"){{
            variants = 3;
        }};

        peatWall = new StaticWall("4a-peat-wall"){{
            variants = 2;
            itemDrop = PsammosItems.peat;
            playerUnmineable = true;
            peatFloor.asFloor().wall = this;
            burningPeatFloor.asFloor().wall = this;
        }};

        packedPeatWall = new StaticWall("packed-peat-wall"){{
            variants = 2;
            itemDrop = PsammosItems.peat;
            playerUnmineable = true;
            packedPeatFloor.asFloor().wall = this;
        }};

        decayingWall = new StaticWall("decaying-wall"){{
            variants = 2;
            decayingFloor.asFloor().wall = this;
        }};

        ashWall = new StaticWall("ash-wall"){{
            variants = 2;
            ash.asFloor().wall = this;
        }};

        quartzWall = new StaticWall("4b-quartz-wall"){{
            variants = 2;
            itemDrop = PsammosItems.quartz;
            playerUnmineable = true;
            quartzFloor.asFloor().wall = this;
            crystallineQuartzFloor.asFloor().wall = this;
        }};

        slateWall = new StaticWall("slate-wall"){{
            variants = 2;
            slate.asFloor().wall = this;
        }};

        osmicStoneWall = new StaticWall("osmic-stone-wall"){{
            variants = 2;
            osmicStone.asFloor().wall = this;
        }};

        desertGlassWall = new StaticWall("desert-glass-wall"){{
            variants = 2;
            desertGlass.asFloor().wall = this;
        }};

        metallicWall = new StaticWall("metallic-wall"){{
            variants = 2;
            metallicFloor.asFloor().wall = this;
        }};

        rustedMetallicWall = new StaticWall("rusted-metallic-wall"){{
            variants = 2;
            rustedMetallicFloor.asFloor().wall = this;
        }};

        crackedSaltWall = new ExplodableWall("cracked-salt-wall"){{
            variants = 2;
            boulder = Blocks.snowBoulder;
        }};

        crumblingSandWall = new ExplodableWall("crumbling-sand-wall"){{
            variants = 2;
            attributes.set(Attribute.sand, 2f);
            boulder = Blocks.sandBoulder;
        }};

        crumblingDuneWall = new ExplodableWall("crumbling-dune-wall"){{
            variants = 2;
            attributes.set(Attribute.sand, 2f);
            boulder = Blocks.basaltBoulder;
        }};

        crumblingPeatWall = new ExplodableWall("crumbling-peat-wall"){{
            variants = 2;
            itemDrop = PsammosItems.peat;
            playerUnmineable = true;
        }};

        crumblingDecayingWall = new ExplodableWall("crumbling-decaying-wall"){{
            variants = 2;
        }};

        crumblingAshWall = new ExplodableWall("crumbling-ash-wall"){{
            variants = 2;
        }};

        crackedQuartzWall = new ExplodableWall("cracked-quartz-wall"){{
            variants = 2;
            itemDrop = PsammosItems.quartz;
            playerUnmineable = true;
        }};

        peatBoulder = new Prop("peat-boulder"){{
            variants = 2;
            peatFloor.asFloor().decoration = this;
            burningPeatFloor.asFloor().decoration = this;
            ((ExplodableWall)crumblingPeatWall).boulder = this;
        }};

        packedPeatBoulder = new Prop("packed-peat-boulder"){{
            variants = 2;
            packedPeatFloor.asFloor().decoration = this;
        }};

        decayingBoulder = new Prop("decaying-boulder"){{
            variants = 2;
            decayingFloor.asFloor().decoration = this;
            ((ExplodableWall)crumblingDecayingWall).boulder = this;
        }};

        ashBoulder = new Prop("ash-boulder"){{
            variants = 2;
            ash.asFloor().decoration = this;
            ((ExplodableWall)crumblingAshWall).boulder = this;
        }};

        quartzBoulder = new Prop("5b-quartz-boulder"){{
            variants = 2;
            quartzFloor.asFloor().decoration = this;
            crystallineQuartzFloor.asFloor().decoration = this;
            ((ExplodableWall)crackedQuartzWall).boulder = this;
        }};

        slateBoulder = new Prop("slate-boulder"){{
            variants = 2;
            slate.asFloor().decoration = this;
        }};

        osmicBoulder = new Prop("osmic-boulder"){{
            variants = 2;
            osmicStone.asFloor().decoration = this;
        }};

        desertGlassBoulder = new Prop("desert-glass-boulder"){{
            variants = 2;
            desertGlass.asFloor().decoration = this;
        }};

        ashPit = new FireVent("ash-pit"){{
            parent = blendGroup = ash;
        }};

        crystalQuartz = new TallBlock("crystal-quartz"){{
            variants = 2;
            clipSize = 128f;
            itemDrop = PsammosItems.quartz;
        }};

        crystalDesertGlass = new TallBlock("crystal-desert-glass"){{
            variants = 2;
            clipSize = 128f;
            itemDrop = PsammosItems.desertGlassShard;
        }};
    }
}
