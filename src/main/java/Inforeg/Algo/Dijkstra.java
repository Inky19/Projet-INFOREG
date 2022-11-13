package Inforeg.Algo;

/*=============================================
Classe PrimMST définissant l'algorithme de PrimMST
Sous classe de la classe Processing
Auteur : Samy AMAL
Date de création : 04/02/2022
Date de dernière modification : 08/03/2022
=============================================*/
import Inforeg.Draw.Draw;
import Inforeg.Graph.Graph;
import Inforeg.ObjetGraph.MyLine;
import Inforeg.ObjetGraph.Node;
import java.awt.Color;

import javax.swing.JOptionPane;

public class Dijkstra extends Algorithm implements AlgorithmST, Processing {

    public Dijkstra(){
        super();
        this.setName("Dijkstra");
    }
    
    @Override
    public boolean process(Draw d) {
        d.setSt(true);
        JOptionPane.showMessageDialog(null, "Sélectionnez un sommet de départ et un sommet d'arrivée pour calculer le plus court chemin entre les deux s'il existe.","Dijkstra - PCC", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
    
    /**
     * Méthode appliquant l'algorithme de Dijkstra sur le graphe orienté
     * représenté par le Draw d afin de déterminer (si existence) le plus court
     * chemin entre les sommets src et dest
     *
     * @param d : Draw représentant le graphe à étudié
     * @param src : sommet de départ du parcours
     * @param dest : sommet de destination du parcours
     * @return true si il existe un chemin, false sinon
     */
    @Override
    public boolean process(Draw d, Node srcNode, Node destNode) {

        int src = d.getG().getNodeId(srcNode);
        int dest = d.getG().getNodeId(destNode);
        int[] dist;
        int[] predecesseur;

        Graph g = d.getG();
        g.updateVariable();
        
        dist = new int[g.getNbsommets()];
        // The output array. dist[i] will hold
        // the shortest distance from src to i

        predecesseur = new int[g.getNbsommets()];

        // vu[i] will true if vertex i is included in shortest
        // path tree or shortest distance from src to i is finalized
        boolean vu[] = new boolean[g.getNbsommets()];

        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < g.getNbsommets(); i++) {
            dist[i] = Integer.MAX_VALUE;
            vu[i] = false;
        }

        // Distance of source vertex from itself is always 0
        dist[src] = 0;
        // Source has no predecesseur
        predecesseur[src] = -1;

        // Find shortest path for all vertices
        for (int count = 0; count < g.getNbsommets() - 1; count++) {
            // Pick the minimum distance vertex from the set of vertices
            // not yet processed. u is always equal to src in first
            // iteration.
            int u = findMin(dist, vu, g.getNbsommets());

            // Mark the picked vertex as processed
            vu[u] = true;

            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v < g.getNbsommets(); v++) // Update dist[v] only if is not in sptSet, there is an
            // edge from u to v, and total weight of path from src to
            // v through u is smaller than current value of dist[v]
            {
                if (!vu[v] && g.getAdj()[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + g.getAdj()[u][v] < dist[v]) {
                    dist[v] = dist[u] + g.getAdj()[u][v];
                    predecesseur[v] = u;
                }
            }
        }
        int s = dest;
        int p = predecesseur[s];
        int count = 0;
        //index dist min and last dist
        while ((s != src) && (count < d.getNodes().size()) && (p != -1)) {
            MyLine l = d.findLine(p, s);
            if (l != null) {
                l.setColor(Color.RED);
                s = p;
                p = predecesseur[p];
                count++;
            } else {
                p = -1;
            }
        }
        if (s != src) {
            d.reinit();
            JOptionPane.showMessageDialog(null, "Il n'existe pas de chemin entre les sommets "
                    + d.getNodes().get(src).getLabel() + " et " + d.getNodes().get(dest).getLabel() + ".",
                    "Dijkstra - PCC", JOptionPane.INFORMATION_MESSAGE);
        } else {
            d.repaint();
            JOptionPane.showMessageDialog(null, "Il existe un plus court chemin entre les sommets "
                    + d.getNodes().get(src).getLabel() + " et " + d.getNodes().get(dest).getLabel()
                    + ", de distance " + dist[dest] + ".",
                    "Dijkstra - PCC", JOptionPane.INFORMATION_MESSAGE);
        }
        return true;
    }
}
