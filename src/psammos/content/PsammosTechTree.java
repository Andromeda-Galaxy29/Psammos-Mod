package psammos.content;

import arc.struct.*;

import static mindustry.content.TechTree.*;
import static psammos.content.PsammosBlocks.*;
import static psammos.content.PsammosItems.*;
import static psammos.content.PsammosLiquids.*;
import static psammos.content.PsammosSectors.*;
import static psammos.content.PsammosUnitTypes.*;
import static mindustry.content.Items.*;
import static mindustry.content.Liquids.*;
import static mindustry.game.Objectives.*;

public class PsammosTechTree {
    public static void load(){
        PsammosPlanets.psammos.techTree = nodeRoot("@planet.psammos-psammos.name", PsammosBlocks.coreDust, false, () ->{
            node(heatproofConveyor, ()->{
                node(heatproofJunction, ()->{
                    node(heatproofRouter, ()->{
                        node(heatproofOverflowGate, ()->{
                            node(heatproofUnderflowGate, ()->{

                            });
                        });
                        node(heatproofContainer, ()->{
                            node(heatproofUnloader, ()->{

                            });
                        });
                    });
                    node(heatproofTunnelConveyor, ()->{

                    });
                });
                node(heatproofPayloadConveyor, Seq.with(new OnSector(driedRiver)), ()->{
                    node(heatproofPayloadRouter, ()->{

                    });
                });
            });

            node(osmiumDrill, ()->{
                node(heatproofPump, Seq.with(new OnSector(quartzValley)), ()->{
                    node(pipe, ()->{
                        node(pipeJunction, ()->{
                            node(pipeRouter, ()->{
                                node(heatproofLiquidContainer, ()->{

                                });
                                node(tunnelPipe, ()->{

                                });
                            });
                        });
                    });
                    node(centrifuge, ()->{
                        node(heatExchanger, ()->{
                            node(heatPump, Seq.with(new OnSector(erodedDesert)), ()->{
                                node(heatPumpRouter, ()->{

                                });
                            });
                            node(peatHeater, Seq.with(new OnSector(erodedDesert)), ()->{

                            });
                        });
                    });
                });

                node(excavatorDrill, Seq.with(new OnSector(quartzValley)), ()->{
                    node(detonationDrill, Seq.with(new OnSector(ancientSwamp)),  ()->{

                    });
                });

                node(windTurbine, Seq.with(new OnSector(quartzValley)), ()->{
                    node(electricPole, ()->{
                        node(electricDistributor, Seq.with(new OnSector(oilRefiningFacility)), ()->{

                        });
                        node(accumulator, ()->{

                        });
                        node(led, ()->{

                        });
                    });
                    node(sieve, Seq.with(new OnSector(quartzValley)), ()->{
                        node(siliconSynthesizer, ()->{
                            node(thermolysisChamber, Seq.with(new OnSector(driedRiver)), ()->{
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
                                            });
                                        });
                                        node(ammoniaBomb, Seq.with(new OnSector(erodedDesert)), ()->{

                                        });
                                    });
                                    node(obliterator, Seq.with(new OnSector(ancientSwamp)), ()->{

                                    });
                                });
                            });
                        });
                        node(filter, Seq.with(new OnSector(ancientSwamp)), ()->{

                        });
                    });
                    node(impulseGenerator, Seq.with(new OnSector(ancientSwamp)), ()->{
                        node(liquidFuelBurner, Seq.with(new OnSector(oilRefiningFacility)), ()->{

                        });
                    });
                });
                node(healingProjector, Seq.with(new OnSector(oilRefiningFacility)), ()->{

                });
            });

            node(cross, ()->{
                node(osmiumWall, ()->{
                    node(osmiumWallLarge, ()->{
                        node(silverWall, ()->{
                            node(silverWallLarge, ()->{
                                node(refinedMetalWall, ()->{
                                    node(refinedMetalWallLarge, ()->{

                                    });
                                });
                            });
                        });
                    });
                });
                node(disseminate, ()->{
                    node(dawn, Seq.with(new OnSector(erodedDesert)), ()->{

                    });
                });
                node(influence, Seq.with(new OnSector(quartzValley)), ()->{
                    node(gunslinger, Seq.with(new OnSector(ancientSwamp)), ()->{
                        node(spray, ()->{

                        });
                    });
                });
            });

            node(coreDune, Seq.with(new OnSector(oilRefiningFacility)), ()->{

            });

            node(specialistUnitForge, Seq.with(new OnSector(driedRiver)), ()->{
                node(fang, ()->{

                });
                node(assaultUnitForge, ()->{
                    node(tip, ()->{

                    });
                    node(scoutUnitForge, Seq.with(new SectorComplete(ancientSwamp)), ()->{
                        node(sciur, ()->{

                        });
                        node(supportUnitForge, Seq.with(new OnSector(ferricSummit)), ()->{
                            node(sine, ()->{

                            });
                        });
                    });
                });
            });

            node(landing, ()->{
                node(quartzValley, Seq.with(new SectorComplete(landing)), ()->{
                    node(driedRiver, Seq.with(new SectorComplete(quartzValley)), ()->{
                        node(ancientSwamp, Seq.with(new SectorComplete(driedRiver)), ()->{
                            node(oilRefiningFacility, Seq.with(new SectorComplete(ancientSwamp)), ()->{
                                node(erodedDesert, Seq.with(new SectorComplete(oilRefiningFacility)), ()->{

                                });
                                node(ferricSummit, Seq.with(new SectorComplete(oilRefiningFacility)), ()->{

                                });
                            });
                            node(evaporatedBasin, Seq.with(new SectorComplete(ancientSwamp)), ()->{

                            });
                        });
                    });
                    node(cavern, Seq.with(new SectorComplete(quartzValley)), ()->{

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
                                        nodeProduce(fuel, ()->{

                                        });
                                        nodeProduce(methane, ()->{
                                            nodeProduce(aerogel, ()->{

                                            });
                                            nodeProduce(hydrogen, ()->{
                                                nodeProduce(ammonia, ()->{

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
                    });
                });
                nodeProduce(silver, ()->{

                });
            });
        });
    }
}
