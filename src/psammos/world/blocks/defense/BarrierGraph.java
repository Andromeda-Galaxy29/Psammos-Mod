package psammos.world.blocks.defense;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.world.Tile;
import psammos.world.blocks.defense.BarrierProjectorNode.BarrierProjectorNodeBuild;

public class BarrierGraph {
    public final Seq<BarrierProjectorNodeBuild> nodes = new Seq<>(false);
    public float averageEfficiency = 0;
    private final int graphID;
    private static int lastGraphID;

    public BarrierGraph() {
        this.graphID = lastGraphID++;
    }

    public int getID() {
        return this.graphID;
    }

    public void update() {
        averageEfficiency = 0;
        for (BarrierProjectorNodeBuild node : nodes) {
            averageEfficiency += node.nodeEfficiency();
        }
        averageEfficiency /= nodes.size;
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
                    nodes.remove(connectedNode);
                }
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
        node.graph = this;
        nodes.add(node);
    }

    public void remove(BarrierProjectorNodeBuild node) {
        nodes.remove(node);
        trySplitGraph();
    }
}
