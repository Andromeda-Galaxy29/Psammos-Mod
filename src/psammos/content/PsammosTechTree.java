package psammos.content;

import arc.struct.*;

import static mindustry.content.TechTree.*;
import static psammos.content.blocks.PsammosTurrets.*;
import static psammos.content.blocks.PsammosProductionBlocks.*;
import static psammos.content.blocks.PsammosDistributionBlocks.*;
import static psammos.content.blocks.PsammosLiquidBlocks.*;
import static psammos.content.blocks.PsammosPowerBlocks.*;
import static psammos.content.blocks.PsammosRadiationBlocks.*;
import static psammos.content.blocks.PsammosWalls.*;
import static psammos.content.blocks.PsammosCraftingBlocks.*;
import static psammos.content.blocks.PsammosPayloadBlocks.*;
import static psammos.content.blocks.PsammosEffectBlocks.*;
import static psammos.content.blocks.PsammosLogicBlocks.*;
import static psammos.content.PsammosItems.*;
import static psammos.content.PsammosLiquids.*;
import static psammos.content.PsammosSectors.*;
import static psammos.content.PsammosUnitTypes.*;
import static psammos.type.RadiationType.*;
import static mindustry.content.Items.*;
import static mindustry.content.Liquids.*;
import static mindustry.game.Objectives.*;

