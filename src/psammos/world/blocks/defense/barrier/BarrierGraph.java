package psammos.world.blocks.defense.barrier;

import arc.math.Mathf;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.world.Tile;
import psammos.world.blocks.defense.barrier.BarrierNode.BarrierNodeBuild;

public class BarrierGraph {
    public final Seq<BarrierNodeBuild> nodes = new Seq<>();
    public float efficiency = 0;
    public boolean broken = false;
    public float buildup = 0;
    public float radscl = 0;
    public float warmup = 0;
    public float hit = 0;
    public float shieldHealth = 0;

    //Temporary storage for nodes when loading saves (not all buildings exist yet when reading save data)
    public final Seq<Tile> readNodes = new Seq<>();

    private final int graphID;
    private static int lastGraphID;

    public BarrierGraph() {
        this.graphID = lastGraphID++;
    }

    public BarrierGraph(BarrierNodeBuild firstNode) {
        this();
        add(firstNode);
    }

    public int getID() {
        return this.graphID;
    }

    // The controller is the one node responsible for running the graph's update code
    public boolean isController(BarrierNodeBuild node) {
        return !nodes.isEmpty() && node == nodes.first();
    }

    public BarrierNodeBuild getController() {
        return nodes.first();
    }

    public BarrierNode getControllerBlock() {
        return (BarrierNode) getController().block;
    }

    public void update() {
        efficiency = 0;
        for (BarrierNodeBuild node : nodes) {
            efficiency += node.nodeEfficiency();
        }
        efficiency /= nodes.size;

        radscl = Mathf.lerpDelta(radscl, broken ? 0f : warmup, 0.05f);
        warmup = Mathf.lerpDelta(warmup, efficiency, 0.1f);

        if(buildup > 0){
            float scale = !broken ? getControllerBlock().cooldown : getControllerBlock().cooldownBroken; // I need to change this if I add more barrier node types
            buildup -= Time.delta * scale;
            if (buildup < 0) {
                buildup = 0;
            }
        }

        if(broken && buildup <= 0){
            broken = false;
        }

        if(buildup >= shieldHealth && !broken){
            broken = true;
            buildup = shieldHealth;
            for (BarrierNodeBuild node : nodes) {
                node.breakEffect();
            }
        }

        if(hit > 0f){
            hit -= 1f / 5f * Time.delta;
        }
    }

    public void trySplitGraph() {
        ObjectMap<BarrierNodeBuild, Boolean> visited = new ObjectMap<>();
        float splitBuildup = buildup / nodes.size;

        for (BarrierNodeBuild node : nodes) {
            Seq<BarrierNodeBuild> connectedNodes = getAllConnected(node, visited);
            if (connectedNodes.size == nodes.size) { //If nothing changed
                return;
            }
            if (connectedNodes.size > 0) {
                BarrierGraph newGraph = new BarrierGraph();
                for (BarrierNodeBuild connectedNode : connectedNodes) {
                    newGraph.add(connectedNode);
                    newGraph.buildup += splitBuildup;
                    buildup -= splitBuildup;
                    remove(connectedNode, false);
                }
                newGraph.radscl = radscl;
                newGraph.warmup = warmup;
                newGraph.broken = broken;
            }
        }
    }
    
    public Seq<BarrierNodeBuild> getAllConnected(BarrierNodeBuild node, ObjectMap<BarrierNodeBuild, Boolean> visited, Seq<BarrierNodeBuild> result) {
        if (visited.containsKey(node) && visited.get(node)) {
            return result;
        }

        visited.put(node, true);
        result.add(node);
        for (int pos : node.links.toArray()) {
            Tile tile = Vars.world.tile(pos);
            if (tile == null || !(tile.build instanceof BarrierNodeBuild nextNode)) {
                continue;
            }

            if (!(visited.containsKey(nextNode) && visited.get(nextNode))) {
                getAllConnected(nextNode, visited, result);
            }
        }
        return result;
    }

    public Seq<BarrierNodeBuild> getAllConnected(BarrierNodeBuild node, ObjectMap<BarrierNodeBuild, Boolean> visited) {
        Seq<BarrierNodeBuild> result = new Seq<>();
        return getAllConnected(node, visited, result);
    }

    public void addGraph(BarrierGraph graph) {
        if (this.getID() == graph.getID()) {
            return;
        }

        if (graph.nodes.size > this.nodes.size) {
            graph.addGraph(this);
            return;
        }

        for (BarrierNodeBuild node : graph.nodes) {
            add(node);
        }
        buildup += graph.buildup;
        broken = broken || graph.broken;
    }

    public void add(BarrierNodeBuild node) {
        shieldHealth += ((BarrierNode) node.block).shieldHealth;
        node.graph = this;
        nodes.add(node);
    }

    public void remove(BarrierNodeBuild node, boolean trySplit) {
        shieldHealth -= ((BarrierNode) node.block).shieldHealth;
        nodes.remove(node);
        if (trySplit) {
            trySplitGraph();
        }
    }

    public void remove(BarrierNodeBuild node) {
        remove(node, true);
    }

    public void damage(float amount){
        buildup += amount;
        hit = 1f;
    }

    public void write(Writes write) {
        write.s(nodes.size);
        for(int i = 0; i < nodes.size; i++) {
            write.i(nodes.get(i).tile.pos());
        }

        write.f(efficiency);
        write.bool(broken);
        write.f(buildup);
        write.f(radscl);
        write.f(warmup);
    }

    public void read(Reads read, byte revision) {
        short amount = read.s();
        for(int i = 0; i < amount; i++) {
            Tile tile = Vars.world.tile(read.i());
            readNodes.add(tile);
        }

        efficiency = read.f();
        broken = read.bool();
        buildup = read.f();
        radscl = read.f();
        warmup = read.f();
    }

    public void afterReadAll() {
        for (Tile tile : readNodes) {
            if (tile.build instanceof BarrierNodeBuild node) {
                add(node);
            }
        }
        readNodes.clear();
    }
}
