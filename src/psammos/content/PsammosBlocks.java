package psammos.content;

import psammos.content.blocks.*;

// Some of the block IDs have number prefixes before them.
// This is leftover from the json version and I can't remove them without breaking things.

public class PsammosBlocks {

    public static void load(){
        PsammosTurrets.load();
        PsammosProductionBlocks.load();
        PsammosDistributionBlocks.load();
        PsammosLiquidBlocks.load();
        PsammosPowerBlocks.load();
        PsammosRadiationBlocks.load();
        PsammosDefenseBlocks.load();
        PsammosCraftingBlocks.load();
        PsammosPayloadBlocks.load();
        PsammosEffectBlocks.load();
        PsammosLogicBlocks.load();
        PsammosEnvironmentBlocks.load();
    }
}