public class PsammosTechTree {
    public static void load(){
        PsammosPlanets.psammos.techTree = nodeRoot("@planet.psammos-psammos.name", coreDust, false, () ->{
            node(heatproofConveyor, ()->{
                node(heatproofJunction, ()->{
                    node(heatproofRouter, ()->{
                        node(heatproofOverflowGate, ()->{
                            node(heatproofUnderflowGate, ()->{

                            });
                        });
                        node(heatproofContainer, Seq.with(new OnSector(excavationSite), new Research(refinery)), ()->{
                            node(heatproofUnloader, ()->{

                            });
                            node(heatproofVault, Seq.with(new SectorComplete(scaldedPlains), new Research(memoryAlloyCrucible)), ()->{

                            });
                        });
                    });
                    node(heatproofTunnelConveyor, ()->{

                    });
                });
                node(heatproofPayloadConveyor, Seq.with(new OnSector(excavationSite), new Research(specialistUnitForge), new Research(refinery)), ()->{
                    node(heatproofPayloadRouter, ()->{
                        node(heatproofPayloadGate, ()->{

                        });
                    });
                });
                node(platedConveyor, Seq.with(new OnSector(scaldedPlains), new Research(memoryAlloyCrucible)), ()->{

                });
            });

            node(osmiumDrill, ()->{
                node(crystalSampler, ()->{

                });
                node(heatproofPump, Seq.with(new OnSector(quartzValley)), ()->{
                    node(pipe, ()->{
                        node(pipeJunction, ()->{
                            node(pipeRouter, ()->{
                                node(overflowPipe, ()->{
                                    node(underflowPipe, ()->{

                                    });
                                });
                                node(heatproofLiquidContainer, Seq.with(new OnSector(excavationSite), new Research(refinery)), ()->{
                                    node(heatproofLiquidTank, ()->{

                                    });
                                });
                            });
                            node(tunnelPipe, ()->{

                            });
                        });
                        node(vacuumPipe, Seq.with(new OnSector(erodedDesert), new Research(aerogelPressurizer)), ()->{

                        });
                    });
                    node(centrifuge, Seq.with(new OnSector(quartzValley)), ()->{
                        node(heatExchanger, Seq.with(new OnSector(excavationSite), new Research(refinery)), ()->{
                            node(heatPump, Seq.with(new OnSector(erodedDesert), new Research(aerogelPressurizer)), ()->{
                                node(heatPumpRouter, ()->{

                                });
                                node(heatRadiator, ()-> {
                                    node(infraredHeater, ()-> {

                                    });
                                });
                            });
                            node(ozoneHeater, Seq.with(new OnSector(oilRefiningFacility), new Research(thermolysisChamber)), ()->{
                                node(peatHeater, Seq.with(new OnSector(erodedDesert), new Research(aerogelPressurizer)), ()->{
                                    node(ammoniaHeater, Seq.with(new OnSector(erodedDesert), new Research(ammoniaCompressor)), ()->{

                                    });
                                });
                            });
                        });
                        node(purifier, Seq.with(new OnSector(erodedDesert), new Research(ammoniaCompressor)), ()->{

                        });
                    });
                });

                node(excavatorDrill, Seq.with(new OnSector(quartzValley)), ()->{
                    node(quarryDrill, Seq.with(new OnSector(scaldedPlains), new Research(memoryAlloyCrucible)), ()->{

                    });
                });

                node(detonationDrill, Seq.with(new OnSector(ancientSwamp), new Research(blastManufacturer)), ()->{

                });

                node(windTurbine, Seq.with(new OnSector(quartzValley)), ()->{
                    node(solarCollector, ()->{
                        node(ultravioletLamp, ()-> {

                        });
                        node(photovoltaicCell, ()-> {

                        });
                        node(mirror, ()->{
                            node(focuser, ()->{
                                node(concaveLens, ()->{

                                });
                                node(convexLens, ()->{

                                });
                            });
                        });
                    });
                    node(electricPole, ()->{
                        node(electricDistributor, Seq.with(new OnSector(oilRefiningFacility), new Research(refinery)), ()->{

                        });
                        node(accumulator, ()->{

                        });
                        node(led, ()->{

                        });
                    });
                    node(piezoelectricGenerator, ()->{
                        node(impulseGenerator, Seq.with(new OnSector(ancientSwamp), new Research(blastManufacturer)), ()->{
                            node(liquidFuelBurner, Seq.with(new OnSector(oilRefiningFacility), new Research(oilDistillationTower), new Research(thermolysisChamber)), ()->{
                                node(thermoelectricGenerator, Seq.with(new OnSector(oilRefiningFacility), new Research(heatExchanger)), ()->{

                                });
                                node(heatEngine, Seq.with(new OnSector(scaldedPlains), new Research(ammoniaCompressor), new Research(memoryAlloyCrucible)), ()->{

                                });
                            });
                        });
                    });
                    node(sieve, Seq.with(new OnSector(quartzValley)), ()->{
                        node(siliconSynthesizer, ()->{
                            node(siliconSynthesisChamber, Seq.with(new OnSector(scaldedPlains), new Research(memoryAlloyCrucible)),  ()->{

                            });
                            node(thermolysisChamber, Seq.with(new OnSector(excavationSite)), ()->{
                                node(thermolysisTower, ()-> {

                                });
                                node(refinery, ()->{
                                    node(atmosphericSeparator, Seq.with(new OnSector(ancientSwamp)), ()->{

                                    });
                                });
                                node(blastManufacturer, Seq.with(new OnSector(ancientSwamp)), ()->{
                                    node(seismicBomb, Seq.with(new OnSector(oilRefiningFacility)), ()->{
                                        node(oilDistillationTower, ()->{
                                            node(aerogelPressurizer, Seq.with(new OnSector(erodedDesert)), ()->{

                                            });
                                            node(steamReformer, Seq.with(new OnSector(erodedDesert)), ()->{
                                                node(ammoniaCompressor, ()->{

                                                });
                                                node(memoryAlloyCrucible, Seq.with(new OnSector(scaldedPlains)), ()->{

                                                });
                                                node(splitterCell, ()-> {

                                                });
                                            });
                                        });
                                        node(ammoniaBomb, Seq.with(new OnSector(erodedDesert)), ()->{

                                        });
                                    });
                                    node(obliterator, Seq.with(new OnSector(ancientSwamp), new Research(blastManufacturer)), ()->{

                                    });
                                });
                            });
                        });
                        node(filter, Seq.with(new OnSector(ancientSwamp), new Research(atmosphericSeparator)), ()->{

                        });
                        node(heatproofMessage, ()->{

                        });
                    });
                });
                node(healingProjector, Seq.with(new OnSector(oilRefiningFacility), new Research(oilDistillationTower)), ()->{

                });
            });

            node(cross, ()->{
                node(osmiumWall, ()->{
                    node(osmiumWallLarge, ()->{

                    });
                    node(silverWall, ()->{
                        node(silverWallLarge, ()->{

                        });
                        node(refinedMetalWall, ()->{
                            node(refinedMetalWallLarge, ()->{

                            });
                            node(gate, ()->{
                                node(gateLarge, ()->{

                                });
                            });
                            node(floatingWall, Seq.with(new OnSector(erodedDesert), new Research(aerogelPressurizer)), ()->{
                                node(floatingWallLarge, ()->{

                                });
                            });
                            node(memoryWall, Seq.with(new OnSector(scaldedPlains), new Research(memoryAlloyCrucible)), ()->{
                                node(memoryWallLarge, ()->{

                                });
                            });
                        });
                    });
                });
                node(disseminate, ()->{
                    node(discharge, Seq.with(new OnSector(ancientSwamp)), ()->{
                        node(dawn, Seq.with(new OnSector(erodedDesert), new Research(aerogelPressurizer), new Research(steamReformer)), ()->{

                        });
                    });
                });
                node(hurl, Seq.with(new OnSector(quartzValley)), ()->{
                    node(gunslinger, Seq.with(new OnSector(ancientSwamp), new Research(blastManufacturer)), ()->{
                        node(spray, ()->{
                            node(burst, Seq.with(new OnSector(erodedDesert), new Research(aerogelPressurizer)), ()->{
                                node(burden, Seq.with(new OnSector(searingChasms), new Research(memoryAlloyCrucible)), ()->{

                                });
                                node(meteor, ()->{

                                });
                            });
                        });
                    });
                    node(influence, Seq.with(new OnSector(quartzValley)), ()->{
                        node(overflow, Seq.with(new OnSector(erodedDesert)), ()->{
                            node(luminosity, ()->{

                            });
                        });
                    });
                });
            });

            node(coreDune, Seq.with(new SectorComplete(oilRefiningFacility)), ()->{

            });

            node(specialistUnitForge, Seq.with(new OnSector(driedRiver)), ()->{
                node(repairDroneAssembler, Seq.with(new OnSector(oilRefiningFacility), new Research(oilDistillationTower)), ()->{
                    node(overclockTower, Seq.with(new OnSector(oilRefiningFacility), new Research(atmosphericSeparator)), ()->{

                    });
                });
                node(fang, ()->{

                });
                node(specialistUnitRecombiner, Seq.with(new OnSector(ruinousHollow)), ()->{
                    node(jaw, ()->{

                    });
                    node(specialistUnitWelder, ()-> {
                        node(maw, () -> {

                        });
                    });
                });
                node(assaultUnitForge, ()->{
                    node(glaive, ()->{

                    });
                    node(assaultUnitRecombiner, Seq.with(new OnSector(ruinousHollow)), ()->{
                        node(pike, ()->{

                        });
                        node(assaultUnitWelder, ()-> {
                            node(spear, () -> {

                            });
                        });
                    });
                    node(scoutUnitForge, Seq.with(new SectorComplete(excavationSite)), ()->{
                        node(sciur, ()->{

                        });
                        node(scoutUnitRecombiner, Seq.with(new OnSector(scaldedPlains)), ()->{
                            node(glirid, ()->{

                            });
                            node(scoutUnitWelder, ()-> {
                                node(exilis, () -> {

                                });
                            });
                        });
                        node(supportUnitForge, Seq.with(new OnSector(ferricSummit)), ()->{
                            node(sine, ()->{

                            });
                            node(supportUnitRecombiner, Seq.with(new OnSector(craterousRange)), ()->{
                                node(helix, ()->{

                                });
                                node(supportUnitWelder, ()-> {
                                    node(trisect, () -> {

                                    });
                                });
                            });
                        });
                        node(frontlineUnitForge, Seq.with(new OnSector(evaporatedBasin)), ()->{
                            node(pawn, ()->{

                            });
                            node(frontlineUnitRecombiner, Seq.with(new OnSector(searingChasms)), ()->{
                                node(knight, ()->{

                                });
                                node(frontlineUnitWelder, ()-> {
                                    node(bishop, () -> {

                                    });
                                });
                            });
                        });
                    });
                });
            });

            node(landing, ()->{
                node(quartzValley, Seq.with(new SectorComplete(landing)), ()->{
                    node(driedRiver, Seq.with(new SectorComplete(quartzValley)), ()->{
                        node(excavationSite, Seq.with(new SectorComplete(driedRiver)), ()->{
                            node(ancientSwamp, Seq.with(new SectorComplete(excavationSite)), ()->{
                                node(evaporatedBasin, Seq.with(new SectorComplete(ancientSwamp)), ()->{
                                    node(oilRefiningFacility, Seq.with(new SectorComplete(evaporatedBasin)), ()->{
                                        node(erodedDesert, Seq.with(new SectorComplete(oilRefiningFacility), new Research(coreDune)), ()->{
                                            node(ruinousHollow, Seq.with(new SectorComplete(erodedDesert)), ()->{
                                                node(scaldedPlains, Seq.with(new SectorComplete(ruinousHollow)), ()->{
                                                    node(searingChasms, Seq.with(new SectorComplete(scaldedPlains), new SectorComplete(craterousRange)), ()->{

                                                    });
                                                    node(enfer, Seq.with(new SectorComplete(scaldedPlains)), ()->{

                                                    });
                                                });
                                            });
                                            node(coruscatedCrevice, Seq.with(new SectorComplete(erodedDesert), new SectorComplete(ferricSummit)), ()->{

                                            });
                                        });
                                        node(ferricSummit, Seq.with(new SectorComplete(oilRefiningFacility)), ()->{
                                            node(craterousRange, Seq.with(new SectorComplete(ferricSummit), new SectorComplete(ruinousHollow)), ()->{

                                            });
                                        });
                                    });
                                });
                            });
                            node(shatteredPathway, Seq.with(new SectorComplete(excavationSite)), ()->{

                            });
                        });
                        node(cavern, Seq.with(new SectorComplete(driedRiver)), ()->{

                        });
                    });
                });
            });

            nodeProduce(osmium, ()->{
                nodeProduce(sand, ()->{
                    nodeProduce(quartz, ()->{
                        nodeProduce(silicon, ()->{
                            nodeProduce(slag, ()->{
                                nodeProduce(refinedMetal, ()->{
                                    nodeProduce(nitrogen, ()->{

                                    });
                                });
                            });
                            nodeProduce(ozone, ()->{
                                nodeProduce(blastCompound, ()->{
                                    nodeProduce(oil, ()->{
                                        nodeProduce(methane, ()->{
                                            nodeProduce(aerogel, ()->{

                                            });
                                            nodeProduce(hydrogen, ()->{
                                                nodeProduce(ammonia, ()->{

                                                });
                                                nodeProduce(memoryAlloy, ()->{

                                                });
                                            });
                                        });
                                    });
                                });
                            });
                        });
                        nodeProduce(PsammosLiquids.quicksand, ()->{
                            nodeProduce(water, ()->{
                                nodeProduce(coldWater, ()->{

                                });
                            });
                        });
                        nodeProduce(peat, ()->{

                        });
                        nodeProduce(desertGlassShard, ()->{

                        });
                    });
                });
                nodeProduce(silver, ()->{

                });
            });
        });
    }
}
