package Inforeg.Algo;

import Inforeg.Draw.Draw;
import Inforeg.ObjetGraph.Node;
import java.util.LinkedList;
import java.util.Stack;

/**
 *
 * @author inky19
 */
public class DFS extends Algorithm implements AlgorithmS {

    public DFS(){
        super(false);
        this.setName("DFS");
    }
    
    
    @Override
    public void process(Draw d, Node src) {
        int start = d.getG().getNodeId(src);
        LinkedList<String> order = new LinkedList<>();
        boolean[] visited = new boolean[d.getNodes().size()];
        dfsRecursive(start, visited, order, d);
        String res = "Chemin du DFS : ";
        for (String s: order){
            res += s + " | ";
        }
        d.setResultat(res);
    }
    
    private void dfsRecursive(int current, boolean[] visited, LinkedList<String> order, Draw d){
        visited[current] = true;

        int[][] adj = d.getG().getAdj();
        for (int i=0;i<adj.length;i++) {
            if (adj[current][i]>0){
                if (!visited[i]){
                    dfsRecursive(i, visited, order, d);
                }
            }
        }
        order.add(d.getNodes().get(current).getLabel());
    }
    
    

    @Override
    public void process(Draw d) {
        if (d.isAuto()){
            process(d, d.getNodes().get(0));
        } else {
            d.setSt(true);
        }
    }

    
}
