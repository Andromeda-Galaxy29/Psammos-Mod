package psammos.world.blocks.defense;

import arc.math.Mathf;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.world.Tile;
import psammos.world.blocks.defense.BarrierProjectorNode.BarrierProjectorNodeBuild;

public class BarrierGraph {
    public final Seq<BarrierProjectorNodeBuild> nodes = new Seq<>();
    public float efficiency = 0;
    public boolean broken = false;
    public float buildup = 0;
    public float radscl = 0;
    public float warmup = 0;
    public float hit = 0;

    public float shieldHealth = 0;

    private final int graphID;
    private static int lastGraphID;

    public BarrierGraph() {
        this.graphID = lastGraphID++;
    }

    public BarrierGraph(BarrierProjectorNodeBuild firstNode) {
        this();
        add(firstNode);
    }

    public int getID() {
        return this.graphID;
    }

    // The controller is the one node responsible for running the graph's update code
    public boolean isController(BarrierProjectorNodeBuild node) {
        return node == nodes.first();
    }

    public BarrierProjectorNodeBuild getController() {
        return nodes.first();
    }

    public BarrierProjectorNode getControllerBlock() {
        return (BarrierProjectorNode) getController().block;
    }

    public void update() {
        efficiency = 0;
        for (BarrierProjectorNodeBuild node : nodes) {
            efficiency += node.nodeEfficiency();
        }
        efficiency /= nodes.size;

        radscl = Mathf.lerpDelta(radscl, broken ? 0f : warmup, 0.05f);
        warmup = Mathf.lerpDelta(warmup, efficiency, 0.1f);

        if(buildup > 0){
            float scale = !broken ? getControllerBlock().cooldown : getControllerBlock().cooldownBroken; // I need to change this if I add more barrier node types
            buildup -= Time.delta * scale;
        }

        if(broken && buildup <= 0){
            broken = false;
        }

        if(buildup >= shieldHealth && !broken){
            broken = true;
            buildup = shieldHealth;
            for (BarrierProjectorNodeBuild node : nodes) {
                node.breakEffect();
            }
        }

        if(hit > 0f){
            hit -= 1f / 5f * Time.delta;
        }
    }

    public void trySplitGraph() {
        ObjectMap<BarrierProjectorNodeBuild, Boolean> visited = new ObjectMap<>();
        for (BarrierProjectorNodeBuild node : nodes) {
            Seq<BarrierProjectorNodeBuild> connectedNodes = getAllConnected(node, visited);
            if (connectedNodes.size == nodes.size) { //If nothing changed
                return;
            }
            if (connectedNodes.size > 0) {
                BarrierGraph newGraph = new BarrierGraph();
                for (BarrierProjectorNodeBuild connectedNode : connectedNodes) {
                    newGraph.add(connectedNode);
                    remove(connectedNode, false);
                }
                //TODO Split buildup
            }
        }
    }
    
    public Seq<BarrierProjectorNodeBuild> getAllConnected(BarrierProjectorNodeBuild node, ObjectMap<BarrierProjectorNodeBuild, Boolean> visited, Seq<BarrierProjectorNodeBuild> result) {
        if (visited.containsKey(node) && visited.get(node)) {
            return result;
        }

        visited.put(node, true);
        result.add(node);
        for (int pos : node.links.toArray()) {
            Tile tile = Vars.world.tile(pos);
            if (tile == null || !(tile.build instanceof BarrierProjectorNodeBuild nextNode)) {
                continue;
            }

            if (!(visited.containsKey(nextNode) && visited.get(nextNode))) {
                getAllConnected(nextNode, visited, result);
            }
        }
        return result;
    }

    public Seq<BarrierProjectorNodeBuild> getAllConnected(BarrierProjectorNodeBuild node, ObjectMap<BarrierProjectorNodeBuild, Boolean> visited) {
        Seq<BarrierProjectorNodeBuild> result = new Seq<>();
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

        for (BarrierProjectorNodeBuild node : graph.nodes) {
            add(node);
        }
    }

    public void add(BarrierProjectorNodeBuild node) {
        //TODO Add buildup
        shieldHealth += ((BarrierProjectorNode) node.block).shieldHealth;
        node.graph = this;
        nodes.add(node);
    }

    public void remove(BarrierProjectorNodeBuild node, boolean trySplit) {
        shieldHealth -= ((BarrierProjectorNode) node.block).shieldHealth;
        nodes.remove(node);
        if (trySplit) {
            trySplitGraph();
        }
    }

    public void remove(BarrierProjectorNodeBuild node) {
        remove(node, true);
    }

    public void damage(float amount){
        buildup += amount;
        hit = 1f;
    }
}
