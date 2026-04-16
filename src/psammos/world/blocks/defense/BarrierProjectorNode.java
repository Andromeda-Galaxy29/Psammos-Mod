package psammos.world.blocks.defense;

import arc.struct.IntSeq;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.world.Block;

public class BarrierProjectorNode extends Block {
    public float range = 10 * Vars.tilesize;
    public int maxNodes = 3;

    public BarrierProjectorNode(String name) {
        super(name);
        configurable = true;
        swapDiagonalPlacement = true;

        config(Integer.class, (entity, value) -> {
            Building other = Vars.world.build(value);
            if (!(other instanceof BarrierProjectorNodeBuild otherNode && entity instanceof BarrierProjectorNodeBuild node)){
                return;
            }

            boolean contains = node.links.contains(value);

            if(contains){
                node.links.removeValue(value);
                otherNode.links.removeValue(node.pos());
                node.graph.trySplitGraph();
            }else if(linkValid(node, otherNode) && node.links.size < maxNodes){

                node.links.addUnique(otherNode.pos());

                if(otherNode.team == node.team){
                    otherNode.power.links.addUnique(node.pos());
                }

                node.graph.addGraph(otherNode.graph);
            }
        });
    }

    public boolean linkValid(Building node, Building otherNode) {
        return true; //TODO
    }

    public class BarrierProjectorNodeBuild extends Building {
        public IntSeq links = new IntSeq();
        public BarrierGraph graph = new BarrierGraph();

        public float nodeEfficiency() {
            return 1;
        }

        @Override
        public void remove() {
            super.remove();
            graph.remove(this);
        }
    }
}
