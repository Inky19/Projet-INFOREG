/*=============================================
Interface Connexe contenant les méthodes
permettants de vérifier si un graphe est connexe
Auteur : Béryl CASSEL
Date de création : 18/03/2022
Date de dernière modification : 18/03/2022
Commentaires ajoutés
=============================================*/

import java.util.PriorityQueue;

public interface Connexe {

    /**
     * Méthode vérifiant s'il existe un chemin entre les
     * deux sommets d'un graphe à l'aide d'un parcours
     * en largeur
     * @param g : Graphe à parcourir
     * @param u : sommet source
     * @param v : sommet de destination
     * @return
     */
    public default boolean existeChemin(Graphe g, int u, int v){
        boolean[] vu = new boolean[g.getNbsommets()];
        for (int i=0;i<g.getNbsommets();i++){
            vu[i] = false;
        }
        PriorityQueue<Integer> q = new PriorityQueue<Integer>();
        q.add(u);

        //Tant que la pile de parcours n'est pas vide
        while (!(q.isEmpty())){
            int ind = q.poll();
            vu[ind]=true;
            for (int j=0;j<g.getNbsommets();j++){

                /*Si il existe un arc entre les sommets ind et j
                et que le sommet j n'a pas encore été étudié*/
                if ((g.getAdj()[ind][j]>0) && (!vu[j])){
                    q.add(j);
                    vu[j]=true;
                }

                /*Si il existe un arc entre les sommets ind et j 
                et que le sommet j est le sommet de destination v*/
                if ((g.getAdj()[ind][j]>0) && (j==v)){
                    //On a trouvé un chemin entre u et v
                    return true;
                }
            }
        }

        /*Tous les sommets atteignables depuis u on t été rencontrés
        sans que l'on rencontre le sommet de destination v*/
        return false;
    }

    /**
     * Méthode permettant de vérifier si un graphe non orienté est connexe
     * @param g : Graphe à étudier
     * @return : true si le graphe est connexe, false sinon
     */
    public default boolean connexe(GNonOriente g){
        for (int i=0;i<g.getNbsommets();i++){
            for (int j=i+1;j<g.getNbsommets();j++){

                /*S'il n'existe pas de chemin entre les sommets
                i et j le graphe n'est pas connexe*/
                if (!existeChemin(g, i, j)){
                    return false;
                }
            }
        }

        /*Tous les couples de sommets on été parcourus et sont
        reliés par au moins un chemin, le graphe est connexe*/
        return true;
    }

    /**
     * Méthode permettant de vérifier si un graphe orienté est connexe
     * @param g : Graphe à étudier
     * @return : true si le graphe est connexe, false sinon
     */
    public default boolean connexe(GOriente g){
        for (int i=0;i<g.getNbsommets();i++){
            for (int j=0;j<g.getNbsommets();j++){

                /*S'il n'existe pas de chemin entre les sommets
                i et j le graphe n'est pas connexe*/
                if (!existeChemin(g, i, j)){
                    return false;
                }
            }
        }

        /*Tous les couples de sommets on été parcourus et sont
        reliés par au moins un chemin, le graphe est connexe*/
        return true;
    }
    
}
