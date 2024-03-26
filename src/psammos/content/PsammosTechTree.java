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
            });

            node(osmiumDrill, ()->{
                node(heatproofPump, Seq.with(new OnSector(quartzValley)), ()->{
                    node(pipe, ()->{
                        node(pipeJunction, ()->{
                            node(pipeRouter, ()->{

                            });
                        });
                    });
                    node(centrifuge, ()->{
                        node(heatExchanger, ()->{

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
                    });
                    node(sieve, Seq.with(new OnSector(quartzValley)), ()->{
                        node(siliconSynthesizer, ()->{
                            node(thermolysisChamber, Seq.with(new OnSector(driedRiver)), ()->{
                                node(refinery, ()->{

                                });
                                node(blastManufacturer, Seq.with(new OnSector(ancientSwamp)), ()->{
                                    node(seismicBomb, Seq.with(new OnSector(oilRefiningFacility)), ()->{
                                        node(oilDistillationTower, ()->{
                                            node(healingProjector, ()->{

                                            });
                                        });
                                    });
                                    node(obliterator, Seq.with(new OnSector(ancientSwamp)), ()->{

                                    });
                                });
                            });
                        });
                    });

                    node(liquidFuelBurner, Seq.with(new OnSector(oilRefiningFacility)), ()->{

                    });
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

                });
                node(influence, Seq.with(new OnSector(quartzValley)), ()->{
                    node(gunslinger, Seq.with(new OnSector(ancientSwamp)), ()->{

                    });
                });
            });

            node(specialistUnitForge, Seq.with(new OnSector(ancientSwamp)), ()->{
                node(fang, ()->{

                });
                node(assaultUnitForge, ()->{
                    node(tip, ()->{

                    });
                    node(scoutUnitForge, Seq.with(new OnSector(oilRefiningFacility)), ()->{
                        node(sciur, ()->{

                        });
                    });
                });
            });

            node(landing, ()->{
                node(quartzValley, Seq.with(new SectorComplete(landing)), ()->{
                    node(driedRiver, Seq.with(new SectorComplete(quartzValley)), ()->{
                        node(ancientSwamp, Seq.with(new SectorComplete(driedRiver)), ()->{
                            node(oilRefiningFacility, Seq.with(new SectorComplete(ancientSwamp)), ()->{

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

                                });
                            });
                            nodeProduce(ozone, ()->{
                                nodeProduce(blastCompound, ()->{
                                    nodeProduce(oil, ()->{
                                        nodeProduce(fuel, ()->{

                                        });
                                        nodeProduce(methane, ()->{

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
